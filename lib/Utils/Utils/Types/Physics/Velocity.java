package Utils.Types.Physics;

public class Velocity {

  private float x_vel;
  private float y_vel;

  public Velocity(                        ) {}
  public Velocity(float x_vel, float y_vel) {
    this.x_vel = x_vel;
    this.y_vel = y_vel;
  }
  
  public float getXVel() {return x_vel;}
  public float getYVel() {return y_vel;}
  
  public void applyAcceleration(Acceleration a) {
    x_vel += a.getXAcceleration();
    y_vel += a.getYAcceleration();
  }
  
  void flipX() {x_vel=-x_vel;}
  void flipY() {y_vel=-y_vel;}
  
  void modX(float mod_factor) {x_vel = x_vel * mod_factor;}
  void modY(float mod_factor) {y_vel = y_vel * mod_factor;}
}
