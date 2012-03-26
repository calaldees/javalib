package Utils.Internet.Download;

import java.io.File;
import java.util.Queue;
import java.util.Collection;

import Utils.ErrorHandeler;
import Utils.Config.ConfigManager;

public class DownloadManager extends Thread {

  public static final String download_path_key = "download_path";

  //-----------------------------------------------------------------------------------------------
  // Variables
  //-----------------------------------------------------------------------------------------------

  private static enum DownloadEvent {wake,sleep,downloading,download_complete,download_failed,que,error};

  private int list_empty_sleep_time       = 30; //Sleep for 30 sec if download list empty
  private int thread_sleep_time           = 10; //Sleep for x Secs once downloaded a file
  private int max_network_errors          =  2; //Number of errors before giveing up

  private int num_network_errors          =   0; //Number of errors so far... reseted when succuessfully downloads a file

  private File default_download_path;

  private boolean download = true;

  private Queue<DownloadItem> downloads = new DownloadQueue();

  //-----------------------------------------------------------------------------------------------
  // Constructors
  //-----------------------------------------------------------------------------------------------
  
  public DownloadManager() {
    super();
    start();
  }
  
  //-----------------------------------------------------------------------------------------------
  // Thread
  //-----------------------------------------------------------------------------------------------
  
  public void run() {
    //while (download) {
      //event(DownloadEvent.wake);
      while(download && pendingDownloads()>0) {
        beginDownloadingNextFile();
      }
      //event(DownloadEvent.sleep);
      //pause(list_empty_sleep_time);
      //stopAfterCurrentDownloadsHaveCompleted();
    //}
  }

  //-----------------------------------------------------------------------------------------------
  // Public
  //-----------------------------------------------------------------------------------------------
  
  public void queue(DownloadItem item) {
    downloads.add(item);
    try {start();} catch (Exception e) {}
  }
  public int  pendingDownloads() {return downloads.size();}
  public Collection<DownloadItem> getDownloads() {return downloads;}

  public void stopAfterCurrentDownloadsHaveCompleted() {download=false;}
  public void stopAllDownloads() {
    stopAfterCurrentDownloadsHaveCompleted();
  }

  public void setDefaultDownloadPath(File   path) {ConfigManager.addToConfig(download_path_key,path.getAbsolutePath());}
  public void setDefaultDownloadPath(String path) {ConfigManager.addToConfig(download_path_key,path);}

  public void setIdleDelayWhenCompletedDownloadListIsEmprty(int delay) {list_empty_sleep_time = delay;}
  public void setPauseDelayAfterSuccessfulDownload(int delay)          {    thread_sleep_time = delay;}
  public void setMaxNumOfTollerableErrorsBeforePause(int num_errors)   {   max_network_errors = num_errors;}

  //-----------------------------------------------------------------------------------------------
  // Private
  //-----------------------------------------------------------------------------------------------
  
  private void beginDownloadingNextFile() {
    DownloadItem item = downloads.poll();
    try                 {item.startDownload();}
    catch (Exception e) {
      ErrorHandeler.error(this.getClass(), "Download Failed", item.toString());
      e.printStackTrace();
    }
  }

  private void pause(int seconds_to_sleep) {
    if (download) {
      try                 {if (seconds_to_sleep>0) {sleep(seconds_to_sleep*1000);}}
      catch (Exception e) {}
    }
  }


}
