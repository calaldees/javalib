package Tile.Model.Map.Filters;

import Utils.Types.Integer.*;
import Tile.Model.Map.LayerIdentifyer;
import Tile.Model.Map.MapFoundationTile;
import Tile.Model.Map.MapFoundation;

public class MapFilter<TileType> {
  
  private MapFoundation   map;
  private LayerIdentifyer layer_id;
    
  public MapFilter(MapFoundation map, LayerIdentifyer layer_id) {
    this.map      = map;
    this.layer_id = layer_id;
  }
 
  public Dimension3D getDimension() {return map.getDimension3D();}
  
  public TileType getTile(Point3D p) {
    return (TileType)map.getTile(p).getLayer(layer_id);
  }
  
  public void setTile(Point3D p, TileType tile) {
    map.setLayer(tile,p,layer_id);
  }
}