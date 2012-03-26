package Utils.XML.XMLLoad;

//import Utils.PairLookup;

import org.xml.sax.Locator;
import java.util.Collection;
import java.util.Map;
import java.util.HashMap;


class LoadProcessorManager {

  private LoadProcessorManager() {}
  
//-------------------------------------------------------------------------
// Variables
//-------------------------------------------------------------------------

  private static final Map<String, LoadProcessor> processors_indexed_by_tag   = new HashMap<String,LoadProcessor>();
  private static final Map<Class , LoadProcessor> processors_indexed_by_class = new HashMap<Class ,LoadProcessor>();
  //private static PairLookup<Class,String>  pair_lookup                 = new PairLookup<Class,String>();

//-------------------------------------------------------------------------
// Static
//-------------------------------------------------------------------------

  public static Collection<String> getRegisteredTags() {
    try                 {return processors_indexed_by_tag.keySet();}
    catch (Exception e) {return null;}
  }

  //Get by class (typesafe & prefered method)
  public static <T> Map<String,T> getGroup(Class<T> class_type) {
    try                 {return getProcessor(class_type).getProcessed();}
    catch (Exception e) {return null;}
  }

  public static <T> T getItem(Class<T> class_type, String name) {
    try                 {return getGroup(class_type).get(name);}
    catch (Exception e) {return null;}
  }

  //Get by String
  @SuppressWarnings("unchecked")
  public static Map<String,Object> getGroup(String tag) {
    try                 {return getProcessor(tag.toUpperCase()).getProcessed();}
    catch (Exception e) {return null;}
  }
  
  public static Object getItem(String tag, String name) {
    try                 {return getGroup(tag.toUpperCase()).get(name);}
    catch (Exception e) {return null;}
  }

//-------------------------------------------------------------------------
// DataProcessor (constructor)
//-------------------------------------------------------------------------

  public static void addProcessor(Class class_type, String tag, LoadProcessor processor) {
    processors_indexed_by_tag.put(tag.toUpperCase(),processor);
    processors_indexed_by_class.put(class_type,processor);
    //pair_lookup.addPair(class_type,tag);
  }

  //public static void putItem(String group, String name, Object o) {
  //  try                 {getGroup(group).put(name, o);}
  //  catch (Exception e) {}
  //}

  //public static Class  tag2class(String tag       ) {Utils.Error.error(DataProcessorManager.class,"Unfinished Program","Implement PairLookup"); try{throw new Exception();} catch (Exception e) {e.printStackTrace();} return null;} //return pair_lookup.lookupB(tag);       }
  //public static String class2tag(Class  class_type) {Utils.Error.error(DataProcessorManager.class,"Unfinished Program","Implement PairLookup"); try{throw new Exception();} catch (Exception e) {e.printStackTrace();} return null;} //return pair_lookup.lookupA(class_type);}//


//-------------------------------------------------------------------------
// XMLHandler Methods
//-------------------------------------------------------------------------

  public static boolean isRegisteredTag(String tag) {
    if (processors_indexed_by_tag.containsKey(tag.toUpperCase())) {return true; }
    else                                                          {return false;}
  }

  public static boolean isRegisteredClass(Class c) {
    if (processors_indexed_by_class.containsKey(c)) {return true; }
    else                                            {return false;}
  }
  
  public static Object process(String tag, DataWrapper data, Locator locator) {
    tag = tag.toUpperCase();
    try                 {return getProcessor(tag).process(data, locator);}
    catch (Exception e) {Utils.ErrorHandeler.error(tag, e, locator); return null;}
  }

  public static boolean catalogObjectsOfThisType(String tag) {
    try                 {return getProcessor(tag.toUpperCase()).catalogObjectsOfThisType();}
    catch (Exception e) {return false;}
  }

  public static Class  getClassFromTag(String tag) {return getProcessor(tag.toUpperCase()).getCatalogClassType();}
  public static String getTagFromClass(Class  c  ) {return getProcessor(c                ).getTagName();}
  

//-------------------------------------------------------------------------
// Private
//-------------------------------------------------------------------------

  @SuppressWarnings("unchecked")
  private static <T> LoadProcessor<T> getProcessor(Class<T> class_type) {
    try                 {return processors_indexed_by_class.get(class_type);}
    catch (Exception e) {return null;}
  }

  private static LoadProcessor getProcessor(String tag) {
    try                 {return processors_indexed_by_tag.get(tag.toUpperCase());}
    catch (Exception e) {return null;}
  }



}
