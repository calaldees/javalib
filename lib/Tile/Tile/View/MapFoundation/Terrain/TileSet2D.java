package Tile.View.MapFoundation.Terrain;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class TileSet2D {

  private Map<String,Tile2D> tiles = new HashMap<String,Tile2D>();

  public void put(Tile2D tile) {
    if (tile instanceof Tile2DWithEdging) {
      Tile2DWithEdging tile_edge = (Tile2DWithEdging)tile;
      if (tile_edge.getEdge()!=null) {
        tiles.put(tile_edge.getEdge(), tile);
        return;
      }
    }
    tiles.put(tile.getName(),tile);
  }
  public Tile2D get(String tile_name) {return tiles.get(tile_name);}
  
  public Collection<Tile2D> getTiles() {return tiles.values();}

}
