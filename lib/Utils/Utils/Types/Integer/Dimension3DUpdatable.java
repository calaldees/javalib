package Utils.Types.Integer;


public class Dimension3DUpdatable extends AbstractDimension3D {
  
  public int width;
  public int length;
  public int height;
  
  public Dimension3DUpdatable() {}
  
  public Dimension3DUpdatable(Dimension3D d) {
    if (d==null) {throw new IllegalArgumentException();}
    width  = d.getWidth();
    length = d.getLength();
    height = d.getHeight();
  }
  
  public Dimension3DUpdatable(int width, int length, int height) {
    this.width  = width;
    this.length = length;
    this.height = height;
  }

  public int getWidth()  {return width;}
  public int getLength() {return length;}
  public int getHeight() {return height;}

  public void setWidth (int width ) {this.width  = width ;}
  public void setLength(int length) {this.length = length;}
  public void setHeight(int height) {this.height = height;}
  
  public Dimension3D clone() {return new Dimension3DUpdatable(this);}
  
  public Dimension3DImmutable getImmutable() {return Dimension3DImmutable.valueOf(this);}

  public void setDimension3D(Dimension3D d                    ) {if (d!=null) {setDimension3D(d.getWidth(),d.getLength(),d.getHeight());} else {setDimension3D(0,0,0);}}
  public void setDimension3D(int width, int length, int height) {
    this.width  = width;
    this.length = length;
    this.height = height;
  }
  public void setDimension3D(Point3D a, Point3D b) {
    if (a!=null && b!=null) {
      width  = b.x()-a.x(); width  += (width >=0 ? 1:-1); //WRONG!!! FIX IT NOW!!!
      length = b.y()-a.y(); length += (length>=0 ? 1:-1);
      height = b.z()-a.z(); height += (height>=0 ? 1:-1);
    }
  }
  
}
