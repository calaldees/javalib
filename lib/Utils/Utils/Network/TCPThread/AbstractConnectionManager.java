package Utils.Network.TCPThread;

import java.util.ArrayList;
import java.util.Collection;

abstract class AbstractConnectionManager implements ConnectionManager {
  private final Collection<AbstractNetworkConnection> connections = new ArrayList<AbstractNetworkConnection>();
  private       boolean                       log_enabled = false;

  //public void addConnection(Socket socket)      {addConnection(new NetworkConnection(socket,this) {});}
  public void addConnection(AbstractNetworkConnection s)    {
    logMessage("New Connection : "+s.getSocket().getInetAddress().getHostAddress());
    connections.add(s);
  }
  public void removeConnection(AbstractNetworkConnection s) {
    logMessage("Lost Connection: "+s.getSocket().getInetAddress().getHostAddress());
    connections.remove(s);
  }

  public Collection<AbstractNetworkConnection> getConnections() {return connections;}

  private void logMessage(String s) {
    if (log_enabled) {System.out.println(s);}
  }
  //public void receive(SocketManager origin, Object o);
}
