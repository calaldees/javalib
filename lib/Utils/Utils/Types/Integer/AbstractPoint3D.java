package Utils.Types.Integer;

//import java.awt.Point;

public abstract class AbstractPoint3D extends AbstractPoint2D implements Point3D {
  
  public abstract int x();
  public abstract int y();
  public abstract int z();
  
  public String toString() {return super.toString() + string_split + z();}
  public int hashCode()    {return 37 * super.hashCode() + z();}
  public boolean equals(Object o) {
    if (o==null) return false;
    if (o==this) return true;
    if (o instanceof Point3D) {
      Point3D p = (Point3D)o;
      return equals(p.x(),p.y(),p.z());
    }
    // breaks transetive rule :(
    //if (o instanceof Point) {
    //  Point p = (Point)o;
    //  return equals(p.x,p.y,0);
    //}
    return false;
  }
  public boolean equals(int x, int y, int z) {return super.equals(x,y) && z()==z;}
  public boolean equals(int x, int y       ) {return super.equals(x,y) && z()==0;}

  public boolean isPositive() {return (super.isPositive() && z()>=0);}

  public int distance(Point3D p)   {return (int)Math.sqrt(distanceSq(p));}
  public int distanceSq(Point3D p) {return Math.abs((p.x()*x())+(p.y()*y())+(p.z()*z()));}

}
