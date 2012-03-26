package Utils.XML.XMLLoad;

import java.util.Map;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Vector;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import Utils.Caster;
import Utils.AbstractStringIndexedValues;

public class DataWrapper extends AbstractStringIndexedValues {


//-------------------------------------------------------------------------
// Static
//-------------------------------------------------------------------------

  private static String   link_key     = Constant.XML_LINK_KEY;
  private static String[] primary_keys = Constant.XML_PRIMARY_KEYS;


//-------------------------------------------------------------------------
// Variables
//-------------------------------------------------------------------------

  private Map<String,Object> map  = new LinkedHashMap<String,Object>();
  //private List<Object>       list = new Vector<Object>();
  private String             name;
  private DataWrapper        parent;

//-------------------------------------------------------------------------
// Constructor
//-------------------------------------------------------------------------

  public DataWrapper(DataWrapper parent) {
    this.parent = parent;
  }

/*
  public DataWrapper(DataWrapper parent, String name) {
    this.parent = parent;
    this.name   = name;
    linkWithParent();
  }


  protected DataWrapper(DataWrapper parent, String sub_name) throws Exception {

  }

  public DataWrapper(Map<String,Object> map) {
    this.map  = map;
    createName(null);
  }

  public DataWrapper(Map<String,Object> map, String parent_name) {
    this.map  = map;
    createName(parent_name);
  }
  
  
  public DataWrapper(DataWrapper data, String sub_name) throws Exception {
    try {
      Map<String,Object> sub = (Map<String,Object>)data.getObject(sub_name);
      list = (List)sub.remove(Constant.XML_LIST_KEY);
    }
    catch (Exception e) {throw new Exception("SHIT!!!");} //failed to aquire child map: "+sub_name+" from parent: "+data
  }


  private void createName(String parent_name) {
    this.parent_name = parent_name;
    name = parent_name;
    String my_name = getPrimaryKeyValue(map);
    if (my_name!=null) {name = my_name;}
  }
   */

//-------------------------------------------------------------------------
// Public Methods
//-------------------------------------------------------------------------

  public String getName()       {
    //if (name==null) {linkWithParent();}
    return name;
  }

  public String      getParentName() {return parent.getName();}
  public DataWrapper getParent()     {return parent;}
  
  public void destroy() {
    map.clear();
    getParent().remove(this);
  }
  void remove(DataWrapper d) {
    map.remove(d.getName());
  }
  //public void remove(String name) {map.remove(name);}
  
  // Trivial pass-through
  public boolean            containsKey(String key) {return map.containsKey(key);}
  public int                size()          {return map.size();}
  public Collection<Object> values()        {return map.values();}
  public Iterator<Object>   valueIterator() {return map.values().iterator();}
  public Set<String>        keySet()        {return map.keySet();}
  public Iterator<String>   keyIterator()   {return map.keySet().iterator();}
  
  public void put(String key, Object value)                    {put(key,value,false);}
  public void put(String key, Object value, boolean overwrite) {
    if (value!=null) {
      if (key!=null) {
        key = key.toLowerCase();
        // If value is a data wrapper containing a single element, stip the datawrapper and just add the single element to the current map by replacing the curren DataWrapper value with the single element
        if (isDataWrapper(value)) {
          Object new_value = Caster.castData(value).getSingle();
          if (new_value!=null) {value = new_value;}
        }
        if (overwrite || !containsKey(key)) {map.put(key,value);}
        else { //Add it to an array of items with this name
          try                 {(Caster.castCollection(get(key))).add(value);}
          catch (Exception e) {
            Collection<Object> c = new Vector<Object>();
            c.add(get(key));
            c.add(value);
            map.put(key,c);
          }
        }
      }
      //try {addSubValue(value);} catch (Exception e) {} //The sub list is used to remeber the order items were added to the parent
      //getParent().
    }
  }
  
  
/*
  protected void addSubValue(Object value) {
    Class c = value.getClass();
    if (!DataWrapper.class.isAssignableFrom(c) &&
        !     String.class.isAssignableFrom(c) &&
        !        Map.class.isAssignableFrom(c)   ) {list.add(value);}
  }
*/
  
  public Object get(String key) {
    try                 {return map.get(key.toLowerCase());}
    catch (Exception e) {return null;}
  }

  public String getString(String key) {
    try                 {return (String)get(key);}
    catch (Exception e) {return getSingle(key);}
  }

  public DataWrapper getSub(String key) {
    try                 {return (DataWrapper)get(key);}
    catch (Exception e) {return null;}
  }
  
  public Collection<DataWrapper> getSubs() {
    Collection<DataWrapper> subs = new Vector<DataWrapper>();
    for (String key : keySet()) {
      DataWrapper d = getSub(key);
      if (d!=null) {subs.add(d);}
    }
    return subs;
  }

  
  public void replaceThisDataWrapperWithProcessedObject(Object o) {
    //finaliseAndMergeIntoParent(tag_name);
    try                 {getParent().put(name,o,true);} //May not have a parent hence try/catch
    catch (Exception e) {}
  }
  
