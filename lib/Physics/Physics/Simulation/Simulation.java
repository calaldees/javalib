package Physics.Simulation;

import java.util.Collection;
import java.util.Vector;
import java.awt.geom.Point2D;
import java.awt.Dimension;

import Utils.Types.Physics.Mass;
import Physics.Simulation.Component.MassConstruct;
import Physics.Simulation.Component.Spring;
import Physics.Simulation.Process.SimulationProcess;
import Utils.ModelViewControllerFramework.Model;


public class Simulation implements Model {

//---------------------------------------------------------------------------------------
// Variables
//---------------------------------------------------------------------------------------
  private       float     gravity = (float)0.3;
  private final Dimension simulation_size = new Dimension(400,400);

  private final Collection<Mass>          masss           = new Vector<Mass>();
  private final Collection<MassConstruct> mass_constructs = new Vector<MassConstruct>();
  //private final Collection<Particle>      particles       = new Vector<Particle>();
  private final Collection<Spring>        springs         = new Vector<Spring>();

  private final Collection<SimulationProcess> simulaion_processs = new Vector<SimulationProcess>();
  
//---------------------------------------------------------------------------------------
// Constructor
//---------------------------------------------------------------------------------------
  
  public Simulation() {
    add(new Physics.Simulation.Process.CollisionDetection());
    add(new Physics.Simulation.Process.CalcForces());
  }
  
//---------------------------------------------------------------------------------------
// Simulate Tick
//---------------------------------------------------------------------------------------
  
  public void updateModel() {
    for (SimulationProcess p : simulaion_processs) {p.process(this);}
    applyForces();        //Apply all acumulated forces
  }

//---------------------------------------------------------------------------------------
// Public Attribute Methods
//---------------------------------------------------------------------------------------
  
  public float getGravity()    {return gravity;}
 
  public void      setSimulationSize(Dimension d) {simulation_size.setSize(d);}
  public Dimension getSimulationSize()            {return simulation_size;}
  
//---------------------------------------------------------------------------------------
// Public Utilitys
//---------------------------------------------------------------------------------------
  public Mass getNearestMass(Point2D p) {
    double min_distance = Double.MAX_VALUE;
    Mass   mass_nearest = null;
    for (Mass m : masss) {
      double new_distance = m.getPoint().distanceSq(p);
      if (new_distance < min_distance) {
        min_distance = new_distance;
        mass_nearest = m;
      }
    }
    return mass_nearest;
  }
  
  
//---------------------------------------------------------------------------------------
// Public Collection Accessors
//---------------------------------------------------------------------------------------
  
  public Collection<Mass>          getMasss()         {return masss;}
  public Collection<Spring>        getSprings()       {return springs;}
  public Collection<MassConstruct> getMassConstucts() {return mass_constructs;}
  
//---------------------------------------------------------------------------------------
// 
//---------------------------------------------------------------------------------------
  
  public void add(Object o) {
    if (o instanceof SimulationProcess) {simulaion_processs.add((SimulationProcess)o);}
    //if (o instanceof Particle     ) {particles.add((Particle)o);}
    if (o instanceof MassConstruct) {mass_constructs.add((MassConstruct)o);}
    if (o instanceof Mass         ) {masss.add(          (Mass)         o);}
    if (o instanceof Spring       ) {springs.add((Spring)o);}
  }

  
//---------------------------------------------------------------------------------------
// Private Simulation Calculation
//---------------------------------------------------------------------------------------


  private void applyForces() {
    for (Mass m : masss) { m.applyForce();}
  }
  

}