package MapEdit.Tools;

import java.io.File;

import Utils.ErrorHandeler;
import Utils.GUIHelpers.GUIManager;
import Utils.XML.XMLLoad.LoadManager;
import Utils.XML.XMLSave.SaveManager;

import Tile.Model.Map.MapFoundation;
import Tile.GUIComponents.GameObjectViewer;

import MapEdit.GUIComponents.MapFrame;
import Utils.Sound.GUISoundList;



public class StandardCommandProcessor {
  
  private final GUIManager gui_manager;
  
  public StandardCommandProcessor(GUIManager gui_manager) {
    this.gui_manager = gui_manager;
  }
  
  public void undo() {
    MapFrame.getSelectedMap().undo();
    MapFrame.getSelectedMapComponent().repaint();
  }
  public void redo() {
    MapFrame.getSelectedMap().redo();
    MapFrame.getSelectedMapComponent().repaint();
  }
  
  public void Open() {
    File map_file = gui_manager.dialogOpen();
    if (map_file==null) {return;}
    try {
      for (MapFoundation map : LoadManager.open(map_file).getObjectsOfType(MapFoundation.class)) {
        MapFrame map_frame = new MapFrame(map);
        map_frame.setFile(map_file);
        gui_manager.addWindow(map_frame);
      }
    }
    catch (Exception e) {ErrorHandeler.error("Failed to Load",map_file.toString(),e);}
  }
  
  public void Save() {
    File map_file = MapFrame.getSelectedMapFrame().getFile();
    if (map_file==null) {Save_As();}
    else                {Save_As(map_file);}
  }
  public void Save_As() {
    File map_file = gui_manager.dialogSave();
    Save_As(map_file);
  }

  public void Save_As(File map_file) {
    if (map_file==null) {return;}
    try {
      System.out.println("SAVE-"+map_file.toString());
      map_file.createNewFile();
      SaveManager.saveObject(map_file,MapFrame.getSelectedMap());
      MapFrame.getSelectedMap().setChangedStatus(false);
      MapFrame.getSelectedMapFrame().setFile(map_file);
    }
    catch (Exception e) {ErrorHandeler.error("Failed to Save",map_file.toString());}
  }
  
  public void Exit() {
    for (MapFrame f : MapFrame.getAllMapFrames()) {
      f.close();
    }
    if (MapFrame.getAllMapFrames().size()==0) {System.exit(0);}
  }
  
  public void Unit_Viewer() {
    gui_manager.showDialog((new GameObjectViewer(RTS.GameObject.Unit.RTSUnitTemplate.class)).getComponent(),"Unit Viewer");
  }

  public void Structure_Viewer() {
    gui_manager.showDialog((new GameObjectViewer(RTS.GameObject.Structure.RTSStructureTemplate.class)).getComponent(),"Structure Viewer");
  }
  
  public void Sound_List() {
    gui_manager.showDialog((new GUISoundList()).getComponent(),"Sound List");
  }
  

}