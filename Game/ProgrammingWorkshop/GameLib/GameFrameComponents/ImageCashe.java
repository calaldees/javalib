package GameLib.GameFrameComponents;

import java.awt.Image;
import java.awt.MediaTracker;
import java.util.HashMap;
import java.util.Map;

public class ImageCashe {
  private Map<String,Image> image_cache = new HashMap<String,Image>();

  private final GameFrameInterface game_frame;
  
  public ImageCashe(GameFrameInterface game_frame) {this.game_frame = game_frame;}


  
  public int getImageWidth (String image_name) {try {return getCacheImage(image_name).getWidth (game_frame.getFrame());} catch (Exception e) {return 0;}}
  public int getImageHeight(String image_name) {try {return getCacheImage(image_name).getHeight(game_frame.getFrame());} catch (Exception e) {return 0;}}

  private Image loadImage(String filename) {
    try {
      Image i = java.awt.Toolkit.getDefaultToolkit().getImage(filename);
      MediaTracker load_tracker = new MediaTracker(game_frame.getFrame());
      load_tracker.addImage(i, 0);
      load_tracker.waitForID(0);
      if (load_tracker.isErrorID(0)) {i=null;}
      return i;
    }
    catch (Exception e) {throw new IllegalStateException("Unable to load Image: "+filename);}
  }
  
  public Image getCacheImage(String filename) {
    if (image_cache.containsKey(filename)) {return image_cache.get(filename);}
    else {
      Image i = loadImage(filename);
      if (i!=null) {image_cache.put(filename,i);}
      return i;
    }
  }

  
}
