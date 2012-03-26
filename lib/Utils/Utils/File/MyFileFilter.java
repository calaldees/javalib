package Utils.File;

import java.io.File;
import javax.swing.filechooser.FileFilter;



public class MyFileFilter extends FileFilter implements java.io.FileFilter {


//-------------------------------------------------------------------------------------------------
// Variables
//-------------------------------------------------------------------------------------------------

  boolean accept_directorys = true;
  String  extension;
  String  description;

//-------------------------------------------------------------------------------------------------
// Constructor
//-------------------------------------------------------------------------------------------------

  public MyFileFilter(String extension) {
    constructor(extension, "", true);
  }
  public MyFileFilter(String extension, String description) {
    constructor(extension, description, true);
  }
  public MyFileFilter(String extension, String description, boolean accept_directorys) {
    constructor(extension, description, accept_directorys);
  }
  private void constructor(String extension, String description, boolean accept_directorys) {
    if (extension.indexOf('.')<0) {extension="."+extension;}
    this.extension   = extension;
    this.description = description + "(*." + extension + ")";
  }

//-------------------------------------------------------------------------------------------------
// Public Mehthods
//-------------------------------------------------------------------------------------------------

  public boolean accept(File f) {
    if (f.isDirectory() && accept_directorys) {return true;}
    String this_file_extension = FileOperations.getExtension(f);
    if (this_file_extension != null) {
      if (extension.equals(this_file_extension)) {return true; }
      else                                       {return false;}
    }
    return false;
  }
    
  public String getDescription() {return description;}

//-------------------------------------------------------------------------------------------------
// End
//-------------------------------------------------------------------------------------------------
}
