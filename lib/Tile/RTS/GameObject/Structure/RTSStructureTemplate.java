package RTS.GameObject.Structure;

import RTS.GameObject.RTSObjectTemplate;

public class RTSStructureTemplate extends RTSObjectTemplate {
  
  
  
  public RTSStructureTemplate() {
    
  }

  //public SpriteSetStructure getSpriteSet()                             {return (SpriteSetStructure)super.getSpriteSet();}
  //public void               setSpriteSet(SpriteSetStructure spriteset) {super.setSpriteSet(spriteset);}
  
  public RTSStructure createGameObject() {return new RTSStructure(this);}
}