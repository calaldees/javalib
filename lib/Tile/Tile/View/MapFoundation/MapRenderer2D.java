package Tile.View.MapFoundation;

import Tile.Model.Map.MapFoundation;
import Tile.Model.Map.MapFoundationWithCursor;
import Tile.Model.Map.Terrain.BaseTileComponent;
import Utils.StringIndexedValues;
import Utils.StringIndexedValuesUpdatable;
import Utils.Types.Integer.Point3D;
import Utils.Types.Integer.Point3DImmutable;
import Utils.Types.Integer.Point3DUpdatable;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;


public class MapRenderer2D extends AbstractMapRenderer {
  {}

  public static final String LABLENAME_TILEEDGE = "edge";
  //public static final String LABLENAME_TILEEDGE_TOP    = "edge_top";
  //public static final String LABLENAME_TILEEDGE_BOTTOM = "edge_bottom";
  //public static final String LABLENAME_TILEEDGE_LEFT   = "edge_left";
  //public static final String LABLENAME_TILEEDGE_RIGHT  = "edge_right";

  private       final boolean EDGEING_ASSISTENCE_ENABLED;

  public final int X_JUMP;
  public final int Y_JUMP;
  
  public MapRenderer2D(AbstractMapTileRenderer tile_renderer) {
    super(tile_renderer);
    
    if (tile_renderer instanceof AbstractMapTileRenderer2D) {
      AbstractMapTileRenderer2D tile_renderer_2d = (AbstractMapTileRenderer2D)tile_renderer;
      this.X_JUMP = tile_renderer_2d.TILE_SIZE.getWidth();
      this.Y_JUMP = tile_renderer_2d.TILE_SIZE.getLength();
      EDGEING_ASSISTENCE_ENABLED = tile_renderer_2d.EDGEING_ASSISTENCE_ENABLED;
    }

    else {
      this.X_JUMP = tile_renderer.getComponentSize(null, null).width;
      this.Y_JUMP = tile_renderer.getComponentSize(null, null).height;
      EDGEING_ASSISTENCE_ENABLED = false;
    }

    if (X_JUMP<=0) {throw new IllegalArgumentException(this.getClass().getName()+": X_JUMP not set - check XML config");}
    if (Y_JUMP<=0) {throw new IllegalArgumentException(this.getClass().getName()+": Y_JUMP not set - check XML config");}
  }

  public void render(Graphics g, ImageObserver io, MapFoundation map, StringIndexedValues options) {
    if (map==null) {return;}
    
    final int MAX_X = map.getDimension3D().getWidth();
    final int MAX_Y = map.getDimension3D().getLength();

    Rectangle clip = g.getClipBounds();
    if (clip==null) {
      clip = new Rectangle( new Point(0,0), getComponentSize(map,options));
    }
    
    
    if (map instanceof MapFoundationWithCursor) {
      ((StringIndexedValuesUpdatable)options).add(LABLENAME_CURRENT_CURSOR_POSITION,((MapFoundationWithCursor)map).getCursor());
    }
    
    Point3D start_index = getMapTileLocation(map,options,new Point(clip.x             ,clip.y              )); //map.getDimension3D().makeInBounds(
    Point3D end_index   = getMapTileLocation(map,options,new Point(clip.x+clip.width-1,clip.y+clip.height-1)); //map.getDimension3D().makeInBounds( 
    
    Point3DUpdatable p = new Point3DUpdatable();
    ((StringIndexedValuesUpdatable)options).add(LABLENAME_CURRENT_DRAW_POSITION,p);

    g.translate(X_JUMP*start_index.x(), Y_JUMP*start_index.y());
    for (p.y=start_index.y() ; p.y<=end_index.y() ; p.y++) {
      for (p.x=start_index.x() ; p.x<=end_index.x() ; p.x++) {
        if (EDGEING_ASSISTENCE_ENABLED) {lookupSuroundingTilesForEdging(map,p,options);}
        renderTile(g,io,map,p,options);
        g.translate(X_JUMP,0);
      }
      g.translate(-(end_index.x()-start_index.x()+1)*X_JUMP,Y_JUMP); //Rewind this XLine back and move to the next YLine
    }
    g.translate(0,-(end_index.y()-start_index.y()+1)*Y_JUMP);
    g.translate(-X_JUMP*start_index.x(), -Y_JUMP*start_index.y());
//g.setColor(Color.BLUE);
//g.drawRect(clip.x,clip.y,clip.width-1,clip.height-1);
  }
  
  public Point3D getMapTileLocation(MapFoundation map, StringIndexedValues options, Point p) {
    return new Point3DImmutable(p.x/X_JUMP,p.y/Y_JUMP,0);
  }

  public Rectangle getClip(MapFoundation map, StringIndexedValues options, Point3D p) {
    return new Rectangle(p.x()*X_JUMP,
                         p.y()*Y_JUMP,
                         X_JUMP,
                         Y_JUMP);
  }

  public Dimension getComponentSize(MapFoundation map, StringIndexedValues options) {
    return new Dimension(map.getDimension3D().getWidth()*X_JUMP,map.getDimension3D().getLength()*Y_JUMP);
  }
  
  public int getXJump() {return X_JUMP;}
  public int getYJump() {return Y_JUMP;}

  //-----------------------------------------------------------------------------------------
  // Edge Assit: looks at the sourading tiles and adds these to the rendering options
  //             the tile renderer can then use this info to decide on what tile to display
  //-----------------------------------------------------------------------------------------

  private void lookupSuroundingTilesForEdging(MapFoundation map, Point3D p, StringIndexedValues options) {
    String base_tile = getTileName(map,p.x(),p.y(),p.z(),null);
    ((StringIndexedValuesUpdatable)options).add(LABLENAME_TILEEDGE,
                                                AbstractMapTileRenderer2D.generateEdgeStringTag(base_tile,                                          //THIS
                                                                                                getTileName(map,p.x()  ,p.y()-1,p.z(),base_tile),   //TOP
                                                                                                getTileName(map,p.x()  ,p.y()+1,p.z(),base_tile),   //BOTTOM
                                                                                                getTileName(map,p.x()-1,p.y()  ,p.z(),base_tile),   //LEFT
                                                                                                getTileName(map,p.x()+1,p.y()  ,p.z(),base_tile) )  //RIGHT
                                                                                               );
  }
  private String getTileName(MapFoundation map, int x, int y, int z, String null_value_replacement) {
    BaseTileComponent b = (BaseTileComponent)map.getLayer(x,y,z,BaseTileComponent.tile_layer);
    if (b!=null) {return b.getName();}
    else         {return null_value_replacement;}
  }

}