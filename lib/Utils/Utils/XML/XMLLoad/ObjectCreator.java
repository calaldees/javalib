package Utils.XML.XMLLoad;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import Utils.Caster;
import Utils.ReflectionUtils;


@SuppressWarnings("unchecked")
class ObjectCreator {



  private ObjectCreator() {} //Ensure NonInstability
  

  
  public static Object createObject(Class class_type, DataWrapper data) throws Exception {
    Object obj = createNewObject(class_type, data);
    setFields(obj, data);
    callSetMethods(obj, data);
    return obj;
  }
  /**
  public static <O> O setValues(O obj, DataWrapper data) throws ValidateException {
    setFields(obj, data);
    callSetMethods(obj, data);
    return obj;
  }
   */


  
  public static void setFields(Object obj, DataWrapper data) throws ValidateException {
    if (obj ==null) {throw new IllegalArgumentException();}
    if (data==null) {throw new IllegalArgumentException();}

    for (String key: data.keySet()) {
      String field_name = key.replace(' ','_').toLowerCase();
      Field  field      = getField(obj.getClass(),field_name);
      if (field==null) {continue;}

      // If the key points to an object then it's either a sub map or a vanilla object.
       //   If it's an object then we want to do some crazzy casting and set the object if we can
       //   If it's a map then ... then ... I dont have a clue .. think of something later
      if (data.isObject(key)) {
        Object o = data.get(key);        // Get Object
        if (o!=null) {                   // The object is real
          if (field.getType().isInstance(o)) {// And the destination is the same type
            try {field.set(obj, o);}           // Set them ... pray to sweet jesus this works
            catch (Exception e) {Error("ObjectCreator is being PANTS!");}
          }
        }
      }
      else {
        ReflectionUtils.setField(field, obj, data.getString(key));
      }

    } // End FOR each key

    //used to have validate casting and checking here with the Validatable interface
    // this was move to LoadProcessor.process because we did not want an exception at the end of overlaying, there may be additional processing after this process that will make the object valid
  }

  
  
  public static void callSetMethods(Object o, DataWrapper data) {
    // Think about looking at set??? methods first before going straght to private variables    
    //foreach "set"method
    //  call setmethod with relevent fields
    Method[] methods = o.getClass().getMethods();
    for (int m=0 ; m<methods.length ; m++) {
      Method method = methods[m];
      if (method.getName().startsWith("set")) {
        String value_name = method.getName().replaceFirst("set", "").toLowerCase();
        Class[] types = method.getParameterTypes();
        if (data.containsKey(value_name)) {
          try {
            if      (types.length==0) {method.invoke(o);}
            else if (types.length==1) {method.invoke(o,Caster.cast(types[0],data.get(value_name)));}
            else {
              // It may be possible here to use Annotations to find out the paramiter names to allow the call of set methods with multiple paramiters
            }
          }
          catch (Exception e) {}
          
        }
      }
    }
  }
  
  

  private static <T> T createNewObject(Class<T> class_type, DataWrapper data) throws Exception {
    // Look for most approriate constructor
    //Create a new object by calling the most appropriate constructor from looking at Metadata
    // -could auto call most apropriate constructor (if you can get the NAMES of the arguments for the contructor and not just the data types)

    /*
     * Could use my own annotations for allow the automatic call of multiple paramiter constructor
    Constructor[] constructors = class_type.getConstructors();
    for (int c=0 ; c<constructors.length ; c++) {
      Constructor constructor = constructors[c];
      System.out.println("Constructor="+constructor.getName());
      
      Annotation[]annotations = constructor.getDeclaredAnnotations();

      for (int j=0 ; j<annotations.length ; j++) {
        System.out.println("Annotaion ("+j+"): "+annotations[j].toString());
      }
      //constructor.getParameterTypes();
    }
    */
    
    try                 {return class_type.newInstance();}                                  //getConstructor(new Object[0]).newInstance(new Object[0]);
    catch (Exception e) {throw new Exception("Could not create with default conrtuctor. I want to eradicate the need for this error, see how Serializeable get round this");}  //Suppressed error because DataProcessorSimple does not have class  //Utils.Error.error(this, "involkDefaultConstructor()", "");}
  }


  private static Field getField(Class c, String field_name) {
    try                 {return c.getDeclaredField(field_name);}
    catch (Exception e) {
      try                 {return getField(c.getSuperclass(),field_name);}
      catch (Exception f) {return null;}
    }
  }

  
  public static void Error(String s) {
    Utils.ErrorHandeler.error("ObjectCreator", s);
  }



// WARNING WARNING WARNING WARNING WARNING WARNING WARNING WARNING WARNING WARNING WARNING WARNING WARNING WARNING WARNING WARNING WARNING 
// ATTENTION!!!
// Ummm .. what does this do? .. where is it used? ... 
// WARNING WARNING WARNING WARNING WARNING WARNING WARNING WARNING WARNING WARNING WARNING WARNING WARNING WARNING WARNING WARNING WARNING 
/**
  public static void setEnumArray(Object obj, String field_name, int size_of_array, int default_int_value, Map map) {
    Field field = null;
    if (obj!=null && field_name!=null && map!=null) {
      try {
        field = obj.getClass().getField(field_name);
        if (field !=null) {
          Class field_type = field.getType();
          if (field_type.isArray()) {
            Class  array_type      = field_type.getComponentType();
            String array_type_name = array_type.getName();
            if (array_type_name.equals("int")) {
              int[] int_array = new int[size_of_array];
              for (int i=0 ; i<int_array.length ; i++) {
                String value_string = Utils.getString(map, ""+i);
                if (value_string != null) {int_array[i] = getInt(value_string);}
                else                      {int_array[i] = default_int_value;}
              }
              field.set(obj, int_array);
            } // End if is Int array
          } //End if is Array
        } // End if feild not null
      } // End Try
      catch (Exception e) {UFO.Error.ErrorMessage("Set Enum Array Error", e);}
    } //End if any objects null
  }
*/







}
