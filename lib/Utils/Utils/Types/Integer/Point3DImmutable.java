package Utils.Types.Integer;

public class Point3DImmutable extends AbstractPoint3D {
      
  public static final Point3DImmutable ZERO = new Point3DImmutable(0,0,0);
  
  private final int x;
  private final int y;
  private final int z;

  public Point3DImmutable(int x, int y, int z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }
  
  public int x() {return x;}
  public int y() {return y;}
  public int z() {return z;}
  
  public static Point3DImmutable valueOf()                    {return ZERO;}
  public static Point3DImmutable valueOf(int x, int y, int z) {return new Point3DImmutable(x,y,z);}
  public static Point3DImmutable valueOf(Object o) {
    if      (o == null)            {return null;}
    else if (o instanceof String ) {return valueOf((String)o);}
    else if (o instanceof Point3D) {return valueOf((Point3D)o);}
    else                           {return valueOf(o.toString());}
  }
  public static Point3DImmutable valueOf(Point3D p) {
    if      (p==null                      ) {return null;}
    else if (p instanceof Point3DImmutable) {return (Point3DImmutable)p;}
    else                                    {return valueOf(p.x(),p.y(),p.z());}
  }
  public static Point3DImmutable valueOf(String s) {
    try {
      String[] ss = s.split(string_split);
      return valueOf(Integer.parseInt(ss[0]), 
                     Integer.parseInt(ss[1]), 
                     Integer.parseInt(ss[2]));
    }
    catch (Exception e) {throw new NumberFormatException();}
  }
  public static Point3D sub(Point3D a, Point3D b) {
    if (a==null) {return b;}
    if (b==null) {return a;}
    return valueOf(a.x()-b.x(),
                   a.y()-b.y(),
                   a.z()-b.z());
  }
  public static Point3D add(Point3D a, Point3D b) {
    if (a==null) {return b;}
    if (b==null) {return a;}
    return valueOf(a.x()+b.x(),
                   a.y()+b.y(),
                   a.z()+b.z());
  }
  
  public static Point3D minPoint(Point3D a, Point3D b) {
    if (a==null) {return b;}
    if (b==null) {return a;}
    return Point3DImmutable.valueOf(Math.min(a.x(),b.x()),
                                    Math.min(a.y(),b.y()),
                                    Math.min(a.z(),b.z()));
  }
  
  public static Point3D maxPoint(Point3D a, Point3D b) {
    if (a==null) {return b;}
    if (b==null) {return a;}
    return Point3DImmutable.valueOf(Math.max(a.x(),b.x()),
                                    Math.max(a.y(),b.y()),
                                    Math.max(a.z(),b.z()));
  }
  
  
  public Point3D add(Point3D p) {
    if (p==null) {return this;}
    else         {return add(p.x(),p.y(),p.z());}
  }
  public Point3D add(int x, int y, int z) {return valueOf(x()+x,y()+y,z()+z);}
  public Point3D sub(Point3D p) {
    if (p==null) {return this;}
    else         {return sub(p.x(),p.y(),p.z());}
  }
  public Point3D sub(int x, int y, int z) {return valueOf(x()-x,y()-y,z()-z);}
  

}
