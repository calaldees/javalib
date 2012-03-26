package Utils.Types.Integer;

import Utils.MathHelper;
import Utils.XML.XMLLoad.Validatable;

public abstract class AbstractCube implements Cube, Validatable {

  public abstract AbstractPoint3D     getPoint3D();
  public abstract AbstractDimension3D getDimension3D();
  
  public String toString() {return getClass().getName()+": "+getPoint3D()+" "+getDimension3D();}
  //public Cube   clone()    {return this;}
  public int hashCode()    {
    int result = 17;
    result = 37 * result + getPoint3D().hashCode();
    result = 37 * result + getDimension3D().hashCode();
    return result;
  }
  public boolean equals(Object o) {
    if (o==null) return false;
    if (o==this) return true;
    if (o instanceof Cube) {
      Cube c = (Cube)o;
      return getPoint3D().equals(c.getPoint3D()) && getDimension3D().equals(c.getDimension3D());
    }
    return false;
  }
  
  public boolean isValid() {
    if (getPoint3D()==null || getDimension3D()==null || !isPositive()) {return false;}
    else                                                               {return true;}
  }
  
  
  public boolean inBounds(Point3D c) {if (c!=null) {return inBounds(c.x(),c.y(),c.z());} return false;}
  public boolean inBounds(int x, int y, int z) {
    Point3D     p = getPoint3D();
    Dimension3D d = getDimension3D();
    return ( MathHelper.between(p.x(), p.x()+d.getWidth() , x)  //WRONG!!! FIX IT NOW!!!
         &&  MathHelper.between(p.y(), p.y()+d.getLength(), y)
         &&  MathHelper.between(p.z(), p.z()+d.getHeight(), z) );
  }

  public boolean inBounds(Cube c) {
    Point3D     ap =   getPoint3D();
    Dimension3D ad =   getDimension3D();
    Point3D     bp = c.getPoint3D();
    Dimension3D bd = c.getDimension3D();
    return (bp.x()>ap.x()
         && bp.y()>ap.y()
         && bp.z()>ap.z()
         && bp.x()+bd.getWidth()  <= ap.x()+ad.getWidth()
         && bp.y()+bd.getLength() <= ap.y()+ad.getLength()
         && bp.z()+bd.getHeight() <= ap.z()+ad.getHeight());
  }
  
  public boolean isPositive() {
    if (getPoint3D().isPositive() && getDimension3D().isPositive()) {return true;}
    else                                                            {return false;}
  }

  public Point3D getMinPoint()         {return Point3DImmutable.valueOf(getLeft()  ,
                                                                        getTop()   ,
                                                                        getFloor()  );}
  public Point3D getMaxPoint()         {return Point3DImmutable.valueOf(getRight() ,
                                                                        getBottom(),
                                                                        getCeling() );}
  public Point3D getPointBottomLeft()  {return Point3DImmutable.valueOf(getLeft()  ,
                                                                        getBottom(),
                                                                        getFloor()  );}
  public Point3D getPointTopRight()    {return Point3DImmutable.valueOf(getRight() ,
                                                                        getTop()   ,
                                                                        getCeling() );}
  public Point3D getPointTopLeft()     {return Point3DImmutable.valueOf(getLeft()  ,
                                                                        getTop()   ,
                                                                        getCeling() );}
  public Point3D getPointBottomRight() {return Point3DImmutable.valueOf(getRight() ,
                                                                        getBottom(),
                                                                        getFloor()  );}
  private int getLeft()   {return Math.min(getPoint3D().x(),getPoint3D().x()+getDimension3D().getWidth() -1);} // WRONG? Do we need the -1?
  private int getRight()  {return Math.max(getPoint3D().x(),getPoint3D().x()+getDimension3D().getWidth() -1);}
  private int getTop()    {return Math.min(getPoint3D().y(),getPoint3D().y()+getDimension3D().getLength()-1);}
  private int getBottom() {return Math.max(getPoint3D().y(),getPoint3D().y()+getDimension3D().getLength()-1);}
  private int getFloor()  {return Math.min(getPoint3D().z(),getPoint3D().z()+getDimension3D().getHeight()-1);}
  private int getCeling() {return Math.max(getPoint3D().z(),getPoint3D().z()+getDimension3D().getHeight()-1);}

  
  public BoundingFlags getBoundingFlags(Point3D p                     ) {return getBoundingFlags(p, null);}
  public BoundingFlags getBoundingFlags(Point3D p, BoundingFlags flags) {
    if (p    ==null) {return null;}
    if (flags==null) {flags = new BoundingFlags();}
    //System.out.println("Check:"+p+" Height:"+d.getHeight());
    if (inBounds(p)) {
      //System.out.println("inside");
      flags.inbounds = true;
      if (p.x()==getLeft()  ) {flags.left  =true;}
      if (p.x()==getRight() ) {flags.right =true;}
      if (p.y()==getTop()   ) {flags.top   =true;}
      if (p.y()==getBottom()) {flags.bottom=true;}
      if (p.z()==getCeling()) {flags.celing=true;}
      if (p.z()==getFloor() ) {flags.floor =true;}
    }
    return flags;
  }
  
  

  
}
