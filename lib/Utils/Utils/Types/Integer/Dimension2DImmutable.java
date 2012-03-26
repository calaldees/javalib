package Utils.Types.Integer;

public class Dimension2DImmutable extends AbstractDimension2D {

  public static final Dimension2DImmutable ONE = new Dimension2DImmutable(1,1);
  
  private final int width;
  private final int length;
  
  public Dimension2DImmutable(int width, int length) {
    this.width  = width;
    this.length = length;
  }

  public int getWidth()  {return width;}
  public int getLength() {return length;}


  public static Dimension2DImmutable valueOf()              {return ONE;}
  public static Dimension2DImmutable valueOf(Dimension2D d) {
    if (d instanceof Dimension2DImmutable) {return (Dimension2DImmutable)d;}
    else                                   {return Dimension2DImmutable.valueOf(d.getWidth(),d.getLength());}
  }
  public static Dimension2DImmutable valueOf(int width, int length) {return new Dimension2DImmutable(width,length);}
  public static Dimension2DImmutable valueOf(Object o) {
    if      (o == null)                {return valueOf();}
    else if (o instanceof String )     {return valueOf((String)o);}
    else if (o instanceof Dimension2D) {return valueOf((Dimension2D)o);}
    else                               {return valueOf(o.toString());}
  }
  public static Dimension2DImmutable valueOf(String s) {
    try {
      String[] ss = s.split(string_split);
      return valueOf(Integer.parseInt(ss[0]), 
                     Integer.parseInt(ss[1]));
    }
    catch (Exception e) {throw new NumberFormatException();}
  }

  public static Dimension2DImmutable valueOf(Point2D a, Point2D b) {
    return Dimension2DImmutable.valueOf(Math.abs(a.x()-b.x())+1,
                                        Math.abs(a.y()-b.y())+1 );
  }
  
}
