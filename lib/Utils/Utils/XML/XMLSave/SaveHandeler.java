package Utils.XML.XMLSave;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URI;


public class SaveHandeler {

  //-------------------------------------------------
  // Variables
  //-------------------------------------------------
  private final PrintWriter writer;
  
  //-------------------------------------------------
  // Constructors
  //-------------------------------------------------
  
  public SaveHandeler(URI destination) throws Exception {
    this(destination.toURL().openConnection().getOutputStream());
  }
  public SaveHandeler(OutputStream out) {
    writer = new PrintWriter(out);
  }
  
  //-------------------------------------------------
  // Tag Management
  //-------------------------------------------------
  
  SaveWrapper getSaveWrapper(String tag) {return new SaveWrapper(tag);}
  
  void writeSaveWrapper(SaveWrapper s) {
    if (s==null) {return;}
    print("<"+s.getName());
    for (String key : s.getFieldKeys()) {
      print(" "+key+"=\""+s.getField(key)+"\"");
    }
    if (!s.hasObjects()) {print("/>\n");}
    else {
      print(">\n");
      for (Object o : s.getObjects()) {
        SaveProcessorManager.save(this,o);
      }
      print("</"+s.getName()+">\n");
    }
  }
  
  //-------------------------------------------------
  // Other
  //-------------------------------------------------
  
  public void print(String s) {writer.print(s);}
  
  void close() {
    writer.flush();
    writer.close();
  }

}