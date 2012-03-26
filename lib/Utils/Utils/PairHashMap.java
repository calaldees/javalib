package Utils;

import java.util.HashMap;
import java.util.Map;


public class PairHashMap<K,V> extends HashMap<K,V> implements PairMap<K,V> {
  private static final long serialVersionUID = 0;

  private Map<V,K> reverse_lookup = new HashMap<V,K>();
  
  public V put(K key, V value) {
    reverse_lookup.put(value, key);
    return super.put(key, value);
  }
  
  public V remove(Object key) {
    reverse_lookup.remove(get(key));
    return super.remove(key);
  }
  
  public K getValueKey(V v) {
    return reverse_lookup.get(v);
  }

}