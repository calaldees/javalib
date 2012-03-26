package Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


public class RegExHelper {
  
  public static List<String> search(String page, String regex   ) {return search(page,Pattern.compile(regex));}
  public static List<String> search(String page, Pattern pattern) {
    List<String> captured_strings = new ArrayList<String>();
    if (page==null || pattern==null) {return captured_strings;}
    try {
      Matcher matcher = pattern.matcher(page);
      while (matcher.find()) {
        //String[] match_group = new String[matcher.groupCount()];
        for (int counter=0 ; counter<matcher.groupCount() ; counter++) {
          captured_strings.add(matcher.group(counter+1));
        }
        return captured_strings;
      }
    }
    catch (Exception e) {Utils.ErrorHandeler.error(RegExHelper.class,"Failed RegEx",pattern.toString());}
    return captured_strings;
  }
  
}
