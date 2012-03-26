package Platform;

import Utils.ModelViewControllerFramework.ViewRenderer;
import Utils.StringIndexedValues;
import Utils.Types.Sprite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.ImageObserver;

public class SceneRenderer implements ViewRenderer<Scene> {

  private final int tile_size_x = Tile.getSize();
  private final int tile_size_y = Tile.getSize();

  public void render(Graphics g, ImageObserver io, Scene sc, StringIndexedValues options) {

    //Draw Paralax Background
    int layer_count = 0;
    for (Sprite layer : sc.getBackground().getLayers()) {
      layer_count++;
      if (layer!=null) {
        int ancor_x = (int)(getView().x * (layer_count/(double)background.getNumberOfLayers()) );
        for (int draw_x = ancor_x%getView().width ; draw_x < getView().width; draw_x+=layer.getWidth()) {
          layer.render(g, io, draw_x, getView().y);
        }
      }
    }

    //Render Blocks
    Stage stage = sc.getStage();
    if (stage!=null) {
      int start_x = -getView().x/Tile.getSize();
      for (int x=start_x ; x<start_x+(getView().width/Tile.getSize())+1 ; x++) {
        int start_y = -getView().y/Tile.getSize();
        for (int y=start_y ; y<start_y+(getView().height/Tile.getSize())+1 ; y++) {
          Tile t = stage.getTile(x, y);
          if (t!=null) {
            t.getSprite().render(g, io, x*tile_size_x+getView().x, y*tile_size_y+getView().y);
          }
        }
      }
    }



    //Render Characters
    //for (Character c : characters) {


  }

  public Dimension getComponentSize(Scene s, StringIndexedValues options) {
    return new Dimension(s.getStage().getWidth()  * tile_size_x,
                         s.getStage().getHeight() * tile_size_y );
  }

}