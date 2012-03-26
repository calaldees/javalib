package Utils.Apps;

import Utils.Display.DisplayController;
import Utils.Display.BackbufferedSmoothScaleableComponent;
import Utils.ModelViewControllerFramework.TimerHandeler;
import Utils.ModelViewControllerFramework.TimerThread;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.KeyListener;

//For test component
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;

import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;


//import java.util.Random;





public class SmoothZoomerTest implements TimerHandeler, MouseWheelListener, MouseMotionListener, KeyListener {

  private static final long serialVersionUID = 0;

  public static void main(String[] args) {
    String image_filename = "./Test.png";
    if (args.length == 1) {image_filename = args[0];}
    new SmoothZoomerTest(image_filename);
  }

  //DefaultModelViewImplementation m = new DefaultModelViewImplementation(this);
  BackbufferedSmoothScaleableComponent buffer_component;
  DisplayController display;

  private final Point mouse_last_pos = new Point();

  public SmoothZoomerTest(String image_filename) {

    buffer_component = new BackbufferedSmoothScaleableComponent(new SmoothZoomerImageComponent(image_filename));
    buffer_component.addMouseMotionListener(this);
    buffer_component.addMouseWheelListener(this);
    
    
    String name = SmoothZoomerTest.class.getSimpleName();
    display = new DisplayController(name);
    display.setComponent(buffer_component);
    display.addKeyListener(this);
    display.addDisplayMode(640, 480, 16);
    display.setFullScreen(false);

    TimerThread timer = new TimerThread(this);
    timer.startTimerThread();
    
    buffer_component.showDebugFrame();
  }


  public int  getTargetUpdatesPerSecond() {return 60;}
  public int  getMaxFrameSkip()           {return 10;}
  public void processModel()              {}
  public void processView()               {display.render();}


  public void mouseDragged(MouseEvent e) {}
  public void mouseMoved(MouseEvent e)   {
    Rectangle view = buffer_component.getViewTarget();
    view.x += e.getX() - mouse_last_pos.x;
    view.y += e.getY() - mouse_last_pos.y;
    buffer_component.setViewTarget(view);
    mouse_last_pos.x = e.getX();
    mouse_last_pos.y = e.getY();
    //System.out.println("ViewMoved:"+view);
  }
  public void mouseWheelMoved(MouseWheelEvent e) {
    Rectangle view = buffer_component.getViewTarget();
    zoomCentered(e.getWheelRotation(),view);
    buffer_component.setViewTarget(view);
  }
  public void keyPressed(KeyEvent e) {
    Rectangle view = buffer_component.getViewTarget();
    int key_move = 50;
    if (e.getKeyCode() == KeyEvent.VK_SPACE) {view.x  = 0; view.y  = 0;}
    if (e.getKeyCode() == KeyEvent.VK_UP)    {view.y += -key_move;}
    if (e.getKeyCode() == KeyEvent.VK_DOWN)  {view.y +=  key_move;}
    if (e.getKeyCode() == KeyEvent.VK_LEFT)  {view.x += -key_move;}
    if (e.getKeyCode() == KeyEvent.VK_RIGHT) {view.x +=  key_move;}
    if (e.getKeyCode() == KeyEvent.VK_PAGE_UP  ) {zoomCentered(-1,view);}
    if (e.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {zoomCentered( 1,view);}
    buffer_component.setViewTarget(view);
  }
  public void keyTyped(KeyEvent e) {}
  public void keyReleased(KeyEvent e) {}

  private void zoomCentered(int steps, Rectangle view) {
    int jump_x = view.width /4;
    int jump_y = view.height/4;
    view.width  += jump_x * steps;
    view.height += jump_y * steps;
    view.x += -(jump_x * steps)/2;
    view.y += -(jump_y * steps)/2;
  }

}
class SmoothZoomerImageComponent extends Component {
  private final int    block_size = 20;
  //private final Random r          = new Random();
  private       Image  i;

  public SmoothZoomerImageComponent(String image_filename) {
    try {
      i = Utils.ImageLoader.ImageLoader.loadImage(image_filename);
    } catch (Exception ex) {
      //Logger.getLogger(SmoothZoomerImageComponent.class.getName()).log(Level.SEVERE, null, ex);
      throw new IllegalArgumentException("Background image not readable: "+image_filename);
    }

    Dimension size = new Dimension(i.getWidth(this),i.getHeight(this));
    setMaximumSize(size);
    setMinimumSize(size);
    setPreferredSize(size);
    setSize(size);
  }

  public void paint(Graphics g) {
    g.drawImage(i, 0, 0, this);
    /*
    g.setColor(Color.BLACK);
    g.fillRect(0, 0, getWidth(), getHeight());
    for (int x=0 ; x<getWidth() ; x+=block_size) {
      for (int y=0 ; y<getHeight() ; y+=block_size) {
        g.setColor(new Color(r.nextInt()));
        g.fillRect(x, y, block_size, block_size);
      }
    }
    */
  }
}