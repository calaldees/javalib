package Platform;

import Platform.CharacterTemplate.CharacterMode;
import Utils.Types.Physics.Mass;
import Utils.Types.Sprite;




public class Character {
  
  private final CharacterTemplate template;
  
  private Mass m = new Mass(0,0,10);
  
  private CharacterMode mode;
  
  public Character(CharacterTemplate template) {this.template = template;}
  
  public int getX() {return (int)m.getPoint().x;}
  public int getY() {return (int)m.getPoint().y;}
  
  public void setState(CharacterMode mode) {this.mode = mode;}
  
  public void moveRight() {}
  public void moveLeft()  {}
  public void moveJump()  {
    if (mode!=CharacterMode.Jump) {
      mode = CharacterMode.Jump;
      //vel.applyAcceleration() ;
    }
  }
  
  public void applyMove() {
    m.applyForce();
  }
  
  public Sprite getFrame() {
    return template.getFrame(mode,0);
  }
}
