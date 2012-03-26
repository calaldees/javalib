package Platform;

import Utils.Types.Sprite;

public class CharacterTemplate {

  public enum CharacterMode {
    Stationary(0),
    Jump      (1),
    Walk      (2),
    Run       (3),
    Sprint    (4),
    Swim      (5),
    Crouch    (6);
    public static CharacterMode getCharacterMode(int index) {
      try                 {return values()[index];}
      catch (Exception e) {return null;}
    }
    private final int index;
    CharacterMode(int index) {this.index = index;}
    public int getIndex() {return index;}
  }

  private final Sprite[][]     sprites = new Sprite[CharacterMode.values().length][];
  private final CharacterModel model   = new CharacterModel();
 
  public CharacterTemplate() {}
  
  public void setAnimation(CharacterMode mode, Sprite   s) {Sprite[] ss = {s}; setAnimation(mode, ss);}
  public void setAnimation(CharacterMode mode, Sprite[] s) {sprites[mode.getIndex()] = s;}

  public Sprite getFrame(CharacterMode mode, int frame) {
    try {
      Sprite[] frames = sprites[mode.getIndex()];
      return frames[frame%frames.length];
    }
    catch (Exception e) {}
    return sprites[0][0];
  }
  
  public CharacterModel getModel() {return model;}
}