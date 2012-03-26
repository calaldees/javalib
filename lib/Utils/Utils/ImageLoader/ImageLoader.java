package Utils.ImageLoader;

import java.awt.Image;
import java.awt.MediaTracker;
import java.net.URL;


public class ImageLoader {

  private static final MediaTracker load_tracker = new MediaTracker(new javax.swing.JButton());
  private static       int          image_counter = 0;
  
  public static Image loadImage(URL    url     ) throws Exception {return loadImage(java.awt.Toolkit.getDefaultToolkit().getImage(url)     );}
  public static Image loadImage(String filename) throws Exception {
    //System.out.println("Loading:"+filename);
    return loadImage(java.awt.Toolkit.getDefaultToolkit().getImage(filename));
  }
  public static Image loadImage(Image img      ) throws Exception {
    load_tracker.addImage(img, image_counter++);
    load_tracker.waitForAll();
    image_counter = 0;
    if (load_tracker.isErrorAny()) {throw new Exception("Unable to load images");}
    return img;
  }

  public static Image loadImageSilent(String filename) {
    try {return loadImage(filename);}
    catch (Exception e) {}
    return null;
  }
}
