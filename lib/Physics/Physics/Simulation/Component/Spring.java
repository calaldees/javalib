package Physics.Simulation.Component;

import Utils.Types.Physics.Force;
import Utils.Types.Physics.Mass;


public class Spring {
  
  private static final float max_rate_change = (float)0.05;

  public static enum SpringState {COMPRESSION_RESTORING,
                                  TENSION_RESTORING,
                                  COMPRESSION_EXTENDING,
                                  TENSION_EXTENDING,
                                  NONE};

  private final MassConstruct m1;
  private final MassConstruct m2;
  private final Material      mat;
  
  private final float         length_initial;
  //private final float         length_initial_sq;
  private final float         length_max;
  private final float         length_min;
  //private final float         length_max_sq;
  private final float         mass_of_spring;

  private float offset_previous;
  //private float length_previous_sq;

  
  public Spring(MassConstruct m1, MassConstruct m2, Material mat) {
    this.m1  = m1;
    this.m2  = m2;
    this.mat = mat;
    m1.addSpring(this);
    m2.addSpring(this);
    length_initial  = calcLength();
    //length_previous = length_initial;
    length_max      = length_initial * mat.getLimitTension();
    length_min      = length_initial * mat.getLimitcompression();
    mass_of_spring  = length_initial * mat.getDensity();
  }
  
  public float getMass() {return mass_of_spring;}
  
  public Mass getMass1() {return m1;}
  public Mass getMass2() {return m2;}
  
  public void applyForceToMass() {
    float length = calcLength();
    float offset_current = length_initial - length;
    //System.out.println("M1:"+m1.getPoint()+" M2:"+m2.getPoint());
    //System.out.println("Length:"+length+" LengthInitial:"+length_initial);
    
    
    //float ratio_change = Math.abs((length_previous / length_initial)-(length_current / length_initial));
    //if (ratio_change > max_rate_change) {
      //take smaller time steps
    //}
    
    if (length > length_max || length < length_min) {
      //System.out.println("Break");
    }
    
    float spring_force = getStrength(offset_current) * offset_current;
    //System.out.println("Offset:"+offset_current+" SpringForce:"+spring_force);
    Force f = m1.getForceInDirectionOf(spring_force,m2);
    //System.out.println(f);
    m1.addForce(f             );
    m2.addForce(f.getInverse());

    offset_previous = offset_current;
  }

  private float calcLengthSq() {return (float)m1.getPoint().distanceSq(m2.getPoint());}
  private float calcLength()   {return (float)m1.getPoint().distance(  m2.getPoint());}
  
  private float getStrength(float offset_current) {
    return mat.getStrength(getCurrentSpringState(offset_current));
  }
  
  private SpringState getCurrentSpringState(float offset) {
    if (offset>0) { //TENSION
      if      (offset > offset_previous) {return SpringState.TENSION_EXTENDING;}
      else if (offset < offset_previous) {return SpringState.TENSION_RESTORING;}
    }
    else if (offset<0) { //COMPRESSION
      if      (offset > offset_previous) {return SpringState.COMPRESSION_RESTORING;}
      else if (offset < offset_previous) {return SpringState.COMPRESSION_EXTENDING;}
    }
    return SpringState.NONE;
  }

}