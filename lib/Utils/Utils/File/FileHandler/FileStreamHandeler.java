package Utils.File.FileHandler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;

import Utils.File.FileOperations;

public class FileStreamHandeler {

  private File file;

  public FileStreamHandeler()                throws IOException {}
  public FileStreamHandeler(String filename) throws IOException {open(filename);}
  public FileStreamHandeler(File   file    ) throws IOException {open(file    );}

  public void setFile(String filename) {
    close();
    file = FileOperations.createNewFileAndOverwriteExisting(filename);
  }
  public void setFile(File file) {
    close();
    this.file = file;
  }
  
  public void open(String s)  {}
  public void open(File f)  {}
  public void close() {}
}
