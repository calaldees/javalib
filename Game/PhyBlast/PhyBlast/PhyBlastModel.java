package PhyBlast;

import Utils.InputBinding.InputBinding;
import Utils.ModelViewControllerFramework.Model;
import java.awt.geom.Point2D;


public class PhyBlastModel implements Model {

  private InputBinding input;

  private Point2D.Double ship_pos = new Point2D.Double();

  public PhyBlastModel(InputBinding input) {
    this.input = input;
  }
  
  public void updateModel() {
    if (input.getState("left" )) {ship_pos.x += -1;}
    if (input.getState("right")) {ship_pos.x +=  1;}
  }

  Point2D getShipPos() {return ship_pos;}
}
