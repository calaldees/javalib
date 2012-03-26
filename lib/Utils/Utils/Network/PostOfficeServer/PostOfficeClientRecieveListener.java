package Utils.Network.PostOfficeServer;

import java.util.Collection;

public interface PostOfficeClientRecieveListener {

  public void recieveData(String key, String data);
  public void recieveKeyList(Collection<String> key_list);
  public void recieveKeyMetaData(KeyMetaData keydata);
  public void recieveKeys(Collection<KeyMetaData> keys);
  public void recieveStateList(Collection<String> states);

}
