package Utils.ImageLoader;

import Utils.Caster;
import Utils.Types.Sprite;

import java.awt.Point;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
//import java.awt.image.PixelGrabber;
import java.util.Vector;
import java.util.HashMap;

import static Utils.ImageLoader.ImageBlockProcessor.Direction;

class ImageBlockProcessor {

  public static enum Direction   {Up, Down, Left, Right};  
  
  Vector<Vector<Sprite>> sprites      = new Vector<Vector<Sprite>>(); //2D Grid of sprites
  Vector<Sprite>         sprite_list  = new Vector<Sprite>();         //List of loaded sprites in order (without null elements)
  HashMap<String,Point>  name_overlay = new HashMap<String,Point>();
  
  //-------------------------------------------------------
  // Constructor
  //-------------------------------------------------------
  
  public ImageBlockProcessor(String file) throws Exception {
    ImageLocator i = new ImageLocator(ImageLoader.loadImage(file));
    getSprites(i);
  }


  
  private void getSprites(ImageLocator i) {
    Point p = new Point(0,0);
    Point backtrack_point = new Point(p);
//System.out.println("getSprites");
    do {
      Vector<Sprite> sprite_row = new Vector<Sprite>();
      backtrack_point.setLocation(p);
//System.out.println("Row="+p);
      do {
//System.out.println("Get Sprite="+p);
        Sprite s = getSprite(i,p);
        sprite_row.add(s);
        if (s!=null) {sprite_list.add(s);}
      } while (i.getNextBlockPoint(p,Direction.Right)!=null);
      sprites.add(sprite_row);
      p.setLocation(backtrack_point);
    } while (i.getNextBlockPoint(p,Direction.Down)!=null);
  }

  private Sprite getSprite(ImageLocator i, Point p) {
    try {
      Point op = i.findOppositePoint(p);
      int width    = op.x - p.x + 1; //the +1 is for size in pixels - not size of array index (e.g 0 to 15 is width 16)
      int height   = op.y - p.y + 1;
      int x_offset = i.sweepForActivePixel( p,Direction.Right);
      int y_offset = i.sweepForActivePixel( p,Direction.Down );
      int sub_width  = width  - i.sweepForActivePixel(op,Direction.Left ) - x_offset;
      int sub_height = height - i.sweepForActivePixel(op,Direction.Up   ) - y_offset;
//System.out.println("x_offset="+x_offset+" y_offset="+y_offset);
      return new Sprite(i.getSubImage(p.x+x_offset,p.y+y_offset,sub_width ,sub_height),x_offset,y_offset,width,height);
    }
    catch (Exception e) {return null;}
  }

  
  //-------------------------------------------------------
  // Public
  //-------------------------------------------------------
  
  public void addNameOverlay(String name, Point p) {name_overlay.put(name.toLowerCase(),p);}
  
  //-------------------------------------------------------
  // Public Accessors - Sprites
  //-------------------------------------------------------
  public Sprite getSprite(Point p) {
    try                 {return getSprite(p.x,p.y);}
    catch (Exception e) {return null;}
  }
  public Sprite getSprite(int x, int y) {
    try                 {return sprites.get(y).get(x);}
    catch (Exception e) {return null;}
  }
  public Sprite getSprite(int i) {
    try                 {return sprite_list.get(i);}
    catch (Exception e) {return null;}
  }
  public Sprite getSprite(String name) {
    Sprite s = getSprite(name_overlay.get(name.toLowerCase()));
    if (s==null) {
      // BUGFIX: if an unknown name was given this method uses Caster.castInt(value), however, if this value cant be cast it return 0 and thus always returns the first sprite
      // WARNING: This bugfix will probably screw things later, maybe if there were 2 verisons of castInt, one of witch throws and exception?
      int possible_sprite_index = Caster.castIntSafe(name);
      if (possible_sprite_index>=0) {s = getSprite(possible_sprite_index);} 
    }
    return s;
  }
  public Vector<Sprite> getSprites(int i) {return sprites.get(i);}
  
  //-------------------------------------------------------
  // Public Accessors - Images
  //-------------------------------------------------------
  public Image getImage(Point p) {
    try                 {return getImage(p.x,p.y);}
    catch (Exception e) {return null;}
  }
  public Image getImage(int x, int y) {
    try                 {return getSprite(x,y).getImage();}
    catch (Exception e) {return null;}
  }
  public Image getImage(int i) {
    try                 {return getSprite(i).getImage();}
    catch (Exception e) {return null;}
  }
  public Image getImage(String name) {
    try                 {return getSprite(name).getImage();}
    catch (Exception e) {return null;}
    //return getImage(name_overlay.get(name.toLowerCase()));
  }
  
}




//------------------------------------------------------------------------------
// ImageLocator Class
//------------------------------------------------------------------------------

class ImageLocator extends java.awt.Component {
  private static final long serialVersionUID = 0;

  private static enum LoopControl {LOOP, OK, ERROR};
  
//  private final Color   null_colour_ = new Color(  0,  0,  0);
  private       Color border_colour_ = new Color(255,255,255);
//  private final int null_colour   =   null_colour_.getRGB();
  private       int border_colour = border_colour_.getRGB();

  
  private Image img;
  private BufferedImage lookup;
  private int   width;
  private int   height;
  //int[] pixels;
  
