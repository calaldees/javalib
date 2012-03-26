package Utils;

import Utils.XML.XMLLoad.LoadManager;
import java.awt.Image;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import Utils.Types.Sprite;
import Utils.Types.Integer.*;
import Utils.XML.XMLLoad.Indexable;
//import Utils.XML.XMLLoad.Constant;
import Utils.ImageLoader.ImageBlockLoader;
import java.util.Vector;


@SuppressWarnings("unchecked")
public class ReflectionUtils {
  
  //private static String split_regex = ;
  
  private ReflectionUtils() {}
  
  
  //----------------------------------------------------------------------
  // Create Object
  //----------------------------------------------------------------------
  
  public static <T> T createObject(Class<T> c) {
    if (c==null) {throw new IllegalArgumentException();}
    try                 {return c.newInstance();}
    catch (Exception e) {
      //ErrorHandeler.error(ReflectionUtils.class,"Cannot create new instance of "+c.getName(),e);
    }
    return null;
  }
  
  //----------------------------------------------------------------------
  // Get Helpers
  //----------------------------------------------------------------------

  public static String getName(Object o) {
    if      (o == null             ) {return null;}
    else if (o instanceof Indexable) {return ((Indexable)o).getName();}
    else {
      for (String key : Utils.XML.XMLLoad.Constant.XML_PRIMARY_KEYS) {
        String name = getFieldStringValue(o,key);
        if (name!=null) {return name;}
      }
    }
    return null;
  }
  
  public static Method getMethod( Object o, String method_name) {
    try                 {return getMethods(o,method_name)[0];}
    catch (Exception e) {}
    return null;
  }
  public static Method[] getMethods(Object o                    ) {return o.getClass().getMethods();}
  public static Method[] getMethods(Object o, String contains) {
    Method[] methods = getMethods(o);
    Vector<Method> methods_return = new Vector<Method>();
    for (int i=0 ; i<methods.length ; i++) {
      if (methods[i].getName().toLowerCase().contains(contains.toLowerCase())) {
        methods_return.add(methods[i]);
      }
    }
    return methods_return.toArray(new Method[0]);
  }
  
  public static Field[] getFields(Object o) {return o.getClass().getFields();}
  
  public static Field[] getFields(Object o, String contains) {
    Field[] fields = getFields(o);
    Vector<Field> fields_return = new Vector<Field>();
    for (int i=0 ; i<fields.length ; i++) {
      if (fields[i].getName().toLowerCase().contains(contains.toLowerCase())) {
        fields_return.add(fields[i]);
      }
    }
    return fields_return.toArray(fields);
  }
  
  public static Object getFieldObject(Object o, String fieldname) {try {return getFieldObject(o,o.getClass().getField(fieldname));} catch (Exception e) {return null;}}
  public static Object getFieldObject(Object o, Field field     ) {
    Object value = null;
    try {
      boolean original_accessible_state = field.isAccessible();
      field.setAccessible(true);
      value = field.get(o);
      field.setAccessible(original_accessible_state);
    }
    catch (Exception e) {}
    return value;
  }

  public static boolean isStringAbleType(Field field) {
    if (field.getType().isPrimitive()                               ) {return true;}
    if (field.getType().isAssignableFrom(AbstractPoint3D.class    ) ||
        field.getType().isAssignableFrom(AbstractDimension3D.class) ) {return true;}
    if (field.getType().isAssignableFrom(String[].class)            ) {return true;}
    return false;
  }
  
  public static Field[] getStringableFields(Object o) {
    int count = 0;
    for (Field f : o.getClass().getFields()) {if (isStringAbleType(f)) count++;}
    Field[] fields = new Field[count];
    count = 0;
    for (Field f : o.getClass().getFields()) {if (isStringAbleType(f)) {fields[count]=f; count++;}}
    return fields;
  }
  
  public static String getFieldStringValue(Object o, String fieldname) {try {return getFieldStringValue(o,o.getClass().getField(fieldname));} catch (Exception e) {return null;}}
  public static String getFieldStringValue(Object o, Field  field    ) {
    Object recovered_object = getFieldObject(o, field);
    if (recovered_object!=null) {return recovered_object.toString();}
    else                        {return null;}
  }

  //----------------------------------------------------------------------
  // Set Helpers
  //----------------------------------------------------------------------
  
