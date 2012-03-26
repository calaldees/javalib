package Tile.Model.Map;

import java.util.Collection;
import java.util.ArrayList;

public class TileOperationOptions {
  
  private Collection<LayerIdentifyer> active_layers = new ArrayList<LayerIdentifyer>();
  private boolean all_layers = false;
  
  public TileOperationOptions() {}
  
  public Collection<LayerIdentifyer> getActiveLayers() {return active_layers;}
  
  public void    addLayer(LayerIdentifyer layer_id) {active_layers.add(   layer_id);}
  public void removeLayer(LayerIdentifyer layer_id) {active_layers.remove(layer_id);}
  
  public boolean     allLayers(         ) {return all_layers;}
  public void     setAllLayers(boolean b) {all_layers = b;}
  
}