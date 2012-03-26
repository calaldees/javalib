package Utils.XML.XMLLoad;

import java.util.EventListener;


public interface LoadEventListener extends EventListener {

  public void eventLoadProgress(LoadEventProgress e);
  public void eventLoadStarted();
  public void eventLoadComplete();

}
