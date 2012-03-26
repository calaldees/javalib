package Tile.View.MapFoundation.Terrain;

import Utils.Types.Sprite;
import Utils.XML.XMLLoad.Indexable;
import Utils.XML.XMLLoad.Validatable;

public class Tile2D implements Indexable, Validatable {

  private Sprite sprite;
  private String name;
  
  //public Tile2D() {}

  //public Tile2D(Sprite sprite) {this.sprite = sprite;}

  public Sprite getSprite() {return sprite;}

  public String getName() {return name;}

  public boolean isValid() {
    if (sprite==null || name==null) {return false;}
    return true;
  }

}