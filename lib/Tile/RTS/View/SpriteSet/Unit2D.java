package RTS.View.SpriteSet;

import Utils.Types.Direction;
import Utils.Types.DirectionDiscreet;
import Utils.Types.DirectionDiscreetGroup;
import Utils.Types.Integer.AbstractDimension2D;
import Utils.Types.Sprite;

import Tile.View.SpriteSet.GameObjectSpriteSet;



public class Unit2D extends GameObjectSpriteSet  {

  private Sprite[]               sprites;
  private DirectionDiscreetGroup direction_group;
  
  public Sprite getSprite(Direction d) {return sprites[direction_group.getNearestDirectionIndex(d)];}

  public AbstractDimension2D getSize() {return getSprite(DirectionDiscreet.N.getDirection()).getSize();}
  
  public DirectionDiscreetGroup getDirectionGroup() {return direction_group;}
  
  public boolean isValid() {
    if (sprites        ==null) {return false;}
    if (direction_group==null) {return false;}
    return true;
  }

  //----------------------------------------------------------------------------
  // Package Private
  //----------------------------------------------------------------------------

  void setSprites(Sprite[] sprites) {
    this.sprites = sprites;
    direction_group = DirectionDiscreetGroup.getDirectionDiscreetGroup(sprites.length);
  }



}