package Tile.Model.Map.Terrain;

import Tile.Model.Map.LayerIdentifyer;
import Tile.Model.Map.MapFoundationTile;
import Utils.XML.XMLLoad.Validatable;


public class BaseTileComponent extends AbstractTileComponent implements Validatable {

  public static final LayerIdentifyer tile_layer = new LayerIdentifyer("MAP",null,LayerIdentifyer.MergeMode.replace,LayerIdentifyer.MergeModeBehaviour.reference,true,true,true,true);

  protected void generateReferenceMapFoundationTile() {
    MapFoundationTile tile = new MapFoundationTile();
    tile.setLayer(this,tile_layer);
    setReferenceMapFoundationTile(tile);
  }

}