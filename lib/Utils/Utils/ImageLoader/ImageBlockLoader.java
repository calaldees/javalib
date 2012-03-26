package Utils.ImageLoader;

import java.io.File;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.awt.Point;
import java.util.Collection;
import Utils.Types.Sprite;

public class ImageBlockLoader {
  
  private static ImageBlockProcessor   sprites;

  public static void load(File file) {
    try                 {sprites = new ImageBlockProcessor(file.toString());}
    catch (Exception e) {
      sprites = null;
      Utils.ErrorHandeler.error("Image Ripping Failed", "yeah .. it failed .. shit", e);
    }
  }

  public static void addNameOverlay(String name, Point p) {sprites.addNameOverlay(name,p);}

  public static Sprite[]           getSpritesArray(int i)   {return getSprites(i).toArray(new Sprite[0]);}
  public static Collection<Sprite> getSprites(int i)        {return sprites.getSprites(i);}
  
  public static Sprite   getSprite(int sprite_num) {return sprites.getSprite(sprite_num);}
  public static Sprite   getSprite(Point p)        {return sprites.getSprite(p);         }
  public static Sprite   getSprite(int x, int y)   {return sprites.getSprite(x,y);       }
  public static Sprite   getSprite(String name)    {return sprites.getSprite(name);      }

  public static Sprite[]           getImageArray(int i)   {throw new UnsupportedOperationException("Not supported yet.");}
  public static Collection<Sprite> getImages(int i)       {throw new UnsupportedOperationException("Not supported yet.");}
  
  public static Image    getImage(int image_num)   {return sprites.getImage(image_num);  }
  public static Image    getImage(Point p)         {return sprites.getImage(p);          }
  public static Image    getImage(int x, int y)    {return sprites.getImage(x,y);        }
  public static Image    getImage(String name)     {return sprites.getImage(name);       }
  
  public static ImageIcon getImageIcon(String name) {
    try                 {return new ImageIcon(getImage(name),name);}
    catch (Exception e) {return null;}
  }

  
}