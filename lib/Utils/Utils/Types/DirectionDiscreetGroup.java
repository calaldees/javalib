package Utils.Types;

import Utils.MathHelper;

public enum DirectionDiscreetGroup {
   Way4(DirectionDiscreet.N,
        DirectionDiscreet.E,
        DirectionDiscreet.S,
        DirectionDiscreet.W),
        
   Way8(DirectionDiscreet.N ,
        DirectionDiscreet.NE,
        DirectionDiscreet.E ,
        DirectionDiscreet.SE,
        DirectionDiscreet.S ,
        DirectionDiscreet.SW,
        DirectionDiscreet.W ,
        DirectionDiscreet.NW),
        
   Way16(DirectionDiscreet.N  ,
         DirectionDiscreet.NNE,
         DirectionDiscreet.NE ,
         DirectionDiscreet.EEN,
         DirectionDiscreet.E  ,
         DirectionDiscreet.EES,
         DirectionDiscreet.SE ,
         DirectionDiscreet.SSE,
         DirectionDiscreet.S  ,
         DirectionDiscreet.SSW,
         DirectionDiscreet.SW ,
         DirectionDiscreet.WWS,
         DirectionDiscreet.W  ,
         DirectionDiscreet.WWN,
         DirectionDiscreet.NW ,
         DirectionDiscreet.NNW);
  
  public static DirectionDiscreetGroup getDirectionDiscreetGroup(int array_size) {
    //switch (array_size) {
    if (array_size== 4) {return DirectionDiscreetGroup.Way4;}
    if (array_size== 8) {return DirectionDiscreetGroup.Way8;}
    if (array_size==16) {return DirectionDiscreetGroup.Way16;}
    //  default : return null;
    //}
    return null;
  }
   
  private final DirectionDiscreet[] directions;
  
  DirectionDiscreetGroup(DirectionDiscreet... directions) {
    this.directions = directions;
  }

  public int getNumberOfDirections() {return directions.length;}
  
  public DirectionDiscreet getNextAnitClockwise(DirectionDiscreet d) {
    int index = getDirectionIndex(d) - 1;
    if (index < 0) {index = getMaxIndex();}
    return directions[index];
  }
  public DirectionDiscreet getNextClockwise    (DirectionDiscreet d) {
    int index = getDirectionIndex(d) + 1;
    if (index > getMaxIndex()) {index = 0;}
    return directions[index];
  }
  
  public int getNearestDirectionIndex(Direction d) {
    if (d==null) {d = Direction.createDirection();}
    int index_best = 0;
    double diff_best = Double.MAX_VALUE;
    for (int index = 0 ; index < directions.length ; index++) {
      double diff_compare = MathHelper.diff(d.getRotation(), directions[index].getDirection().getRotation());
      if (diff_compare<diff_best) {
        diff_best = diff_compare;
        index_best = index;
      }
      else {break;}
    }
    if (MathHelper.diff(1, d.getRotation())<diff_best) {index_best = 0;} //special case for full circle
    return index_best;
  }
  
  public DirectionDiscreet getNearestDirection(Direction d) {return directions[getNearestDirectionIndex(d)];}
  
  public int getDirectionIndex(DirectionDiscreet d) {throw new UnsupportedOperationException("no");}
  public int getMaxIndex()                          {return directions.length;}
  
  public Direction getDirectionAtIndex(int index) {return directions[index].getDirection();}
}