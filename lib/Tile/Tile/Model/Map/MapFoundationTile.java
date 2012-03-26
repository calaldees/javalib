package Tile.Model.Map;

import java.util.Collection;
import java.util.Map;
import java.util.HashMap;

import Utils.ObjectCloner;
import Tile.Model.Map.LayerIdentifyer.MergeMode;
import Tile.Model.Map.LayerIdentifyer.MergeModeBehaviour;


public class MapFoundationTile {
  
  private final Map<LayerIdentifyer,Object> layers = new HashMap<LayerIdentifyer,Object>();
  
  public MapFoundationTile() {}
  
  public Collection<LayerIdentifyer> getLayers() {return layers.keySet();}
  
  public int layerCount() {return layers.size();}
  
  private void clearTile() {layers.clear();}
  
  public MapFoundationTile getCopy() {
    MapFoundationTile tile = new MapFoundationTile();
    tile.mergeTile(this);
    return tile;
  }
  
  void setTile(MapFoundationTile tile) {
    clearTile();
    mergeTile(tile);
  }
  
  public <T> T  getLayer(Class<T>        c       ) {return (T)getLayer(LayerIdentifyer.getLayerIdentifyer(c));}
  public Object getLayer(LayerIdentifyer layer_id) {return layers.get(layer_id);}
  
  public Object extractLayer(LayerIdentifyer layer_id) {
    Object item = getLayer(layer_id);
    removeLayer(layer_id);
    return item;
  }

  public void removeLayer(LayerIdentifyer layer_id) {layers.remove(layer_id);}
  
  public void setLayer(Object item                          ) {setLayer(item, LayerIdentifyer.getLayerIdentifyer(item.getClass()));}
  public void setLayer(Object item, LayerIdentifyer layer_id) {
    if      (layer_id==null) {return;}
    else if (item    ==null) {removeLayer(layer_id);}
    else                     {layers.put(layer_id,item);}
  }
  
  public void mergeTile(MapFoundationTile tile                              ) {mergeTile(tile,null);}
  public void mergeTile(MapFoundationTile tile, TileOperationOptions options) {
    if (tile==null) {return;}
    for (LayerIdentifyer layer_id: tile.getLayers()) {
      mergeLayer(tile.getLayer(layer_id),options,layer_id);
    }
  }
  
  public boolean mergeLayer(Object item                                                        ) {return mergeLayer(item,null);}
  public boolean mergeLayer(Object item, TileOperationOptions options                          ) {return mergeLayer(item,options,LayerIdentifyer.getLayerIdentifyer(item.getClass()));}
  public boolean mergeLayer(Object item, TileOperationOptions options, LayerIdentifyer layer_id) {
    if (item    ==null) {return  true;}
    if (layer_id==null) {return false;}
    MergeMode          merge_mode           = layer_id.getMergeMode();
    MergeModeBehaviour merge_mode_behaviour = layer_id.getMergeModeBehaviour();
    Object existing_item = getLayer(layer_id);
    if (existing_item==null || merge_mode==MergeMode.replace) {
      if      (merge_mode_behaviour==MergeModeBehaviour.reference) {setLayer(                         item ,layer_id); return true;}
      else if (merge_mode_behaviour==MergeModeBehaviour.clone)     {setLayer(ObjectCloner.cloneObject(item),layer_id); return true;}
    }
    if (existing_item instanceof MergeableMapLayer && merge_mode==MergeMode.merge) {
      MergeableMapLayer existing_layer = (MergeableMapLayer)existing_item;
      if      (merge_mode_behaviour==MergeModeBehaviour.reference) {return existing_layer.merge(                         item ,options);}
      else if (merge_mode_behaviour==MergeModeBehaviour.clone    ) {return existing_layer.merge(ObjectCloner.cloneObject(item),options);}
    }
    return false;
  }

}