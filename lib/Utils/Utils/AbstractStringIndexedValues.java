package Utils;

import Utils.Types.Integer.*;

import java.awt.Point;
import java.awt.Dimension;
import java.util.Set;


public abstract class AbstractStringIndexedValues implements StringIndexedValues {

  public abstract String getString(String key);
  public abstract Set<String> keySet();
  
  public boolean isProperty(String name, String value) {
    try {
      if (getString(name).equals(value)) {return true;}
    }
    catch (Exception e) {}
    return false;
  }
  
  public int         getInt(String key)         {return Caster.castInt(              getString(key) );}
  public boolean     getBool(String key)        {return Caster.castBool(             getString(key) );}
  public Point       getPoint(String key)       {return Caster.castPoint(            getString(key) );}
  public Point3D     getPoint3D(String key)     {return Point3DImmutable.valueOf(    getString(key) );}
  public Dimension   getDimension(String key)   {return Caster.castDimension(        getString(key) );}
  public Dimension3D getDimension3D(String key) {return Dimension3DImmutable.valueOf(getString(key) );}
  public String[]    getStrings(String key)     {return Caster.castStrings(          getString(key) );}
  //public Collection  getArray(String key)       {return Caster.castArray(       getObject(key));}
  
}
