package Utils.Types.Physics;

public class Force {

  private float x_force;
  private float y_force;

  public Force() {}
  
  public Force(float x_force, float y_force) {
    this.x_force = x_force;
    this.y_force = y_force;
  }
  
  public float getXForce() {return x_force;}
  public float getYForce() {return y_force;}
  
  public void addForce(Force f) {
    x_force += f.getXForce();
    y_force += f.getYForce();
  }
  
  public Force getInverse() {
    return new Force(-x_force,-y_force);
  }
  
  public void reset() {
    x_force=0;
    y_force=0;
  }
  
  public String toString() {
    return "Force:("+x_force+","+y_force+")";
  }

}