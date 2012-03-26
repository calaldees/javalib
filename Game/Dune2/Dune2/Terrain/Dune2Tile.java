package Dune2.Terrain;

import Tile.Model.Map.LayerIdentifyer;
import Tile.Model.Map.MapFoundationTile;
import Tile.Model.Map.Terrain.AbstractTileComponent;
import Utils.XML.XMLLoad.Validatable;

public class Dune2Tile extends AbstractTileComponent implements Validatable  {

  public static final LayerIdentifyer tile_layer = new LayerIdentifyer("MAP",LayerIdentifyer.MergeMode.replace,LayerIdentifyer.MergeModeBehaviour.reference);
  
  private Dune2Tile minor_type;
  
  /*
  public static enum TileType {
    SAND       (null         )       ,
    DUNE       (TileType.SAND)       ,
    SPICE_LIGHT(TileType.SAND)       ,
    SPICE_HEAVY(TileType.SPICE_LIGHT),
    ROCK       (TileType.SAND)       ,
    MOUNTAIN   (TileType.ROCK)        ;
    
    private TileType minor_type;
    TileType(TileType minor_type) {this.minor_type = minor_type;}
    public TileType getMinorType() {return minor_type;}
  }
   
  
  private TileType type;
  private boolean edge_top;
  private boolean edge_bottom;
  private boolean edge_left;
  private boolean edge_right;
  

  //public Dune2Tile() {}
  
  
  public TileType getType() {return type;}
  public boolean getEdgeTop()    {return edge_top;}
  public boolean getEdgeBottom() {return edge_bottom;}
  public boolean getEdgeLeft()   {return edge_left;}
  public boolean getEdgeRight()  {return edge_right;}

   */
  
  protected void generateReferenceMapFoundationTile() {
    MapFoundationTile tile = new MapFoundationTile();
    tile.setLayer(this,tile_layer);
    setReferenceMapFoundationTile(tile);
  }
  
}