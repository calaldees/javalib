package Platform;

import Utils.Types.Sprite;

public class Background {
  private Sprite[] layers;
  
  public void setLayers(Sprite[] layers) {this.layers = layers;}
  public Sprite getLayer(int layer) {return layers[layer];}
  public int getNumberOfLayers() {return layers.length;}
  public Sprite[] getLayers() {return layers;}
}
