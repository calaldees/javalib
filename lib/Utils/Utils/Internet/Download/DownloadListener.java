package Utils.Internet.Download;

import Utils.Internet.*;
import java.util.EventListener;

public interface DownloadListener extends EventListener {
  public void eventDownloadSleep();
  public void eventDownloadWake();
  public void eventDownloadQued(String item);
  public void eventDownloadComplete(String item);
  public void eventDownloadFailed(String item);
  public void eventDownloading(String url);
  public void eventDownloadError(Object source, String title, String message);
}
