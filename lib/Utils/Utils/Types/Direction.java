package Utils.Types;

public class Direction {
  
  private static final Direction ZERO = new Direction(0);
  
  private final double radians;
  private final double rotation;
  
  public static Direction createDirection(               ) {return     Direction.ZERO;}
  public static Direction createDirection(double rotation) {return new Direction(rotation);}
  
  Direction(double rotation) {
    this.rotation = rotation;
    this.radians  = rotation * 2 * Math.PI;
  }

  public double getRadians()  {return radians;}
  public double getRotation() {return rotation;}

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 53 * hash + (int) (Double.doubleToLongBits(this.rotation) ^ (Double.doubleToLongBits(this.rotation) >>> 32));
    return hash;
  }
  
  @Override
  public boolean equals(Object o) {
    if (o instanceof DirectionDiscreet) {o = ((DirectionDiscreet)o).getDirection();}
    if (o instanceof Direction        ) {return ((Direction)o).getRotation()==getRotation();}
    return false;
  }
}