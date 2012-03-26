package Physics.Simulation.Controllers;

import java.awt.Graphics;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import Utils.ModelViewControllerFramework.Controller;
import Utils.ModelViewControllerFramework.Model;

import Physics.Simulation.Simulation;


class ControllerMouseDirect implements Controller, MouseListener, MouseMotionListener {
  
  private Component ui_component;
  
  public ControllerMouseDirect() {}
  
  public void bindController(Component c) {
    ui_component = c;
    ui_component.addMouseListener(this);
    ui_component.addMouseMotionListener(this);
  }
  
  public void unbindController() {
    ui_component.removeMouseListener(this);
    ui_component.removeMouseMotionListener(this);
    ui_component = null;
  }
  
  public void manipulateModel(Model m) {
  }
  
  public void renderFeedback(Graphics g) {}
  
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
}
