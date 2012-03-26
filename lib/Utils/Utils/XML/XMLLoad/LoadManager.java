package Utils.XML.XMLLoad;


import java.io.InputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URI;
import java.util.Iterator;
import java.util.Stack;
import java.util.Map;
import java.util.Collection;
import java.util.Vector;
import java.util.zip.GZIPInputStream;

import Utils.ErrorHandeler;
import Utils.XML.XMLLoad.LoadEventProgress;
import Utils.XML.XMLLoad.LoadEventListener;
import Utils.File.FileOperations;



/**
 To Do:-
   getting by tag "strings" needs to go .. these need to become typesafe ... that means remodeling DataProcessor and DataProcessorManager
   Make sure only one file is loaded at a time. setup a que (threads)
   Fire Load Events
   Auto Locate
*/




public class LoadManager {

  static {
    new Utils.ImageLoader.XMLImageTagHandeler();
    new Utils.Sound.XMLSoundHandeler();
    Utils.Config.ConfigManager.initXMLHandlers();
    new XMLFindTagHandeler();
  }

//-------------------------------------------------------------------------
// Enforce Non-Instability
//-------------------------------------------------------------------------
  private LoadManager() {}
  
//-------------------------------------------------------------------------
// Public Static
//-------------------------------------------------------------------------

  public static boolean isRegisteredClass(Class c) {return LoadProcessorManager.isRegisteredClass(c);}
  public static String  getTagFromClass(Class c)   {return LoadProcessorManager.getTagFromClass(c);}
  
  public static Collection<String> getRegisteredTags() {return LoadProcessorManager.getRegisteredTags();}

  public static <T> Map<String,T>      getGroup(Class<T> class_type) {return LoadProcessorManager.getGroup(class_type);}
  public static     Map<String,Object> getGroup(String tag         ) {return LoadProcessorManager.getGroup(tag  );}

  public static <T> Collection<T> getGroupList(Class<T>  class_type) {try {return getGroup(class_type).values();} catch (Exception e) {return null;}}
  //public static     Collection    getGroupList(String tag       ) {try {return getGroup(tag       ).values();} catch (Exception e) {return null;}}

  // just an example of how to do the type-saafeness! = public static <T> T getObjectsOfType(Class<T> object_type, String name) {return null;}
  
  public  static <T>  T getItem(Class<T>  class_type, String name) {return LoadProcessorManager.getItem(class_type,name);}
  public  static Object getItem(String group        , String name) {return LoadProcessorManager.getItem(     group,name);}
  // AUTO LOCATE REQUIREIMPLEMENTING FOR GETITEM!!!! SOON!!!
  /**
  //
  private static Object getItem(Object item      , String name) {
    // - Check Memory (to see if it's already loaded)
    if (item!=null) {return item;}

    // - Else Try and locate it elsewhere
    //open( autoLocateFile(group, name) );
    //return DataProcessorManager.getItem(group, name);
    
    // THIS IS A ***ING MESS AND IT'S BROKEN!! .. ***ER!!! ... 
    return null;
  }
  */


