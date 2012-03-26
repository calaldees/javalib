package GameLib.GameFrameComponents;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

class GameFrameComponent extends Component {
  private final BufferedImage i;
  public GameFrameComponent(BufferedImage i) {
    this.i = i;
    Dimension size = new Dimension(i.getWidth(), i.getHeight());
    setMaximumSize(size);
    //setMinimumSize(size);
    setPreferredSize(size);
    setSize(size);
  }
  public void paint(Graphics g) {
    g.drawImage(i,0,0,getWidth(),getHeight(),this);
    //g.drawImage(i,0,0,this);
  }
}
