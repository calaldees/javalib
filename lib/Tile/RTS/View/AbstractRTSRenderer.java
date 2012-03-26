package RTS.View;

import Tile.View.SpriteSet.GameObjectSpriteSet;
import Utils.ModelViewControllerFramework.ViewRenderer;
import Utils.Types.Integer.Dimension2DImmutable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

abstract class AbstractRTSRenderer<T> implements ViewRenderer<T> {

  protected final Dimension2DImmutable            tile_size;
  protected final Map<String,GameObjectSpriteSet> sprite_sets = new HashMap<String,GameObjectSpriteSet>();

  void addSpriteSet(GameObjectSpriteSet sprite_set) {sprite_sets.put(sprite_set.getNameOfModelPair(), sprite_set);}
  Collection<GameObjectSpriteSet> getSpriteSets()   {return sprite_sets.values();}

  public AbstractRTSRenderer(Dimension2DImmutable tile_size) {
    this.tile_size = tile_size;
  }

}
