package UFO.terrain;

import Utils.Types.Sprite;
import Utils.XML.XMLLoad.Validatable;

import Tile.Map.terrain.TerrainComponent;
import Tile.Map.MapFoundationTile;
import Tile.Map.LayerIdentifyer;


public class UFOTerrainComponent extends TerrainComponent implements Validatable {
  
  public static enum TileType {
    LEFT  (new LayerIdentifyer("TERRAIN_LEFT"  ,LayerIdentifyer.MergeMode.replace,LayerIdentifyer.MergeModeBehaviour.reference)),
    RIGHT (new LayerIdentifyer("TERRAIN_RIGHT" ,LayerIdentifyer.MergeMode.replace,LayerIdentifyer.MergeModeBehaviour.reference)),
    FLOOR (new LayerIdentifyer("TERRAIN_FLOOR" ,LayerIdentifyer.MergeMode.replace,LayerIdentifyer.MergeModeBehaviour.reference)),
    OBJECT(new LayerIdentifyer("TERRAIN_OBJECT",LayerIdentifyer.MergeMode.replace,LayerIdentifyer.MergeModeBehaviour.reference));
    
    private final LayerIdentifyer layer_id;
    TileType(LayerIdentifyer layer_id) {this.layer_id = layer_id;}
    public LayerIdentifyer getLayerID() {return layer_id;}
  }
  
  private TileType type;
  private String   damaged;
  private String   anim_next_tile;
  private int      anim_pause_time;
  private int      light      = 0;
  private int      durability = 0;
  private int      height     = -1;
  private boolean  opaque     = true;
  
  public UFOTerrainComponent(Sprite s) {super(s);}
  
  public LayerIdentifyer getLayerIdentifyer() {return type.getLayerID();}
  
  protected void generateReferenceMapFoundationTile() {
    MapFoundationTile tile = new MapFoundationTile();
    tile.setLayer(this,this.getLayerIdentifyer());
    setReferenceMapFoundationTile(tile);
  }
  
  public boolean isValid() {
    if (!super.isValid()
     || type == null) {return false;}
    else              {return true; }
  }
}