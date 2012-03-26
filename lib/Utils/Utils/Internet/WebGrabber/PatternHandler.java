package Utils.Internet.WebGrabber;

import java.util.List;

import Utils.Internet.NetTools;
import Utils.RegExHelper;


public abstract class PatternHandler {
    
    private String pattern;
    
    public PatternHandler(String pattern) {this.pattern = pattern;}
    
    public String getPattern() {return pattern;}

    
    public void invokePatternOnURL(String url                      ) {invokePattern(NetTools.readURL(url           ));}
    public void invokePatternOnURL(String url, String ... post_data) {invokePattern(NetTools.readURL(url, post_data));}
    public void invokePattern(String page) {
      handlePattern(RegExHelper.search(page,getPattern()));
    }
    
    public abstract void handlePattern(List<String> data);
    
}
