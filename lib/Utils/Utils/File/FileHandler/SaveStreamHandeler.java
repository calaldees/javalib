package Utils.File.FileHandler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;


public class SaveStreamHandeler {

  FileOutputStream   file_output_stream   = null;
  ObjectOutputStream object_output_stream = null;

  public SaveStreamHandeler()                throws IOException {}
  public SaveStreamHandeler(String filename) throws IOException {open(filename);}
  public SaveStreamHandeler(File   file    ) throws IOException {open(file    );}

  public void open(String filename) throws IOException {open(new File(filename));}
  public void open(File   file    ) throws IOException {
      file_output_stream   = new FileOutputStream(file);
      object_output_stream = new ObjectOutputStream(file_output_stream);
  }

  public void write(Object o) throws IOException {
    object_output_stream.writeObject(o);
  }

  public void close() throws IOException {
    object_output_stream.flush();
    file_output_stream.close();
    object_output_stream = null;
    file_output_stream   = null;
  }

}
