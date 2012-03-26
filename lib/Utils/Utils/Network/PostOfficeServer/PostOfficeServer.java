package Utils.Network.PostOfficeServer;

import Utils.Network.TCPMultiplexServer.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class PostOfficeServer implements MultiplexServerListener {
  
  public static void main(String[] args) {try {new PostOfficeServer();} catch (Exception e) {}}

  static final String COMMAND_SPLIT = " ";
  private static final Collection<ClientChannel> NULL_CLIENTS = new ArrayList<ClientChannel>();
  //static final String DEBUG_CLIENT_NEWLINE = "\r";
  
  private final MultiplexServer server;
  private final Map<String,KeyData                        > map                   = new HashMap<String,KeyData                        >();
  private final Map<String,PostOfficeServerCommandHandeler> commands              = new HashMap<String,PostOfficeServerCommandHandeler>();
  private final Collection<ClientChannel>                   subscribe_all_clients = new ArrayList<ClientChannel>();
  private final Map<String,Collection<ClientChannel>>       subscribers           = new HashMap<String,Collection<ClientChannel>>();
  
  void registerCommandHandeler(PostOfficeServerCommandHandeler command) {commands.put(command.getCommandName(), command);}

  void    addSubscribeAllClient(ClientChannel client) {
    removeSubscriber(client); //Remove subscriptions to existing keys
    if (!subscribe_all_clients.contains(client)) { //We dont want clients adding themselfs more than once
      subscribe_all_clients.add(client);
    }
  }
  void removeSubscribeAllClient(ClientChannel client) {
    removeSubscriber(client);
    subscribe_all_clients.remove(client);
  }
  void addSubscriber(String key, ClientChannel client) {
    if (subscribers.containsKey(key)) {
      Collection<ClientChannel> subscriber_list = subscribers.get(key);
      if (!subscriber_list.contains(key)) { //We dont want clients adding themself more than once
        subscriber_list.add(client);
      }
    }
    else {
      Collection<ClientChannel> subscribers_for_key = new ArrayList<ClientChannel>();
      subscribers_for_key.add(client);
      subscribers.put(key, subscribers_for_key);
    }
  }
  void removeSubscriber(String key, ClientChannel client) {
    if (!subscribers.containsKey(key)) {return;}
    Collection<ClientChannel> subscribers_for_key = subscribers.get(key);
    subscribers_for_key.remove(client);
    if (subscribers_for_key.size()==0) {subscribers.remove(key);}
  }
  Collection<ClientChannel> getSubscribers(String key) {
    if (subscribers.containsKey(key)) {return subscribers.get(key);}
    else                              {return NULL_CLIENTS;}
  }
  private void removeSubscriber(ClientChannel client) {
    for (Collection<ClientChannel> s : subscribers.values()) {s.remove(client);}
  }

  private static String current_username;
  static String getCurrentUser()                {return current_username;}
  static void   setCurrentUser(String username) {current_username = username;}
  
  public PostOfficeServer() throws Exception {
    server = new MultiplexServer();
    server.addListener(this);
    new CmdGetData(this);
    new CmdPutData(this);
    new CmdGetKeyList(this);
    new CmdGetKeys(this);
    new CmdExit(this);
    new CmdHelp(this);
    new CmdSubscribe(this);
    new CmdUnSubscribe(this);
    new CmdSubscribeAll(this);
    new CmdUnSubscribeAll(this);
    putData("Test1","1");
    putData("Test2","2");
    putData("Test3","3");
    putData("Badger","THEY ARE EVIL!!!");
    putData("Monkey","Clever");
  }

  private boolean containsData(String key) {return map.containsKey(key);}
  KeyData getData(String key) {
    if (key!=null && map.containsKey(key)) {
      return map.get(key);
    }
    return null;
  }
  void putData(String key, String data) {
    if (containsData(key)) {getData(key).setData(data);}         //Get the existing key and add data
    else                   {map.put(key,new KeyData(key,data));} //Create a new key
    for (ClientChannel client : getSubscribers(key)  ) {sendGetDataToClient(client,key);}
    for (ClientChannel client : subscribe_all_clients) {sendGetDataToClient(client,key);}
    /*
    //Update subscribers remove any old subscribers
    for (Iterator<ClientChannel> i = subscribed.iterator() ; i.hasNext() ;) {
      ClientChannel client = i.next();
      if (client.sendMessage(CmdGetData.cmd_string+PostOfficeServer.COMMAND_SPLIT+meta.getKey()+PostOfficeServer.COMMAND_SPLIT+data)) {}
      else {i.remove();}
    }


 */
  }
  void sendGetDataToClient(ClientChannel client, String key) {
    KeyData key_data = getData(key);
    String      data = "";
    if (key_data!=null) {data = PostOfficeServer.COMMAND_SPLIT+key_data.getData();}
    client.sendMessage(CmdGetData.cmd_string+PostOfficeServer.COMMAND_SPLIT+key+data);
  }

  
  Collection<String> getCommands() {return commands.keySet();}
  
  Collection<KeyMetaData> getKeys(String regex) {
    if (regex!=null && regex.trim().length()==0) {regex=null;}
    Collection<KeyMetaData> keys = new ArrayList<KeyMetaData>();
    for (KeyData key_data : map.values()) {
      if (regex==null || key_data.getMeta().getKey().contains(regex)) {
        keys.add(key_data.getMeta());
      }
    }
    return keys;
  }
  
  public void msgRecived(ClientChannel client, String msg) {
//System.out.println("MSG:"+msg);
    setCurrentUser(client.getChannelRemoteAddress());
    String cmd = getMessageComponentCmd(msg);
    if (commands.containsKey(cmd)) {
      executeCommand(cmd,client,getMessageComponentData(msg));
//client.sendMessage(DEBUG_CLIENT_NEWLINE);
    }
    else {
      client.sendMessage("Unknown Command");
    }
  }
  private void executeCommand(String cmd, ClientChannel client, String data) {
      try                 {commands.get(cmd).process(client, data);}
      catch (Exception e) {System.out.println("Unable to process command: "+cmd+" "+data);}
  }
  
  public void newClient(ClientChannel client)   {
    client.sendMessage("PostOfficeSever test - type help for list of commands");
    //System.out.println("New:"+client.getChannelRemoteAddress());
  }
  public void closeClient(ClientChannel client) {
    client.sendMessage("Goodbye");
    removeSubscribeAllClient(client);
    //System.out.println("Close"+client.getChannelRemoteAddress());
  }
  public void closeServer() {} //save state

  static String getMessageComponentCmd(String msg) {
    if (msg==null) {return null;}
    int index = Utils.MathHelper.MinValueNotNegative(msg.indexOf(" "),msg.indexOf("\n"));
    if (index<0 || index==Integer.MAX_VALUE) {index=msg.length();}
    return msg.substring(0, index);
  }
  static String getMessageComponentData(String msg) {
    if (msg==null) {return null;}
    int index = Utils.MathHelper.MinValueNotNegative(msg.indexOf(" "),msg.indexOf("\n"));
    if (index<0 || index==Integer.MAX_VALUE) {return null;}
    return msg.substring(index+1, msg.length());
  }

}

