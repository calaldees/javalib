package Utils.Network.PostOfficeServer;

import java.awt.Image;
import java.util.Collection;

public interface PostOfficeClientBlocking extends PostOfficeClient {

  public Collection<KeyData> getSubscriptions();
  
  public String getData(String key);
  public Image  getImage(String key);

  public Collection<String>      getKeyList();
  public            KeyMetaData  getKeyMetaData(String key);
  public Collection<KeyMetaData> getKeys(String regex);
  
  public Collection<String> getStateList();
}