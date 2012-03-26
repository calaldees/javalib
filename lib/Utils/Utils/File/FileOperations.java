package Utils.File;

import java.io.File;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.StringWriter;
import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Utils.ErrorHandeler;
import Utils.RegExHelper;

public class FileOperations {

//-------------------------------------------------------------------------------------------------
// Static Methods
//-------------------------------------------------------------------------------------------------

  private static Pattern filename_pattern = Pattern.compile("(.*)[\\\\/]((.*?)\\.(.*?))$");
 
  public static String readFile(String filename) {return readFile(new File(filename));}
  public static String readFile(File file      ) {
    try {
      BufferedInputStream  in  = new BufferedInputStream(new FileInputStream(file));
      StringWriter         out = new StringWriter();
      int b; while ((b = in.read()) != -1) out.write(b);
      String page = out.toString();
      in.close();
      out.close();
      return page;
    } catch (Exception e) {return null;}
  }

  //public static String getFilename( String absolute_path) {return RegExHelper.search(absolute_path,filename_pattern)[2];}
  //public static String getPath(     String absolute_path) {return RegExHelper.search(absolute_path,filename_pattern)[1];}
  //public static String getExtension(String absolute_path) {return RegExHelper.search(absolute_path,filename_pattern)[4];}

  public static String getFilename(String filename) {return new File(filename).getName();}

  public static String getDirectory(String filename) {return getDirectory(new File(filename)).getAbsolutePath();}
  public static File   getDirectory(File file      ) {
    if (file==null) {return null;}
    if (file.isDirectory()) {return file;}
    if (file.isFile()     ) {return file.getParentFile();}
    return null;
  }

  public static String getExtension(URI    uri     ) {return getExtension(uri.getPath());}
  public static String getExtension(File   file    ) {return getExtension(file.getName());}
  public static String getExtension(String filename) {
    try                 {return filename.substring(filename.lastIndexOf('.')+1, filename.length());}
    catch (Exception e) {return null;}
  }

  public static File replaceExtension(File file, String new_ext) {
    if (!new_ext.startsWith(".")) {new_ext = "."+new_ext;}
    String filename        = file.toString();
    String new_file_string = filename.substring(0,filename.lastIndexOf('.')).concat(new_ext);
    File   new_file        = new File(new_file_string);
    if (new_file.canRead()) {return new_file;}
    return null;
  }

  public static String getFilenameWithoutExtension(File file)       {
    try                 {return getFilenameWithoutExtension(file.getName());}
    catch (Exception e) {return null;}
  }
  public static String getFilenameWithoutExtension(String filename) {
    try {

      //Matcher matcher = filename_pattern.matcher(filename);
      //while (matcher.find()) {return matcher.group(2);}
        /*
        for (int counter=0 ; counter<matcher.groupCount() ; counter++) {
          System.out.print("G"+counter+":"+matcher.group(counter+1)+" ");
          System.out.println();
        }
         */
      int start_pos = filename.lastIndexOf('/') + 1;
      int end_pos   = filename.lastIndexOf('.');
      if (start_pos<0) {start_pos=0;}
      if (end_pos  <0) {end_pos  = filename.length();}
      return filename.substring(start_pos,end_pos);
    }
    catch (Exception e) {}
    return null;
  }
  

  public static File[] getFileList(File file, String[] exts) {
    if (exts!=null && exts.length>=1) {
      if (exts.length==1) {return getFileList(file, exts[0]);}
      else {
        ErrorHandeler.error("getFileList(String[])", "Method not implemented fully, FIX IT YOU SLACKER!");
        return getFileList(file, exts[0]);
      }
    }
    else {ErrorHandeler.error("getFileList()", "Unable to open Extensions - "+exts);}
    return null;
  }

  public static File[] getFileList(String path            ) {return getFileList(path          ,"*");}
  public static File[] getFileList(String path, String ext) {return getFileList(new File(path),ext);}
  public static File[] getFileList(File file  , String ext) {
    MyFileFilter filter    = new MyFileFilter(ext, "", false);
    File[]       file_list = file.listFiles(filter);
    return file_list;
  }

  public static boolean deleteFile(String filename) {
    try                 {return deleteFile(new File(filename));}
    catch (Exception e) {return false;}
  }
  public static boolean deleteFile(File file) {
    try                 {return file.delete();}
    catch (Exception e) {return false;}
  }

  public static File createNewFileAndOverwriteExisting(String filename) {
    deleteFile(filename);
    return createNewFile(filename);
  }
  public static File createNewFile(String filename) {
    File file = new File(filename);
    if (file.exists() && file.length()>0) {return null;}
    return setupWriteableFile(file);
  }

  public static File setupWriteableFile(File file                 ) {return setupWriteableFile(file,null);}
  public static File setupWriteableFile(File file, String filename) {
    try {
      if (!createDirectorys(file)) {return null;}
      if (file.isDirectory()) {
        file = new File(file.getCanonicalPath()+filename);
      }
      if (file.isFile()) {
        if (!file.exists())   {file.createNewFile();}
        if (!file.canWrite()) {return null;}
        return file;
      }
    } 
    catch (Exception e) {}
    return null;
  }

  public static boolean createDirectorys(String pathname) {return createDirectorys(new File(pathname));}
  public static boolean createDirectorys(File file) {
    try {
      if (file.isFile()     ) {
        if (file.getParentFile().exists()) {return true;}
        else                               {return file.getParentFile().mkdirs();}
      }
      if (file.isDirectory()) {
        if (file.exists()) {return true;}
        else               {return file.mkdirs();}
      }
    }
    catch (Exception e) {}
    return false;
  }

  public static boolean isFilePossibleToCreate(String filename) {
    try                 {return isFilePossibleToCreate(new File(filename));}
    catch (Exception e) {return false;}
  }
  public static boolean isFilePossibleToCreate(File   file    ) {
    if (file==null) {return false;}
    System.out.println("IMPLEMENT FILE POSSIBLE!!");
    return false;
  }
  
  public static boolean renameFile(String source_filename, String destination_filename)                    {return renameFile(source_filename, destination_filename, false);}
  public static boolean renameFile(String source_filename, String destination_filename, boolean overwrite) {
    try {
      File source      = new File(     source_filename);
      File destination = new File(destination_filename);
      if (source.exists()) {
        if (destination.exists() && overwrite) {destination.delete();}
        return source.renameTo(destination);
      }
    }
    catch (Exception e) {}
    return false;
  }

  public static String getRelativePath(String filename, String root) {
    return filename.replaceFirst(root, "");
  }

  public static String webPathCleaner(String path) {
    return path.replaceAll("\\", "/");
  }

  // make sure it cleans the path. (see cleanPath below)
  public static String concatPaths(String ... paths) {
    String path = "";
    for (String path_component : paths) {
      if (!path.endsWith("/") && !path.endsWith("\\")) {path_component = "/" + path_component;}
      path = path + path_component;
    }
    return path;
  }
  
  // always make sure a path ends in / or \
  // if not start with drive or http then make it start with "./"
  public static String cleanPath(String path) {
    System.out.println("IMPLEMENT PATH CLEANING!!! " + path);
    return path;
  }
  
  public static String cleanFilename(String filename) {
    char r = '-';
    filename = filename.replace(':',r);
    filename = filename.replace('\\',r);
    filename = filename.replace('/',r);
    filename = filename.replace('*',r);
    filename = filename.replace('?',r);
    filename = filename.replace('"',r);
    filename = filename.replace('<',r);
    filename = filename.replace('>',r);
    filename = filename.replace('|',r);
    return filename;
  }
}
