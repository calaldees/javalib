package Dune2.View.Simple;


import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.ImageObserver;

import Tile.Model.Map.MapFoundationTile;
import Tile.Model.Map.Terrain.BaseTileComponent;
import Tile.View.MapFoundation.AbstractMapTileRenderer;
import Utils.StringIndexedValues;
import Utils.Types.Integer.BoundingFlags;

//Temp for colour map
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;


public class ViewSimpleMapTileRenderer extends AbstractMapTileRenderer {

  private static final int TILE_SIZE = 10;
  private Map<String,Color> colour_mapping = new HashMap<String,Color>();
  
  
  public ViewSimpleMapTileRenderer() {
    colour_mapping.put("sand"       , new Color(255,183, 40));
    colour_mapping.put("dune"       , new Color(255,165,100));
    colour_mapping.put("rock"       , new Color(160,160,160));
    colour_mapping.put("mountain"   , new Color(127, 50,  0));
    colour_mapping.put("spice_light", new Color(255,120, 50));
    colour_mapping.put("spice_heavy", new Color(255, 70,  0));
  }
  
  public void render(Graphics g, ImageObserver io, MapFoundationTile t, StringIndexedValues options, boolean cursor, BoundingFlags selection_flags, MapFoundationTile tile_overlay) {
    if (t!=null) {
      BaseTileComponent tile = (BaseTileComponent) t.getLayer(BaseTileComponent.tile_layer);
      if (tile!=null) {
        Color c = colour_mapping.get(tile.getName());
        if (c==null) {c = Color.BLACK;}
        g.setColor(c);
        g.fillRect(0, 0, TILE_SIZE+1, TILE_SIZE+1); //Add +1 to remove gaps?
      }
    }
    paintSelectionBounds(g,io,selection_flags);
    if (cursor) {
      g.setColor(Color.WHITE);
      g.drawRect(0, 0, TILE_SIZE, TILE_SIZE);
    }
  }


  public Dimension getComponentSize(MapFoundationTile t, StringIndexedValues options) {
    return new Dimension(TILE_SIZE+1,TILE_SIZE+1); //Add +1 to remove gaps?
  }

  private void paintSelectionBounds(Graphics g, ImageObserver io, BoundingFlags flags) {
    g.setColor(Color.BLUE);
    if (flags.inbounds) {}
    if (flags.left    ) {g.drawLine(        0,         0,         0, TILE_SIZE);}
    if (flags.right   ) {g.drawLine(TILE_SIZE,         0, TILE_SIZE, TILE_SIZE);}
    if (flags.top     ) {g.drawLine(        0,         0, TILE_SIZE,         0);}
    if (flags.bottom  ) {g.drawLine(        0, TILE_SIZE, TILE_SIZE, TILE_SIZE);}
  }
  
}
