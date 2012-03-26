package Utils.Internet.Download;

import java.io.File;
import java.util.ArrayDeque;

class DownloadQueue extends ArrayDeque<DownloadItem> {
  
  private static final long serialVersionUID = 0;

  //----------------------------------------------------------------------------
  // Variables
  //----------------------------------------------------------------------------
  
  private File queue_file;
  
  
  //----------------------------------------------------------------------------
  // Constructor
  //----------------------------------------------------------------------------
  
  public DownloadQueue(         ) {super();}
  public DownloadQueue(File file) {
    this();
    setQueueFile(file);
  }
  
  //----------------------------------------------------------------------------
  // Public
  //----------------------------------------------------------------------------
  
  public void setQueueFile(File file) {
    save();
    queue_file = file;
    load();
  }
  
  //----------------------------------------------------------------------------
  // Overrides
  //----------------------------------------------------------------------------
  public boolean      offer(DownloadItem item) {return add(item);}
  public boolean      add(DownloadItem item)   {
    update();
    try                 {return super.add(item);}
    catch (Exception e) {return false;}
  }

  public DownloadItem remove() {return poll();}
  public DownloadItem poll()   {
    update(); 
    try                 {return super.poll();}
    catch (Exception e) {return null;}
  }
  
  
  //----------------------------------------------------------------------------
  // Private
  //----------------------------------------------------------------------------
  
  private void update() {} //System.out.println("Update to DownloadQueue");}
  private void save()   {System.out.println("Save");}
  private void load()   {System.out.println("Load");}
}
