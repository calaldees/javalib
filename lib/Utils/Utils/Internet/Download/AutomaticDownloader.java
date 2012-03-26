package Utils.Internet.Download;

import java.net.URL;
import java.util.List;

import Utils.RegExHelper;
import Utils.Internet.NetTools;


public class AutomaticDownloader {
  
  private static final String[] item_finder_templates = {
    "(?i)<a href=\"?(.*?)[\" ]?.*?>.*?item_to_find.*?</a>",
    "(?i)<a href=\"?(.*?item_to_find.*?)[\" ]?.*?>"
  };
  
  public static DownloadItem findFileToDownload(String page, String item_to_find) {
    if (page==null || item_to_find==null) {return null;}

    String[] item_finders = getItemFinderRegEx(item_to_find);
    List<String> search_result;
    for (String item_to_search_pattern : item_finders) {
      search_result = RegExHelper.search(page, item_to_search_pattern);
      if (search_result.size()>0) {
        // This isnt the whole story!!!
        // We may need to transverse several pages looking for this file
        // Using URLConnection + contentType() we can find if it's a bin or html and repeat the process if its html
        String file_url = search_result.get(0);
        try {
          // below we recurse pages to find throws actual file .. 
          //Some may need to submit forms ... this could be interesting
          if (contentTypeIsHTML(file_url)) {return findFileToDownload(NetTools.readURL(file_url),item_to_find);}
          else                             {return new DownloadItemURL(file_url);}
        }
        catch (Exception e) {}
      }
    }
    
    //there may be pages that require you to accept licesnces or choose a download server, this will need thinking about
    // DO THE CODE HERE ... NOW!! ... 
    return null;
  }
  
  
  private static boolean contentTypeIsHTML(String url) throws Exception {
    String content_type = (new URL(url)).openConnection().getContentType();
System.out.println("TYPE="+content_type);
    if (content_type.toLowerCase().equals("text/html")) {return true;}
    else                                                {return false;}
  }
  
  private static String[] getItemFinderRegEx(String item_to_find) {
    String[] item_finders = new String[item_finder_templates.length];
    for (int i = 0; i < item_finders.length; i++) {
      item_finders[i]=item_finder_templates[i].replaceAll("item_to_find",item_to_find);
    }
    return item_finders;
  }
  
}