package Utils.ModelViewControllerFramework.Controllers;

import Utils.ModelViewControllerFramework.Controller;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;


public abstract class AbstractControllerMouseDirect implements Controller, MouseListener, MouseMotionListener, MouseWheelListener {

  private Component ui_component;

  public AbstractControllerMouseDirect() {}

  public void bindController(Component c) {
    ui_component = c;
    ui_component.addMouseListener(this);
    ui_component.addMouseMotionListener(this);
    ui_component.addMouseWheelListener(this);
  }

  public void unbindController() {
    ui_component.removeMouseListener(this);
    ui_component.removeMouseMotionListener(this);
    ui_component.removeMouseWheelListener(this);
    ui_component = null;
  }


//---------------------------------------------------------------------------------------
// Mouse Events
//---------------------------------------------------------------------------------------
  public void mouseClicked(MouseEvent e)  {}
  public void mouseEntered(MouseEvent e)  {}
  public void mouseExited(MouseEvent e)   {}
  public void mousePressed(MouseEvent e)  {}
  public void mouseReleased(MouseEvent e) {}
  public void mouseDragged(MouseEvent e)  {}
  public void mouseMoved(MouseEvent e)    {}
  public void mouseWheelMoved(MouseWheelEvent e) {}
}