package Tile.View.MapFoundation;

import Utils.ModelViewControllerFramework.ViewRenderer;
import Utils.ModelViewControllerFramework.View.*;
import Tile.Model.Map.MapFoundationTile;
import Utils.StringIndexedValues;
import Utils.Types.Integer.BoundingFlags;
import Utils.Types.Integer.Point3D;
import java.awt.Graphics;
import java.awt.image.ImageObserver;

public abstract class AbstractMapTileRenderer implements ViewRenderer<MapFoundationTile> {
  
  //public AbstractMapTileRenderer() {super(MapFoundationTile.class);}
  
  public void render(Graphics g, ImageObserver io, MapFoundationTile t, StringIndexedValues options) {
    boolean           cursor          = false;
    BoundingFlags     selection_flags = null;
    MapFoundationTile tile_overlay    = null;
    
    if (options!=null) {
      // Check if current tile is CURSOR, if so then set the cursor flag
      Point3D draw_pos    = options.getPoint3D(AbstractMapRenderer.LABLENAME_CURRENT_DRAW_POSITION);
      Point3D cursor_pos  = options.getPoint3D(AbstractMapRenderer.LABLENAME_CURRENT_CURSOR_POSITION);
      if (cursor_pos!=null && cursor_pos.equals(draw_pos)) {
        cursor=true;
      }

      //Get Selection flags
      selection_flags = options.get(BoundingFlags.class,AbstractMapRenderer.LABLENAME_BOUNDINGFLAGS);
      
      //Floating Tile
      tile_overlay = options.get(MapFoundationTile.class,AbstractMapRenderer.LABLENAME_FLOATING_MAP_TILE);
    }
    
    if (selection_flags==null) {selection_flags = new BoundingFlags();}
    render(g,io,t,options,cursor,selection_flags,tile_overlay);
  }
  
  abstract public void render(Graphics g, ImageObserver io, MapFoundationTile t, StringIndexedValues options, boolean cursor, BoundingFlags selection_flags, MapFoundationTile tile_overlay);

}
