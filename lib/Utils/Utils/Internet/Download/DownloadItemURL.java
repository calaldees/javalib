package Utils.Internet.Download;

import java.io.File;
import java.util.Map;
import java.util.HashMap;
import java.net.URL;
import java.net.URLConnection;
import java.net.HttpURLConnection;

import Utils.Config.ConfigManager;
import Utils.Internet.NetTools;
import Utils.File.FileOperations;

public class DownloadItemURL implements DownloadItem {

  //-----------------------------------------------------------------------------------------------
  // Static - AutoDestination
  //-----------------------------------------------------------------------------------------------

  private static Map<String,File> destinations = new HashMap<String,File>();

  public static void addAutoDestination(String search, String path) {addAutoDestination(search, new File(path));}
  public static void addAutoDestination(String search, File   path) {destinations.put(search,path);}

  private static File findAutoDestination(URL    url) {return findAutoDestination(url.toExternalForm());}
  private static File findAutoDestination(String url) {
    for (String search : destinations.keySet()) {
      if (url.matches(search)) {return destinations.get(search);}
    }
    //Else return default directory from config
    return new File(ConfigManager.get(DownloadManager.download_path_key));
  }

  //-----------------------------------------------------------------------------------------------
  // Variables
  //-----------------------------------------------------------------------------------------------

  private URL  source;
  private File destination;

  private int filesize = Integer.MIN_VALUE;

  //-----------------------------------------------------------------------------------------------
  // Constructor
  //-----------------------------------------------------------------------------------------------

  public DownloadItemURL(URL url           ) throws Exception {this(url,null);}
  public DownloadItemURL(URL url, File file) throws Exception {
    setSource(url);
    setDestination(file);
  }
  public DownloadItemURL(String url                    ) throws Exception {this(url,null);}
  public DownloadItemURL(String url, String destination) throws Exception {
    setSource(url);
    setDestination(destination);
  }

  //-----------------------------------------------------------------------------------------------
  // Public
  //-----------------------------------------------------------------------------------------------

  public void setSource(String url) throws Exception {setSource(NetTools.stringToURL(url));}
  public void setSource(URL    url) throws Exception {
    //check file actually exisits on server
    if (!existsOnServer()) {throw new Exception("File dose not exist on server - "+url);}
    else                   {source = url;}
  }
  
  public void setDestination(String destination) throws Exception {}
  public void setDestination(File file)          throws Exception {
    destination = file;
    if (!checkDestination()) {throw new Exception("Destination implasable - "+destination);}
  }

  public void startDownload() throws DownloadFailedException {
    checkDestination();
    FileOperations.setupWriteableFile(destination,source.getFile());
    try                 {NetTools.copyURLToFile(source,destination);}
    catch (Exception e) {throw new DownloadFailedException();}
  }

  public void cancelDownload() {
    pauseDownload();
    FileOperations.deleteFile(destination);
  }

  public void pauseDownload() {
    if (isCurentlyDownloading()) {
      //close all connections
    }
  }
  public boolean isCurentlyDownloading() {return false;}
  public float   percentComplete() {return 0;}

  public String  getHostName()     {return source.getHost();}
  public int     sizeInBytes()     {
    if (filesize>0) {return filesize;}
    else {
      try {filesize = source.openConnection().getContentLength();} catch (Exception e) {}
      return filesize;
    }
  }

  public boolean existsOnServer() {
    try {
      HttpURLConnection connection = (HttpURLConnection)source.openConnection();
      int response = connection.getResponseCode();
      filesize = connection.getContentLength();
      connection.disconnect();
      if (response == HttpURLConnection.HTTP_OK) {return true;}
    }
    catch (Exception e) {}
    return false;
  }

  public String toString() {return this.getClass().getName()+" URL="+source.toString()+" DESTINATION="+destination.toString();}
  
  //-----------------------------------------------------------------------------------------------
  // Private
  //-----------------------------------------------------------------------------------------------

  private boolean checkDestination() {
    if (destination==null) {destination = findAutoDestination(source);}
    return FileOperations.isFilePossibleToCreate(destination);
  }

}







