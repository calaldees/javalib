package Utils.Network.TCPThread;

import java.net.Socket;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * An abstract class that can be extended to easly setup a network connection that can transfer Objects.
 * Objects that are being transfered over a network must be "Serializable".
 *
 * When creating a new NetoworkConnection you can provide an optional ConnectionManager. This is optional and can be used to pass messages between multiple connections
 *
 * @see ConnectionManager
 * @see java.io.Serializable
 * @author Allan Callaghan
 */

public abstract class AbstractNetworkConnection implements Runnable {

  static final int  port_default      = 9756;
  
//-------------------------------------------------------------------------
// Variables
//-------------------------------------------------------------------------

  private Socket             socket;  // The socket for this NetworkConnection
  private InputStream        input;   // Each socket is a 2 way means of communication so we need - Input
  private OutputStream       output;  //                                                          - Output
  private ConnectionManager  connection_manager; // Optional - This is an interface to let anther object keep track of all the connections being made
  private long               thread_sleep_time =  100;


//-------------------------------------------------------------------------
// Constructors
//-------------------------------------------------------------------------

  public AbstractNetworkConnection(String host, int port                           ) throws Exception {this(null   , new Socket(host, port) );}
  //public NetworkConnection(Socket socket                                   ) throws Exception {this(null   , socket);                 }
  //public NetworkConnection(ConnectionManager manager, String host          ) throws Exception {this(manager, new Socket(host, port_default)); }
  //public NetworkConnection(ConnectionManager manager, String host, int port) throws Exception {this(manager, new Socket(host, port)); }
  /**
   * Creates a connection
   *
   * @param manager an object tracking this connection (can be null)
   * @param socket  an already setup/connected socket
   */
  public AbstractNetworkConnection(ConnectionManager manager, Socket socket        ) throws Exception {
    if (socket==null) {throw new Exception("Failed to setup NetworkConnection - null socket recived");}
    this.socket             = socket;
    this.connection_manager = manager;
    output = socket.getOutputStream();
    input  = socket.getInputStream();
    new Thread(this).start();
    if (connection_manager!=null) {connection_manager.addConnection(this);}
    connectHandeler();
  }


//-------------------------------------------------------------------------
// Public Methods
//-------------------------------------------------------------------------

  /** 
   * Closes this connection perminatly and informs the @see ConnectionManager
   */
  public void close() {
    disconnectHandeler();
    if (connection_manager!=null) {connection_manager.removeConnection(this);}
    try {output.close(); output=null;} catch (IOException e) {}//e.printStackTrace();}
    try {input.close();  input =null;} catch (IOException e) {}//e.printStackTrace();}
    try {socket.close();             } catch (IOException e) {}//e.printStackTrace();}
  }


  public Socket getSocket() {return socket;}


  // Optional Methods that can be overridden

  /** 
   * Override this method to customise behaviou after the connection is setup
   * eg. tell the server who or what you are
   */
  protected void    connectHandeler() {}
  protected void disconnectHandeler() {}
  abstract protected void checkInputStreamForData();

  //to be used in the extending class's constructor to setup the steam required
  protected InputStream   getInputStream() {return input; }
  protected OutputStream getOutputStream() {return output;}
  
  // intended to be overridden and have it's return typed changed to enable a customer connection manager to communicate will all clients
  protected ConnectionManager getConnectionManager() {return connection_manager;}
  
//-------------------------------------------------------------------------
// Private Methods
//-------------------------------------------------------------------------

  private long getSleepTime() {return thread_sleep_time;}

//-------------------------------------------------------------------------
// Runnable Interface Implementation
//-------------------------------------------------------------------------
  
  public void run() {
    while (getSocket().isConnected()) {
      try                 {checkInputStreamForData();}
      catch (Exception e) {close();                  }
      try                 {Thread.sleep(getSleepTime());}
      catch (Exception e) {}
    }
  }

  
}