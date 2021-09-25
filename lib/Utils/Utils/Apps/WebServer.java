package Utils.Apps;

import Utils.Network.TCPThread.ConnectionManager;
import Utils.Network.TCPThread.AbstractNetworkConnection;
import Utils.Network.TCPThread.NetworkServer;

public class WebServer implements ConnectionManager {

  public static String host_path;

  public static void main(String[] args) {
    if (args.length != 1) {System.out.println(WebServer.class.getName()+" host_path");}
    else {
      System.out.println("Miniserver: "+args[0]);
      host_path = args[0]; //.replaceAll("\\\\", "\\\\\\\\");
      new WebServer(8000);
    }
  }
  
  public WebServer(int port) {
    new NetworkServer(port, this);
  }

  public void    addConnection(AbstractNetworkConnection c) {} //System.out.println("Hello:"+c.getSocket().getInetAddress().toString());
  public void removeConnection(AbstractNetworkConnection c) {} //System.out.println("Goodbye:"+c.getSocket().getInetAddress().toString());
  public Class getNetworkConnectionClass() {return WebServerNetworkConnection.class;}

}
