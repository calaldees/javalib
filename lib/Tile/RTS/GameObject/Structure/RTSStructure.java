package RTS.GameObject.Structure;

import RTS.GameObject.*;

public class RTSStructure extends RTSObject {

  RTSStructureMode mode = RTSStructureMode.Normal;
  
  public RTSStructure(RTSStructureTemplate template) {
    super(template);
  }

  public RTSStructureTemplate getTemplate() {return (RTSStructureTemplate)super.getTemplate();}
  
  public RTSStructureMode getMode()                      {return mode;}
  public void             setMode(RTSStructureMode mode) {this.mode = mode;}
}