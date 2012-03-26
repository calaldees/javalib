package RTS.View.SpriteSet;

import Tile.View.SpriteSet.GameObjectSpriteSet;

import Utils.Types.Sprite;
import Utils.Types.Integer.AbstractDimension2D;

public class Unit2DSingle extends GameObjectSpriteSet {


  public static enum SpriteMode {
    NORMAL(0),
    LOADED(1),
    DAMAGED(2);
    private int mode;
    SpriteMode(int i) {mode = i;}
    public int getMode() {return mode;}
  }


  private Sprite[][] sprites = new Sprite[SpriteMode.values().length][]; //[mode][animation_frame]

  public void setSpriteSingle(Sprite sprite)                  {setSpriteSingle(sprite,SpriteMode.NORMAL);}
  public void setSpriteSingle(Sprite sprite, SpriteMode mode) {
    Sprite[] sprites_new = new Sprite[1];
    sprites_new[0] = sprite;
    sprites[mode.getMode()] = sprites_new;
  }

  public void setSpriteSingleModes(Sprite[] sprites_modes) {
    //Sprite[] sprites_new = new Sprite[1];
    for (int s = 0 ; s<sprites_modes.length ; s++) {
      sprites[s] = new Sprite[1];
      sprites[s][0] = sprites_modes[s];
    }
  }

  public void setSpriteAnimation(Sprite[] sprites_animation                 ) {setSpriteAnimation(sprites_animation, SpriteMode.NORMAL);}
  public void setSpriteAnimation(Sprite[] sprites_animation, SpriteMode mode) {
    sprites[mode.getMode()] = sprites_animation;
  }


  //More setters needed for differnt modes and animations

  
  public Sprite getSprite(                                           ) {return getSprite(SpriteMode.NORMAL);}
  public Sprite getSprite(SpriteMode sprite_mode                     ) {return getSprite(sprite_mode,0);}
  public Sprite getSprite(SpriteMode sprite_mode, int animation_frame) {
    int mode  = sprite_mode.getMode();
    int frame = animation_frame%sprites[mode].length;
    return sprites[mode][frame];
  }
  
  public AbstractDimension2D getSize() {return getSprite().getSize();}
  
  public boolean isValid() {
    return sprites!=null      &&
           sprites.length > 0 &&
           sprites[0][0]!=null;
  }

}