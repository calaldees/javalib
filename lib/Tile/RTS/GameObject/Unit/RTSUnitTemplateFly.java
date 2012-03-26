package RTS.GameObject.Unit;


public class RTSUnitTemplateFly extends RTSUnitTemplate {

  private double speed_takeoff;
  private double speed_land;
  private double max_altitude;
  
  public RTSUnitTemplateFly() {
  
  }
  
  public RTSUnitFly createGameObject() {return new RTSUnitFly(this);}
}