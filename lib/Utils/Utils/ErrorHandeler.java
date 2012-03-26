package Utils;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JRootPane;
import javax.swing.JOptionPane;
import java.lang.StackTraceElement;

import org.xml.sax.Locator;

public class ErrorHandeler {

  private static JRootPane root;

  public static void reportTo(JRootPane           r) {root = r;}
  public static void reportTo(JFrame          frame) {reportTo( frame.getRootPane());}
  public static void reportTo(JInternalFrame iframe) {reportTo(iframe.getRootPane());}

  //TEMP!!! REMOVE THIS METHOD!! .. we need to know where it comes from
  public static void error(String title, String message) {error("unknown", title, message);}

  public static void error(String title, String message, Exception e) {
    StackTraceElement[] stack = e.getStackTrace();
    error(stack[0].getClassName(), title, message+"\n"+e.getMessage());
    e.printStackTrace();
  }

  public static void error(Object source, String title, String message) {
    if (source!=null) {error(source.getClass(),title,message);}
    else              {error(                  title,message);}
  }

  public static void error(Class class_type, String title, Exception e   ) {error(class_type,title,e.getMessage()); e.printStackTrace();}
  public static void error(Class class_type, String title, String message) {
    String class_name = "";
    if (class_type!=null) {class_name = class_type.getName();}
    error(class_name, title, message);
  }


  
  public static void error(Class class_type, Exception e, Locator locator) {error(class_type.getName(),e,locator);}
  public static void error(String       tag, Exception e, Locator locator) {
    error("Loading from XML - "+tag, 
          "File:"+locator.getSystemId()   +"\n"+
          "Line:"+locator.getLineNumber() +"\n"+
          "Type:"+tag,
          e);
    e.printStackTrace();
  }

  private static void error(String source, String title, String message) {
    if (root!=null) {
      JOptionPane.showMessageDialog(root, message, source+" - "+title , JOptionPane.ERROR_MESSAGE);
    }
    else {
      System.err.println("---------------------------------------------");
      System.err.println("CAUSE : "+source );
      System.err.println("ERROR : "+title  );
      System.err.println("DETAIL: "+message);
      System.err.println("---------------------------------------------");
    }
  }


}
