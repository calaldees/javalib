package GameLib.Net;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


//--------------------------------------------------------------------------------------
// Network Recive Thread
//--------------------------------------------------------------------------------------

public class NetworkConnectionImplementation {

  public  static final int network_port            = 9367;
  public  static final int network_sleep_time      = 0;
  
  private Socket             network_socket;
  private ObjectOutputStream network_output;
  private ObjectInputStream  network_input;

  private final NetworkConnection network_listener;
  
  public NetworkConnectionImplementation(NetworkConnection network_listener) {
    this.network_listener = network_listener;
  }
  
  //---------------------------------------------------------------------------
  // Overideable Methods
  //---------------------------------------------------------------------------
  
  //public void networkReceive(String s) {network_listener.networkReceive(s);}
  public void networkReceive(Object o) {
    if (o==null) {return;}
    //if (o instanceof String) {networkReceive((String)o           );}
    //else                     {networkReceive(        o.toString());}
    network_listener.networkReceive(o);
  }

  
  //---------------------------------------------------------------------------
  // Public Methods
  //---------------------------------------------------------------------------
  
  public boolean networkConnect()                    {return networkConnect(null);}
  public boolean networkConnect(String host_address) {
    if (host_address==null) {return networkConnect("localhost");}  //throw new IllegalArgumentException("networkConnect given no host_address to connect to");
    try {
      network_socket = new Socket(host_address,network_port);
      network_output = new ObjectOutputStream(network_socket.getOutputStream());
      network_input  = new ObjectInputStream( network_socket.getInputStream() );
      new ReciveThread(network_input, this, network_sleep_time);
      //network_listener.networkConnecting();
    }
    catch (Exception e) {}
    return networkIsConnected();
  }
  public void networkDisconnect() {
    try {network_output.flush(); network_output.close(); network_output=null;} catch (Exception e) {}//e.printStackTrace();}
    try {                        network_input.close();  network_input =null;} catch (Exception e) {}//e.printStackTrace();}
    try {                        network_socket.close(); network_socket=null;} catch (Exception e) {}//e.printStackTrace();}
    //network_listener.networkDisconnecting();
  }
  
  public boolean networkSend(Object o) {
    try                 {network_output.writeObject(o); return true; } 
    catch (Exception e) {                               return false;}
  }
  
  public boolean networkIsConnected() {
    if (network_socket==null) {return false;}
    else                      {return network_socket.isConnected();}
  }
  
}
class ReciveThread extends Thread {

  private final ObjectInputStream input;
  private final NetworkConnectionImplementation connection;
  private final int               sleep_time;

  public ReciveThread(ObjectInputStream input, NetworkConnectionImplementation connection, int sleep_time) {
    super();
    this.input      = input;
    this.connection = connection;
    this.sleep_time = sleep_time;
    start();
  }

  public void run() {
    while (connection.networkIsConnected()) {
      try                 {connection.networkReceive(input.readObject());}
      catch (Exception e) {connection.networkDisconnect();}
      try                 {if (sleep_time>0) {sleep(sleep_time);}}
      catch (Exception e) {}
    }
  }
}

