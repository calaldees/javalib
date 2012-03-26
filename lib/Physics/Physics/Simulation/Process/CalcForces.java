package Physics.Simulation.Process;

import Utils.Types.Physics.Force;
import Utils.Types.Physics.Mass;
import Physics.Simulation.Component.MassConstruct;
import Physics.Simulation.Component.Spring;
import Physics.Simulation.Simulation;

public class CalcForces implements SimulationProcess {

  public void process(Simulation sim) {
    for (Spring         s : sim.getSprings()      ) {s.applyForceToMass();}
    for (MassConstruct mc : sim.getMassConstucts()) {mc.applyRotationalForces();}
    if (sim.getGravity()>0) {
      for (Mass m : sim.getMasss()) {
        m.addForce(new Force(0,m.getMass()*sim.getGravity()));
      }
    }
  }

}
