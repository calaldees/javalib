package Tile.GUIComponents;

//import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import Utils.Types.Integer.*;
import Tile.Model.Map.MapFoundationSelectable;
import java.awt.event.MouseWheelEvent;

public class MapComponentSelectable extends MapComponentWithCursor {
  
  private static final long serialVersionUID = 0;

  private CubeUpdatable selection_in_progress;
  private Point3D       anchor_point;
  //private Dimension3DUpdatable selected_area;
  
  public MapComponentSelectable(MapFoundationSelectable map) {
    super(map);
  }
  
  //-------------------------------------------------------------------------
  // Public
  //-------------------------------------------------------------------------
  public MapFoundationSelectable getMap() {return (MapFoundationSelectable)super.getMap();}
  
  //-------------------------------------------------------------------------
  // Mouse Listener Overrides
  //-------------------------------------------------------------------------
  
  public void mousePressed(MouseEvent e) {
    
    Point3D cursor = mousePosToTileLocation(e);
    //If right click, unselect all
    if (e.getButton()==MouseEvent.BUTTON3) {
      getMap().selectNone();
      selection_in_progress = null;
      repaint();
    }
    else {
      boolean has_selected_area = getMap().hasSelectedArea();

      //System.out.println("make selection point: "+cursor);
      if (getMap().inBounds(cursor)) {
        //Point3D p                     = Point3DImmutable.valueOf(cursor);
        //        selected_area         = new Dimension3DUpdatable();

        anchor_point          = Point3DImmutable.valueOf(cursor);
        selection_in_progress = new CubeUpdatable(anchor_point);

        if (e.isShiftDown()) {
          getMap().addSelection(selection_in_progress);
          repaint(e);
        }
        else {
          getMap().selectNone();
          getMap().addSelection(selection_in_progress);
          repaint();
        }
      }
    }
  }
  
  public void mouseDragged(MouseEvent e) {
    //System.out.println("CURSOR:"+getMap().getCursor());
    if (selection_in_progress!=null && hasCursorMoved(e,true) ) {
      selection_in_progress.setCube(anchor_point, getMap().getCursor());
      //System.out.println("dragged moved "+selected_area+ " anchor:"+selection_in_progress.getPoint3D()+" cursor:"+getMap().getCursor());
      repaint(selection_in_progress);
    }
  }

  /*
  public void mouseExited(MouseEvent e) {
    selection_in_progress=null;
    selected_area        =null;
    super.mouseExited(e);
  }
  */
  
  public void mouseWheelMoved(MouseWheelEvent e) {
    //super.mouseWheelMoved(e);
    mouseDragged(e);
  }
  
  protected void cancelCurrentSelection() {
    selection_in_progress = null;
  }
  
}
