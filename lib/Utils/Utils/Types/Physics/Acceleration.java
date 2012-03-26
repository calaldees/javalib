package Utils.Types.Physics;

public class Acceleration {
  
  private final float x_acc;
  private final float y_acc;
  
  public static Acceleration getAcceleration(float mass, Force f) {
    //f=ma - a=f/m
    return new Acceleration(f.getXForce()/mass,f.getYForce()/mass);
  }

  private Acceleration(float x_acc, float y_acc) {
    this.x_acc = x_acc;
    this.y_acc = y_acc;
  }

  public float getXAcceleration() {return x_acc;}
  public float getYAcceleration() {return y_acc;}
  
}
