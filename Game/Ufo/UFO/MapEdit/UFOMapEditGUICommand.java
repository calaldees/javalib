package UFO.MapEdit;

import MapEdit.GUIComponents.MapFrame;
import Tile.Map.MapFoundationEditable;
import MapEdit.Tools.MapTool;
import UFO.terrain.UFOTerrainComponent;
import Utils.Types.Integer.Dimension3DUpdatable;

import java.io.File;

import Utils.GUIHelpers.GUIManager;
import javax.swing.JInternalFrame;

import Utils.XML.XMLLoad.LoadManager;
//import Tile.Map.terrain.TerrainComponent;


public class UFOMapEditGUICommand {
  
  private GUIManager gui_manager;
  
  public UFOMapEditGUICommand(GUIManager gui_manager) {this.gui_manager = gui_manager;}

  public void New() {
    Dimension3DUpdatable map_size = new Dimension3DUpdatable(10,10,1); //gui_manager.showDialogClass("New Map Dimensions", Utils.Types.Integer.Dimension3DUpdatable.class);
    if (map_size!=null) {
      System.out.println("New Map: "+map_size.toString());
      
      MapFoundationEditable map = new MapFoundationEditable(map_size.getImmutable());
      Object o = LoadManager.getItem(UFOTerrainComponent.class,"null_floor");
      for (int y=0 ; y<map.getDimension3D().getLength(); y++ ) {
        for (int x=0 ; x<map.getDimension3D().getWidth(); x++ ) {
          map.setLayer(o,x,y,0,UFOTerrainComponent.TileType.FLOOR.getLayerID());
        }
      }
      gui_manager.addWindow(new MapFrame(map));
    }
  }
 /* 
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
  
  
  */

  
  public void Single_Layer(boolean b) {
    System.out.println("Layer is "+b);
  }
  
  public void MapLayer(int layer) {
    System.out.println("MapLayer="+layer);
  }
  
  public void Brush(boolean selected)  {if (selected) MapTool.activate("Brush");}
  public void Select(boolean selected) {if (selected) MapTool.activate("Select");}

  
  
}