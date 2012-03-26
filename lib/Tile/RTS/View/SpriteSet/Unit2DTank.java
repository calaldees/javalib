package RTS.View.SpriteSet;

import Utils.Types.Direction;
import Utils.Types.DirectionDiscreetGroup;
import Utils.Types.Sprite;

public class Unit2DTank extends Unit2D {

  private Sprite[]               sprites_tracks;
  private DirectionDiscreetGroup direction_group_tracks;
  
  //public Unit2DTank(Sprite[] sprites_turret, Sprite[] sprites_tracks) {
    //super(sprites_turret);
  //}

  public Sprite getSpriteTracks(Direction d) {return sprites_tracks[direction_group_tracks.getNearestDirectionIndex(d)];}
  
  public boolean isValid() {
    if (        sprites_tracks==null) {return false;}
    if (direction_group_tracks==null) {return false;}
    return super.isValid();
  }
  
  void setSpriteTracks(Sprite[] sprites_tracks) {
    this.sprites_tracks = sprites_tracks;
    direction_group_tracks = DirectionDiscreetGroup.getDirectionDiscreetGroup(sprites_tracks.length);
  }

}