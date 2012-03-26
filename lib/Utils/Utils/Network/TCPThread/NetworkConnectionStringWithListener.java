package Utils.Network.TCPThread;

import java.util.ArrayList;
import java.util.Collection;

public class NetworkConnectionStringWithListener extends AbstractNetworkConnectionString {

  Collection<NetworkMessageStringListener> listeners = new ArrayList<NetworkMessageStringListener>();

  public NetworkConnectionStringWithListener(String host, int port) throws Exception {
    super(host,port);
  }

  public void    addNetworkMessageStringListener(NetworkMessageStringListener listener) {listeners.add(listener);}
  public void removeNetworkMessageStringListener(NetworkMessageStringListener listener) {listeners.remove(listener);}

  public void reciveMessage(String message) {
    for (NetworkMessageStringListener listener : listeners) {
      listener.reciveMessage(message);
    }
  }
}