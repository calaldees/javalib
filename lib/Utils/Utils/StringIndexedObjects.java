package Utils;

import java.awt.Dimension;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import Utils.Types.Integer.*;

public class StringIndexedObjects implements StringIndexedValuesUpdatable {
  
  Map<String,Object> index = new HashMap<String,Object>();

  public boolean containsKey(String key) {return index.containsKey(key);}
  public Set<String> keySet() {return index.keySet();}

  public <T> T       get           (Class<T> c, String key) {return Caster.cast(c,safeGet(key));}
  
  //depricated
  public String      getString     (String key) {return Caster.castString(safeGet(key));}
  public int         getInt        (String key) {return Caster.castInt(safeGet(key));}
  public boolean     getBool       (String key) {return Caster.castBool(safeGet(key));}
  public Point       getPoint      (String key) {return Caster.cast(Point.class,safeGet(key));}
  public Point3D     getPoint3D    (String key) {return Point3DImmutable.valueOf(safeGet(key));}
  public Dimension   getDimension  (String key) {return Caster.cast(Dimension.class,safeGet(key));}
  public Dimension3D getDimension3D(String key) {return Dimension3DImmutable.valueOf(safeGet(key));}

  public boolean add(String key, Object o) {
    if (o==null) {index.remove(key);}
    else         {index.put(key.toLowerCase(),o);}
    return true; //why return true? hu? .. investigate
  }
  
  
  public String[] getStrings(String key) {throw new UnsupportedOperationException("Not yet implemented");}

  private Object safeGet(String key) {
    try                 {return index.get(key.toLowerCase());}
    catch (Exception e) {return null;}
  }

}
