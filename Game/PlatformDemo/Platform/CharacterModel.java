package Platform;

import Platform.CharacterTemplate.CharacterMode;
import Utils.Types.Physics.Force;

public class CharacterModel {
  
  class CharacterModelMode {
    public final CharacterMode mode;
    //public final Force  acceleration;
    public final double limit;
    //public final double friction;
    public CharacterModelMode(CharacterMode mode, double acceleration_force, double limit) {
      this.mode         = mode;
      //this.acceleration = new Force(acceleration_force);
      this.limit        = limit;
      //this.friction     = friction;
    }
  }
  
  private static final double friction_default = 0.5;
  
  private       float                mass  = 10;
  private final CharacterModelMode[] modes = new CharacterModelMode[CharacterMode.values().length];
  
  public CharacterModel() {}
  
  public void addMode(CharacterMode mode,double acceleration,double limit) {
    modes[mode.getIndex()] = new CharacterModelMode(mode, acceleration, limit);
  }
  
  //public Force getAcceleration(CharacterMode mode) {
  //  try                 {return modes[mode.getIndex()].acceleration;}
  //  catch (Exception e) {return null;}
  //}
  public double getLimit       (CharacterMode mode) {
    try                 {return modes[mode.getIndex()].limit;       }
    catch (Exception e) {return 0;}
  }
  //public double getFriction    (CharacterMode mode) {
    //try                 {return modes[mode.getIndex()].friction;    }
    //catch (Exception e) {return friction_default;}
  //}
  
}
