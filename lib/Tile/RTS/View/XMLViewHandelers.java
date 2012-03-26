package RTS.View;

import Tile.View.Cursor.Cursor;
import Tile.View.MapFoundation.MapRenderer2D;
import Tile.View.MapFoundation.MapTileRenderer2DWithEdging;
import Tile.View.MapFoundation.Terrain.Tile2DWithEdging;
import Tile.View.MapFoundation.Terrain.TileSet2D;
import Tile.View.SpriteSet.GameObjectSpriteSet;
import Tile.View.TileView;
import Utils.ModelViewControllerFramework.View;
import Utils.Types.Integer.Dimension2DImmutable;
import Utils.XML.XMLLoad.DataWrapper;
import Utils.XML.XMLLoad.LoadProcessor;

public class XMLViewHandelers {

  public XMLViewHandelers() {
    new XMLView2DHandeler();
    new LoadProcessor(Tile2DWithEdging.class,"TILE2D");
  }
}

class XMLView2DHandeler extends LoadProcessor<View> {
  public XMLView2DHandeler() {super(View.class, "VIEW", false);} //The final "false" turns off cataloging loaded items, this is because Views are registered with the ViewManager

  public View overlay(View view, DataWrapper data) throws Exception {throw new IllegalStateException();} //Class's that are not cataloged should NEVER overlay data, individual items within the view can be overlayed because these items will be cataloged

  //TODO: just having the view constructed under CREATE is not helpful for overlaying additional bits later
  // implement overlay!
  public View create(DataWrapper data) throws Exception {
    if (data.get("type").equals("Tile2D")) {
      TileView v = new TileView(data.getName());

      Dimension2DImmutable tile_size = Dimension2DImmutable.valueOf(data.getInt("x_jump"), data.getInt("y_jump"));
      if (!tile_size.isPositive()) {throw new Exception("Tile Size for TILE2D view is invalid - " + tile_size.toString());}

      for (Cursor cursor : data.getSub("CURSORS").getObjectsOfType(Cursor.class)) {
        v.getCursorSet().addCursor(cursor);
      }

      TileSet2D tileset = new TileSet2D();
      for (Tile2DWithEdging t : data.getSub("TILESET2D").getObjectsOfType(Tile2DWithEdging.class)) {
        tileset.put(t);
      }

      MapTileRenderer2DWithEdging tile_renderer = new MapTileRenderer2DWithEdging(tile_size,v.getCursorSet(), tileset, data.getBool("EDGEASSIST"));

      v.addRenderer(Tile.Model.Map.MapFoundationTile.class,                   tile_renderer );
      v.addRenderer(Tile.Model.Map.MapFoundation.class    , new MapRenderer2D(tile_renderer));

      RTSUnitRenderer unit_renderer = new RTSUnitRenderer(tile_size);
      for (GameObjectSpriteSet sprite_set : data.getSub("UNITS2D").getObjectsOfType(GameObjectSpriteSet.class)) {
        unit_renderer.addSpriteSet(sprite_set);
      }
      //v.addRenderer(Tile.GameObject.GameObject.class, unit_renderer);
      v.addRenderer(RTS.GameObject.Unit.RTSUnit.class, unit_renderer);

      RTSStructureRenderer structure_renderer = new RTSStructureRenderer(tile_size);
      for (GameObjectSpriteSet sprite_set : data.getSub("STRUCTURES2D").getObjectsOfType(GameObjectSpriteSet.class)) {
        structure_renderer.addSpriteSet(sprite_set);
      }
      v.addRenderer(RTS.GameObject.Structure.RTSStructure.class, structure_renderer);

      return v;
    }
    return null;
  }
}