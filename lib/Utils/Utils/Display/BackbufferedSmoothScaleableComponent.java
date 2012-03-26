package Utils.Display;

import Utils.Types.Integer.Dimension2D;
import Utils.Types.Integer.Dimension2DImmutable;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.WindowEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.VolatileImage;

//Test frame imorts
import javax.swing.JFrame;
import java.awt.Color;
import Utils.ModelViewControllerFramework.TimerHandeler;
import Utils.ModelViewControllerFramework.TimerThread;
import java.awt.event.WindowListener;


public class BackbufferedSmoothScaleableComponent extends Component implements ComponentView {
  private static final long serialVersionUID = 0;
    
  //private final DisplayController   master;
  private       VolatileImage       v;

  private final Component           c;
  private final Rectangle           c_size_lookup;
  
  //Volatile Image Size Control
  private final Dimension2D image_size_max;
  private final Dimension2D image_size_min;
  private final int         image_resize_jump;   // Amount the image should grow by if needed
  //private final double      image_resize_ratio;  // Used to maintian aspect ratio of backbuffer image to the same as the component size

  //Visable Area Control
  private final Rectangle          area_buffered       = new Rectangle();
  private final Rectangle          area_current        = new Rectangle();
  private final Rectangle2D.Double area_current_double = new Rectangle2D.Double();
  private final Rectangle          area_target         = new Rectangle();
  private       double             area_acceleration   = 10;

  private boolean enforce_aspect = true;

  public BackbufferedSmoothScaleableComponent(Component c) {
    //this.master = master;
    this.c = c;
    c_size_lookup = new Rectangle(0,0,c.getWidth(),c.getHeight());

    Dimension size = new Dimension(640,480);
System.out.println("NOTE: Hard Coded 640,480 for "+getClass().getName());
    setMaximumSize(size);
    setMinimumSize(size);
    setPreferredSize(size);
    setSize(size);

    image_size_min = Dimension2DImmutable.valueOf(size.width/2, size.height/2);
    image_size_max = Dimension2DImmutable.valueOf(size.width*2, size.height*2);
    image_resize_jump = image_size_min.getWidth();
    

    setView(new Rectangle(0,0,size.width,size.height));
    //r.setView(area_current);

  }

  private Graphics getBackbuferGraphic() {
    Graphics gimg = v.createGraphics();
    gimg.setClip(0,0,area_buffered.width,area_buffered.height);
    gimg.translate(-area_buffered.x, -area_buffered.y);
    return gimg;
  }

  public void paint(Graphics g) {
    stepViewTowardsTarget();
    //System.out.println("ViewStepNow:"+area_current);
    if (!area_buffered.contains(area_current)) {
//System.out.println("Current outside buffer");
//System.out.println("Buffer : "+area_buffered);
//System.out.println("Current: "+area_current );
      //Check Zoom Redraw - reinit volatile backbuffer to new size
      if (area_buffered.width  < area_current.width  ||
          area_buffered.height < area_current.height ||
          area_current.width  < v.getWidth() /2 || 
          area_current.height < v.getHeight()/2        ) {
        v = getNewVolatileImageBackbufferToFit(area_current.getSize()); //reinit volatile image to new size to accomadate view area
//System.out.println("New Volatile: width="+v.getWidth()+" height="+v.getHeight());
      }
      //set new buffered area to draw from
      area_buffered.x = area_current.x - ((area_buffered.width /2) - (area_current.width /2));
      area_buffered.y = area_current.y - ((area_buffered.height/2) - (area_current.height/2));
      //System.out.println("ProposedBuffer: "+area_buffered);
      //limit buffer size to actual component (stop drawing areas outdide the component)
      limitRectangle(area_buffered,c_size_lookup);
      //System.out.println("LimitedBuffer: "+area_buffered);

      //redraw to buffer
      Graphics gimg = getBackbuferGraphic();

//System.out.println("Repaint component to volatile image");
      c.paint(gimg);
      gimg.dispose();
    }

    int sx1 = area_current.x - area_buffered.x;
    int sy1 = area_current.y - area_buffered.y;
    g.drawImage(v,
                0                               , 0                 ,
                getWidth()                      , getHeight()       ,
                sx1                             , sy1               ,
                sx1 + area_current.width , sy1 + area_current.height,
                this);
    g.dispose();
  }

  //Sets view area with absolute position
  public void setView(Rectangle r) {
    limitRectangle(r,c_size_lookup);
    enforceAspectRatio(r);
    area_target .setBounds(r);
    area_current.setBounds(r);
    assignRectangleToRectangle2D(r,area_current_double);
  }

  //Sets view to move too smoothy and incrmentaly
  public void  setViewTarget(Rectangle r)  {
    limitRectangle(r,c_size_lookup);
    enforceAspectRatio(r);
    area_target.setBounds(r);
  }
  

  public Rectangle getViewTarget()             {return new Rectangle(area_target);}
  public void      setTargetAcceleration(double acceleration) {area_acceleration = acceleration;}

