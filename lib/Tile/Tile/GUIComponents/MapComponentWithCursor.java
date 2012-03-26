package Tile.GUIComponents;


import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import Tile.Model.Map.MapFoundationWithCursor;
import Utils.Types.Integer.*;

public class MapComponentWithCursor extends MapComponent implements MouseListener, MouseMotionListener, MouseWheelListener {

  private static final long serialVersionUID = 0;
  
//-------------------------------------------------------------------------
// Constructor
//-------------------------------------------------------------------------
  
  public MapComponentWithCursor(MapFoundationWithCursor map) {
    super(map);
    addMouseListener(this);
    addMouseMotionListener(this);
    addMouseWheelListener(this);
  }
  
//-------------------------------------------------------------------------
// Public
//-------------------------------------------------------------------------
  public MapFoundationWithCursor getMap() {return (MapFoundationWithCursor)super.getMap();}
  
//-------------------------------------------------------------------------
// Mouse Events
//-------------------------------------------------------------------------
  public void mouseClicked(MouseEvent e) {}
  public void mousePressed(MouseEvent e) {}
  public void mouseReleased(MouseEvent e) {}
  public void mouseEntered(MouseEvent e) {}
  public void mouseExited(MouseEvent e) {
    if (getMap().getCursor()!=null) {
      Rectangle clip = getClip(getMap().getCursor());
      getMap().setCursor(null);
      repaint(clip); //this was orriginally breaking clip into clip.width etc to pass to repaint(int,int,int,int) ... why? ... comment is here in case of bug later
    }
  }
  public void mouseDragged(MouseEvent e) {mouseMoved(e);}
  public void mouseMoved(MouseEvent e)   {hasCursorMoved(e);}
  public void mouseWheelMoved(MouseWheelEvent e) {
    int ticks = e.getWheelRotation();
    if      (ticks>0) {setLayerValueUp();}
    else if (ticks<0) {setLayerValueDown();}
    hasCursorMoved(e);
  }

  
//-------------------------------------------------------------------------
// Protected
//-------------------------------------------------------------------------
  

//-------------------------------------------------------------------------
// Repaint Assistance
//-------------------------------------------------------------------------

  public void repaint(Point3D a           ) {repaint(getClip(a   ));}
  public void repaint(Point3D a, Point3D b) {repaint(getClip(a, b));}

  public void repaint(MouseEvent e) {repaint(mousePosToTileLocation(e));}
  
  public void repaint(Rectangle clip) {
    if (clip!=null) {super.repaint(clip);}  // repaint(clip.x, clip.y, clip.width, clip.height);}
  }
  
  public void repaint(AbstractCube c) {repaint(getClip(c));}
  
  
//-------------------------------------------------------------------------
// Private
//-------------------------------------------------------------------------

  protected boolean hasCursorMoved(MouseEvent e                          ) {return hasCursorMoved(e,false);}
  protected boolean hasCursorMoved(MouseEvent e, boolean strict_in_bounds) {
    Point3D new_cursor = mousePosToTileLocation(e);
//System.out.println("NEW_RAW:"+new_cursor+" strict_in_bounds:"+strict_in_bounds);
    Point3D old_cursor = getMap().getCursor();
    if (strict_in_bounds) {new_cursor = getMap().getDimension3D().makeInBounds(new_cursor);}
/*
    if (strict_in_bounds && !getMap().inBounds(new_cursor)) {
      Point3DUpdatable max_cursor = new Point3DUpdatable(new_cursor);
      Dimension3D      map_size   = getMap().getDimension3D();
      if (max_cursor.x()<0                    ) {max_cursor.setx(0); }
      if (max_cursor.y()<0                    ) {max_cursor.sety(0); }
      if (max_cursor.z()<0                    ) {max_cursor.setz(0); }
      if (max_cursor.x()>=map_size.getWidth() ) {max_cursor.setx(map_size.getWidth() -1);}
      if (max_cursor.y()>=map_size.getLength()) {max_cursor.sety(map_size.getLength()-1);}
      if (max_cursor.z()>=map_size.getHeight()) {max_cursor.setz(map_size.getHeight()-1);}
      new_cursor = max_cursor.getImmutable();
    }
 */
    if (!strict_in_bounds && !getMap().inBounds(new_cursor)) {new_cursor=null;}
//System.out.println("NEW_PROCESSED:"+new_cursor);
    //try {throw new Exception("test");} catch (Exception exp) {exp.printStackTrace();}
    if ( (old_cursor==null && new_cursor!=null) || (old_cursor!=null && !old_cursor.equals(new_cursor)) ) {
//System.out.println("SET");
      getMap().setCursor(new_cursor);
      repaint(getCursorClip(old_cursor,new_cursor));
      return true;
    }
    return false;
  }
  
  protected Point3D mousePosToTileLocation(MouseEvent e) {
    Point   mouse_pos  = e.getPoint();
    Point3D new_cursor = getMapTileLocation(mouse_pos);
    return new_cursor;
  }

  private Rectangle getClip(AbstractCube c) {
    if (c==null) {return null;}
    return getClip(c.getPointBottomLeft() ,
                   c.getPointBottomRight(),
                   c.getPointTopLeft()    ,
                   c.getPointTopRight()    );
  }
  
  private Rectangle getClip(Point3D a, Point3D b, Point3D c, Point3D d) {
    return getClip(a).union(getClip(b)).union(getClip(c)).union(getClip(d));
  }

  
  private Rectangle getCursorClip(Point3D a, Point3D b) {return getClip(getCursorClip(a),getCursorClip(b));}
  private Rectangle getCursorClip(Point3D p           ) {
    if (p==null) {return null;}
    Rectangle clip = getClip(p);
    //clip.height += Z_JUMP * p.z();
    return clip;
  }
  
  private Rectangle getClip(Point3D a, Point3D b) {
    return getClip(getClip(a),getClip(b));
  }
  
  private Rectangle getClip(Rectangle a, Rectangle b) {
    if (a==null && b==null) {return null;}
    if (a==null && b!=null) {return b;}
    if (a!=null && b==null) {return a;}
    return a.union(b);
  }

}
