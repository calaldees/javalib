package Physics.Simulation.Utils;

import Utils.Types.Physics.Force;
import Physics.Simulation.Component.Particle;
import Physics.Simulation.Process.SimulationProcess;
import Physics.Simulation.Simulation;
import java.awt.geom.Point2D;
import java.util.Random;


public class ParticleCreator implements SimulationProcess {

  private final int rate = 4; 
  private       long last;
  
  private final int    magnitude_of_initial_random_force = 5;
  private final Random r = new Random();
  private Point2D.Float source = new Point2D.Float(100,100);
  
  public ParticleCreator() {}
  
  public void setRate() {}

  public void process(Simulation sim) {
    if (last%rate==0) {
      sim.add(createParticle());
    }
    last++;
  }
  
  private Particle createParticle() {
    Particle p = new Particle(source);
    p.addForce(
      new Force(r.nextFloat()*magnitude_of_initial_random_force,
                r.nextFloat()*(-magnitude_of_initial_random_force))
    );
    return p;
  }
}