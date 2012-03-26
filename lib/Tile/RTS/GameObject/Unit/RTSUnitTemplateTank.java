package RTS.GameObject.Unit;

//import RTS.View.SpriteSet.SpriteSetUnitTank;

public class RTSUnitTemplateTank extends RTSUnitTemplateStandard {

  private double turn_speed_tracks;
  
  public RTSUnitTemplateTank() {
    
  }

  public RTSUnitTank createGameObject() {return new RTSUnitTank(this);}
  
  //public SpriteSetUnitTank getSpriteSet(                           ) {return (SpriteSetUnitTank)super.getSpriteSet();}
  //public void              setSpriteSet(SpriteSetUnitTank spriteset) {super.setSpriteSet(spriteset);}
  
  private double getTurnSpeedTracks() {return turn_speed_tracks;}
}