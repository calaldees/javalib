package Utils;

import Utils.Types.Integer.Dimension3D;
import Utils.Types.Integer.Point3D;
import java.awt.Dimension;
import java.awt.Point;
import java.util.Set;


public interface StringIndexedValues {

  public boolean     containsKey(String key);
  public Set<String> keySet();
  
  //public boolean isProperty(String name, String value);
  
  public <T> T get(Class<T> c, String key);

  // this will be depricated .. well .. I hope! ... it is entirely possible to use the above get method for most cases
  public int         getInt(String key);
  public boolean     getBool(String key);  
  public Point       getPoint(String key);
  public Point3D     getPoint3D(String key);
  public Dimension   getDimension(String key);
  public Dimension3D getDimension3D(String key);
  public String      getString(String key);
  public String[]    getStrings(String key);
  //public Collection  getArray(String key)       {return Caster.castArray(       getObject(key));}
  
}
