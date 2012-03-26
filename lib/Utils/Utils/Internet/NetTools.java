package Utils.Internet;

import java.io.File;
import java.io.InputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.net.HttpURLConnection;

import com.myjavatools.web.ClientHttpRequest;

import Utils.File.FileOperations;
import Utils.ErrorHandeler;
import Utils.File.StreamManager;

public class NetTools {

  private static int ERROR_RETRYS = 2;

  public static void setProxy(String proxy_host_name, int port) {
    if (proxy_host_name!=null) {
      System.setProperty( "http.proxyHost", proxy_host_name );
      System.setProperty( "http.proxyPort", Integer.toString(port));
      System.setProperty( "proxySet", "true" );
    }
  }

  public static URL stringToURL(String url_string) {
    URL url = null;
    try {
      url = new URL(url_string);
    } catch (Exception e) {e.printStackTrace();}
    return url;
  }

  public  static String readURL(String        url_string  ) {
    try                 {return readURL(stringToURL(url_string));}
    catch (Exception e) {ErrorHandeler.error("Error reading URL","page="+url_string,e);}
    return null;
  }
  private static String readURL(URL           url         ) throws IOException {return readURL(url.openConnection());}
  private static String readURL(URLConnection connection  ) throws IOException {return readURL(connection.getInputStream());}
  private static String readURL(InputStream   input_stream) {
    for (int retry=0 ; retry<=ERROR_RETRYS ; retry++) {
      try                 {return StreamManager.inputStreamToString(input_stream);} 
      catch (Exception e) {retryError(null);}
    }
    return null;
  }

  public static String readURL(String url_string, String ... post_data) {
    try {
      ClientHttpRequest http_connection = new ClientHttpRequest(url_string);
      for (String post_item : post_data) {
        String name = post_item.split("=")[0];
        String data = post_item.split("=")[1];
        http_connection.setParameter(name,data);
      }
      return readURL(http_connection.post());
    }
    catch (Exception e) {ErrorHandeler.error("Error reading URL","page="+url_string+" post="+post_data,e);}
    return null;
  }

  public static long getLastUpdateDate(String url) {return getLastUpdateDate(stringToURL(url));}
  public static long getLastUpdateDate(URL    url) {
    try {
      URLConnection connection = url.openConnection();
      return connection.getLastModified();
    }
    catch (Exception e) {}
    return 0;
  }
  
  public static boolean imageExistsOnServer(String url) {return imageExistsOnServer(stringToURL(url));}
  public static boolean imageExistsOnServer(URL    url) {
    if (url == null) {return false;}
    for (int retry=0 ; retry<=ERROR_RETRYS ; retry++) {
      try {
        // Setup Connection and pretend to be a browser
        HttpURLConnection http = (HttpURLConnection)url.openConnection();
        http.setRequestMethod("HEAD");
        http.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.01; Windows NT 5.0)");
        //http.setRequestProperty("Referer", host); Need to extract the host name from the URL
        System.out.println("Unfinished Method");
        http.setInstanceFollowRedirects(false);
        http.connect();

        int response_code = http.getResponseCode();

        http.disconnect();

        if (response_code >= 200 && response_code <= 299) {return true;}  //System.out.println("Server Has it!"); 
        else                                              {return false;} //System.out.println("Server no have image code "+response_code);
      }
      catch (Exception e) {}
      retryError(url);
    }
    return false;
  }



  public static void copyURLToFile(String source_url, String filename) throws Exception {copyURLToFile(stringToURL(source_url), FileOperations.createNewFile(filename));}
  public static void copyURLToFile(String source_url, File local_file) throws Exception {copyURLToFile(stringToURL(source_url), local_file);}
  public static void copyURLToFile(URL    source_url, File local_file) throws Exception {
    if (source_url==null || local_file==null) {throw new Exception("Source or Destinating file error when attempting to copy");}
    for (int retry=0 ; retry<=ERROR_RETRYS ; retry++) {
      try {
        // Do awesomeness with incomplete files - support resuming!!! YEAH!!! .. that would be awesome!
        StreamManager.inputStreamToFile(source_url.openStream(), local_file);
        return;
      }
      catch (Exception e) {retryError(source_url);}
      
    }
    ErrorHandeler.error("Failed to download file", source_url+" to file "+local_file);
    local_file.delete();
    throw new Exception("Failed to download File");
  }


  private static void retryError(URL url) {
    if (url!=null) {
      ErrorHandeler.error("Failed URL - Retrying", url.toString());
    }
    pause(5);
  }

  private static void pause(int seconds) {
    //synchronized (this) {
    //  try { wait(seconds * 1000); } catch (InterruptedException e) {Error.error("Failed Pausing",e);}
    //}
  }

}
