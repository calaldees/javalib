package Utils.XML.XMLSave;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.zip.GZIPOutputStream;

public class SaveManager {
  
  private SaveManager() {}
  
  
  public static void saveObject(File file       , Object... objs) {saveObject(file.toURI(), objs);}
  public static void saveObject(URI  destination, Object... objs) {
    try {
      OutputStream output_stream = null;
      String file_extension = Utils.File.FileOperations.getExtension(destination);
      if      (file_extension.equals(Utils.XML.XMLLoad.Constant.XML_EXTENSION           )) {output_stream =                      new FileOutputStream(new File(destination)) ;}
      else if (file_extension.equals(Utils.XML.XMLLoad.Constant.XML_EXTENSION_COMPRESSED)) {output_stream = new GZIPOutputStream(new FileOutputStream(new File(destination)));}
      else                                                                                 {throw new IllegalArgumentException("Not a supported file extension: " + file_extension);}

      SaveHandeler saver = new SaveHandeler(output_stream);
      saver.print(Constant.XML_HEADER);
      saver.print(Constant.XML_SAVE_HEADER);
      for (Object o : objs) {
        SaveProcessorManager.save(saver,o);
      }
      saver.print(Constant.XML_SAVE_FOOTER);
      saver.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  public static String lookupXMLTagName(Class c) {
    return null;
  }
  
  public static Class lookupXMLTagClass(String name) {
    return null;
  }
}
