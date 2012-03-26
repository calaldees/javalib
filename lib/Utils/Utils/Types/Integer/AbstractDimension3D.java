package Utils.Types.Integer;
import java.awt.Rectangle;
import java.awt.Point;

public abstract class AbstractDimension3D extends AbstractDimension2D implements Dimension3D {

  public abstract int getHeight();
  
  public String toString() {return super.toString()+string_split+getHeight();}
  public int    hashCode() {return 37 * super.hashCode() + getHeight();}
  public boolean equals(Object o) {
    if (o==null) return false;
    if (o==this) return true;
    if (o instanceof Dimension3D) {
      Dimension3D d = (Dimension3D)o;
      return equals(d.getWidth(), d.getLength(), d.getHeight());
    }
    return false;
  }
  public boolean equals(int width, int length, int height) {
    return (this.getWidth()==width && this.getLength()==length && this.getHeight()==height);
  }

  public boolean isPositive() {return (super.isPositive() && getHeight()>0);}

  
  public boolean inBounds(int x, int y    )    {              return inBounds(x  ,y  ,0  );               }
  public boolean inBounds(Point2D p       )    {if (p!=null) {return inBounds(p.x(),p.y());} return false;}
  public boolean inBounds(Point   p       )    {if (p!=null) {return inBounds(p      ,0  );} return false;}
  public boolean inBounds(Point   p, int z)    {if (p!=null) {return inBounds(p.x,p.y,  z);} return false;}
  public boolean inBounds(Point3D p)           {if (p!=null) {return inBounds(p.x(),p.y(),p.z());} return false;}
  public boolean inBounds(int x, int y, int z) {
    return super.inBounds(x, y)
           && this.getHeight()>=0 && this.getHeight()<z;
  }
  public boolean inBounds(Rectangle r) {
    if (r==null) {return false;}
    return (inBounds(r.x,r.y) 
        && (r.x + r.width <=getWidth())
        && (r.y + r.height<=getLength()) );
  }

  //Should this be implemented in CUBE?
  /*
  public boolean inBounds(Cube s) {
    if (s==null && !s.isValid()) {return false;}
    Point3D     p = s.getPoint();
    Dimension3D d = s.getDimension();
    if (s.isPositive()
     && p.isPositive()
     && (p.x+d.x)<=getX()
     && (p.y+d.y)<=getY()
     && (p.z+d.z)<=getZ() ) {return true;}
     return false;
  }
*/
  
  /*
  public void setDimension(Dimension3D d) {
    x = d.x;
    y = d.y;
    z = d.z;
  }

  public void setDimension(int x, int y, int z) {
    if (x<1) {x=1;}
    if (y<1) {y=1;}
    if (z<1) {z=1;}
    this.x = x;
    this.y = y;
    this.z = z;
  }

  public void clear() {
    setDimension(0,0,0);
  }
*/

  public Point3D makeInBounds(Point3D p) {
    if (inBounds(p)) {return p;}
    else {
      Point3DUpdatable max_p = new Point3DUpdatable(p);
      if (max_p.x()<0           ) {max_p.setx(0); }
      if (max_p.y()<0           ) {max_p.sety(0); }
      if (max_p.z()<0           ) {max_p.setz(0); }
      if (max_p.x()>=getWidth() ) {max_p.setx(getWidth() -1);}
      if (max_p.y()>=getLength()) {max_p.sety(getLength()-1);}
      if (max_p.z()>=getHeight()) {max_p.setz(getHeight()-1);}
      return max_p.getImmutable();
    }
  }
  
}
