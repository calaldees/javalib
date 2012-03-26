package Physics.Simulation.Process;

import Utils.Types.Physics.Mass;
import Physics.Simulation.Component.Particle;
import Physics.Simulation.Simulation;
import java.awt.geom.Point2D;
import java.util.Iterator;

public class CollisionDetection implements SimulationProcess {

  private final float bounce_factor = (float) -0.75;
  
  public void process(Simulation sim) {
    float width  = (float)sim.getSimulationSize().getWidth();
    float height = (float)sim.getSimulationSize().getHeight();
    
    for (Iterator<Mass> mass_iterator = sim.getMasss().iterator(); mass_iterator.hasNext(); ) {
      Mass m = mass_iterator.next();
      
      boolean collide = false;
      Point2D.Float p = m.getPoint();
      if     (p.x < 0     ) {m.modXVel(bounce_factor); p.x=0;     collide=true;}
      else if(p.x > width ) {m.modXVel(bounce_factor); p.x=width; collide=true;}
      if     (p.y > height) {m.modYVel(bounce_factor); p.y=height-(p.y-height); collide=true;}
      
      if (m instanceof Particle) {
        Particle particle = (Particle)m;
        particle.registerIteration();
        if (collide) {particle.registerColition();}
        if (!particle.alive()) {mass_iterator.remove();}
      }
    }
  }

}
