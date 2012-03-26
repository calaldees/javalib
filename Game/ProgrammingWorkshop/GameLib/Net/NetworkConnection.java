package GameLib.Net;

public interface NetworkConnection {

  public boolean networkConnect(String host_address);
  public void    networkDisconnect();
  
  //public void    networkReceive(String s);
  public void    networkReceive(Object o);
  public boolean networkSend(Object o);
  
  public boolean networkIsConnected();
}
