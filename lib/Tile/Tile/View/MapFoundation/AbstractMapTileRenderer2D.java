package Tile.View.MapFoundation;

import Tile.Model.Map.MapFoundationTile;
import Tile.View.Cursor.Cursor2D;
import Tile.View.Cursor.CursorSet;
import Tile.View.Cursor.SelectionHighlight2D;
import Tile.View.MapFoundation.Terrain.TileSet2D;
import Utils.StringIndexedValues;
import Utils.Types.Integer.BoundingFlags;
import Utils.Types.Integer.Dimension2DImmutable;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.ImageObserver;

public abstract class AbstractMapTileRenderer2D extends AbstractMapTileRenderer {

  public static final String EDGE_STRING_SPLIT = ",";

  public    final Dimension2DImmutable TILE_SIZE;
  public    final boolean              EDGEING_ASSISTENCE_ENABLED;
  protected final CursorSet            cursors;
  protected final TileSet2D            tileset;
  

  public AbstractMapTileRenderer2D(Dimension2DImmutable tile_size, CursorSet cursors, TileSet2D tileset, boolean edge_assistence_enabled) {
    this.TILE_SIZE = tile_size;
    this.cursors   = cursors;
    this.tileset   = tileset;
    this.EDGEING_ASSISTENCE_ENABLED = edge_assistence_enabled; //games like C&C that have each tile specifyed individually can optionally turn edge assistance off
  }

  public void render(Graphics g, ImageObserver io, MapFoundationTile t, StringIndexedValues options, boolean cursor, BoundingFlags selection_flags, MapFoundationTile tile_overlay) {
    if (tile_overlay!=null) {
      if (t!=null) {
        t = t.getCopy();
        t.mergeTile(tile_overlay);
      }
      else {t = tile_overlay;}
    }

    if (t!=null) {
      renderTile(g,io,t,options);
    }

    paintSelectionBounds(g,io,selection_flags);
    if (cursor) {
      getCursor2D().getCursor().render(g,io);
    }
  }

  public abstract void renderTile(Graphics g, ImageObserver io, MapFoundationTile t, StringIndexedValues options);

  public Dimension getComponentSize(MapFoundationTile t, StringIndexedValues options) {return TILE_SIZE.getAWTDimension();}

  private void paintSelectionBounds(Graphics g, ImageObserver io, BoundingFlags flags) {
    SelectionHighlight2D selection_graphics = null;
    if (flags.inbounds) {selection_graphics = getSelectionHighlight2D();}
    if (flags.inbounds && selection_graphics!=null) {
      if (flags.inbounds) {selection_graphics.getInBounds().render(g,io);}
      if (flags.left    ) {selection_graphics.getLeft()    .render(g,io);}
      if (flags.right   ) {selection_graphics.getRight()   .render(g,io);}
      if (flags.top     ) {selection_graphics.getTop()     .render(g,io);}
      if (flags.bottom  ) {selection_graphics.getBottom()  .render(g,io);}
    }
  }

  private Cursor2D getCursor2D() {
    //get string for global selected cursor?
    return (Cursor2D)cursors.getCursor("select");
  }
  private SelectionHighlight2D getSelectionHighlight2D() {
    return (SelectionHighlight2D)cursors.getCursor("highlight_green");
  }

  public static String generateEdgeStringTag(String... tile_names) {
    StringBuilder s = new StringBuilder();
    for (String tile_name : tile_names) {
      s.append(tile_name);
      s.append(EDGE_STRING_SPLIT);
    }
    return s.toString();
  }

}