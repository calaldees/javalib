package Utils.Network.TCPMultiplexServer;

public interface MultiplexServerListener {
  void newClient(ClientChannel client);
  void msgRecived(ClientChannel client, String msg);
  void closeClient(ClientChannel client);
  void closeServer();
}