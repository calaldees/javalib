package RTS.GameObject.Unit;


public class RTSUnitTemplateStandard extends RTSUnitTemplate {

  public RTSUnitTemplateStandard() {
  }

  public RTSUnit createGameObject() {return new RTSUnit(this);}
  
  //public SpriteSetUnit getSpriteSet()                        {return (SpriteSetUnit)super.getSpriteSet();}
  //public void          setSpriteSet(SpriteSetUnit spriteset) {super.setSpriteSet(spriteset);}
  
  
}
