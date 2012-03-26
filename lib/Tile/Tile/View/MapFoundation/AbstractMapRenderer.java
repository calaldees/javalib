package Tile.View.MapFoundation;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;

import Utils.StringIndexedValues;
import Utils.StringIndexedValuesUpdatable;
import Utils.Types.Integer.Point3D;
import Utils.Types.Integer.Point3DImmutable;

import Tile.Model.Map.MapFoundation;
import Tile.Model.Map.MapFoundationSelectable;
import Tile.Model.Map.MapFoundationTile;
import Utils.ModelViewControllerFramework.ViewRenderer;


public abstract class AbstractMapRenderer implements ViewRenderer<MapFoundation> {
  
  public static final String LABLENAME_CURRENT_DRAW_POSITION   = "current_draw_postion";
  public static final String LABLENAME_CURRENT_CURSOR_POSITION = "current_cursor_position";
  public static final String LABLENAME_BOUNDINGFLAGS           = "bounding_flags";
  public static final String LABLENAME_FLOATING_MAP            = "floating_map";
  public static final String LABLENAME_FLOATING_MAP_TILE       = "floating_tile";
  public static final String LABLENAME_FLOATING_CURSOR_OFFSET  = "floating_offset";
  
  

  //public       int X_SIZE; //= ConfigManager.getInt("x_size");
  //public       int Y_SIZE; //= ConfigManager.getInt("y_size");
  
  private final ViewRenderer<MapFoundationTile> r; // = RendererManager.getRenderer(MapFoundationTile.class);
  
  public AbstractMapRenderer(ViewRenderer<MapFoundationTile> tile_renderer) {
    //super(MapFoundation.class);
    r = tile_renderer;
    if (r==null) {throw new IllegalArgumentException(this.getClass().getName()+": Tile Renderer can not be null");}
  }
  
  public abstract Point3D   getMapTileLocation(MapFoundation map, StringIndexedValues options, Point   p);
  public abstract Rectangle getClip           (MapFoundation map, StringIndexedValues options, Point3D p);
  
  protected void renderTile(Graphics g, ImageObserver io, MapFoundation map, Point3D p, StringIndexedValues options) {
    // Do stuff with Selected flags and add them to options
    if (options instanceof StringIndexedValuesUpdatable) {
      StringIndexedValuesUpdatable options_updatable = (StringIndexedValuesUpdatable)options;
      
      if (map instanceof MapFoundationSelectable) {
        MapFoundationSelectable map_selectable = (MapFoundationSelectable)map;
        options_updatable.add(LABLENAME_BOUNDINGFLAGS,map_selectable.getBoundingFlags(p));

        if (options.containsKey(LABLENAME_FLOATING_MAP)) {
          MapFoundation floating_map = options.get(MapFoundation.class,LABLENAME_FLOATING_MAP);
          if (floating_map!=null) {
            Point3D draw_pos   = options.getPoint3D(LABLENAME_CURRENT_DRAW_POSITION  );
            Point3D cursor_pos = options.getPoint3D(LABLENAME_CURRENT_CURSOR_POSITION);
            Point3D get_pos    = Point3DImmutable.sub(draw_pos,cursor_pos);
            if (options.containsKey(LABLENAME_FLOATING_CURSOR_OFFSET)) {get_pos = Point3DImmutable.sub(get_pos,options.getPoint3D(LABLENAME_FLOATING_CURSOR_OFFSET));}
            MapFoundationTile floating_tile = null;
            if ( ((map_selectable.hasSelectedArea() && map_selectable.isInsideSelection(draw_pos)) || !map_selectable.hasSelectedArea())
                  && 
                  floating_map.inBounds(get_pos)) {
              floating_tile = floating_map.getTile(get_pos);
            }
            options_updatable.add(LABLENAME_FLOATING_MAP_TILE,floating_tile);
          }
        }
      }
    }
    r.render(g,io,map.getTile(p),options);
  }
  
  public abstract int getXJump();
  public abstract int getYJump();
}
