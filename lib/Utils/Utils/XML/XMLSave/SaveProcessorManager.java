package Utils.XML.XMLSave;

import java.util.HashMap;
import java.util.Map;

import Utils.ErrorHandeler;


public class SaveProcessorManager {
  
  private static final Map<Class,SaveProcessor> save_processors_indexed_by_class = new HashMap<Class,SaveProcessor>();
  
  private SaveProcessorManager() {}
  
  public static void addProcessor(SaveProcessor processor) {
    save_processors_indexed_by_class.put(processor.getClassType(),processor);
  }
  
  public static boolean isSaveableClass(Class c) {return save_processors_indexed_by_class.containsKey(c);}
  
  public static void save(SaveHandeler saver, Object o) {
    if (o    ==null) {throw new IllegalArgumentException();}
    if (saver==null) {throw new IllegalArgumentException();}
    SaveProcessor s = getProcessor(o.getClass());
    if (s==null) {ErrorHandeler.error("Unable to Save","There is no processor avalabe to save objects of class "+o.getClass());}
    else         {s.save(saver,o);}
  }
  
  private static SaveProcessor getProcessor(Class c) {
    if (save_processors_indexed_by_class.containsKey(c)) {return save_processors_indexed_by_class.get(c);}
    else {
      for (Class index_class : save_processors_indexed_by_class.keySet()) {
        if (index_class.isAssignableFrom(c)) {return getProcessor(index_class);}
      }
    }
    return null;
  }
}