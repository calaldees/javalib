package Utils.Network.PostOfficeServer;

import java.util.Date;
import java.util.List;

public class KeyMetaHistory {

  private final long   timestamp;
  private final String who;

  public static KeyMetaHistory parse(String data) {
    List<String> split = Utils.RegExHelper.search(data, "\\[(.*?),(.*?)\\]");
    if (split.size() == 2) {
      return new KeyMetaHistory(Long.parseLong(split.get(0)),split.get(1));
    }
    throw new IllegalArgumentException("unable to parse "+KeyMetaHistory.class.getName()+" with "+data);
  }
  private KeyMetaHistory(long timestamp, String who) {
    this.timestamp = timestamp;
    this.who       = who;
  }

  public KeyMetaHistory() {
    this((new Date(System.currentTimeMillis())).getTime(),PostOfficeServer.getCurrentUser());
  }

  public long   getTimestamp() {return timestamp;}
  public String getWho()       {return who;}

  public String toString() {
    return "["+getTimestamp()+","+getWho()+"]";
  }
}
