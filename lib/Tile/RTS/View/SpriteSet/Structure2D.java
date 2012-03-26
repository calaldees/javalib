package RTS.View.SpriteSet;

import RTS.GameObject.Structure.RTSStructureMode;
import Tile.View.SpriteSet.GameObjectSpriteSet;
import Utils.Types.Integer.AbstractDimension2D;
import Utils.Types.Sprite;

public class Structure2D extends GameObjectSpriteSet {

  private Sprite[][] sprites = new Sprite[RTSStructureMode.getNumberOfModes()][];
  
  //public void

  void setSprites(Sprite[]... s) {
    for (int i=0 ; i<s.length ; i++) {sprites[i] = s[i];}
  }

  void setSprites(RTSStructureMode mode, Sprite[] sprites_new) {
    if (mode==null || sprites_new==null) {return;}
    sprites[mode.getIndex()] = sprites_new;
  }
  void setSprites(RTSStructureMode mode, Sprite sprite) {
    Sprite[] s = {sprite};
    setSprites(mode, s);
  }

  public AbstractDimension2D getSize() {return sprites[0][0].getSize();}

  public Sprite getSprite(RTSStructureMode mode, int frame) {
    Sprite[] s = sprites[mode.getIndex()];
    if (s==null || s.length==0 || s[frame%s.length]==null) {return getDefaultSprite();}
    return s[frame%s.length];
  }


  
  public Sprite getDefaultSprite() {return sprites[RTSStructureMode.Normal.getIndex()][0];}



  public boolean isValid() {
    if (sprites[0]==null || sprites[0][0]==null) {return false;}
    return true;
  }
  
}