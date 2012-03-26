package RTS.GameObject.Unit;

import Utils.Types.Direction;

public class RTSUnitTank extends RTSUnit {

  private Direction direction_tracks;
  
  public RTSUnitTank(RTSUnitTemplateTank template) {
    super(template);
  }

  public RTSUnitTemplateTank getTemplate() {return (RTSUnitTemplateTank)super.getTemplate();}
  
  public Direction getDirectionTracks(           ) {return direction_tracks;}
  public void      setDirectionTracks(Direction d) {direction_tracks = d;}
}