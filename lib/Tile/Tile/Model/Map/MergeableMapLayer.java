package Tile.Model.Map;

public interface MergeableMapLayer {
  public boolean merge(Object o, TileOperationOptions op);
}
