package Utils.Network.PostOfficeServer;

public interface PostOfficeClientCallback extends PostOfficeClient {

  public void     getData(String key);
  //public Image  getImage(String key);
  public void     getKeyList();
  public void     getKeyMetaData(String key);
  public void     getKeys(String regex);
  public void     getStateList();


  //public void recieveSubscriptions(Collection<KeyData> subscriptions);
}