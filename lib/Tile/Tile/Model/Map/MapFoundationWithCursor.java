package Tile.Model.Map;

import Utils.Types.Integer.Dimension3D;
import Utils.Types.Integer.Point3D;
import Utils.Types.Integer.Point3DImmutable;


public class MapFoundationWithCursor extends MapFoundation {
//-------------------------------------------------------------------------
// Variables
//-------------------------------------------------------------------------
  private Point3DImmutable cursor = null;
  
//-------------------------------------------------------------------------
// Constructors
//-------------------------------------------------------------------------
  public MapFoundationWithCursor(Dimension3D d)            {super(d);}
  public MapFoundationWithCursor(MapFoundation source_map) {super(source_map);}
  
//-------------------------------------------------------------------------
// Cursor
//-------------------------------------------------------------------------
  public Point3D getCursor(         ) {return cursor;}
  public void    setCursor(Point3D p) {cursor=Point3DImmutable.valueOf(p);}
}