package Utils.Types.Integer;

public class Point2DImmutable extends AbstractPoint2D {

  public static final Point2DImmutable ZERO = new Point2DImmutable(0,0);
  
  private final int x;
  private final int y;

  public Point2DImmutable(int x, int y) {
    this.x = x;
    this.y = y;
  }
  
  public int x() {return x;}
  public int y() {return y;}
  
  public static Point2DImmutable valueOf()             {return ZERO;}
  public static Point2DImmutable valueOf(int x, int y) {return new Point2DImmutable(x,y);}
  public static Point2DImmutable valueOf(Object o) {
    if      (o == null)            {return null;}
    else if (o instanceof String ) {return valueOf((String)o);}
    else if (o instanceof Point2D) {return valueOf((Point2D)o);}
    else                           {return valueOf(o.toString());}
  }
  public static Point2DImmutable valueOf(Point2D p) {
    if      (p==null                      ) {return null;}
    else if (p instanceof Point2DImmutable) {return (Point2DImmutable)p;}
    else                                    {return valueOf(p.x(),p.y());}
  }
  public static Point2DImmutable valueOf(String s) {
    try {
      String[] ss =  stripBrackets(s).split(string_split); //RegExHelper.search(s, "(.*)[,|](.*)");
      return valueOf(Integer.parseInt(ss[0]), 
                     Integer.parseInt(ss[1]));
    }
    catch (Exception e) {throw new NumberFormatException();}
  }
  public static Point2DImmutable[] valueOf(String[] ss) {
    Point2DImmutable[] points = new Point2DImmutable[ss.length];
    for (int i = 0 ; i < ss.length ; i++) {
      points[i] = valueOf(ss[i]);
    }
    return points;
  }
  
  
  public static Point2D sub(Point2D a, Point2D b) {
    return valueOf(a.x()-b.x(),
                   a.y()-b.y());
  }
  public static Point2D add(Point2D a, Point2D b) {
    return valueOf(a.x()+b.x(),
                   a.y()+b.y());
  }
  
  public static Point2D minPoint(Point2D a, Point2D b) {
    if (a==null) {return b;}
    if (b==null) {return a;}
    return valueOf(Math.min(a.x(),b.x()),
                   Math.min(a.y(),b.y()));
  }
  
  public static Point2D maxPoint(Point2D a, Point2D b) {
    if (a==null) {return b;}
    if (b==null) {return a;}
    return valueOf(Math.max(a.x(),b.x()),
                   Math.max(a.y(),b.y()));
  }
  
  public Point2D add(Point2D p) {
    if (p==null) {return this;}
    else         {return add(p.x(),p.y());}
  }
  public Point2D add(int x, int y) {return valueOf(x()+x,y()+y);}
  
  public Point2D sub(Point2D p) {
    if (p==null) {return this;}
    else         {return sub(p.x(),p.y());}
  }
  public Point2D sub(int x, int y) {return valueOf(x()-x,y()-y);}
  

  //This ia a hacky crox of crap ... I want it to genericly strip any type of brackets .. oh well .. maybe when I have more time
  private static String stripBrackets(String s) {
    int start = 0;
    int end   = s.length();
    if (s.indexOf    ('(')>=0) {start = s.indexOf    ('(')+1;}
    if (s.lastIndexOf(')')>=0) {end   = s.lastIndexOf(')');}
    return s.substring(start, end);
  }
  
}