//------------------------------------------------------------------------------
//  Command Class's
//------------------------------------------------------------------------------

class CmdGetData extends PostOfficeServerCommandHandeler {
  public static final String cmd_string = "getdata";
  public CmdGetData(PostOfficeServer server) {super(cmd_string,server);}
  public void process(ClientChannel client, String msg) {
    server.sendGetDataToClient(client, msg);
  }
}

class CmdPutData extends PostOfficeServerCommandHandeler {
  public CmdPutData(PostOfficeServer server) {super("putdata",server);}
  public void process(ClientChannel client, String msg) {
    String key  = PostOfficeServer.getMessageComponentCmd (msg);
    String data = PostOfficeServer.getMessageComponentData(msg);
    server.putData(key,data);
  }
}

class CmdGetKeyList extends PostOfficeServerCommandHandeler {
  public CmdGetKeyList(PostOfficeServer server) {super("getkeylist",server);}
  public void process(ClientChannel client, String msg) {
    client.bufferMessage(getCommandName() + PostOfficeServer.COMMAND_SPLIT);
    for (KeyMetaData key_meta_data : server.getKeys(msg)) {
      client.bufferMessage(key_meta_data.getKey() + PostOfficeServer.COMMAND_SPLIT);
    }
    client.bufferSend();
  }
}

class CmdGetKeys extends PostOfficeServerCommandHandeler {
  public CmdGetKeys(PostOfficeServer server) {super("getkeys",server);}
  public void process(ClientChannel client, String msg) {
    client.bufferMessage(getCommandName()+"\n");
    for (KeyMetaData meta : server.getKeys(msg)) {
      client.bufferMessage(meta.toString()+"\n");
    }
    client.bufferSend();
  }
}

class CmdExit extends PostOfficeServerCommandHandeler {
  public CmdExit(PostOfficeServer server) {super("exit",server);}
  public void process(ClientChannel client, String msg) {
    client.close();
  }
}

class CmdHelp extends PostOfficeServerCommandHandeler {
  public CmdHelp(PostOfficeServer server) {super("help",server);}
  public void process(ClientChannel client, String msg) {
    client.bufferMessage(getCommandName() + PostOfficeServer.COMMAND_SPLIT);
    for (String command : server.getCommands()) {
      client.bufferMessage(command + PostOfficeServer.COMMAND_SPLIT);
    }
    client.bufferSend();
  }
}

class CmdSubscribe extends PostOfficeServerCommandHandeler {
  public CmdSubscribe(PostOfficeServer server) {super("subscribe",server);}
  public void process(ClientChannel client, String msg) {
    server.addSubscriber(msg, client);
  }
}

class CmdUnSubscribe extends PostOfficeServerCommandHandeler {
  public CmdUnSubscribe(PostOfficeServer server) {super("unsubscribe",server);}
  public void process(ClientChannel client, String msg) {
    server.removeSubscriber(msg,client);
  }
}

class CmdSubscribeAll extends PostOfficeServerCommandHandeler {
  public CmdSubscribeAll(PostOfficeServer server) {super("subscribeall",server);}
  public void process(ClientChannel client, String msg) {
    server.addSubscribeAllClient(client);
  }
}

class CmdUnSubscribeAll extends PostOfficeServerCommandHandeler {
  public CmdUnSubscribeAll(PostOfficeServer server) {super("unsubscribeall",server);}
  public void process(ClientChannel client, String msg) {
    server.removeSubscribeAllClient(client);
  }
}