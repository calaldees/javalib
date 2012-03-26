package Utils.XML.XMLSave;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import Utils.ReflectionUtils;

public class XMLOutput {
  
  private XMLOutput() {}
  
  public static void output(OutputStream stream, Object... objs) {output(new PrintWriter(stream), objs);}
  
  private static void output(PrintWriter writer, Object... objs) {
    for (Object o : objs) {
      String tag = SaveManager.lookupXMLTagName(o.getClass());
      if (tag==null) {break;}
      writeTagBegin(writer,tag);
      if (outputObjectPrimatives(writer,o) && !hasSubObjects(o)) {
        writeSingleEnd(writer);
      }
      else {
        writeTagBeginEnd(writer);
        output(writer,getSubObjs(o));
        writeTagEnd(writer,tag);
      }
    }
  }
  
  private static void writeTagBegin(PrintWriter writer, String tag) {
    writer.print("<"+tag+" ");
  }
  
  private static void writeTagBeginEnd(PrintWriter writer) {
    writer.print(">\n");
  }

  private static void writeTagEnd(PrintWriter writer, String tag) {
    writer.print("</"+tag+">\n");
  }
  
  private static void writeSingleEnd(PrintWriter writer) {
    writer.print("/>\n");
  }

  private static void writeTagSingle(PrintWriter writer, Object o) {
    
  }
  
  private static boolean hasSubObjects(Object o) {
    return false;
  }
  
  private static Object[] getSubObjs(Object o) {
    return null;
  }
  
  private static boolean outputObjectPrimatives(PrintWriter writer, Object o) {
    if (o==null) {return false;}
    boolean field_outputed = false;
    for (Field field : o.getClass().getFields()) {
      if (ReflectionUtils.isStringAbleType(field)) {
        try {
          writer.print(field.getName()+"=\""+field.get(o)+"\" ");
          field_outputed = true;
        }
        catch (Exception e) {
          System.out.println("ERROR string feild: "+e.getMessage());
        }
      }
    }
    return field_outputed;
  }
  
}
