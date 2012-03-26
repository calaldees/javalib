package Platform;

import Utils.Types.Sprite;
import java.util.HashMap;
import java.util.Map;

public class Tile {

  private static final int size = 16;
  
  private static final Map<String,Tile> tiles = new HashMap<String,Tile>();
  private static void addTile(Tile   t   ) {tiles.put(t.getName(), t);}
  public  static Tile getTile(String name) {return tiles.get(name);   }
  public  static int  getSize(           ) {return size;}

  
  private final Sprite t;
  private final String name;
  
  public Tile(String name, Sprite t) {
    this.name = name;
    this.t    = t;
    addTile(this);
  }
  
  public String getName()   {return name;}
  public Sprite getSprite() {return t;}
  
}
