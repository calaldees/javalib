package Utils.Network.PostOfficeServer;

public interface PostOfficeClient {
  public void   subscribe(String key);
  public void unsubscribe(String key);

  public void putData(String key, String data);
  //public void putImage(String key, Image i);

  public void loadState(String state_name);
  public void saveState(String state_name);
}
