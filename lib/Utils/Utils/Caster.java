package Utils;

import Utils.Types.Integer.*;
import Utils.XML.XMLLoad.DataWrapper;

import java.awt.Point;
import java.awt.Dimension;
//import Utils.Point3D;
//import Utils.Dimension3D;

//import java.lang.Integer;
import java.util.ArrayList;
import java.util.Map;
import java.util.Collection;
import java.util.Vector;

@SuppressWarnings("unchecked")
public class Caster {

  private static String split_regex = Utils.Constant.STRING_SPLIT_REGEX;
  
  //-----------------------------------------------------------------------
  // Utils
  //-----------------------------------------------------------------------
  
  public static boolean castableTo(Class c, Object o) {
    try {
      if (o.getClass().isAssignableFrom(c)) {return true;}
    } catch (Exception e) {}
    return false;
  }

  private static <T> T castPimative(Class<T> c, Object o) {
    //throw new UnsupportedOperationException();
  //}
    
    try {
      if (o.getClass()==c) {return (T)o;}
      String s = null;
      if (o instanceof String) {s=(String)o;}
      else                     {s=o.toString();}
      /*
      if      (c.isAssignableFrom(boolean.class)) {return (T)castBool(s);  }
      else if (c.isAssignableFrom(byte.class)   ) {return (T)castByte(s);  }
      else if (c.isAssignableFrom(short.class)  ) {return (T)castShort(s); }
      else if (c.isAssignableFrom(int.class)    ) {return (T)castInt(s);   }
      else if (c.isAssignableFrom(long.class)   ) {return (T)castLong(s);  }
      else if (c.isAssignableFrom(float.class)  ) {return (T)castFloat(s); }
      else if (c.isAssignableFrom(double.class) ) {return (T)castDouble(s);}
      //else {throw new Exception("Unknown Primative Type");}
       */
      if      (c.isAssignableFrom(boolean.class)) {return (T)Boolean.valueOf(castBool(s));  }
      else if (c.isAssignableFrom(byte.class)   ) {return (T)Byte.decode(s); }
      else if (c.isAssignableFrom(short.class)  ) {return (T)Short.decode(s);}
      else if (c.isAssignableFrom(int.class)    ) {return (T)Integer.decode(s);}
      else if (c.isAssignableFrom(long.class)   ) {return (T)Long.decode(s);  }
      else if (c.isAssignableFrom(float.class)  ) {return (T)parseFloat(s); }
      else if (c.isAssignableFrom(double.class) ) {return (T)parseDouble(s);}
      else {throw new Exception("Unknown Primative Type");}
    }
    catch (Exception e) {return null;}
  }
  
  public static <T> T cast(Class<T> c, Object o) {
    try {
      if (c.isPrimitive())            {castPimative(c,o);}
      if (castableTo(String.class,o)) {return cast(c, (String)o);}
      else                            {return (T)o;}
    }
    catch (Exception e) {}
    return null;
  }
  public static <T> T cast(Class<T> c, String s) throws Exception {
    if      (s==null)         {throw new Exception("Null string passed to cast method");}
    else if (c.isInstance(s)) {return (T)s;}
    else if (c.isPrimitive()) {return castPimative(c,s);}
    else if (c.isArray()) {
      Class    array_class = c.getComponentType();
      String[] values      = s.split(split_regex);
      /*
      T array = new T[values.length];
      for (int i=0; i<values.length ; i++) {
        array[i] = cast(array_class, values[i]);
      }
      return array;
      */
      //System.out.println("Do something here you mo fo!");
      throw new UnsupportedOperationException();
    }

    else if (c.isAssignableFrom(java.awt.Point.class)          ) {return (T)castPoint(s);                   }
    else if (c.isAssignableFrom(Point3D.class)                 ) {return (T)Point3DImmutable.valueOf(s);    }
    else if (c.isAssignableFrom(java.awt.Dimension.class)      ) {return (T)castDimension(s);               }
    else if (c.isAssignableFrom(Dimension3D.class)             ) {return (T)Dimension3DImmutable.valueOf(s);}
    throw new Exception("Unable to Cast value="+s+" to "+c.getName());
  }
  
  //-----------------------------------------------------------------------
  // Primative String Cast
  //-----------------------------------------------------------------------

  public static byte   castByte(String s)   {try {return Byte.parseByte(s);}     catch (Exception e) {return 0;}}
  public static short  castShort(String s)  {try {return Short.parseShort(s);}   catch (Exception e) {return 0;}}
  public static long   castLong(String s)   {try {return Long.parseLong(s);}     catch (Exception e) {return 0;}}
  public static float  castFloat(String s)  {try {return parseFloat(s);}         catch (Exception e) {return 0;}}
  public static double castDouble(String s) {try {return parseDouble(s);}        catch (Exception e) {return 0;}}
  public static int    castInt(String s)    {try {return Integer.parseInt(s);}   catch (Exception e) {return 0;}}
  public static int    castIntSafe(String s){try {return Integer.parseInt(s);}   catch (Exception e) {return Integer.MIN_VALUE;}}

  //-----------------------------------------------------------------------
  // Primative Object Cast
  //-----------------------------------------------------------------------
  public static int castInt(Object o) {
    if      (o==null)              {return 0;}
    else if (o instanceof String ) {return castInt((String)o);}
    else if (o instanceof Integer) {return ((Integer)o).intValue();}
    ErrorHandeler.error("Cast Error", "Utils.Caster cant cast Integer because of unknown object type");
    return 0;
  }
  
