package Utils.Display;

import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import java.awt.*;
import java.util.Queue;
import java.util.LinkedList;
import Utils.ErrorHandeler;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Collection;


public class DisplayController implements KeyListener, RenderInvolker {

  //public static enum BufferMode {Single_Buffer, Double_Buffer, Tripple_Buffer};

//-------------------------------------------------------------------------
// Variables
//-------------------------------------------------------------------------

  private Queue<DisplayMode>  display_modes = new LinkedList<DisplayMode>();

  private GraphicsDevice device;
  private DisplayMode    old_display_mode;
  private int            number_of_buffers = 2;
  
  private String         app_name;
  private Image          icon;
  private JFrame         frame;
  
  private Component   frame_contents;
  //private KeyListener key_listener;
  private final Collection<KeyListener> key_listeners = new ArrayList<KeyListener>();
  
  private boolean fullscreen;

//-------------------------------------------------------------------------
// Constructor
//-------------------------------------------------------------------------

  public DisplayController(                           ) {this("Java"  ,null);}
  public DisplayController(String app_name            ) {this(app_name,null);}
  public DisplayController(String app_name, Image icon) {
    this.app_name = app_name;
    this.icon     = icon;
  }


//-------------------------------------------------------------------------
// Setup
//-------------------------------------------------------------------------


  public void addDisplayMode(int display_x_size, int display_y_size, int depth) {
    display_modes.add(new DisplayMode(display_x_size, display_y_size, depth, 0));
    try {
      GraphicsEnvironment   env    = GraphicsEnvironment.getLocalGraphicsEnvironment();
                            device = env.getDefaultScreenDevice();
      GraphicsConfiguration gc     = device.getDefaultConfiguration();

      old_display_mode = device.getDisplayMode();
    }
    catch (Exception e) {}

  }


//-------------------------------------------------------------------------
// Initalize
//-------------------------------------------------------------------------

  
  private void setFrameDefaults() {
    if (frame!=null) {frame.dispose();}
    frame = new JFrame(app_name);
    //frame.setName(app_name);
    frame.setSize(display_modes.peek().getWidth() , display_modes.peek().getHeight());
    frame.setIconImage(icon);
    frame.setResizable(false);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.addKeyListener(this);
    for (KeyListener key_listener : key_listeners) {
      frame.addKeyListener(key_listener);
    }
    setComponent(frame_contents);
  }


  public void closeDisplay() {
    try {device.setDisplayMode(old_display_mode);} catch (Exception e) {}
    try {device.setFullScreenWindow(null);       } catch (Exception e) {}
    frame.dispose();
  }



//-------------------------------------------------------------------------
// Switch Mode (Full Screen, Windowed)
//-------------------------------------------------------------------------

  public void toggleFullScreen() {setFullScreen(!fullscreen);}
  
  public void setFullScreen(boolean fullscreen) {
    this.fullscreen = fullscreen;
    //if (frame != null) {frame.dispose();}
    
    //Full Screen Mode
    if (fullscreen) {
      try {
         //IMPROVEMENT NEEDED! Frame should be created here with GraphicsConfiguration
         //a graphics configuration should be included in the fame contructor

        setFrameDefaults();
        
        frame.setUndecorated(true);
        frame.setIgnoreRepaint(true);
        frame.setVisible(true);


        if (device.isFullScreenSupported()) {
          device.setFullScreenWindow(frame);
          chooseBestDisplayMode(device);
        }

        frame.createBufferStrategy(number_of_buffers);
        
      }
      catch (Exception e) {ErrorHandeler.error("Display Change Failed","Could not set Fullscreen Mode",e);}      
    }
    
    //Window Mode
    else {
      try {
        setFrameDefaults();
        frame.setUndecorated(false);
        frame.setIgnoreRepaint(false);
        frame.setVisible(true);

        try {device.setDisplayMode(old_display_mode);} catch (Exception e) {}
        device.setFullScreenWindow(null);

        frame.createBufferStrategy(number_of_buffers);
      }
      catch (Exception e) {ErrorHandeler.error("Display Change Failed","Could not set Windowed Mode",e);}  
    }
  }

//-------------------------------------------------------------------------
// Other
//-------------------------------------------------------------------------
  
  public void render() {
    try {
      BufferStrategy buffer = frame.getBufferStrategy();
      Graphics g = buffer.getDrawGraphics();
      frame_contents.paint(g);
      buffer.show();
    }
    catch (Exception e) {}
    
  }
  
  /*
  private BufferStrategy buffer;
  public Graphics getGraphics() {
    try {
      buffer = frame.getBufferStrategy();
      return buffer.getDrawGraphics();
    }
    catch (Exception e) {}
    return null;
    
  }
  public void flipBuffer() {
    if (buffer!=null) {
      buffer.show();
      buffer = null;
    }
  }
  */

 

//-------------------------------------------------------------------------
// Other
//-------------------------------------------------------------------------

  public Component getComponent(           ) {return frame_contents;}
  public void      setComponent(Component c) {
    frame_contents = c;
    if (frame!=null && frame_contents!=null) {frame.getContentPane().add(frame_contents);}
  }

  //public void setComponentToUseScaleableBackbuffer(Component c) {
  //  setComponent(new BackbufferedSmoothScaleableComponent(c));
  //}
  
  public void addKeyListener(KeyListener key_listener) {
    key_listeners.add(key_listener);
    if (frame!=null) {frame.addKeyListener(key_listener);}
  }
  public void removeKeyListener(KeyListener key_listener) {
    key_listeners.remove(key_listener);
    if (frame!=null) {frame.removeKeyListener(key_listener);}
  }
  
  public int       getWidth()  {return frame.getSize().width;}
  public int       getHeight() {return frame.getSize().height;}

  GraphicsConfiguration getGraphicsConfiguration() {
    if (frame!=null) {return frame.getGraphicsConfiguration();}
    else             {return null;}
  }
//-------------------------------------------------------------------------
// Private
//-------------------------------------------------------------------------


  private void chooseBestDisplayMode(GraphicsDevice device) {
    DisplayMode bestDisplayMode = null;

    if (device.isDisplayChangeSupported() && display_modes.size()>0) {
      DisplayMode[] modes = device.getDisplayModes();
      for (DisplayMode prefered_display_mode : display_modes) {

        for (int i = 0; i < modes.length; i++) {
          if (modes[i].getWidth() == prefered_display_mode.getWidth()
           && modes[i].getHeight() == prefered_display_mode.getHeight()
           && modes[i].getBitDepth() == prefered_display_mode.getBitDepth()
          ) {bestDisplayMode = prefered_display_mode;}
        }

      }

      // Set the display mode to the one selected
      if (bestDisplayMode != null) {
        try {device.setDisplayMode(bestDisplayMode);}
        catch (Exception e) {ErrorHandeler.error("Display Change Failed","Could not change display mode",e);}
      }

    }
  }

  public void keyTyped   (KeyEvent e) {}
  public void keyReleased(KeyEvent e) {}
  public void keyPressed (KeyEvent e) {
    //if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {closeDisplay();}
    if (e.isAltDown()) {
      if (e.getKeyCode() == KeyEvent.VK_ENTER) {toggleFullScreen();}
    }
  
  }


}


