package Utils.Network.PostOfficeServer;

import Utils.Network.TCPThread.NetworkConnectionStringWithListener;
import Utils.Network.TCPThread.NetworkMessageStringListener;
import java.util.ArrayList;
import java.util.Collection;

public class PostOfficeClientConnectionCallback implements PostOfficeClientCallback,  NetworkMessageStringListener {

  private enum PostOfficeCommand {
    getdata("getdata"),
    putdata("putdata"),

    subscribe("subscribe"),
    unsubscribe("unsubscribe"),

    getkeylist("getkeylist"),
    getkeys("getkeys"),
    getkeymeta("getkeymeta"),
    
    getstatelist("getstatelist"),
    loadstate("loadstate"),
    savestate("savestate"),
    ;
    private final String command;
    PostOfficeCommand(String command) {this.command = command;}
    public String toString() {return command;}
    public boolean isCommand(String c) {return command.equals(c);}
  }

  private final NetworkConnectionStringWithListener connection;
  private final Collection<PostOfficeClientRecieveListener> listeners = new ArrayList<PostOfficeClientRecieveListener>();

  public PostOfficeClientConnectionCallback(String host, int port) throws Exception {
    connection = new NetworkConnectionStringWithListener(host, port);
    connection.addNetworkMessageStringListener(this);
  }

  public void    addPostOfficeClientRecieveListener(PostOfficeClientRecieveListener listener) {listeners.add   (listener);}
  public void removePostOfficeClientRecieveListener(PostOfficeClientRecieveListener listener) {listeners.remove(listener);}

  
  public void getData(String key)              {connection.sendMessage(PostOfficeCommand.getdata       .toString()+" "+key);}
  public void putData(String key, String data) {connection.sendMessage(PostOfficeCommand.putdata       .toString()+" "+key+" "+data);}

  public void   subscribe(String key)          {connection.sendMessage(PostOfficeCommand.subscribe     .toString()+" "+key);}
  public void unsubscribe(String key)          {connection.sendMessage(PostOfficeCommand.unsubscribe   .toString()+" "+key);}

  public void getKeyList()                     {connection.sendMessage(PostOfficeCommand.getkeylist    .toString());}
  public void getKeys(String regex)            {connection.sendMessage(PostOfficeCommand.getkeys       .toString()+" "+regex);}
  public void getKeyMetaData(String key)       {connection.sendMessage(PostOfficeCommand.getkeymeta    .toString()+" "+key);}

  public void getStateList()                   {connection.sendMessage(PostOfficeCommand.getstatelist  .toString());} //WHAT THE ****!
  public void loadState(String state_name)     {connection.sendMessage(PostOfficeCommand.loadstate     .toString()+" "+state_name);}
  public void saveState(String state_name)     {connection.sendMessage(PostOfficeCommand.savestate     .toString()+" "+state_name);}


  public void reciveMessage(String message) {
    String cmd      = PostOfficeServer.getMessageComponentCmd(message);
    String cmd_data = PostOfficeServer.getMessageComponentData(message);
    try {
      if (PostOfficeCommand.getdata.isCommand(cmd)) {
        String key      = PostOfficeServer.getMessageComponentCmd(cmd_data);
        String key_data = PostOfficeServer.getMessageComponentData(cmd_data);
        for (PostOfficeClientRecieveListener listener : listeners) {
          listener.recieveData(key,key_data);
        }
        return;
      }

      if (PostOfficeCommand.getkeys.isCommand(cmd)) {
        Collection<KeyMetaData> keys = new ArrayList<KeyMetaData>();
        if (cmd_data!=null) {
          for (String key_meta_string : cmd_data.split("\n")) {
            keys.add(KeyMetaData.parse(key_meta_string));
          }
        }
        for (PostOfficeClientRecieveListener listener : listeners) {
          listener.recieveKeys(keys);
        }
        return;
      }

      if (PostOfficeCommand.getkeylist.isCommand(cmd)) {
        Collection<String> key_list = new ArrayList<String>();
        for (String key : cmd_data.split(PostOfficeServer.COMMAND_SPLIT)) {
          key_list.add(key);
        }
        for (PostOfficeClientRecieveListener listener : listeners) {
          listener.recieveKeyList(key_list);
        }
        return;
      }

      if (PostOfficeCommand.getstatelist.isCommand(cmd)) {
        Collection<String> state_list = new ArrayList<String>();
        for (String key : cmd_data.split(PostOfficeServer.COMMAND_SPLIT)) {
          state_list.add(key);
        }
        for (PostOfficeClientRecieveListener listener : listeners) {
          listener.recieveStateList(state_list);
        }
        return;
      }
    }
    catch (Exception e) {
      System.out.println("Failed to process command "+cmd);
      e.printStackTrace();
    }

  }

}