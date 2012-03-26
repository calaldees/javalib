package RTS.GameObject;

import Tile.Model.GameObject.GameObject;

public class RTSObject extends GameObject {

  public RTSObject(RTSObjectTemplate template) {
    super(template);
  }

  public RTSObjectTemplate getTemplate() {return (RTSObjectTemplate)super.getTemplate();}
  
}