package Utils.Types.Physics;

import java.awt.geom.Point2D;

public class Mass {

  private static final float default_mass = 1;
  private static final float circle_quauter_in_radians = (float)(Math.PI / 2);
  
  private Point2D.Float p = new Point2D.Float();
  private float         m = default_mass;
  private Velocity      v = new Velocity();
  private Force         f = new Force();
  private boolean       moveable = true;
  
  public Mass(Point2D.Float p          ) {this(p      ,default_mass);}
  public Mass(Point2D.Float p , float m) {this(p.x,p.y,m           );}
  public Mass(float x, float y         ) {this(  x,  y,default_mass);}
  public Mass(float x, float y, float m) {
    p.x = x;
    p.y = y;
    this.m = m;
  }
  
  public Point2D.Float getPoint() {return p;}
  
  public void    setMoveable(boolean b) {moveable = b;}
  public boolean getMoveable()          {return moveable;}
  
  public void addForce(Force f) {
    //System.out.println("Add Force - "+f);
    this.f.addForce(f);
  }

  public void applyForce() {
    //System.out.println("Applying Force -"+f);
    if (moveable) {
      v.applyAcceleration(Acceleration.getAcceleration(m,f));
      p.x += v.getXVel();
      p.y += v.getYVel();
    }
    f.reset();
  }
  
  public void  addMass(float m) {this.m += m;}
  public float getMass()        {return m;}

  public Force getForceInDirectionOf(float force, Mass mass_direction) {
    Point2D.Float difference = pointDifference(this,mass_direction);

    float ratio = (float)(Math.atan(Math.abs(difference.x)/Math.abs(difference.y))) / circle_quauter_in_radians;
    
    float x_force = force *    ratio;
    float y_force = force * (1-ratio);
    if (difference.x<0) {x_force=-x_force;}
    if (difference.y<0) {y_force=-y_force;}
    return new Force(x_force,y_force);
  }
  
  public void flipXVel() {v.flipX();}
  public void flipYVel() {v.flipY();}
  public void modXVel(float mod) {v.modX(mod);}
  public void modYVel(float mod) {v.modY(mod);}
  
  private static Point2D.Float pointDifference(Mass m1, Mass m2) {
    return new Point2D.Float(m1.getPoint().x - m2.getPoint().x,
                             m1.getPoint().y - m2.getPoint().y);
  }
}