package Utils.File;

import java.io.*;
import java.net.URI;

public class StreamManager {
  
    public static void inputStreamToFile(InputStream input_stream, String filename) throws Exception {inputStreamToFile(input_stream, FileOperations.createNewFile(filename));}
    public static void inputStreamToFile(InputStream input_stream, File   file    ) throws Exception {
      BufferedInputStream  in  = new BufferedInputStream(  input_stream               );
      BufferedOutputStream out = new BufferedOutputStream( new FileOutputStream(file) );
      int b;
      while ((b = in.read()) != -1) out.write(b);
      out.flush();
      in.close();
      out.close();
    }

    public static String inputStreamToString(InputStream input_stream) throws Exception {
      BufferedInputStream  in  = new BufferedInputStream(input_stream);
      StringWriter         out = new StringWriter();
      int b; while ((b = in.read()) != -1) out.write(b);
      String page = out.toString();
      in.close();
      out.close();
      return page;
    }
    
    public static InputStream getInputStream(File f) {
      try                 {return new FileInputStream(f);} 
      catch (Exception e) {return null;}
    }
    
    public static InputStream getInputStream(URI uri) {
      try                 {return uri.toURL().openStream();} 
      catch (Exception e) {return null;}
    }

    public static void fileToOutputStream(File f, OutputStream output) throws Exception {
      BufferedInputStream  in  = new BufferedInputStream(getInputStream(f));
      BufferedOutputStream out = new BufferedOutputStream(output);
      int b;
      while ((b = in.read()) != -1) out.write(b);
      out.flush();
      in.close();
      out.close();
    }
}