  //At the end of the element we check to see if it has a primary key and link it to it's parent
  public void finaliseAndMergeIntoParent()                {finaliseAndMergeIntoParent(null);}
  public void finaliseAndMergeIntoParent(String tag_name) {
    if (name==null) {name = getPrimaryKey();}
    if (name==null) {name = tag_name;       }
    if (parent!=null) {parent.put(name,this);}
  }
  
  // Link Assistance
  public boolean isSingle() {
    if      (size()==1                         ) {return true;}
    else if (size()==2 && getPrimaryKey()!=null) {return true;}
    else                                         {return false;}
  }

  public boolean isSingleLink() {
    if (isSingle() && map.containsKey(link_key)) {return true; }
    else                                         {return false;}
  }
  public boolean hasLink()     {if (getLinkName()!=null) {return true;} return false;}
  public String  getLinkName() {return getString(link_key);}


  // Sub Get


  // Safe String Get
  public String getSingle() {return getSingle(this);}


  // Object Type
  public boolean isObject(String key) {
    if (!map.containsKey(key)) {return false;}
    Object o = get(key);
    if (o instanceof String     ) {return false;}
    if (o instanceof DataWrapper) {return false;}
    if (o instanceof Collection ) {return false;}
    return true;
  }
  


  @SuppressWarnings("unchecked")
  public <T> Collection<T> getObjectsOfType(Class<T> object_type) {
    Collection<T> objects = new Vector<T>();
    for (Object o : values()) {
      if (isAssignableFrom(o,object_type)) {objects.add((T)o);}
    }
    return objects;
  }

  private boolean isAssignableFrom(Object o, Class c) {return c.isInstance(o);}

  // On some occations we have a sub map with a single element. 
  // On these occations we dont give a flying monkey what that element is named, we just want it's value
  // "getSingle" does just that;
  // called automaticaly from "get()"


  public void merge(DataWrapper data_to_merge) {
    throw new UnsupportedOperationException("Finish me!");
  }
  
//-------------------------------------------------------------------------
// Private Methods
//-------------------------------------------------------------------------


  public void remove(String key) {map.remove(key);}

  private String getPrimaryKey()                                  {return getPrimaryKey(false);}
  private String getPrimaryKey(boolean remove_found_key_from_map) {
    if (map==null) {return null;}
    for (int i=0 ; i<primary_keys.length ; i++) {
      String key = primary_keys[i];
      if (containsKey(key)) {
        String key_value = getString(key);
        if (key_value!=null) {
          if (remove_found_key_from_map) {remove(key);}
          return key_value;
        }
      }
    }
    return null;
  }
  private boolean isPrimaryKey(String key) {
    for (int i=0 ; i<primary_keys.length ; i++) {
      if (key.equals(primary_keys[i])) {return true;}
    }
    return false;
  }

  private String getSingle(String key      ) {return getSingle(getSub(key));}
  private String getSingle(DataWrapper data) {
    try {
      if (data.isSingle()) {
        for (Iterator iterator=data.keyIterator() ; iterator.hasNext() ; ) {
          String key = (String)iterator.next();
          if (!isPrimaryKey(key)) {return getString(key);}
        }
      }
    }
    catch (Exception e) {}
    return null;
  }

  private static boolean isDataWrapper(Object o) {
    if (DataWrapper.class.isAssignableFrom(o.getClass())) {return true;}
    return false;
  }
  
  
//-------------------------------------------------------------------------
// Protected Methods
//-------------------------------------------------------------------------

  
  
  
//-------------------------------------------------------------------------
// Debugging
//-------------------------------------------------------------------------

  public String toString() {
    return "DataWrapper: toString() - Implement me!!";
  }
  
  public void displayData() {
    System.out.println("-Displaying "+getName()+"-");
    displayData(this, "");
  }

  //public  static void displayData(DataWrapper data                ) {displayMap(data., "");}
  
  private static void displayData(DataWrapper data, String indentation) {
    for (String key: data.keySet()) {
      System.out.print(indentation);
      System.out.print(key + " = ");
      Object value = data.get(key);
      if      (value == null)      {System.out.println("UN-DEFINED!");}
      else if (Caster.castString(value)!=null) {System.out.println(Caster.castString(value));}
      else if (Caster.castCollection(value) !=null) {
        System.out.println();
        displayCollection(Caster.castCollection(value), indentation.concat("  "));
      }
      else if (Caster.castData(value)   !=null) {
        System.out.println();
        displayData(Caster.castData(value), indentation.concat("  "));
      }
      else {System.out.println(value.getClass().getName() + " = " + value.toString());}
    }
  }
    
  private static void displayCollection(Collection<Object> c, String indentation) {
    for (Object o: c) {
      if      (Caster.castData(o) !=null) {displayData(Caster.castData(o), indentation.concat("  "));}
      else if (o!=null)                   {System.out.println(indentation+o.toString());}
      else                                {System.out.println(indentation+"NULL");}
    }
  }

  
  
  
    public <T> T get(Class<T> c, String key) {
      throw new UnsupportedOperationException();
    }


}
