package Utils;

import java.lang.reflect.*;

@SuppressWarnings("unchecked")
public class ObjectCloner {

  private ObjectCloner() {}
  
  public static <T> T cloneObject(T o) {
    Class c = o.getClass();
    //search + run copy constructor
    for (Constructor constructor : c.getConstructors()) {
      try {
        if (constructor.getParameterTypes()[0].isInstance(o)) {return (T)constructor.newInstance(o);}
      }
      catch (Exception e) {}
    }
    
    //check for 
    // - static factory newInstance()
    // - public clone method
    for (Method method : c.getDeclaredMethods()) {
      if (method.getName().equals("clone")) {
        Object nullorizorpassorisorjavaisgayorizor = null;
        try                 {return (T)method.invoke(o,nullorizorpassorisorjavaisgayorizor);}
        catch (Exception e) {}
      }
      if (method.getName().equals("getInstance")) {
        try                 {return (T)method.invoke(o,o);}
        catch (Exception e) {}
      }
    }
    
    return null;
  }
  
}
