package GameLib.GameFrameComponents;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Drawing {

  private final GameFrameInterface game_frame;
  
  private BufferedImage     frame_image;

  public Drawing(GameFrameInterface game_frame) {this.game_frame = game_frame;}
  
  public BufferedImage getFrameImage(                         ) {return frame_image;}
  public void          setFrameImage(BufferedImage frame_image) {this.frame_image = frame_image;}
  
  public Graphics getGraphics() {return frame_image.getGraphics();}
  
  public int getWidth()  {return frame_image.getWidth();}
  public int getHeight() {return frame_image.getHeight();}

  
  public void putPixel(int x, int y, Color c) {
    try                 {frame_image.setRGB(x,y,c.getRGB());}
    catch (Exception e) {}
  }
  
  public Color getPixel(int x, int y) {
    try                 {return new Color(frame_image.getRGB(x,y));}
    catch (Exception e) {return null;}
    //if (c.equals(frame.getBackground()) || c.getAlpha()>0) {return null;}
  }
  
  public void putImage(int x, int y, String filename)           {putImage(x,y,game_frame.getCashedImage(filename)  );}
  public void putImage(int x, int y, String filename, double r) {putImage(x,y,game_frame.getCashedImage(filename),r);}

  public void putImage(int x, int y, Image i) {
    getGraphics().drawImage(i,x,y,game_frame.getFrame());
  }
  
  public void putImage(int x, int y, Image i, double r) {
    AffineTransform rotate_tranform = new AffineTransform();
    double half_width  = i.getWidth(game_frame.getFrame()) /2d;
    double half_height = i.getHeight(game_frame.getFrame())/2d;
    rotate_tranform.translate(x-half_width, y-half_height);
    rotate_tranform.rotate(r,half_width,half_height); //*Math.PI
    ((Graphics2D)getGraphics()).drawImage(i, rotate_tranform, game_frame.getFrame());
  }
  
  public void drawLine(int x1, int y1, int x2, int y2, Color c) {
    Graphics g = getGraphics();
    g.setColor(c);
    g.drawLine(x1,y1,x2,y2);
  }
  
  public void drawRectangle(int x, int y, int width, int height, Color c) {
    Graphics g = getGraphics();
    g.setColor(c);
    g.fillRect(x,y,width,height);
  }
  public void drawRectangle(Rectangle r, Color c) {
    try {drawRectangle(r.x,r.y,r.width,r.height,c);} catch (Exception e) {}
  }
  
  public void drawCircleFilled(int x, int y, int size, Color c) {
    Graphics g = getGraphics();
    g.setColor(c);
    g.fillOval(x,y,size,size);
  }
  
  public void drawString(int x, int y, String s) {
    Graphics g = getGraphics();
    g.setColor(Color.WHITE);
    g.drawString(s, x, y);
  }
  
  public void repaintScreen() {game_frame.getFrame().repaint();}
  public void clearScreen()   {getGraphics().clearRect(0,0,frame_image.getWidth(),frame_image.getHeight());}
 
  public void setBackgroundColor(Color c) {
    throw new UnsupportedOperationException();
  }

}
