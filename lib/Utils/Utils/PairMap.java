package Utils;

import java.util.Map;

public interface PairMap<K,V> extends Map<K,V> {
  K       getValueKey(V v);
}