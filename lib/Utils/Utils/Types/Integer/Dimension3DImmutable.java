package Utils.Types.Integer;

public class Dimension3DImmutable extends AbstractDimension3D {
  
  public static final Dimension3DImmutable ONE = new Dimension3DImmutable(1,1,1);
  
  private final int width;
  private final int length;
  private final int height;
  
  public Dimension3DImmutable(int width, int length, int height) {
    this.width  = width;
    this.length = length;
    this.height = height;
  }

  public int getWidth()  {return width;}
  public int getLength() {return length;}
  public int getHeight() {return height;}

  public static Dimension3DImmutable valueOf()              {return ONE;}
  public static Dimension3DImmutable valueOf(Dimension3D d) {
    if (d instanceof Dimension3DImmutable) {return (Dimension3DImmutable)d;}
    else                                   {return Dimension3DImmutable.valueOf(d.getWidth(),d.getLength(),d.getHeight());}
  }
  public static Dimension3DImmutable valueOf(int width, int length, int height) {return new Dimension3DImmutable(width,length,height);}
  public static Dimension3DImmutable valueOf(Object o) {
    if      (o == null)                {return valueOf();}
    else if (o instanceof String )     {return valueOf((String)o);}
    else if (o instanceof Dimension3D) {return valueOf((Dimension3D)o);}
    else                               {return valueOf(o.toString());}
  }
  public static Dimension3DImmutable valueOf(String s) {
    try {
      String[] ss = s.split(string_split);
      if (ss.length==2) {return valueOf(Integer.parseInt(ss[0]),
                                        Integer.parseInt(ss[1]),
                                                             1 );}
      if (ss.length==3) {return valueOf(Integer.parseInt(ss[0]),
                                        Integer.parseInt(ss[1]),
                                        Integer.parseInt(ss[2]));}
    }
    catch (Exception e) {}
    throw new NumberFormatException();
  }

  public static Dimension3DImmutable valueOf(Point3D a, Point3D b) {
    return Dimension3DImmutable.valueOf(Math.abs(a.x()-b.x())+1,
                                        Math.abs(a.y()-b.y())+1,
                                        Math.abs(a.z()-b.z())+1 );
  }
  
}
