package Utils.Network.TCPThread;

import Utils.ErrorHandeler;
import java.net.Socket;
import java.net.ServerSocket;
import java.io.IOException;
import java.lang.reflect.Constructor;


public class NetworkServer implements Runnable {

//-------------------------------------------------------------------------
// Varaiables
//-------------------------------------------------------------------------

  //Class             network_connection_template;
  ConnectionManager connection_manager;
  ServerSocket      server_socket;

  boolean server_active = true;


//-------------------------------------------------------------------------
// Constructor
//-------------------------------------------------------------------------

  public NetworkServer(int port, ConnectionManager manager) {
    connection_manager          = manager;
    //network_connection_template = class_template;
    try {
      server_socket               = new ServerSocket(port);
      new Thread(this,"NetworkServer").start();
    }
    catch (Exception e) {
      ErrorHandeler.error(getClass(), "Unable to start server", e);
    }
  }


//-------------------------------------------------------------------------
// Public Methods
//-------------------------------------------------------------------------

  public void close() {
    server_active = false;
    try {server_socket.close();} catch (IOException e) {e.printStackTrace();}
  }


//-------------------------------------------------------------------------
// Thread
//-------------------------------------------------------------------------

  public void run() {
    while(server_active) {
      try                 {createNewConnection(server_socket.accept());}
      catch (Exception e) {System.err.println("Failed ServerSocket");}
      //try {Thread.sleep(Constant.thread_sleep_time);} catch (Exception e) {}
    }
  }


//-------------------------------------------------------------------------
// Private Methods
//-------------------------------------------------------------------------

  private AbstractNetworkConnection createNewConnection(Socket socket) {
    try {
      return (AbstractNetworkConnection)connection_manager.getNetworkConnectionClass().getConstructor( new Class[]{ConnectionManager.class,Socket.class} ).newInstance( new Object[]{connection_manager,socket} );
      //Constructor c = connection_manager.getNetworkConnectionClass().getConstructor( new Class[]{ConnectionManager.class,Socket.class} );
      //System.out.println("Constructot"+c.toString());
      //Object created = c.newInstance( new Object[]{connection_manager,socket} );
      //return (NetworkConnection)created;
    }
    catch (Exception e) {e.printStackTrace(); return null;}
  }

}
