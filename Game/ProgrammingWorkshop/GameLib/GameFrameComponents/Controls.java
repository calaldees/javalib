package GameLib.GameFrameComponents;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.HashMap;
import java.util.Map;

public class Controls implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {

  private final Map<Integer,Boolean> keys_down = new HashMap<Integer,Boolean>();
  
  private final GameFrameInterface game_frame;
  
  private final Point mouse_position = new Point();
  
  public Controls(GameFrameInterface game_frame) {this.game_frame = game_frame;}
  
  public void resetKeysPressed() {keys_down.clear();}
  
  public void keyPressed (int key_code) {}
  public void keyReleased(int key_code) {}
  /*
  public void keySpace() {}
  public void keyEscape() {game_frame.exit();}
  public void keyUp() {}
  public void keyDown() {}
  public void keyLeft() {}
  public void keyRight() {}
  public void keyW() {}
  public void keyS() {}
  public void keyA() {}
  public void keyD() {}
   */
  
  public boolean isKeyPressed(int keycode) {
    if (keys_down.containsKey(keycode)) {return keys_down.get(keycode);}
    else                                {return false;}
  }
  
  public Point getMousePosition() {return mouse_position;}
  
  public void mousePressed(int x, int y) {game_frame.mousePressed(x, y);}
  public void mouseMoved  (int x, int y) {game_frame.mouseMoved  (x, y);}
  public void mouseDragged(int x, int y) {game_frame.mouseDragged(x, y);}
  public void mouseWheelUp()             {game_frame.mouseWheelUp();    }
  public void mouseWheelDown()           {game_frame.mouseWheelDown();  }
  
  
  //----------------------------------------------------------
  // Private
  //----------------------------------------------------------

  //----------------------------------------------------------
  // Events
  //----------------------------------------------------------
  public void keyTyped(KeyEvent e) {}
  public void keyReleased(KeyEvent e) {
    keys_down.remove(e.getKeyCode());
    game_frame.keyReleased(e.getKeyCode());
  }
  public void keyPressed(KeyEvent e) {
    keys_down.put(e.getKeyCode(), true);
    /*
    switch (e.getKeyCode()) {
      case KeyEvent.VK_UP     : keyUp();     break;
      case KeyEvent.VK_DOWN   : keyDown();   break;
      case KeyEvent.VK_LEFT   : keyLeft();   break;
      case KeyEvent.VK_RIGHT  : keyRight();  break;
      case KeyEvent.VK_SPACE  : keySpace();  break;
      case KeyEvent.VK_ESCAPE : keyEscape(); break;
      case KeyEvent.VK_W : keyW(); break;
      case KeyEvent.VK_S : keyS(); break;
      case KeyEvent.VK_A : keyA(); break;
      case KeyEvent.VK_D : keyD(); break;
      default                 : keyPressed(e.getKeyCode()); break;
    }
     */
    if (e.isAltDown()) {
      if (e.getKeyCode() == KeyEvent.VK_ENTER) {game_frame.toggleFullScreen();}
    }
    if (e.isControlDown()) {
      if (e.getKeyCode() == KeyEvent.VK_C) {game_frame.exit();}
    }
    if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {game_frame.exit();}
    game_frame.keyPressed(e.getKeyCode());
  }

  public void mouseClicked(MouseEvent e)  {}
  public void mousePressed(MouseEvent e)  {updateMousePosition(e); mousePressed(mouse_position.x,mouse_position.y);}
  public void mouseReleased(MouseEvent e) {}
  public void mouseEntered(MouseEvent e)  {}
  public void mouseExited(MouseEvent e)   {}
  public void mouseDragged(MouseEvent e)  {updateMousePosition(e); mouseDragged(mouse_position.x,mouse_position.y);}
  public void mouseMoved(MouseEvent e)    {updateMousePosition(e); mouseMoved(mouse_position.x,mouse_position.y);}

  private void updateMousePosition(MouseEvent e) {
    mouse_position.x = (int)((e.getX()/(double)game_frame.getFrame().getContentPane().getWidth() )*game_frame.getFrameImage().getWidth() );
    mouse_position.y = (int)((e.getY()/(double)game_frame.getFrame().getContentPane().getHeight())*game_frame.getFrameImage().getHeight());
  }
    
  public void mouseWheelMoved(MouseWheelEvent e) {
    int rotation = e.getWheelRotation();
    if      (rotation>0) {mouseWheelUp();}
    else if (rotation<0) {mouseWheelDown();}
  }
}
