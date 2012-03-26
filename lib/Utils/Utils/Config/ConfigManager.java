package Utils.Config;


import java.util.Map;
import java.util.Set;
import java.util.HashMap;

import Utils.StringIndexedStrings;
import Utils.StringIndexedValues;
import Utils.XML.XMLLoad.LoadManager;
import Utils.XML.XMLLoad.DataWrapper;
import Utils.XML.XMLLoad.LoadProcessorParseOnly;



public class ConfigManager {
  
  static {new XMLConfigHandeler();}
      // {ConfigManager.addToConfig(default_path,"./");}
  
  public static final String default_path = "default_path";
 
  private static StringIndexedStrings current_config = new StringIndexedStrings();
  
  public static void initXMLHandlers() {} //by running this the static initalizers are run
  
  public static void loadConfig(String filename) {LoadManager.open(filename);}
  public static void loadConfig(String filename, String ... args) {
    loadConfig(filename);
    addToConfig(args);
  }
    
  public static void addToConfig(String key, String value  ) {current_config.add(key,value);}
  public static void addToConfig(StringIndexedValues config) {current_config.add(config);}
  public static void addToConfig(String ... args) {
    for (String argument: args) {
      String[] arg_components = argument.split("=");
      //TAKE QUOTES OUT!!!!! .. replace the first and last " out
      if (arg_components.length==2) {
        addToConfig(arg_components[0],arg_components[1]);
      }
    }
  }
  
  public static String get(String key) {return current_config.getString(key);}
  public static int    getInt(String key) {return current_config.getInt(key);}
  //more static stuff to get values from config ....
  //..
  //..
  //..
}



//--------------------------------------------------------------------
// Class XMLConfigHandler
//--------------------------------------------------------------------
class XMLConfigHandeler extends LoadProcessorParseOnly {
  public XMLConfigHandeler() {super("CONFIG");}
  public void parse(DataWrapper data) {ConfigManager.addToConfig(data);}
}