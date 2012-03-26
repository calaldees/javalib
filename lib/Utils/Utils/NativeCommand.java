package Utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class NativeCommand {
 
  public static BufferedReader exec(String command) {
    try {
      System.out.println(command);
      Process p = Runtime.getRuntime().exec("cmd /C "+command);
      return new BufferedReader(new InputStreamReader(p.getInputStream()));
    } 
    catch (IOException e) {e.printStackTrace();}
    return null;
  }

  public static void execDefault(String command) {
    try {
      BufferedReader in = exec(command);
      String line = null;
      while ((line = in.readLine()) != null) {System.out.println(line);}
    }
    catch (Exception e) {e.printStackTrace();}
  }

}