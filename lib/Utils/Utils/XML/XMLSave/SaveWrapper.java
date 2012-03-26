package Utils.XML.XMLSave;

import java.util.HashMap;
import java.util.Collection;
import java.util.Map;
import java.util.Vector;

class SaveWrapper {
  private final String             name;
  private final Map<String,String> fields  = new HashMap<String,String>();
  private final Collection<Object> objects = new Vector<Object>();
  
  public SaveWrapper(String name) {
    this.name   = name;
  }
  
  public String getName() {return name;}
  
  public void addField(String key, String value) {if (key!=null && value!=null) fields.put(key,value);}
  public void addObject(Object o)                {if (o!=null) objects.add(o);}
  
  public Collection<String> getFieldKeys()       {return fields.keySet();}
  public String             getField(String key) {return fields.get(key);}
  
  public Collection<Object> getObjects() {return objects;}
  
  public boolean hasFields()  {if ( fields.size()>0) return true; return false;}
  public boolean hasObjects() {if (objects.size()>0) return true; return false;}
  
}