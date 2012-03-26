package Physics.Simulation.Component;

import static Physics.Simulation.Component.Spring.SpringState;

public class Material {

  private String name;

  private float density;
  
  private float limit_tension; //% of original length
  private float limit_compression;
  private float limit_angular;
  
  private float strength_compression_push;
  private float strength_compression_restore;
  private float strength_tension_push;
  private float strength_tension_restore;

  private float strength_angular;

  private float correction_angular;     //1=linear 1>stronger towards limit 1<weaker towards limit
  private float correction_tension;
  private float correction_compression;

  public Material() {}

  public String getName() {return name;}
  
  public float getDensity()          {return density;}
  public float getLimitTension()     {return limit_tension;    }
  public float getLimitcompression() {return limit_compression;}

  public float getStrength(SpringState spring_state) {
    switch (spring_state) {
      case COMPRESSION_EXTENDING: return strength_compression_push;    
      case COMPRESSION_RESTORING: return strength_compression_restore;
      case TENSION_EXTENDING:     return strength_tension_push;
      case TENSION_RESTORING:     return strength_tension_restore;
    }
    return 0;
  }
  
  public String toString() {
    return "Material: "+getName()+ " Implement this method for more info";
  }
}
