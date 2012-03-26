package Tile.View.MapFoundation;

import Tile.Model.Map.MapFoundationTile;
import Tile.Model.Map.Terrain.BaseTileComponent;
import Tile.View.Cursor.CursorSet;
import Tile.View.MapFoundation.Terrain.Tile2D;
import Tile.View.MapFoundation.Terrain.TileSet2D;
import Utils.StringIndexedValues;
import Utils.Types.Integer.Dimension2DImmutable;
import java.awt.Graphics;
import java.awt.image.ImageObserver;

public class MapTileRenderer2DWithEdging extends AbstractMapTileRenderer2D {

  public MapTileRenderer2DWithEdging(Dimension2DImmutable tile_size, CursorSet cursors, TileSet2D tileset, boolean edge_assist) {super(tile_size, cursors, tileset, edge_assist);}
  
  public void renderTile(Graphics g, ImageObserver io, MapFoundationTile tile_foundation, StringIndexedValues options) {
    Tile2D tile = null;
    if (options!=null) {tile = tileset.get(options.getString(MapRenderer2D.LABLENAME_TILEEDGE));}
    if (tile==null) {
      BaseTileComponent tile_base = (BaseTileComponent) tile_foundation.getLayer(BaseTileComponent.tile_layer);
      if (tile_base!=null) {
        tile = tileset.get(tile_base.getName());
      }
    }
    if (tile!=null) {
      tile.getSprite().render(g,io);
    }
  }

}