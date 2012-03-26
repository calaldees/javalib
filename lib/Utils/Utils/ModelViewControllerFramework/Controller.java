package Utils.ModelViewControllerFramework;


import java.awt.Graphics;
import java.awt.Component;


public interface Controller {
  public void bindController(Component c);
  public void unbindController();
  public void manipulateModel(Model        m);
  //public void manipulateView (ViewRenderer r);
  public void renderFeedback (Graphics     g);
}