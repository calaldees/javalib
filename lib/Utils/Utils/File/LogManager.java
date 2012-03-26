package Utils.File;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.LineNumberReader;

import Utils.ErrorHandeler;


public class LogManager {

  public static void log(String filename, String string) {
    try {
      FileWriter log = new FileWriter(filename, true);
//System.out.println("Log - " + string);
      log.write(string,0,string.length());
      log.write(13);
      log.write(10);
      log.close();
    } catch (Exception e) {ErrorHandeler.error("Failed Writing to Log File","File="+filename+" Msg="+string,e);}
  }



  public static boolean fileContains(String filename, String s) {
    try {
      LineNumberReader reader = new LineNumberReader( new FileReader(filename) );
      String line;
      while ( (line=reader.readLine()) != null ) {
        if (line.indexOf(s)>=0) {
          reader.close();
          return true;
        }
      }
      reader.close();
    } catch (Exception e) {}
    return false;
  }

}
