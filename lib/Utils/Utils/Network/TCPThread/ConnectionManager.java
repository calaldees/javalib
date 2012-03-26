package Utils.Network.TCPThread;

//import java.net.Socket;
//import java.util.Collection;


public interface ConnectionManager {
  //public void addConnection(Socket socket);
  public void addConnection(AbstractNetworkConnection c);
  public void removeConnection(AbstractNetworkConnection c);
  //public Collection<NetworkConnection> getConnections();

  public Class getNetworkConnectionClass();

  //public void receive(SocketManager origin, Object o);
}