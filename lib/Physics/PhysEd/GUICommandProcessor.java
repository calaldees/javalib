package PhysEd;

import java.io.File;
import Utils.GUIHelpers.GUIManager;

public class GUICommandProcessor {
  private GUIManager gui_manager;

  public GUICommandProcessor(GUIManager gui_manager) {this.gui_manager = gui_manager;}
  
  public void New() {
    System.out.println("New");
  }
  
  public void Open() {
    File map_file = gui_manager.dialogOpen();
    System.out.println("OPEN-"+map_file.toString());
  }
  
  public void Save() {
    File map_file = gui_manager.dialogSave();
    System.out.println("SAVE-"+map_file.toString());
  }
  
  public void Exit() {
    System.out.println("Exiting");
    System.exit(0);
  }
}
