package Utils.Types.Integer;

import java.awt.Dimension;
import java.awt.Point;

public abstract class AbstractDimension2D implements Dimension2D {

  protected static final String string_split = ",";
 
  public abstract int getWidth();
  public abstract int getLength();  
  
  
  public String toString() {return getWidth()+string_split+getLength();}

  public int hashCode()    {
    int result = 17;
    result = 37 * result + getWidth();
    result = 37 * result + getLength();
    return result;
  }
  public boolean equals(Object o) {
    if (o==null) return false;
    if (o==this) return true;
    if (o instanceof Dimension2D) {
      Dimension2D d = (Dimension2D)o;
      return equals(d.getWidth(), d.getLength());
    }
    return false;
  }
  public boolean equals(int width, int length) {
    return (this.getWidth()==width && this.getLength()==length);
  }

  public boolean isPositive() {return (getWidth()>0 && getLength()>0);}

  public boolean inBounds(Point2D p) {if (p!=null) {return inBounds(p.x(),p.y());} return false;}
  public boolean inBounds(Point   p) {if (p!=null) {return inBounds(p.x  ,p.y  );} return false;}
  public boolean inBounds(int x, int y    )    {
    return (x >=0 && x < getWidth()
         && y >=0 && y < getLength());
  }
  
  public Dimension getAWTDimension() {return new Dimension(getWidth(),getLength());}
}
