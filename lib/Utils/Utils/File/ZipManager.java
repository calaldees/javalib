package Utils.File;

import java.io.File;
import java.util.Enumeration;
import java.util.Collection;
import java.util.Vector;
import java.util.zip.ZipFile;
import java.util.zip.ZipEntry;

import Utils.File.StreamManager;

public class ZipManager {
  
  public static Collection<String> getFileList(File    file) throws Exception {return getFileList(new ZipFile(file));}
  public static Collection<String> getFileList(ZipFile zip ) throws Exception {
    Collection<String> filelist = new Vector<String>();
    Enumeration<? extends ZipEntry> zip_entrys = zip.entries();
    while (zip_entrys.hasMoreElements()) {
      ZipEntry zip_entry = zip_entrys.nextElement();
      filelist.add(zip_entry.getName());
    }
    return filelist;
  }
  
  public static void extractZip(String zipfilename       ) {extractZip(new File(zipfilename));}
  public static void extractZip(File zipfile             ) {extractZip(zipfile, zipfile.getParent());}
  public static void extractZip(File zipfile, String path) {
    try {
      ZipFile zip = new ZipFile(zipfile);
      Enumeration<? extends ZipEntry> zip_entrys = zip.entries();
      while (zip_entrys.hasMoreElements()) {
        ZipEntry zip_entry = zip_entrys.nextElement();
        String   filename  = FileOperations.concatPaths(path, zip_entry.getName());
        //System.out.println(filename);
        StreamManager.inputStreamToFile(zip.getInputStream(zip_entry), filename);
      }
    }
    catch (Exception e) {Utils.ErrorHandeler.error("Failed to extract file from zip",zipfile.toString(),e);}
  }
}