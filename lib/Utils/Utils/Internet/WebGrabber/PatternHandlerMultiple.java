package Utils.Internet.WebGrabber;

import java.util.Collection;
import java.util.Vector;

import Utils.Internet.NetTools;




public class PatternHandlerMultiple {

    private static Collection<PatternHandler> patterns = new Vector<PatternHandler>();
    
    public static void addPatternHandler(PatternHandler pattern_handler) {patterns.add(pattern_handler);}
    
    public static void involkeURL(String url                      ) {involkePage(NetTools.readURL(url          ));}
    public static void involkeURL(String url, String ... post_data) {involkePage(NetTools.readURL(url,post_data));}
    public static void involkePage(String page) {
      for (PatternHandler pattern_handeler : patterns) {pattern_handeler.invokePattern(page);}
    }
    
}