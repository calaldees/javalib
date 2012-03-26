package Utils.Types;

public enum DirectionDiscreet {
  N  (0     ),
  NNE(0.0625),
  NE (0.125 ),
  EEN(0.1875),
  E  (0.25  ),
  EES(0.3125),
  SE (0.375 ),
  SSE(0.4375),
  S  (0.5   ),
  SSW(0.5625),
  SW (0.625 ),
  WWS(0.6875),
  W  (0.75  ),
  WWN(0.8125),
  NW (0.875 ),
  NNW(0.9375);

  private final Direction direction;
  
  DirectionDiscreet(double d) {
    direction = new Direction(d);
  }

  public Direction getDirection() {return direction;}
  
  public boolean equals(Direction d) {return getDirection().equals(d);}
}