  public void setEnforceAspectRatio(boolean enforce_aspect) {this.enforce_aspect = enforce_aspect;}

  public void showDebugFrame() {
    //BackbufferTestFrame b = 
    new BackbufferTestFrame(this);
  }

  //---------------------------------------------------------------
  // Private
  //---------------------------------------------------------------

  private Rectangle enforceAspectRatio(Rectangle r) {
    if (enforce_aspect) {
      r.height = (int)(r.width * getCompoentAspectRatio());
    }
    return r;
  }

  private void stepViewTowardsTarget() {
    if (area_acceleration>1) {
      area_current_double.x      += (area_target.x      - area_current_double.x     )/area_acceleration;
      area_current_double.y      += (area_target.y      - area_current_double.y     )/area_acceleration;
      area_current_double.width  += (area_target.width  - area_current_double.width )/area_acceleration;
      area_current_double.height += (area_target.height - area_current_double.height)/area_acceleration;
      assignRectangle2DToRectangle(area_current_double,area_current);
      //limitRectangle(area_current,c_size_lookup);
    }
  }

  private double getCompoentAspectRatio() {return (double)getHeight() / (double)getWidth();}

  private VolatileImage getNewVolatileImageBackbufferToFit(Dimension d) {
    int    new_width  = image_resize_jump * ((d.width/image_resize_jump)+1);
    int    new_height = (int)(new_width * getCompoentAspectRatio());
    //Limit Max size
    if (new_width > image_size_max.getWidth() || new_height > image_size_max.getLength()) {
      new_width  = image_size_max.getWidth();
      new_height = image_size_max.getLength();
    }
    //Limit Min size
    if (new_width < image_size_min.getWidth() || new_height < image_size_min.getLength()) {
      new_width  = image_size_min.getWidth();
      new_height = image_size_min.getLength();
    }

    area_buffered.width  = new_width;
    area_buffered.height = new_height;
    if (v!=null && new_width == v.getWidth() && new_height == v.getHeight()) {return v;}
    return getGraphicsConfiguration().createCompatibleVolatileImage(new_width, new_height);
  }

  private void assignRectangleToRectangle2D(Rectangle   source, Rectangle2D destination) {destination.setRect((double)source.x, (double)source.y,(double)source.width,(double)source.height);}
  private void assignRectangle2DToRectangle(Rectangle2D source, Rectangle   destination) {destination.setBounds((int)source.getX(), (int)source.getY(),(int)source.getWidth(),(int)source.getHeight());}

  //changes r's x,y,width,height to be inside limit
  private void limitRectangle(Rectangle r, Rectangle limit) {  
    if (r.x + r.width  > limit.width ) {r.x = limit.width -r.width; }
    if (r.y + r.height > limit.height) {r.y = limit.height-r.height;}
    if (r.x < 0) {r.x = 0;}
    if (r.y < 0) {r.y = 0;}
    if (r.width  > limit.width ) {r.width  = limit.width; }
    if (r.height > limit.height) {r.height = limit.height;}
  }

  //Test frame methods
  VolatileImage getBackbufferImage() {return v;}
  Rectangle     getAreaBuffered()    {return area_buffered;}
  Rectangle     getAreaCurrent()     {return area_current;}
}


//------------------------------------------------------------------------------
// JFrame for debugging- Shows contents of backbuffer and draw area
//------------------------------------------------------------------------------

class BackbufferTestFrame extends JFrame implements TimerHandeler, WindowListener {
  private static final long serialVersionUID = 0;

  private BackbufferedSmoothScaleableComponent b;
  private TimerThread                          timer;

  public BackbufferTestFrame(BackbufferedSmoothScaleableComponent b) {
    this.b=b;
    setSize(500,500);
    setVisible(true);
    timer = new TimerThread(this);
    timer.startTimerThread();
    addWindowListener(this);
  }
  public void paint(Graphics g) {
    g.setColor(Color.BLACK);
    g.fillRect(0,0, getWidth(), getHeight());

    Rectangle area_buffered = b.getAreaBuffered();
    Rectangle area_current  = b.getAreaCurrent();
    g.drawImage(b.getBackbufferImage(),0,0,this);
    g.setColor(Color.BLUE);
    g.drawRect(area_current.x - area_buffered.x,
               area_current.y - area_buffered.y,
               area_current.width,
               area_current.height              );
  }

  public int getTargetUpdatesPerSecond() {return 1;}
  public int getMaxFrameSkip() {return 2;}
  public void processModel() {}
  public void processView() {repaint();}

  public void windowOpened(WindowEvent e)      {}
  public void windowClosing(WindowEvent e)     {timer.stopTimerThread();}
  public void windowClosed(WindowEvent e)      {}
  public void windowIconified(WindowEvent e)   {timer.pauseTimerThread();}
  public void windowDeiconified(WindowEvent e) {timer.startTimerThread();}
  public void windowActivated(WindowEvent e)   {}
  public void windowDeactivated(WindowEvent e) {}

}