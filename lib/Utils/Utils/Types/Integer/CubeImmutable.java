package Utils.Types.Integer;

public class CubeImmutable extends AbstractCube {
  
  private final Point3DImmutable     p;
  private final Dimension3DImmutable d;
  
  public CubeImmutable(Point3D p, Dimension3D d) {
    this.p = Point3DImmutable.valueOf(p);
    this.d = Dimension3DImmutable.valueOf(d);
  }
  
  public AbstractPoint3D     getPoint3D()     {return p;}
  public AbstractDimension3D getDimension3D() {return d;}
  
  public static CubeImmutable valueOf(Point3D p, Dimension3D d) {return new CubeImmutable(p,d);}
  public static CubeImmutable valueOf(int x, int y, int z, int width, int height, int depth) {
    return valueOf(Point3DImmutable.valueOf(x,y,z),
                   Dimension3DImmutable.valueOf(width,height,depth));
  }
  public static CubeImmutable valueOf(Point3D a           ) {return valueOf(a,a);}
  public static CubeImmutable valueOf(Point3D a, Point3D b) {
    return valueOf(Point3DImmutable.minPoint(a,b), Dimension3DImmutable.valueOf(a,b));
  }
}
