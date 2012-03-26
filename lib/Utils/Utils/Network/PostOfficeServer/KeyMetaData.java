package Utils.Network.PostOfficeServer;

public class KeyMetaData {

  private static final String key_meta_split = " ";
  
  private final String         key;
  //private final Class          type;
  private final KeyMetaHistory created;
  private       KeyMetaHistory modifyed;
  private       KeyMetaHistory accessed;
  
  public static KeyMetaData parse(String key_meta_string) {
    try {
      String[] s = key_meta_string.split(key_meta_split);
      String         key      =                      s[0];
      KeyMetaHistory created  = KeyMetaHistory.parse(s[1]);
      KeyMetaHistory modifyed = null;
      KeyMetaHistory accessed = null;
      if (s.length>2) {modifyed = KeyMetaHistory.parse(s[2]);}
      if (s.length>3) {accessed = KeyMetaHistory.parse(s[3]);}
      return new KeyMetaData(key, created, modifyed, accessed);
    }
    catch (Exception e) {throw new IllegalArgumentException("Unable to parse key: "+key_meta_string);}
  }
  private KeyMetaData(String key, KeyMetaHistory created, KeyMetaHistory modifyed, KeyMetaHistory accessed) {
    if (key==null) {throw new IllegalArgumentException("key name cannot be null");}
    if (key==null) {throw new IllegalArgumentException("created timestamp is null");}
    this.key      = key;
    this.created  = created;
    this.modifyed = modifyed;
    this.accessed = accessed;
  }

  public KeyMetaData(String key) {
    this.key = key;
    //type = null;
    created  = new KeyMetaHistory();
  }
  
  public String         getKey()             {return key;}
  //public Class          getType()            {return type;}
  public KeyMetaHistory getHistoryCreated()  {return created;}
  public KeyMetaHistory getHistoryModifyed() {return modifyed;}
  public KeyMetaHistory getHistoryAccessed() {return accessed;}
  
  void setAccessed() {accessed = new KeyMetaHistory();}
  void setModifyed() {modifyed = new KeyMetaHistory();}
  
  public String toString() {
    StringBuilder key_meta = new StringBuilder();
    key_meta.append(key);
    key_meta.append(key_meta_split);
    key_meta.append(created.toString());
    key_meta.append(key_meta_split);
    if (modifyed!=null) {key_meta.append(modifyed.toString());}
    key_meta.append(key_meta_split);
    if (accessed!=null) {key_meta.append(accessed.toString());}
    return key_meta.toString();
  }
}