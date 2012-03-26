package Utils.Types.Integer;

public class CubeUpdatable extends AbstractCube {
  
  private final Point3DUpdatable p;
  private final Dimension3DUpdatable d;
  
  public CubeUpdatable(Point3D p               ) {this(p,new Dimension3DUpdatable(1,1,1));}
  public CubeUpdatable(Point3D p, Dimension3D d) {
    if (p instanceof Point3DUpdatable)     {this.p = (Point3DUpdatable)p;}
    else                                   {this.p = new Point3DUpdatable(p);}
    if (d instanceof Dimension3DUpdatable) {this.d = (Dimension3DUpdatable)d;}
    else                                   {this.d = new Dimension3DUpdatable(d);}
  }

  public AbstractPoint3D     getPoint3D()     {return p;}
  public AbstractDimension3D getDimension3D() {return d;}
  
  public void setCube(Point3D a, Point3D b) {
    Point3D min_point = Point3DImmutable.minPoint(a,b);
    Point3D max_point = Point3DImmutable.maxPoint(a,b);
    p.setPoint(min_point);
    d.setDimension3D(min_point,max_point);
  }
  
  public void expandCubeToEncompusPoint(Point3D p_new) {
    if (inBounds(p_new)) {return;}
    Point3DUpdatable min_point = new Point3DUpdatable(getMinPoint());
    Point3DUpdatable max_point = new Point3DUpdatable(getMaxPoint());
    if (p_new.x()<min_point.x()) {min_point.setx(p_new.x());}
    if (p_new.y()<min_point.y()) {min_point.sety(p_new.y());}
    if (p_new.z()<min_point.z()) {min_point.setz(p_new.z());}
    if (p_new.x()>max_point.x()) {max_point.setx(p_new.x());}
    if (p_new.y()>max_point.y()) {max_point.sety(p_new.y());}
    if (p_new.z()>max_point.z()) {max_point.setz(p_new.z());}
    setCube(min_point,max_point);
  }

  public void normalize() {setCube(getMinPoint(),getMaxPoint());}
  
}
