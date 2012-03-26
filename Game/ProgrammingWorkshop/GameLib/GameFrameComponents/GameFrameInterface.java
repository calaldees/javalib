package GameLib.GameFrameComponents;

import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;


public interface GameFrameInterface {

  public void clearScreen();

  JFrame        getFrame();
  BufferedImage getFrameImage();
  void          setFrameImage(BufferedImage frame_image);
  void          resetKeysPressed();
  Image         getCashedImage(String name);
  Controls      getControlsListener();
  public void setFullScreen(boolean fullscreen);
  public void toggleFullScreen();
  public void exit();
  public void exitFinal();
  public void timerEvent();
  public void timerPause(boolean pause);

  public void    keyPressed (int keycode);
  public void    keyReleased(int keycode);
  public boolean isKeyPressed(int keycode);
  public Point   getMousePosition();
  public void    mousePressed(int x, int y);
  public void    mouseMoved(int x, int y);
  public void    mouseDragged(int x, int y);
  public void    mouseWheelUp();
  public void    mouseWheelDown();

  
}
