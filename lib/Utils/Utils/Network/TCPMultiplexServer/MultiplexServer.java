package Utils.Network.TCPMultiplexServer;

import Utils.ErrorHandeler;
import Utils.PairHashMap;
import Utils.PairMap;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MultiplexServer implements Runnable {
  
  static final int DEFAULT_PORT_NUMBER = 9537;
  
  //private String message_terminate_connection;
  private String message_end                  = "\r";
  
  private boolean server_active = true;
  
  private Selector            selector;
  private ServerSocketChannel server_channel;
  
  private final PairMap<SelectableChannel,ClientChannel> clients   = new PairHashMap<SelectableChannel,ClientChannel>();
  private final List<MultiplexServerListener>             listeners = new Vector<MultiplexServerListener>();
  
  public MultiplexServer()                throws Exception {this(DEFAULT_PORT_NUMBER);}
  public MultiplexServer(int port_number) throws Exception {startServer(port_number);}


  private void startServer(int port_number) throws Exception {
    server_channel = ServerSocketChannel.open();
    server_channel.configureBlocking(false);
    ServerSocket server_socket = server_channel.socket();
    server_socket.bind(new InetSocketAddress(port_number));
    selector = Selector.open();
    server_channel.register(selector, SelectionKey.OP_ACCEPT);
    new Thread(this).start();

  }
  
  public void    addListener(MultiplexServerListener listener) {listeners.add   (listener);}
  public void removeListener(MultiplexServerListener listener) {listeners.remove(listener);}
  
  public void   setMessageEnd(String message_end) {this.message_end = message_end;}
  public String getMessageEnd()                   {return message_end;}
  //public void setMessageTerminateConnection() {}
  
  public void run() {
    while(server_active) {
      try {selector.select();} catch (Exception e) {ErrorHandeler.error(this.getClass(), "Error selecting channel", e);}
      for (Iterator<SelectionKey> i = selector.selectedKeys().iterator() ; i.hasNext() ; ) {
        SelectionKey key = i.next(); 
        i.remove();
        if      (key.isReadable()  ) {readFromChannel(key);} //data incomming
        else if (key.isAcceptable()) {newChannel(key);     } //new connection
        else {
System.out.println("Did not process key, please implement: " + key);
        }
      }
    }
    
    for (MultiplexServerListener listener : listeners   ) {listener.closeServer();}
    for (ClientChannel          client   : getClients()) {client.close();}
    
    try {
      selector.close();       selector=null;
      server_channel.close(); server_channel=null;
    }
    catch (Exception e) {}
  }
  
  public void stopServer() {server_active=false;}
  
  public void sendAll(String msg) {
    for (ClientChannel client : getClients()) {client.sendMessage(msg);}
  }
  
  public Collection<ClientChannel> getClients() {return clients.values();}
  
  private void newChannel(SelectionKey key) {
    try {
      SocketChannel channel = server_channel.accept();
      channel.configureBlocking(false);
      channel.register(selector, SelectionKey.OP_READ);
      ClientChannel client_channel = new ClientChannel(channel,this);
      clients.put(channel, client_channel);
      for (MultiplexServerListener listener : listeners) {listener.newClient(client_channel);}
    }
    catch (Exception e) {ErrorHandeler.error(this.getClass(), "Failed client connect", e);}
  }
  
  private void readFromChannel(SelectionKey key) {
    ClientChannel client = clients.get(key.channel());
    if (client==null) {ErrorHandeler.error(this.getClass(), "Client not registered as connected", "message recived from client that does not exisit!?"); return;}
    else {
      String msg = client.readMessage();
      if (msg!=null) {
//System.out.println("Recived: "+msg);
//client.sendMessage(msg+"\r\n");
        //if (msg.trim().equals(message_terminate_connection)) {client.close();}
        //else {
          String[] msgs = msg.split(getMessageEnd());
          for (MultiplexServerListener listener : listeners) {
            for (String m : msgs) {
              listener.msgRecived(client,m);
            }
          }
        //}
      }
    }
//System.out.println("Read");
  }
  
  void removeClient(ClientChannel client) {
    for (MultiplexServerListener listener : listeners) {listener.closeClient(client);}
    clients.remove(clients.getValueKey(client));
    try                    {client.closeChannel();}
    catch (IOException ex) {Logger.getLogger(MultiplexServer.class.getName()).log(Level.SEVERE, null, ex);}
  }
}