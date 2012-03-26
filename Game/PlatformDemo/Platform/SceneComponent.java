package Platform;

import java.awt.Component;
import java.awt.Graphics;

public class SceneComponent extends Component {

  private final Scene         scene;
  private final SceneRenderer renderer = new SceneRenderer();

  public SceneComponent(Scene scene) {
    this.scene = scene;
  }
  public void paint(Graphics g) {
    renderer.render(g, this, scene, null);
  }
}