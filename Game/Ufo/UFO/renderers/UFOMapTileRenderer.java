package UFO.renderers;


import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.ImageObserver;

import Utils.StringIndexedValues;
import Utils.Types.Integer.BoundingFlags;
import Utils.Types.Integer.Point3D;

import Tile.Cursor.CursorIsometric;
import Tile.Cursor.SelectionHighlightIsometric;
import Tile.Renderers.AbstractMapTileRenderer;
import Tile.Renderers.AbstractMapRenderer;
import Tile.Map.MapFoundationTile;
import Tile.Map.LayerIdentifyer;
import Tile.Map.terrain.TerrainComponent;

import UFO.terrain.UFOTerrainComponent;


public class UFOMapTileRenderer extends AbstractMapTileRenderer {

  private CursorIsometric             cursor_type         = Utils.XML.XMLLoad.LoadManager.getItem(CursorIsometric.class            ,"default");
  private SelectionHighlightIsometric selection_highlight = Utils.XML.XMLLoad.LoadManager.getItem(SelectionHighlightIsometric.class,"select" );
  
  private final LayerIdentifyer layer_floor  = UFOTerrainComponent.TileType.FLOOR.getLayerID();
  private final LayerIdentifyer layer_left   = UFOTerrainComponent.TileType.LEFT.getLayerID();
  private final LayerIdentifyer layer_right  = UFOTerrainComponent.TileType.RIGHT.getLayerID();
  private final LayerIdentifyer layer_object = UFOTerrainComponent.TileType.OBJECT.getLayerID();

  
  public void render(Graphics g, ImageObserver io, MapFoundationTile t, StringIndexedValues options, boolean cursor, BoundingFlags selection_flags, MapFoundationTile tile_overlay) {
    if (cursor) {cursor_type.getBack().render(g,io);}
    if (selection_flags.floor) {selection_highlight.getFloor().render(g,io);}
    if (selection_flags.left)  {selection_highlight.getTopLeft().render(g,io);}
    if (selection_flags.top )  {selection_highlight.getTopRight().render(g,io);}
    renderLayer(g, io, t.getLayer(layer_floor));
    renderLayer(g, io, t.getLayer(layer_left));
    renderLayer(g, io, t.getLayer(layer_right));
    renderLayer(g, io, t.getLayer(layer_object));
    if (cursor) {cursor_type.getFront().render(g,io);}
    if (selection_flags.right)  {selection_highlight.getBottomRight().render(g,io);}
    if (selection_flags.bottom) {selection_highlight.getBottomLeft().render(g,io);}
    if (selection_flags.celing) {selection_highlight.getCelling().render(g,io);}
  }
  
  private void renderLayer(Graphics g, ImageObserver io, Object o) {
    if (o instanceof TerrainComponent) {
      ((TerrainComponent)o).getSprite().render(g,io);
    }
  }

  // This could cause massive garbage colelction ... investigate please
  public Dimension getComponentSize(MapFoundationTile t, StringIndexedValues options) {
    return new Dimension(AbstractMapRenderer.X_SIZE, AbstractMapRenderer.Y_SIZE);
  }
  
}