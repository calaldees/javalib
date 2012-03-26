package RTS.GameObject.Unit;

import RTS.GameObject.RTSObjectTemplate;


public class RTSUnitTemplate extends RTSObjectTemplate {

  private double move_speed;
  private double turn_speed;
  
  public RTSUnitTemplate() {
    
  }

  public RTSUnit createGameObject() {return new RTSUnit(this);}
  
  
}
