package Dune2.MapEdit;

import Tile.Model.Map.MapFoundationEditable;
import MapEdit.GUIComponents.MapFrame;
//import MapEdit.Tools.MapTool;
import Utils.Types.Integer.Dimension3DUpdatable;
//import java.io.File;

import Utils.GUIHelpers.GUIManager;
import Utils.XML.XMLLoad.LoadManager;
import Tile.Model.Map.Terrain.BaseTileComponent;

public class Dune2MapEditGUICommand {
  private GUIManager gui_manager;
  public Dune2MapEditGUICommand(GUIManager gui_manager) {
    if (gui_manager==null) {throw new IllegalArgumentException();}
    this.gui_manager = gui_manager;
  }

  public void New() {
    Dimension3DUpdatable map_size = gui_manager.showDialog("New Map Dimensions", new Dimension3DUpdatable(20,20,1)); //Utils.Types.Integer.Dimension3DUpdatable.class 
    //Dimension3DUpdatable map_size = new Dimension3DUpdatable(10,10,1);
    if (map_size!=null) {
      System.out.println("New Map: "+map_size.toString());
      
      MapFoundationEditable map = new MapFoundationEditable(map_size.getImmutable());
      Object o = LoadManager.getItem(BaseTileComponent.class,"sand");
      for (int y=0 ; y<map.getDimension3D().getLength(); y++ ) {
        for (int x=0 ; x<map.getDimension3D().getWidth(); x++ ) {
          map.setLayer(o,x,y,0,BaseTileComponent.tile_layer);
        }
      }
      gui_manager.addWindow(new MapFrame(map));
    }
  }

}