package Utils.ImageLoader;

import Utils.Types.Sprite;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageObserver;

public class SpriteArrayOpperations {
 
  private SpriteArrayOpperations() {}

  public static Sprite[] completeRotation(Sprite[] sprites) {
    Sprite[] sprites_complete = null;
    if (sprites.length == 5) {
      sprites_complete = new Sprite[8];
      sprites_complete[0] =               sprites[0] ;
      sprites_complete[1] =               sprites[1] ;
      sprites_complete[2] =               sprites[2] ;
      sprites_complete[3] =               sprites[3] ;
      sprites_complete[4] =               sprites[4] ;
      sprites_complete[5] = reflectSprite(sprites[3]);
      sprites_complete[6] = reflectSprite(sprites[2]);
      sprites_complete[7] = reflectSprite(sprites[1]);
    }
    if (sprites_complete == null) {return sprites;}
    else                          {return sprites_complete;}
  }
  
  public static Sprite[] completeAnimation(Sprite[] sprites) {
    return null;
  }
  
  private static Sprite reflectSprite(Sprite s) {
    Sprite sprite_reflected = null;
    try {
      Image fliped_image = Toolkit.getDefaultToolkit().createImage( new FilteredImageSource(s.getImage().getSource(), new FilterFlipX()) );
      //ImageLoader.loadImage(fliped_image);
      int new_x_offset = s.getWidth() - s.getImageWidth() - s.getXoffset();
      sprite_reflected = new Sprite(fliped_image, new_x_offset, s.getYoffset(), s.getWidth(), s.getHeight() );
    } catch (Exception e) {}
    return sprite_reflected;
  }
}
