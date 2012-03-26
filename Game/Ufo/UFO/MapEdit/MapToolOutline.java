package UFO.MapEdit;

import java.awt.event.MouseEvent;
import Utils.Types.Integer.*;
import Tile.Map.MapFoundationEditableUndoable;
import MapEdit.Tools.MapTool;
import MapEdit.Tools.MapToolEvent;

public class MapToolOutline extends MapTool {
  
  public MapToolOutline() {super("Outline",null,true,false);}

  public void apply(MapToolEvent e) {
    if (e.getMouseEvent().getButton()   ==MouseEvent.BUTTON1        && 
        e.getMouseEvent().getModifiers()==MouseEvent.MOUSE_RELEASED &&
        e.getMap().hasSelectedArea()) {
      MapFoundationEditableUndoable map   = e.getMap();
      OutlineFramework              frame = getSelectedOutlineFramework();
      for (AbstractCube selection : e.getMap().getSelections()) {
        Point3DUpdatable p   = new Point3DUpdatable(selection.getMinPoint());
        Point3D          max =                      selection.getMaxPoint();
        for (; p.z<=max.z() ; p.z++) {
          for (; p.y<=max.y() ; p.y++) {
            for (; p.x<=max.x() ; p.x++) {
              BoundingFlags flags = e.getMap().getBoundingFlags(p);
              if (flags.left  ) {map.mergeTile(frame.getTopLeft()  ,p);}
              if (flags.top   ) {map.mergeTile(frame.getTopRight() ,p);}
              if (flags.floor ) {map.mergeTile(frame.getFloorBase(),p);}
              if (flags.right ) {map.mergeTile(frame.getBottomRight(), null,p.x+1,p.y  ,p.z  );}
              if (flags.bottom) {map.mergeTile(frame.getBottomLeft() , null,p.x  ,p.y+1,p.z  );}
              if (flags.celing) {map.mergeTile(frame.getCelling()    , null,p.x  ,p.y  ,p.z+1);}
              if (flags.inbounds && !flags.floor) {map.mergeTile(frame.getFloor(),p);}
            }
          }
        }
        e.getMapComponent().repaint(selection);
      }
      map.selectNone();
    }
  }
  
  private OutlineFramework getSelectedOutlineFramework() {
    return null;
  }
  
}
