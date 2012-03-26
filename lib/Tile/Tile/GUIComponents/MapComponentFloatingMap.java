package Tile.GUIComponents;

import Utils.Types.Integer.AbstractCube;
import Utils.Types.Integer.CubeImmutable;
import Utils.Types.Integer.Point3DImmutable;
import Utils.Types.Integer.Point3D;
import Tile.Model.Map.MapFoundation;
import Tile.Model.Map.MapFoundationEditableUndoable;
import Tile.View.MapFoundation.AbstractMapRenderer;
import java.awt.event.MouseEvent;



public class MapComponentFloatingMap extends MapComponentSelectable {

  private static final long serialVersionUID = 0;

  public MapComponentFloatingMap(MapFoundationEditableUndoable map) {super(map);}
  
  public MapFoundationEditableUndoable getMap() {return (MapFoundationEditableUndoable)super.getMap();}
  
  public void setFloatingMap(MapFoundation floating_map, Point3D offset) {
    rendering_options.add(AbstractMapRenderer.LABLENAME_FLOATING_MAP          ,floating_map);
    rendering_options.add(AbstractMapRenderer.LABLENAME_FLOATING_CURSOR_OFFSET,offset      );
  }
  public MapFoundation getFloatingMap()       {return rendering_options.get(MapFoundation.class,AbstractMapRenderer.LABLENAME_FLOATING_MAP);}
  public Point3D       getFloatCursorOffset() {return rendering_options.getPoint3D(AbstractMapRenderer.LABLENAME_FLOATING_CURSOR_OFFSET);}
  

  
  public boolean hasFloatingMap() {if (getFloatingMap()==null) {return false;} return true;}

  
  public void mousePressed(MouseEvent e) {
    Point3D cursor = mousePosToTileLocation(e);
    if (e.getButton()==MouseEvent.BUTTON1) {
      if      (hasFloatingMap())                   {pasteFloatingMap(cursor);}
      else if (getMap().isInsideSelection(cursor)) {
        getMap().newOperation("Floating Map");
        setFloatingMap(getMap().getSubMap(),
                       Point3DImmutable.sub(getMap().getSelectedAreasEncompasingCube().getPoint3D(),cursor));
        //getMap().eraseSelectedArea();
        cancelCurrentSelection();
        getMap().selectNone();
        repaintFloatingArea();
      //Move mode stuff to drag items around
      //System.out.println("Move mode not implemented yet");
      }
      else {super.mousePressed(e);}
    }
    else {super.mousePressed(e);}
  }
  
  public void mouseReleased(MouseEvent e) {
    if (hasFloatingMap()) {pasteFloatingMap(getSelectionStartPoint());}
    else                  {super.mouseReleased(e);}
  }
  
  public void mouseDragged(MouseEvent e) {
    if (hasFloatingMap()) {if (hasCursorMoved(e)) repaint();}
    else                  {super.mouseDragged(e);}
  }
  
  public void mouseMoved(MouseEvent e) {
    if (hasFloatingMap()) {if (hasCursorMoved(e)) repaint();}
    else                  {super.mouseMoved(e);}
  }
  
  private void pasteFloatingMap(Point3D p) {
    if (hasFloatingMap() && p!=null) {
      AbstractCube floating_map_area = getFloatingArea();
      getMap().pasteSubMap(getFloatingMap(),p,null);
      setFloatingMap(null,null);
      repaint(floating_map_area);
    }
  }
  
  private Point3D getSelectionStartPoint() {
    return Point3DImmutable.add(getMap().getCursor(),getFloatCursorOffset());
  }
  
  private AbstractCube getFloatingArea() {
    return CubeImmutable.valueOf(getSelectionStartPoint(),getFloatingMap().getDimension3D());
  }
  
  public void repaintFloatingArea() {
    repaint(getFloatingArea());
  }
}
