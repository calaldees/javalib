package Utils.File.FileHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Vector;

public class OpenStreamHandeler {

  FileInputStream   file_input_stream   = null;
  ObjectInputStream object_input_stream = null;

  public OpenStreamHandeler() {}
  public OpenStreamHandeler(String filename) throws IOException {open(filename);}
  public OpenStreamHandeler(File   file    ) throws IOException {open(file    );}

  public void open(String filename) throws IOException {open(new File(filename));}
  public void open(File   file    ) throws IOException {
    file_input_stream   = new FileInputStream(file);
    object_input_stream = new ObjectInputStream(file_input_stream);
  }

  public Object read() throws Exception {
    return object_input_stream.readObject();
  }

  public void close() throws IOException {
    object_input_stream.close();
    file_input_stream.close();
    object_input_stream = null;
    file_input_stream   = null;
  }

  public static Object openObject(String filename) {
    try {
      OpenStreamHandeler stream = new OpenStreamHandeler(filename);
      Object o = stream.read();
      stream.close();
      return o;
    }
    catch (Exception e) {return null;}
  }
  
  public static Collection<Object> openObjects(String filename) {
    try {
      OpenStreamHandeler stream = new OpenStreamHandeler(filename);
      Collection<Object> c = new Vector<Object>();
      while (true) {
        try                 {c.add(stream.read());}
        catch (Exception e) {break;}
      }
      stream.close();
      return c;
    }
    catch (Exception e) {return null;}
  }
}

