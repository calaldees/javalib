package MapEdit.Tools;

import Tile.Model.Map.MapFoundationEditableUndoable;



public class MapToolBrush extends MapTool {
  
  public MapToolBrush() {
    super("Brush",null,false,true);
  }

  public void apply(MapToolEvent e) {
    if (getSelectedTile()!=null) {
      MapFoundationEditableUndoable map = e.getMap();
      map.mergeTile(getSelectedTile(),map.getCursor());
    }
  }

}