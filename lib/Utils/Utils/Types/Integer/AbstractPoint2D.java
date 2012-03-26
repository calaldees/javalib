package Utils.Types.Integer;

public abstract class AbstractPoint2D implements Point2D {
  
  public AbstractPoint2D() {
  }
  
  public abstract int x();
  public abstract int y();

  protected static final String string_split = ",";
  
  public String toString() {return x() + string_split + y();}
  public int hashCode()    {
    int result = 17;
    result = 37 * result + x();
    result = 37 * result + y();
    return result;
  }
  public boolean equals(Object o) {
    if (o==null) return false;
    if (o==this) return true;
    if (o instanceof Point2D) {
      Point2D p = (Point2D)o;
      return equals(p.x(),p.y());
    }
    // breaks transetive rule :(
    //if (o instanceof Point) {
    //  Point p = (Point)o;
    //  return equals(p.x,p.y,0);
    //}
    return false;
  }
  
  public boolean equals(int x, int y) {return (x()==x && y()==y);}

  public boolean isPositive() {return (x()>=0 && y()>=0);}

  public int distance(Point2D p)   {return (int)Math.sqrt(distanceSq(p));}
  public int distanceSq(Point2D p) {return Math.abs((p.x()*x())+(p.y()*y()));}
  
  
}
