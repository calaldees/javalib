package Utils.Network.PostOfficeServer;

public class KeyData {

  private final KeyMetaData meta;
  private       String      data;
  
  public KeyData(String key, String data) {
    this.meta = new KeyMetaData(key);
    setData(data);
  }
  
  public void setData(String data) {
    getMeta().setModifyed();
    this.data = data;
  }

  public String getData() {
    getMeta().setAccessed();
    return data;
  }
  public KeyMetaData getMeta() {return meta;}

}