  public static DataWrapper open(URI         uri     ) {try {return XMLParser.parse(uri.toURL().openStream());} catch (Exception e) {return null;}}
  public static DataWrapper open(String      filename) {try {return open(new File(filename));                 } catch (Exception e) {return null;}}
  public static DataWrapper open(File        file    ) {
    if (file==null) {return null;}
    //eventLoadStarted();

    // Load Individual File
    if (file.isFile()) {
      
      String ext = FileOperations.getExtension(file).toLowerCase();
      InputStream input = null;
      try {
        if      (ext.equals(Constant.XML_EXTENSION)           ) {return XMLParser.parse(file);}
        else if (ext.equals(Constant.XML_EXTENSION_COMPRESSED)) {return open( new GZIPInputStream(new FileInputStream(file)) );}
        else                                                    {ErrorHandeler.error("Unable to open file", "Unable to load file of type "+ext);}
      }
      catch (Exception e) {
        ErrorHandeler.error("Error Loading File", "internal error",e);
        return null;
      }
    }

    // Load All files in Group
    else if (file.isDirectory()) {
      DataWrapper data = null;
      for (File file_to_load : FileOperations.getFileList(file, Constant.DATA_EXTENSIONS )) {
//fireLoadEvent(FilenameOperations.getFilenameWithoutExtension(file_to_load), i, file_list.length);
        DataWrapper data_new = open(file_to_load);
        if (data_new!=null) {
          if   (data==null) {data     = data_new ;}
          else              {data.merge(data_new);}
        }
      }
      return data;
//fireLoadEvent("Loading "+file.getName()+" Complete", file_list.length, file_list.length);
    }

    //eventLoadComplete();
// BUG IMMINANT!!! LOOK HERE!!!!
// BUG !!!
// WARNING!! .. ONLY fire compete events when ALL load threads have stoped ... a listener may attempt to refresh there list when NEW data is being read in, this would result in a concurent modfication exception
//
    return null;
  }
  public static DataWrapper open(InputStream input   ) {
    if (input==null) {throw new IllegalArgumentException();}
    return XMLParser.parse(input);
  }


  public static File autoLocateFile(File   dir  , String name) {return autoLocateFile(dir.toString(), name);}
  public static File autoLocateFile(String group, String name) {
    if (group==null || name==null) {return null;}
    try {group = java.net.URLDecoder.decode(group,"UTF-8");} catch (Exception e) {}
    //To Locate the required file we could look
    // - in default local directory for that group (/Unit/ or /Equipment/)
    // - Look on the server
    // - Look for an online resource .. somewhere ... this could get tricky


    // here we have a list of where we are going to look for the image file
    // in future we could maybe add extra online resorces or maybe even a server search
    Stack<File> stack = new Stack<File>();

    try {stack.push( new File(name)                          );} catch (Exception e) {}
    try {stack.push( new File(group + File.separator + name) );} catch (Exception e) {}

    while (!stack.empty()) {
      File file = stack.pop();
      if (file.canRead()) {return file;}
    }

    Utils.ErrorHandeler.error("Failed to locate Resorce", "Unable to locate "+group+"/"+name);

    return null;
  }




//-------------------------------------------------------------------------
// Public Static - Debug
//-------------------------------------------------------------------------

  public static void activeTags() {
    System.out.println("Tags:");
    for (String tag_name : getRegisteredTags()) {
      System.out.println(tag_name);
    }
  }

  public static void listLoaded() {
    System.out.println("--Loaded Objects--");
    for (String tag_name : getRegisteredTags()) {
      System.out.println(tag_name);
      for (String item_name : getGroup(tag_name).keySet()) {
        try {
          System.out.print("  "+item_name+" = ");
          Object o = LoadProcessorManager.getItem(tag_name, item_name);
          if (o!=null) {System.out.println(o.toString());}
          else         {System.out.println("null");      }
        }
        catch (Exception e) {}
      }
    }
  }

//-------------------------------------------------------------------------
// Load Listener
//-------------------------------------------------------------------------

  private static Collection<LoadEventListener> load_event_listeners = new Vector<LoadEventListener>();

  public static boolean    addLoadEventListener(LoadEventListener listener) {return load_event_listeners.add(listener);}
  public static boolean removeLoadEventListener(LoadEventListener listener) {return load_event_listeners.remove(listener);}

  private static void eventLoadProgressUpdate(String name, int index, int length) {
    for (LoadEventListener listener : load_event_listeners) {
      listener.eventLoadProgress(new LoadEventProgress(new Object(), name, index, length));
    }
  }
  private static void eventLoadStarted() {
    for (LoadEventListener listener : load_event_listeners) {
      listener.eventLoadStarted();
    }
  }
  private static void eventLoadComplete() {
    for (LoadEventListener listener : load_event_listeners) {
      listener.eventLoadComplete();
    }
  }



}
