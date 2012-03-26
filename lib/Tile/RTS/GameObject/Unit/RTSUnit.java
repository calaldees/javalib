package RTS.GameObject.Unit;

import RTS.GameObject.*;

public class RTSUnit extends RTSObject {
   
  public RTSUnit(RTSUnitTemplate template) {
    super(template);
  }

  public RTSUnitTemplate getTemplate() {return (RTSUnitTemplate)super.getTemplate();}
  
}
