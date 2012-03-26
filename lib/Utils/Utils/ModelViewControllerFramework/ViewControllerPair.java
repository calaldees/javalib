package Utils.ModelViewControllerFramework;

import java.awt.Component;

public class ViewControllerPair {

  private Component    component;
  private ViewRenderer view_renderer;
  private Controller   controller;

  public ViewControllerPair(Component c) {component = c;}

  public Component    getComponent()    {return component;}
  public ViewRenderer getViewRenderer() {return view_renderer;}
  public Controller   getController()   {return controller;}

  public void setViewRenderer(ViewRenderer view_renderer) {this.view_renderer = view_renderer;}
  public void setController  (Controller   controller   ) {
    if (this.controller!=null) {this.controller.unbindController();}
    this.controller = controller;
    if (this.controller!=null) {this.controller.bindController(getComponent());}
  }
}