  public ImageLocator(Image img) throws Exception {
    if (img==null) {throw new Exception("Null argument - no image provided");}
    this.img = img;
    width    = img.getWidth(this);
    height   = img.getHeight(this);
    
    lookup = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    lookup.getGraphics().drawImage(img,0,0,this);
    /*  PIXEL GRABBER METHOD
    pixels   = new int[width * height];
    PixelGrabber pg = new PixelGrabber(img, 0, 0, width, height, pixels, 0, width);
    pg.grabPixels();
    if ((pg.getStatus() & ImageObserver.ABORT) != 0) {throw new Exception("Unable to grab pixels from image");}
    */
  }

  public Image getImage() {return img;}

  public Image getSubImage(Point p     , Dimension d)           {return getSubImage(p.x,p.y,d.width,d.height);}
  public Image getSubImage(Point p     , int width, int height) {return getSubImage(p.x,p.y,  width,  height);}
  public Image getSubImage(int x, int y, int width, int height) {
    return createImage( new java.awt.image.FilteredImageSource(img.getSource(), new java.awt.image.CropImageFilter(x,y,width,height)) );
  }
  
  public int getPixel(Point p     ) {return getPixel(p.x,p.y);}
  public int getPixel(int x, int y) {
    try                 {return lookup.getRGB(x,y);  } //if (inBounds(x,y)) {return pixels[y*width + x];}
    catch (Exception e) {return border_colour;}
  }
 
  public Point getNextBlockPoint(Point p, Direction d) {
    while (getPixel(p) != border_colour) {incDirection(p,d);}
    int pixel = getPixel(incDirection(p,d));
    if (pixel==border_colour) {p=null;}
    return p;
  }

  public int sweepForActivePixel(Point p, Direction d) throws Exception {
    int x_start = p.x;
    int y_start = p.y;
    int sweep_inc = 0;
    Direction sweep_direction = seekDirection(d);
    boolean previous_line_detected_border = false;
//System.out.println("Null Colour="+null_colour+" This Colour="+getPixel(p)+ " at "+p);
    LoopControl exit = LoopControl.LOOP;
    while (exit==LoopControl.LOOP) {
      if      (getPixel(p)==border_colour)                {exit = LoopControl.ERROR;}
      else if (!activePixelOnThisLine(p,sweep_direction)) {sweep_inc++;}
      else                                                {exit = LoopControl.OK;}
      incDirection(p,d);
//System.out.println("Direction:"+d+" SweepD:"+sweep_direction+" YO "+sweep_inc);
    }
    p.x = x_start;
    p.y = y_start;
    if (exit==LoopControl.ERROR) {throw new Exception("Image Block Null");}
    if (sweep_inc>0) {sweep_inc=sweep_inc-1;}
    return sweep_inc;
  }
  private boolean activePixelOnThisLine(Point p, Direction d) {
    int x_start = p.x;
    int y_start = p.y;
    int pixel;
    boolean found_active_pixel = false;
    LoopControl exit = LoopControl.LOOP;
    while (exit==LoopControl.LOOP) {
      pixel = getPixel(p);
      if      ((new Color(pixel,true)).getAlpha()==0)          {}
      else if (pixel == border_colour) {                         exit=LoopControl.ERROR;}
      else                             {found_active_pixel=true; exit=LoopControl.OK;   }
      incDirection(p,d);
    }
//System.out.println("activePixelOnThisLine loop:"+exit);
    p.x = x_start;
    p.y = y_start;
    return found_active_pixel;
  }
  
  
  public Point findOppositePoint(Point p) {
    Point op = new Point(p);
    while (getPixel(op) != border_colour) {incDirection(op,Direction.Right);}
    incDirection(op,Direction.Left);
    while (getPixel(op) != border_colour) {incDirection(op,Direction.Down );}
    incDirection(op,Direction.Up);
    return op;
  }
  /*
  private boolean inBounds(Point p     ) {return inBounds(p.x,p.y);}
  private boolean inBounds(int x, int y) {
    if (x>=0 && y>=0 && x<=width && y<=height) {return true; }
    else                                       {return false;}
  }
  */
  private Point incDirection(Point p, Direction d) {
    p.x += directionX(d);
    p.y += directionY(d);
    return p;
  }
  
  private int directionX(Direction d) {
    if (d==Direction.Left ) {return -1;}
    if (d==Direction.Right) {return  1;}
                            return  0;
  }
  private int directionY(Direction d) {
    if (d==Direction.Up  ) {return -1;}
    if (d==Direction.Down) {return  1;}
                           return  0;
  }
  
  private Direction seekDirection(Direction d) {
    if (d==Direction.Down ) {return Direction.Right;} //Sweep from top to bottom
    if (d==Direction.Right) {return Direction.Down; } //Sweep from Right to left
    if (d==Direction.Up   ) {return Direction.Left; } //           Bottom to top
    if (d==Direction.Left ) {return Direction.Up;   } //           left to right
    return null;
  }
}
