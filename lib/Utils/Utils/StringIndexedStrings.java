package Utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class StringIndexedStrings extends AbstractStringIndexedValues implements StringIndexedValuesUpdatable {
  
  private final Map<String,String> index = new HashMap<String,String>();

  public boolean containsKey(String key) {return index.containsKey(key);}
  
  public String      getString(String key) {return index.get(key.toLowerCase());}
  public Set<String> keySet()              {return index.keySet();}
  
  public void add(String key, String value) {index.put(key.toLowerCase(),value);}
  public void add(StringIndexedValues index) {
    for (String key: index.keySet()) {
      add(key,index.getString(key));
    }
  }

  public void add(String key, int i) {add(key,""+i);}

  //This should see if it's a stand and convert it if not ... finsih it bitch!
  public boolean add(String key, Object o) {throw new UnsupportedOperationException("Not yet implemented");}


  public <T> T get(Class<T> c, String key) {
    Object o = index.get(key);
    return Caster.cast(c,o);
  }


}
