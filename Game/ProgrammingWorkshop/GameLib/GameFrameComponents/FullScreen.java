package GameLib.GameFrameComponents;

import java.awt.Component;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;

import javax.swing.JFrame;


public class FullScreen implements WindowStateListener, WindowListener {
  
  private        final GraphicsDevice screen_device           = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
  private static final DisplayMode    display_mode_fullscreen = new DisplayMode(640,480,16,DisplayMode.REFRESH_RATE_UNKNOWN);
  private        final DisplayMode    display_mode_original   = screen_device.getDisplayMode();

  private final GameFrameInterface game_frame;
  private final String name;
  private final int    width;
  private final int    height;

  private JFrame       frame;
  private boolean      fullscreen  = false;

  
  public FullScreen(String name, int width, int height, GameFrameInterface game_frame) {
    this.game_frame = game_frame;
    this.name       = name;
    this.width      = width;
    this.height     = height;
  }
  
  public JFrame getFrame() {return frame;}
  
  public void setFullScreen(boolean fullscreen) {
    game_frame.timerPause(true);
    this.fullscreen=fullscreen;

    if (frame != null) {frame.dispose();}

    frame = new JFrame(name);     
    //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.addKeyListener(game_frame.getControlsListener());
    frame.addWindowListener(this);
    frame.addWindowStateListener(this);
    frame.setSize(width,height);
    //frame.setResizable(false);

    if (game_frame.getFrameImage()==null) {
      game_frame.setFrameImage(frame.getGraphicsConfiguration().createCompatibleImage(width,height));
      game_frame.clearScreen();
    }
    
    Component c = new GameFrameComponent(game_frame.getFrameImage());
    c.addMouseListener(game_frame.getControlsListener());
    c.addMouseMotionListener(game_frame.getControlsListener());
    c.addMouseWheelListener(game_frame.getControlsListener());
    frame.add(c);
 
    if (fullscreen && screen_device.isFullScreenSupported()) {
      try {
        frame.setUndecorated(true);
        screen_device.setFullScreenWindow(frame);
        screen_device.setDisplayMode(display_mode_fullscreen);
        //frame.requestFocus();
        //frame.setVisible(false);     
      }
      catch (Exception e) {}
    }
    if (!fullscreen) {
      try {

        //frame.requestFocus();
        screen_device.setDisplayMode(display_mode_original);
        screen_device.setFullScreenWindow(null);
        //frame.setVisible(false);
        //frame.setUndecorated(false);
      }
      catch (Exception e) {}
      frame.pack();
    }
    
    frame.setVisible(true);
    game_frame.timerPause(false);
  }
  
  public void toggleFullScreen() {setFullScreen(!fullscreen);}

  public void windowStateChanged(WindowEvent e) {
    if (e.getNewState() == JFrame.MAXIMIZED_BOTH         ) {game_frame.setFullScreen(true);}    
  }
  public void windowOpened(WindowEvent e) {}
  public void windowClosing(WindowEvent e) {game_frame.exit();}
  public void windowClosed(WindowEvent e) {}
  public void windowIconified(WindowEvent e) {game_frame.timerPause(true);}
  public void windowDeiconified(WindowEvent e) {game_frame.timerPause(false);}
  public void windowActivated(WindowEvent e) {}
  public void windowDeactivated(WindowEvent e) {}
}
