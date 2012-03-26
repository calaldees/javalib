package Dune2.View.Simple;

import Tile.View.MapFoundation.MapRenderer2D;
import Tile.View.TileView;

public class ViewSimple {
  
  public static void initViewSimple() {
    TileView          view = new TileView("default");
    ViewSimpleMapTileRenderer renderer_tile = new ViewSimpleMapTileRenderer();
    MapRenderer2D             renderer_map  = new MapRenderer2D(renderer_tile);
    view.addRenderer(Tile.Model.Map.MapFoundationTile.class, renderer_tile);
    view.addRenderer(Tile.Model.Map.MapFoundation.class    , renderer_map );
  }
}