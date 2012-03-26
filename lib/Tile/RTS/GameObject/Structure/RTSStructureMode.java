package RTS.GameObject.Structure;

public enum RTSStructureMode {
  Normal  (0),
  Building(1),
  Damaged (2),
  Working (3);

  public static int getNumberOfModes() {return RTSStructureMode.values().length;}
  
  private int i;
  private RTSStructureMode(int i) {
    this.i = i;
  }
  public int getIndex() {return i;}
}