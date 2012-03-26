package Tile.Model.Map.Terrain;

import Utils.XML.XMLLoad.Validatable;
import Utils.XML.XMLLoad.Indexable;
import Tile.Model.Map.MapFoundationTile;

public abstract class AbstractTileComponent implements Validatable, Indexable {
  
  private String            name;
  private MapFoundationTile reference_map_tile;
  private boolean           hide_in_editor = false;

  public AbstractTileComponent() {}
  
  public String  getName()         {return name;}
  public boolean getHideInEditor() {return hide_in_editor;}
  public MapFoundationTile getMapFoundationTile() {
    //if (reference_map_tile==null) {generateReferenceMapFoundationTile();}  // This was a concept of lazzy initalsation, but this is forced at Validation time instead, this line is left just in case
    return reference_map_tile;
  }

  protected void          setReferenceMapFoundationTile(MapFoundationTile tile) {reference_map_tile = tile;   }
  protected abstract void generateReferenceMapFoundationTile();
  
  public boolean isValid() {
    generateReferenceMapFoundationTile();
    if (getName()              == null
     || getMapFoundationTile() == null ) {return false;}
    else                                 {return true; }
  }
}