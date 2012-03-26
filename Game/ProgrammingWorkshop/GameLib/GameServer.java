package GameLib;

import GameLib.Net.NetworkConnectionImplementation;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class GameServer {

  private static final int     network_port       = NetworkConnectionImplementation.network_port;
  public  static final int     network_sleep_time = NetworkConnectionImplementation.network_sleep_time;
  public  static       boolean show_messages      = false;
  public  static       boolean show_connections   = true;
  
  public GameServer(           ) {this("echo");}
  public GameServer(String mode) {
    if (show_connections) {System.out.println("Server Mode = "+mode+ " (show_messages="+show_messages+")");}
    ConnectionManager connection_manager = null;
    if      (mode.equalsIgnoreCase("sync")) {connection_manager = new ConnectionManagerSyncronised();}
    else if (mode.equalsIgnoreCase("echo")) {connection_manager = new ConnectionManagerEcho();}
    else                                    {System.out.println("Unknown Mode: use 'echo' or 'sync'"); System.exit(0);}
    ConnectionRequestListener connection_request_listener = new ConnectionRequestListener(connection_manager,network_port);
  }
  
  public static void main(String[] args) {
    String mode = "echo";
    for (String arg : args) {
      if (arg.startsWith("mode"))         {mode            =arg.substring(arg.indexOf("=")+1);}
      if (arg.equals("show_messages"   )) {show_messages   =true;}
      if (arg.equals("hide_connections")) {show_connections=false;}
    }
    new GameServer(mode);
  }
  
}



//---------------------------------------------------
// Connection Request
//---------------------------------------------------

class ConnectionRequestListener extends Thread {
  
  private ConnectionManager manager;
  private ServerSocket server_socket;
  private boolean      server_active = true;
  
  public ConnectionRequestListener(ConnectionManager manager, int port) {
    super();
    try {server_socket = new ServerSocket(port);} catch (Exception e) {throw new IllegalStateException();}
    this.manager = manager;
    if (GameServer.show_connections) {System.out.println("Listening for connections");}
    start();
  }
  
  public void close() {
    server_active = false;
    try {server_socket.close();} catch (Exception e) {e.printStackTrace();}
  }
  

  public void run() {
    while(server_active) {
      try                 {createNewConnection(server_socket.accept());}
      catch (Exception e) {System.err.println("Failed ServerSocket");}
    }
  }
  
  private void createNewConnection(Socket socket) {
    manager.addConnection(socket);
  }
}


//---------------------------------------------------
// Connection Manager (list of connections)
//---------------------------------------------------

interface ConnectionManager {
  public void addConnection(Socket socket);
  public void addConnection(SocketManager s);
  public void removeConnection(SocketManager s);
  public Collection<SocketManager> getConnections();

  public void receive(SocketManager origin, Object o);
}

abstract class AbstractConnectionManager implements ConnectionManager {
  private final Collection<SocketManager> connections = new ArrayList<SocketManager>();
  
  public void addConnection(Socket socket)      {addConnection(new SocketManager(socket,this));}
  public void addConnection(SocketManager s)    {
    println("New Connection : "+s.getSocket().getInetAddress().getHostAddress());
    connections.add(s);
  }
  public void removeConnection(SocketManager s) {
    println("Lost Connection: "+s.getSocket().getInetAddress().getHostAddress());
    connections.remove(s);
  }
  
  public Collection<SocketManager> getConnections() {return connections;}

  private void println(String s) {
    if (GameServer.show_connections) {System.out.println(s);}
  }
  //public void receive(SocketManager origin, Object o);
}

class ConnectionManagerEcho extends AbstractConnectionManager {

  public void receive(SocketManager origin, Object o) {
    if (GameServer.show_messages) {System.out.println(origin.getSocket().getInetAddress().getHostAddress()+"<-"+o.toString());}
    for (SocketManager sm: getConnections()) {
      //System.out.println("To:"+sm.getSocket().getInetAddress().getHostAddress()+" O:"+o);
      sm.send(o);
    }
  }
}

class ConnectionManagerSyncronised extends AbstractConnectionManager {

  private final Map<SocketManager,Object> messages = new HashMap<SocketManager,Object>();

  public void receive(SocketManager origin, Object o) {
    if (GameServer.show_messages) {System.out.println(origin.getSocket().getInetAddress().getHostAddress()+"<-"+o.toString());}
    messages.put(origin, o);
    if (messages.size()==getConnections().size()) {
      for (SocketManager sm : getConnections()) {
        for (Object message : messages.values()) {
          sm.send(message);
          if (GameServer.show_messages) {System.out.println(sm.getSocket().getInetAddress().getHostAddress()+"->"+o.toString());}
        }
      }
      messages.clear();
    }
  }
}


//---------------------------------------------------
// Socket Manager
//---------------------------------------------------

class SocketManager extends Thread {

  private Socket             socket;
  private ObjectInputStream  input;
  private ObjectOutputStream output;
  private final ConnectionManager  connection_manager;

  public SocketManager(Socket socket, ConnectionManager connection_manager) {
    super();
    try {
      this.socket  = socket;
      this.input   = new ObjectInputStream( socket.getInputStream() );
      this.output  = new ObjectOutputStream(socket.getOutputStream());
      this.connection_manager = connection_manager;
    }
    catch (Exception e) {throw new IllegalStateException("Unable to setup Socket");}
    start();
  }
 
  public void run() {
    while (socket!=null && socket.isConnected()) {
      try                 {receive(input.readObject());}
      catch (Exception e) {close();}

      try                 {sleep(GameServer.network_sleep_time);}
      catch (Exception e) {}
    }
  }

  public boolean send(Object o) {
    try                 {output.writeObject(o); return true; }
    catch (Exception e) {                       return false;}
  }

  public void receive(Object o) {
    connection_manager.receive(this, o);
  }
  
  public void close() {
    connection_manager.removeConnection(this);
    try {output.close(); output=null;} catch (Exception e) {}
    try {input.close();  input =null;} catch (Exception e) {}
    try {socket.close(); socket=null;} catch (Exception e) {}
  }

  public Socket getSocket() {return socket;}
}
