package Utils.Display;

import java.awt.Rectangle;


public interface ComponentView {

  public void      setView      (Rectangle r);
  public void      setViewTarget(Rectangle r);
  public Rectangle getViewTarget();
  public void      setTargetAcceleration(double acceleration);

  /*
  public void shiftViewX(int x_shift) {
    Rectenage target = getViewTarget();
    target.x += x_shift;
    setViewTarget(target);
  }
  public void      shiftViewY(int y_shift);
*/
  
}