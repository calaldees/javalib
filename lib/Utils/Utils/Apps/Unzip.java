package Utils.Apps;

import java.io.LineNumberReader;
import java.io.FileReader;

import Utils.ErrorHandeler;
import Utils.File.FileOperations;
import Utils.File.ZipManager;
import Utils.NativeCommand;

public class Unzip {

  private final String zip_extension = "zip";

  public Unzip(String ... args) {
    for (String filename: args) {
      if (FileOperations.getExtension(filename).compareToIgnoreCase(zip_extension)==0) {            extract(filename);}
      else                                                                             {extractFromFileList(filename);}
    }
  }

  public static void main(String ... args) {
    System.out.println("Batch Unzip Util");
    if (!(args.length>0)) {ErrorHandeler.error("No arguments supplied","use filename of zip/jar file or a .txt file containing a list of zip/jar files");}
    else                  {new Unzip(args);}
  }

  
  
  //-----------------------------------------------------------
  // Private Methods
  //-----------------------------------------------------------
  
  
  private void extractFromFileList(String filename) {
    try {
      LineNumberReader line_reader = new LineNumberReader(new FileReader(filename));
      String           line;
      while  ((line=line_reader.readLine())!=null) {
      //for (int temp=0 ; temp<1 ; temp++) {
        //line=line_reader.readLine();
        extract(line);
        //System.out.println(FileOperations.getFilenameWithoutExtension(line));
        //System.out.println();
      }
      line_reader.close();
    }
    catch (Exception e) {}
  }

  private void extract(String filename) {
    System.out.println("Extracting - "+filename);
    try                 {ZipManager.extractZip(filename);}
    catch (Exception e) {ErrorHandeler.error(this,"Failed to Extract Zip",filename);}
  }

}