  public static void setField(Field field, Object obj, String value) {
    try {
      // Get Info about the FIELD being set

      boolean original_accessible_state = field.isAccessible();
              field.setAccessible(true);
      Class   field_type      = field.getType();
      String  field_type_name = field_type.getName(); //Used for error messages

      if (field_type.isArray()) {
        Class  array_type      = field_type.getComponentType();
        //String array_type_name = array_type.getName();
        String[] values        = Caster.castStringsFormatted(value); //Some strings involve complex splitting such as (5,3),(Hi,Mum,WOW!) to count brackets .. so I replaced the simple value.split(Utils.Constant.STRING_SPLIT_REGEX);
        if (array_type.isPrimitive()) {
          if      (array_type.isAssignableFrom(boolean.class)) {field.set(obj, Caster.castBool(values));}
          else if (array_type.isAssignableFrom(byte.class)   ) {field.set(obj, Caster.castByte(values));}
          else if (array_type.isAssignableFrom(short.class)  ) {field.set(obj, Caster.castShort(values));}
          else if (array_type.isAssignableFrom(int.class)    ) {field.set(obj, Caster.castInt(values));}
          else if (array_type.isAssignableFrom(long.class)   ) {field.set(obj, Caster.castLong(values));}
          else if (array_type.isAssignableFrom(float.class)  ) {field.set(obj, Caster.castFloat(values));}
          else if (array_type.isAssignableFrom(double.class) ) {field.set(obj, Caster.castDouble(values));}
        }
        else {
          if (array_type.isAssignableFrom(Point2D.class)) {field.set(obj, Point2DImmutable.valueOf(values));}
          if (array_type.isAssignableFrom(Image.class)  ) {field.set(obj, ImageBlockLoader.getImageArray(  Caster.castInt(value)));}
          if (array_type.isAssignableFrom(Sprite.class) ) {field.set(obj, ImageBlockLoader.getSpritesArray(Caster.castInt(value)));}
          else {
            //Error("Please Implement non primative type arrays - this will have probably occoured when importing and array of string. Get codeing!");
          }
        }
      }

      else if (field_type.isEnum()) {
        Object[] enum_constants = field_type.getEnumConstants();
        for (int i=0 ; i<enum_constants.length ; i++) {
          if ( value.toLowerCase().equals(enum_constants[i].toString().toLowerCase()) ) { 
            field.set(obj, enum_constants[i]);
            break;
          }
        }
      }

      else if (field_type.isPrimitive()) {
        if      (field_type.isAssignableFrom(boolean.class)) {field.setBoolean(obj, Caster.castBool(value));}   // Old Way = field_type_name.equals("byte"))
        else if (field_type.isAssignableFrom(byte.class)   ) {field.setByte(   obj, Caster.castByte(value));}
        else if (field_type.isAssignableFrom(short.class)  ) {field.setShort(  obj, Caster.castShort(value));}
        else if (field_type.isAssignableFrom(int.class)    ) {field.setInt(    obj, Caster.castInt(value));}
        else if (field_type.isAssignableFrom(long.class)   ) {field.setLong(   obj, Caster.castLong(value));}
        else if (field_type.isAssignableFrom(float.class)  ) {field.setFloat(  obj, Caster.castFloat(value));}
        else if (field_type.isAssignableFrom(double.class) ) {field.setDouble( obj, Caster.castDouble(value));}
        else                                                 {Error("Unknown Primative Type");}
      }

      else if (LoadManager.isRegisteredClass(field_type)) {
//System.out.println(field.getName()+"("+field_type_name+") = "+value);
        field.set(obj, LoadManager.getItem(field_type,value));
      }
      
      else if (field_type.isInstance(value)) {field.set(obj, value);}  // If it's a string then set it to the value

      //else if (field_type.isInstance(new Object())) {
// Have a table of class's
// It may be possible to use reflection to autocreate an object from the data give.
// It's just a thought.
// Best just to use the Manual Caster methods for now
        if      (field_type.isAssignableFrom(java.awt.Point.class)     ) {field.set(obj, Caster.castPoint(value));      }
        else if (field_type.isAssignableFrom(java.awt.Dimension.class) ) {field.set(obj, Caster.castDimension(value));  }
        else if (field_type.isAssignableFrom(Point3D.class)            ) {field.set(obj, Point3DImmutable.valueOf(value));}
        else if (field_type.isAssignableFrom(Dimension3D.class) ||
                 field_type.isAssignableFrom(AbstractDimension3D.class)) {field.set(obj, Dimension3DImmutable.valueOf(value));}
        else if (field_type.isAssignableFrom(Image.class)              ) {
          Image i = ImageBlockLoader.getImage(Caster.castPoint(value));
          if (i==null) {i = ImageBlockLoader.getImage(value);}
          if (i!=null) {field.set(obj, i);}
        }
        else if (field_type.isAssignableFrom(Sprite.class)            ) {
          Sprite s = ImageBlockLoader.getSprite(Caster.castPoint(value));
          if (s==null) {s = ImageBlockLoader.getSprite(value);}
          if (s!=null) {field.set(obj, s);}
        }
        else {
//System.out.println("MOOOO!!!");
          //Error("CRAP! I broke this when refactoring, check SetFeild() in old Object Creator");
          /*          
          Object aquired_item = DataProcessorManager.getItem(field_type,value);  //See if item exists and can be looked up
          if (aquired_item!=null) {field.set(obj,aquired_item);}
          else                    {
            Error("Applying map to sub object "+field_type_name);

            DataWrapper sub_map = data.getSub(key);
            setFields(field.get(obj), sub_map);

          }
          */
        }
      //}

      //else {Error("Feild Type not known - "+field_type_name+" - Something very weird is going on! Seek Help!");}

      field.setAccessible(original_accessible_state);
    } //End Try

    catch (Exception e) {} //Error("Unable to set Object=" + obj.getClass().getName() );}

  }
  
  //----------------------------------------------------------------------
  // Other
  //----------------------------------------------------------------------
  
  public static void Error(String s) {
    Utils.ErrorHandeler.error("ReflectionUtils", s);
  }
  
}
