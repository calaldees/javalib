package Physics.Simulation.Component;

import Utils.Types.Physics.Mass;
import java.awt.geom.Point2D;
import java.util.List;
import java.util.LinkedList;

public class MassConstruct extends Mass {
  
  private final SpringList springs = new SpringList();
  
  public MassConstruct(Point2D.Float p          ) {super(p         );}
  public MassConstruct(Point2D.Float p , float m) {super(p.x,p.y, m);}
  public MassConstruct(float x, float y         ) {super(  x,  y   );}
  public MassConstruct(float x, float y, float m) {super(  x,  y, m);}
  
  public void addSpring(Spring s) {
    springs.addSpring(s);
    addMass(s.getMass()/2);
  }
  
  public void applyRotationalForces() {
    
  }
}

class SpringList {
  private final List<SpringConnection> spring_connections = new LinkedList<SpringConnection>();
  
  public void addSpring(Spring s) {}

}

class SpringConnection {
  
  private Spring spring;
  private float  radians_clockwise_to_next;
  
  public SpringConnection() {
  }
  
}