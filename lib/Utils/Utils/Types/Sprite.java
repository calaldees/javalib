package Utils.Types;

import java.awt.Image;
import java.awt.Graphics;
import java.awt.image.ImageObserver;

import Utils.ImageLoader.ImageLoader;
import Utils.Types.Integer.Dimension2DImmutable;
import Utils.Types.Integer.Point2DImmutable;


public class Sprite {

  private static final ImageObserver null_observer = new NullObserver();//new java.awt.Container();
  
  private final Image                img;
  private final Point2DImmutable     offset;
  private final Dimension2DImmutable size;

  public Sprite(Image img, int x, int y, int width, int height) throws Exception {
    this.img    = ImageLoader.loadImage(img);
    this.offset = new Point2DImmutable(x,y);
    this.size   = new Dimension2DImmutable(width,height);
    if (this.img==null) {throw new Exception("Sprite constructor given null image");}
    if (this.offset.x()<0 || 
        this.offset.y()<0 || 
        this.size.getWidth()  <0 ||
        this.size.getLength() <0) {throw new Exception("Sprite constructor out of range");}
  }
  
  public Image getImage()       {return img;}
  public int   getImageWidth () {return img.getWidth (null_observer);}
  public int   getImageHeight() {return img.getHeight(null_observer);}
  
  public Point2DImmutable getOffset()  {return offset;}
  public int              getXoffset() {return offset.x();}
  public int              getYoffset() {return offset.y();}
  
  public Dimension2DImmutable getSize()   {return size;}
  public int                  getWidth()  {return size.getWidth();}
  public int                  getHeight() {return size.getLength();}

  public String toString() {
    return "Sprite: size="+size+" offset="+offset;
  }

  public void render(Graphics g                                ) {render(g,null    );}
  public void render(Graphics g, ImageObserver io              ) {render(g,io  ,0,0);}
  public void render(Graphics g, ImageObserver io, int x, int y) {g.drawImage(img,x+getXoffset(),y+getYoffset(),io);}
}

//WARNING! this is a hack ... when drawing there must be an image observer ... in future render(Graphics g) should also take an image observer
class NullObserver implements ImageObserver {
  public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {return false;}
}