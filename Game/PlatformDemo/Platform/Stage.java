package Platform;

import Utils.Types.Integer.AbstractDimension2D;

public class Stage {
  
  private final AbstractDimension2D size;
  private final Tile[][] map;
  
  public Stage(AbstractDimension2D size) {
    this.size = size;
    map = new Tile[size.getWidth()][size.getLength()];
  }
  
  public int getWidth()  {return size.getWidth(); }
  public int getHeight() {return size.getLength();}
  
  public void setTile(Tile t, int x, int y) {
    if (t!=null && size.inBounds(x, y)) {map[x][y] = t;}
  }
  public Tile getTile(        int x, int y) {
    if (size.inBounds(x, y)) {return map[x][y];}
    return null;
  }
  
}