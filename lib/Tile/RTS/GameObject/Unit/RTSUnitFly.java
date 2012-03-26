package RTS.GameObject.Unit;

public class RTSUnitFly extends RTSUnit {

  public RTSUnitFly(RTSUnitTemplateFly template) {
    super(template);
  }

  public RTSUnitTemplateFly getTemplate() {return (RTSUnitTemplateFly)super.getTemplate();}
  
}