  public static boolean castBool(Object o) {
    if (o==null) {return false;}
    else if (o instanceof String ) {return castBool((String)o);}
    else if (o instanceof Boolean) {return ((Boolean)o).booleanValue();}
    ErrorHandeler.error("Cast Error", "Utils.Caster cant cast Boolean because of unknown object type");    
    return false;
  }
  
  //-----------------------------------------------------------------------
  // ENum Cast
  //-----------------------------------------------------------------------
  public static <EnumClass> EnumClass castEnum(Class<EnumClass> enum_class, String s) {
    if (enum_class.isEnum()) {
      for (EnumClass o: enum_class.getEnumConstants()) {
        if (s.toLowerCase().equals(o.toString().toLowerCase())) {
          return o;
        }
      }
    }
    return null;
  }
  
  //-----------------------------------------------------------------------
  // Object Cast
  //-----------------------------------------------------------------------

  public static DataWrapper castData(Object o) {
    try                 {return (DataWrapper)o;}
    catch (Exception e) {return null;}
  }

  public static Collection<Object> castCollection(Object o) {
    try                 {return (Collection<Object>)o;}
    catch (Exception e) {return null;}
  }

  public static String castString(Object o) {
    try                 {return (String)o;}
    catch (Exception e) {return null;}
  }

  public static String[] castStrings(String s) {
    try                 {return s.split(Constant.STRING_SPLIT_REGEX);}
    catch (Exception e) {return null;}
  }
  
  public static String[] castStringsFormatted(String s) {
    Collection<String> split = new ArrayList<String>();
    int bracket_start = -1;
    int last_split    =  0;
    for (int i = 0 ; i < s.length() ; i++) {
      char c = s.charAt(i);
      if (c=='(' || c=='[' || c=='{') {bracket_start++;}
      if (c==')' || c==']' || c=='}') {if (bracket_start>=0) {bracket_start--;}}
      if (c==',' || c==' ' || c=='|') {
        if (bracket_start<0) {
          split.add(s.substring(last_split, i));
          last_split = i;
        }
      }
    }
    split.add(s.substring(last_split, s.length()));
    return split.toArray(new String[0]);
  }
  


  public static boolean castBool(String s) {
    if (s==null) {return false;}
    if (s.toLowerCase().equals("yes")  ||
        s.toLowerCase().equals("y")    ||
        s.toLowerCase().equals("ok")   ||
        s.toLowerCase().equals("true") ||
        s.toLowerCase().equals("t")      ) {return true;}
    return false;
  }

  public static Point castPoint(String s) {
    try {
      String[] ss = s.split(",");
      return new Point(castInt(ss[0]), castInt(ss[1]));
    }
    catch (Exception e) {return null;}
  }

  public static Dimension castDimension(String s) {
    try {
      String[] ss = s.split(",");
      return new Dimension(castInt(ss[0]), castInt(ss[1]));
    }
    catch (Exception e) {return null;}
  }

  public static Map castMap(Object o) {
    try                 {return (Map)o;}
    catch (Exception e) {return null;}
  }





  //-----------------------------------------------------------------------
  // Array Cast
  //-----------------------------------------------------------------------



  public static boolean[] castBool(String[] s) {
    if (s==null) {return null;}
    boolean[] array = new boolean[s.length];
    for (int i=0 ; i<array.length ; i++) {array[i] = castBool(s[i]);}
    return array;
  }

  public static byte[] castByte(String[] s) {
    if (s==null) {return null;}
    byte[] array = new byte[s.length];
    for (int i=0 ; i<array.length ; i++) {array[i] = castByte(s[i]);}
    return array;
  }

  public static short[] castShort(String[] s) {
    if (s==null) {return null;}
    short[] array = new short[s.length];
    for (int i=0 ; i<array.length ; i++) {array[i] = castShort(s[i]);}
    return array;
  }

  public static int[] castInt(String[] s) {
    if (s==null) {return null;}
    int[] array = new int[s.length];
    for (int i=0 ; i<array.length ; i++) {array[i] = castInt(s[i]);}
    return array;
  }

  public static long[] castLong(String[] s) {
    if (s==null) {return null;}
    long[] array = new long[s.length];
    for (int i=0 ; i<array.length ; i++) {array[i] = castLong(s[i]);}
    return array;
  }

  public static float[] castFloat(String[] s) {
    if (s==null) {return null;}
    float[] array = new float[s.length];
    for (int i=0 ; i<array.length ; i++) {array[i] = castFloat(s[i]);}
    return array;
  }

  public static double[] castDouble(String[] s) {
    if (s==null) {return null;}
    double[] array = new double[s.length];
    for (int i=0 ; i<array.length ; i++) {array[i] = castDouble(s[i]);}
    return array;
  }


  private static Double parseDouble(String s) {
    s = s.trim();
    if (s.endsWith("%")) {return Double.valueOf(s.replace('%',' '))/100;}
    else                 {return Double.valueOf(s);}
  }
  private static Float parseFloat(String s) {
    s = s.trim();
    if (s.endsWith("%")) {return Float.valueOf(s.replace('%',' '))/100;}
    else                 {return Float.valueOf(s);}
  }

  
}
