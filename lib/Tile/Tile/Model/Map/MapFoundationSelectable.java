package Tile.Model.Map;

import java.util.Collection;
import java.util.Vector;

import Utils.Types.Integer.*;


public class MapFoundationSelectable extends MapFoundationWithCursor {
//-------------------------------------------------------------------------
// Variables
//-------------------------------------------------------------------------

  private Collection<AbstractCube> selections = new Vector<AbstractCube>();
  
//-------------------------------------------------------------------------
// Constructors
//-------------------------------------------------------------------------
  public MapFoundationSelectable(Dimension3D d)            {super(d);}
  public MapFoundationSelectable(MapFoundation source_map) {super(source_map);}
  
//-------------------------------------------------------------------------
// Selection Management
//-------------------------------------------------------------------------

  public Collection<AbstractCube> getSelections() {return selections;}
  
  public void setSelection(AbstractCube c) {
    selectNone();
    addSelection(c);
  }
  
  public void addSelection(AbstractCube c) {selections.add(c);}
  
  public boolean hasSelectedArea() {
    if (getSelections().size()>0) {return true;}
    else                          {return false;}
  }
  
  public boolean isInsideSelection(Point3D p) {
    for (AbstractCube selection : getSelections()) {
      if (selection.inBounds(p)) {return true;}
    }
    return false;
  }

  public void selectNone() {selections.clear();}
  public void selectAll()  {
    selectNone();
    addSelection(CubeImmutable.valueOf(Point3DImmutable.ZERO,getDimension3D()));
  }

  public Cube getSelectedAreasEncompasingCube() {
    return CubeImmutable.valueOf(getSelectedAreasReferencePoint(),getSelectedAreasEncompasingDimesion());
  }
  
  public BoundingFlags getBoundingFlags(Point3D p) {
    BoundingFlags flags = new BoundingFlags();
    for (AbstractCube selection : getSelections()) {
      selection.getBoundingFlags(p,flags);
    }
    return flags;
  }
  
//-------------------------------------------------------------------------
// Private - Selection Area Helpers
//-------------------------------------------------------------------------

  protected Point3D getSelectedAreasReferencePoint() {
    if (!hasSelectedArea()) {return Point3DImmutable.ZERO;}
    Point3DUpdatable min = new Point3DUpdatable(getDimension3D().getWidth(), getDimension3D().getLength(), getDimension3D().getHeight());
    for (Cube selection : getSelections()) {
      int x = selection.getPoint3D().x();
      int y = selection.getPoint3D().y();
      int z = selection.getPoint3D().z();
      if (min.x()>x) {min.setx(x);}
      if (min.y()>y) {min.sety(y);}
      if (min.z()>z) {min.setz(z);}
    }
    return min.getImmutable();
  }
  
  private Dimension3D getSelectedAreasEncompasingDimesion() {
    if (!hasSelectedArea()) {return getDimension3D();}
    
    Point3D          min = getSelectedAreasReferencePoint();
    Point3DUpdatable max = new Point3DUpdatable();
    for (Cube selection : getSelections()) {
      //Is selction a max
      int x = selection.getPoint3D().x() + selection.getDimension3D().getWidth();
      int y = selection.getPoint3D().y() + selection.getDimension3D().getLength();
      int z = selection.getPoint3D().z() + selection.getDimension3D().getHeight();
      if (max.x()<x) {max.setx(x);}
      if (max.y()<y) {max.sety(y);}
      if (max.z()<z) {max.setz(z);}
    }
    
    return Dimension3DImmutable.valueOf(Math.abs(min.x()-max.x()), 
                                        Math.abs(min.y()-max.y()), 
                                        Math.abs(min.z()-max.z()));
    
  }
  
  
  
}
