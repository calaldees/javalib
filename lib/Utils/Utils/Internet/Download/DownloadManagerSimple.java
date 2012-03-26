package Utils.Internet.Download;

import Utils.Internet.*;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.util.LinkedList;
import Utils.File.LogManager;
import Utils.File.FileOperations;

import Utils.Internet.Download.DownloadListener;


public class DownloadManagerSimple extends Thread {

  //-----------------------------------------------------------------------------------------------
  // Variables
  //-----------------------------------------------------------------------------------------------
  private static enum DownloadEvent {wake,sleep,downloading,download_complete,download_failed,que,error};
  
  private int list_empty_sleep_time       = 180; //Sleep for 3 Mins if download list empty
  private int thread_sleep_time           =  10; //Sleep for x Secs once downloaded a file
  private int max_network_errors          =   2; //Number of errors before giveing up
  
  private int num_network_errors          =   0; //Number of errors so far... reseted when succuessfully downloads a file
  
  private LinkedList<String> queue = new LinkedList<String>();
  private boolean download = true;
  
  private String download_list_filename;
  private String auto_filename_host;
  private String path;
  
  private LinkedList<DownloadListener> listeners = new LinkedList<DownloadListener>();
  
  //-----------------------------------------------------------------------------------------------
  // Constructors
  //-----------------------------------------------------------------------------------------------
  
  public DownloadManagerSimple(String path) {
    super();
    this.path = path;
  }
  
  public DownloadManagerSimple(String path, String download_list_filename) {
    super();
    this.path                   = path;
    this.download_list_filename = download_list_filename;
    if (download_list_filename!=null) {start();}
  }

  //-----------------------------------------------------------------------------------------------
  // Thread
  //-----------------------------------------------------------------------------------------------
  
  public void run() {
    while (download) {
      event(DownloadEvent.wake);
      String download_string;
      while(download && (download_string=getNextFile())!=null) {
        download(download_string);
      }
      event(DownloadEvent.sleep);
      pause(list_empty_sleep_time);
    }
  }

  //-----------------------------------------------------------------------------------------------
  // Public
  //-----------------------------------------------------------------------------------------------
  
  public              void queue(String url                 ) {queue(url,null);}
  public synchronized void queue(String url, String filename) {
    event(DownloadEvent.que, "URL="+url+" File="+filename);
    String item = url;
    if (filename!=null) {item = item+" "+filename;}
    LogManager.log(download_list_filename, item);
    queue.offer(item);
  }
  
  public void download(String download_string) {
    String url      = download_string.substring(0                             , download_string.indexOf(" "));
    String filename = download_string.substring(download_string.indexOf(" ")+1, download_string.length()    );
    if (filename==null || filename.length()<3) {filename = null;}
    download(url,filename);
  }
  public void download(String url, String filename) {
    if (filename==null) {filename = url.replaceFirst(auto_filename_host,"");}
    filename = FileOperations.cleanFilename(filename);
    //reportStatus("Dnld Att: URL="+url+" File="+filename);
    event(DownloadEvent.downloading, url);
    try {
      NetTools.copyURLToFile(url ,path + filename);    //this could throw an exception and exit this block
      event(DownloadEvent.download_complete, filename);
      num_network_errors = 0;
      pause(thread_sleep_time);
    }
    catch (Exception e) {
      num_network_errors++;
      if (num_network_errors>=max_network_errors) {
        event(DownloadEvent.error, "Download Errors > "+max_network_errors+" -> Finishing download thread");
        finish();
      }
    }
  }
  
  public void finish()                     {download=false;}
  public void setDownloadPath(String path) {this.path = path;}
  public void setSleepTime(int sleep_time) {thread_sleep_time = sleep_time;}
  public void setAutoFilenameHost(String auto_filename_host) {this.auto_filename_host = auto_filename_host;}

  //-----------------------------------------------------------------------------------------------
  // Public
  //-----------------------------------------------------------------------------------------------
  
  private synchronized String getNextFile() {
    if (queue.size()==0) {
      try {
        LineNumberReader reader = new LineNumberReader( new FileReader(download_list_filename) );
        String line;
        while ((line=reader.readLine()) != null) {
          queue.offer(line);
        }
        reader.close();
        if (!FileOperations.renameFile(download_list_filename, download_list_filename+".backup", true)) {event(DownloadEvent.error, "Backup File Failed, could not backup "+download_list_filename);}
      }
      catch (Exception e) {event(DownloadEvent.download_failed, download_list_filename);}
    }
    return queue.poll();
  }


  
  private void pause(int sleep_time) {
    if (download) {
      try                 {if (sleep_time>0) {sleep(sleep_time*1000);}}
      catch (Exception e) {}    
    }
  }

  //-----------------------------------------------------------------------------------------------
  // Listener Events
  //-----------------------------------------------------------------------------------------------
  
  public void addDownloadListener(DownloadListener listener)    {listeners.add(listener);}
  public void removeDownloadListener(DownloadListener listener) {listeners.remove(listener);}
  
  private void event(DownloadEvent event             ) {event(event,null);}
  private void event(DownloadEvent event, String info) {
    for (DownloadListener listener : listeners) {
       switch (event) {
         case wake:
           listener.eventDownloadWake();
           break;
         case sleep:
           listener.eventDownloadSleep();
           break;
         case downloading:
           listener.eventDownloading(info);
           break;
         case download_complete:
           listener.eventDownloadComplete(info);
           break;
         case download_failed:
           listener.eventDownloadFailed(info);
           break;
         case que:
           listener.eventDownloadQued(info);
           break;
         case error:
           listener.eventDownloadError(this, "Download Error", info);
           break;
       }
    }
  }
 
}
