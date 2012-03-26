package MapEdit.GUIComponents;


import java.awt.event.MouseEvent;
import Tile.GUIComponents.MapComponentFloatingMap;
import Tile.Model.Map.MapFoundationEditableUndoable;
import MapEdit.Tools.MapTool;
import MapEdit.Tools.MapToolEvent;


public class MapComponentEditable extends MapComponentFloatingMap {
  
  private static final long serialVersionUID = 0;

  public MapComponentEditable(MapFoundationEditableUndoable map) {
    super(map);
  }
  
  //-------------------------------------------------------------------------
  // Public
  //-------------------------------------------------------------------------
  public MapFoundationEditableUndoable getMap() {return super.getMap();}
  
  public void mousePressed(MouseEvent e) {
    if (MapTool.getSelectedToolEnableSelection() || hasFloatingMap()) {super.mousePressed(e);}
    MapTool.applySelectedTool(new MapToolEvent(this, e));
  }
  public void mouseDragged(MouseEvent e) {
    if (MapTool.getSelectedToolEnableSelection() || hasFloatingMap()) {super.mouseDragged(e);}
    else { //the reason for the else is because super.mouseDragged calls hasCursorMoved. hasCursorMoved should only be called once or it creates multiple repaints and problems (selection dragged at limits conflit)
      if (hasCursorMoved(e)) {MapTool.applySelectedTool(new MapToolEvent(this, e));}
    }
  }
  public void mouseReleased(MouseEvent e) {
    MapTool.applySelectedTool(new MapToolEvent(this, e));
  }
}
