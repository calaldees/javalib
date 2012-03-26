package Physics.Simulation.Component;

import Utils.Types.Physics.Mass;
import java.awt.Color;
import java.awt.geom.Point2D;

public class Particle extends Mass {

  private Color color = Color.RED; 
  private int   lifespan_colitions  =   3;
  private int   lifespan_iterations = 200;
  
  public Particle(Point2D.Float p          ) {super(p         );}
  public Particle(Point2D.Float p , float m) {super(p.x,p.y, m);}
  public Particle(float x, float y         ) {super(  x,  y   );}
  public Particle(float x, float y, float m) {super(  x,  y, m);}
  
  public void registerIteration() {lifespan_iterations += -1;}
  public void registerColition()  {lifespan_colitions  += -1;}
  
  public boolean alive() {return lifespan_colitions>0 && lifespan_iterations>0;}
  
  public Color getColor() {return color;}
}
