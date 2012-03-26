package PhyBlast;

import Utils.ImageLoader.ImageLoader;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.Point2D;


public class PsyBlastRenderer extends Component {
  private static final long serialVersionUID = 0;

  private PhyBlastModel model;
  
  private Image ship = ImageLoader.loadImageSilent("ship.png");

  public PsyBlastRenderer(PhyBlastModel model) {
    this.model = model;
  }

  public void paint(Graphics g) {
    Point2D p = model.getShipPos();
    g.drawImage(ship, (int)p.getX(), (int)p.getY(), this);
  }

}
