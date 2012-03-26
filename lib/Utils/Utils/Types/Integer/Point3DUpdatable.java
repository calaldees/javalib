package Utils.Types.Integer;

public class Point3DUpdatable extends AbstractPoint3D {
  
  public int x;
  public int y;
  public int z;
  
  public Point3DUpdatable()          {this(Point3DImmutable.ZERO);}
  public Point3DUpdatable(Point3D p) {
    this.x = p.x();
    this.y = p.y();
    this.z = p.z();
  }
  public Point3DUpdatable(int x, int y, int z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  public int x() {return x;}
  public int y() {return y;}
  public int z() {return z;}
  
  public static Point3D valueOf(String s) {return new Point3DUpdatable(Point3DImmutable.valueOf(s));}
  
  public Point3DImmutable getImmutable() {return Point3DImmutable.valueOf(x(),y(),z());}
  
  public void setx(int x) {this.x = x;}
  public void sety(int y) {this.y = y;}
  public void setz(int z) {this.z = z;}
  public void setPoint(Point3D p) {
    if (p==null) {clear();                    }
    else         {setPoint(p.x(),p.y(),p.z());}
  }
  public void setPoint(int x, int y, int z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  public void clear() {setPoint(0,0,0);}

  public void add(Point3D p          ) {if (p!=null) {add(p.x(),p.y(),p.z());}}
  public void add(int x, int y, int z) {
    this.x += x;
    this.y += y;
    this.z += z;
  }
  
}