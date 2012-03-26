package Tile.View.MapFoundation;


import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;

//import Utils.StringIndexedStrings;
import Utils.StringIndexedValues;
import Utils.StringIndexedValuesUpdatable;
import Utils.Types.Integer.Point3D;
import Utils.Types.Integer.Point3DImmutable;
import Utils.Types.Integer.Point3DUpdatable;
import Tile.Model.Map.*;
import Utils.ModelViewControllerFramework.ViewRenderer;
import static Tile.GUIComponents.MapComponent.*;



public class MapRendererIsometric extends AbstractMapRenderer {
  
  public static final String LABLENAME_DRAW_SINGLE_LAYER  = "draw_single_layer";
  
  public  final int X_JUMP;
  public  final int Y_JUMP;
  public  final int Z_JUMP;
  private final int X_SIZE;        //Derived constant - see contructor
  private final int Y_SIZE;        //Derived constant
  private final int GRADIENT;      //Derived constant
  private final int Y_JUMP_DOUBLE; //Derived constant
  
  //private static final int TILE_HEIGHT = Y_SIZE + Z_JUMP;
  private int MAX_DISPLAYABLE_LAYERS = 4;// = ConfigManager.getInt("MAX_DISPLAYABLE_LAYERS");
  
 
  
  public MapRendererIsometric(ViewRenderer<MapFoundationTile> tile_renderer, int x_jump, int y_jump, int z_jump) {
    super(tile_renderer);
    this.X_JUMP = x_jump;
    this.Y_JUMP = y_jump;
    this.Z_JUMP = z_jump;
    if (X_JUMP<=0) {throw new IllegalArgumentException(this.getClass().getName()+": X_JUMP not set");}
    if (Y_JUMP<=0) {throw new IllegalArgumentException(this.getClass().getName()+": Y_JUMP not set");}
    if (Z_JUMP<=0) {throw new IllegalArgumentException(this.getClass().getName()+": Z_JUMP not set");}
    //if (MAX_DISPLAYABLE_LAYERS<=0) {throw new IllegalArgumentException(this.getClass().getName()+": Isometric Displayable layers not set");}
    X_SIZE        =  X_JUMP * 2;
    Y_SIZE        = (Y_JUMP * 2) + Z_JUMP;
    GRADIENT      = X_JUMP / Y_JUMP;
    Y_JUMP_DOUBLE = Y_JUMP * 2;
  }
  
  
  public void render(Graphics g, ImageObserver io, MapFoundation map, StringIndexedValues options) {
    if (map==null) {return;}
    
    final int MAX_X = map.getDimension3D().getWidth();
    final int MAX_Y = map.getDimension3D().getLength();
    
    int layers = map.getDimension3D().getHeight();                      //The number of layers to be show
    if (options!=null) {layers = options.getInt(LABLENAME_LAYER)+1;}    //+1 because if we are viewing Layer=0 then we are showing "1" layer (the layer 0)
    
    Rectangle clip = g.getClipBounds();
    if (clip==null) {clip = new Rectangle( new Point(0,0), getComponentSize(map,options));}
    

    
    //Set the number of Z Layers to show
    
    int z_begin = 0;                                //Default start drawing from the bottom layer
    int z_end   = map.getDimension3D().getHeight(); //Default draw ALL layers by stopping at the final (top) layer
    if (layers>0 && layers<z_end)                     {z_end   = layers;} //Check "layers" is in range of this map and correct if needed, if "layers" is 0 (draw no layers) then z_end will stay as MAX for this map
    if (z_end-z_begin>MAX_DISPLAYABLE_LAYERS)         {z_begin = z_end - MAX_DISPLAYABLE_LAYERS;}
    if (options.getBool(LABLENAME_DRAW_SINGLE_LAYER)) {z_begin = z_end - 1;}
    layers = z_end - z_begin;
    
    //Point3D cursor = null;
    if (map instanceof MapFoundationWithCursor) {
      //cursor=
      ((StringIndexedValuesUpdatable)options).add(LABLENAME_CURRENT_CURSOR_POSITION,((MapFoundationWithCursor)map).getCursor());
    }

    Point3DUpdatable p = new Point3DUpdatable();
    //if (options instanceof StringIndexedValuesUpdatable) {
    ((StringIndexedValuesUpdatable)options).add(LABLENAME_CURRENT_DRAW_POSITION,p);
    //}

    g.translate(X_JUMP * (MAX_Y-1), 0                  );
    g.translate(0                 , Z_JUMP * (layers-1)); // Translate the bottom layers down (+y) so the top layer is always at the same place
//REM? WHAT DOES THIS DO!!!    
    clip.y += -(Z_JUMP * layers);  // Move the clip area for the bottom layer
    
    for (p.z=z_begin ; p.z<z_end ; p.z++) {
      int clip_y = clip.y - (Z_JUMP * (p.z+1)); // Make sure we dont calculate the gap at the top (z_jump)
      int top    = (clip_y / Y_JUMP) - 1;
      int left   = (clip.x / X_JUMP);
      int right  = (MAX_X + MAX_Y) - ((clip.x + clip.width ) / X_JUMP) - 2;
      int bottom = (MAX_X + MAX_Y) - ((clip_y + clip.height) / Y_JUMP) - 3;

      int last_z = z_end - 1;
      Point3D start_index = getMapTileIndex(map, new Point(clip.x+clip.width, clip.y            ), last_z, p.z); // We need the +z_jump because getMapTileIndex() subtracts it
      Point3D end_index   = getMapTileIndex(map, new Point(clip.x           , clip.y+clip.height), last_z, p.z);

      int y_begin = start_index.y();
      int y_end   =   end_index.y() + 3; // +4 is needed to ensure the walls of the tiles BELOW the clip area are draw. No harm is done to efficency because no tiles extra tiles are draw in X loop

      // Limit range to actual size of array (prevent null exceptions)
      y_begin = limit(y_begin);
      y_end   = limit(y_end, MAX_Y);

      //translate for y_rows 0 to y_begin. We need to pretend we have drawn the previous rows
      g.translate(-X_JUMP*y_begin, Y_JUMP*y_begin);

      for (p.y=y_begin ; p.y<y_end ; p.y++) {
        int x_begin = Math.max( top-p.y          , left-(MAX_Y-p.y)           );
        int x_end   = Math.min( MAX_X-(right-p.y), MAX_X-(bottom-(MAX_Y-p.y)) );

        x_begin = limit(x_begin, MAX_X);
        x_end   = limit(x_end  , MAX_X);

        if (x_end<x_begin) {x_end = x_begin;}

        //translate for x_tile 0 to x_begin. We need to pretend we have draw the previous tiles
        g.translate(X_JUMP*x_begin, Y_JUMP*x_begin);

        for (p.x=x_begin; p.x<x_end ; p.x++) {
          //g.setColor(Color.MAGENTA);
          //g.drawRect(0,0,X_SIZE,Y_SIZE);
          renderTile(g,io,map,p,options);
          
          g.translate(X_JUMP,Y_JUMP); // Goto Next Tile/Square 
        } //End X
        
        g.translate(-x_end*X_JUMP, -x_end*Y_JUMP); // Rewind Row
        g.translate(-X_JUMP,Y_JUMP);               // Goto Next Low
      } // End Y
      
      g.translate(y_end*X_JUMP, -y_end*Y_JUMP); // Rewind Level
      g.translate(0,-Z_JUMP);                   // Goto Next Level

      clip.y      += Z_JUMP; // Change the clip bounds for the next layer
      clip.height += Z_JUMP;
    } // End Z
    
    // Go back to start position
    g.translate(-X_JUMP * (MAX_Y-1),-Z_JUMP);
    
    //Interesting ... this clip area is ***ed ... why? ... it should show the clip area .. but it is too high
    //g.setColor(Color.RED);
    //g.drawRect(clip.x+1,clip.y+1,clip.width-2,clip.height-2);
  }
  
  public Rectangle getClip(MapFoundation map, StringIndexedValues options, Point3D p) {return getClip(map,p);}
  public Rectangle getClip(MapFoundation map                             , Point3D p) {
    if (p==null) {return null;}
    Point ap = new Point((X_JUMP*getMapLength(map)) + p.x()*X_JUMP - p.y()*X_JUMP, p.y()*Y_JUMP + p.x()*Y_JUMP + Z_JUMP);  // Returns top middle corner of floor sprite
    return new Rectangle(ap.x-X_JUMP, ap.y-Z_JUMP+1, X_SIZE-1, Y_SIZE);
  }
  
  
  public Dimension getComponentSize(MapFoundation map, StringIndexedValues options) {
    int layers_to_view = map.getDimension3D().getHeight();
    if (options!=null) {layers_to_view = options.getInt(LABLENAME_LAYER);}
    if (layers_to_view>=MAX_DISPLAYABLE_LAYERS) {layers_to_view=MAX_DISPLAYABLE_LAYERS-1;}  //W THE **** is THIS!
    int l = map.getDimension3D().getLength();
    int w = map.getDimension3D().getWidth();
    int x = 2 * sizeCalc(X_JUMP,w,l);
    int y = 2 * sizeCalc(Y_JUMP,l,w) + (Z_JUMP * (layers_to_view+1));
    return new Dimension(x,y);
  }

  public int getXJump() {return X_JUMP;}
  public int getYJump() {return Y_JUMP;}
  
//-------------------------------------------------------------------------
// Private
//-------------------------------------------------------------------------
  
  private static int sizeCalc(int jump, int l, int w) {
    return (jump*Math.min(l,w)) + (jump*(Math.max(l,w)-Math.min(l,w))/2);
  }

  public Point3D getMapTileLocation(MapFoundation map, StringIndexedValues options, Point p) {
    return getMapTileIndex(map,p,options.getInt(LABLENAME_LAYER));
  }
  // Take Point and find the tile index it corresponds to
  //
  // "FEEL THE FORCE MOTHER F***ER!!!!" - Quote Me: after ird finished this method
  // This F***IN ALGORITHUM ... I spent days working through this Ba****d
  // I mailed my friends ... I was getting my arse kicked.
  // I cant belive the result is so simple. Im dumb, so very very dumb
  // (Later that evening)
  // OH F**K!!! the F**KN thing only works for map size X=Y. Im really pissed now!
  // (Later still)
  // It's 1:43 AM ... I have work tomorow ... im tyred ... but the mother f***er is finished
  // (Personal note: im going to give the Blood Brothers Band the box of chocs tomorow)
  public  Point3D getMapTileIndex(MapFoundation map, Point p, int layer) {return getMapTileIndex(map,p,layer,layer);}
  private Point3D getMapTileIndex(MapFoundation map, Point p, int top_layer, int target_layer) {

    if (map==null || p==null) {return null;}
    
    int cx = X_JUMP*getMapLength(map);
    int cy = Y_JUMP*getMapLength(map);
    
    int p_y = p.y - (((top_layer - target_layer) + 1) * Z_JUMP);

    int d          = (p_y   %Y_JUMP_DOUBLE) * GRADIENT;
    int x_row      = (p.x+d)/X_SIZE;
    int y_row      =  p_y   /Y_JUMP_DOUBLE;
    int push       =  cy    /Y_JUMP_DOUBLE;
    int x_index    = x_row + y_row - push;

        d          = X_SIZE - d;
        x_row      = (p.x+d)/X_SIZE;
        push       = cx/X_SIZE;
    int y_index    = push - x_row + y_row;

    return new Point3DImmutable(x_index, y_index, target_layer);
  }
  
  private static int getMapLength(MapFoundation map) {return map.getDimension3D().getLength();}
  
  private static int limit(int value) {
    if      (value<0    ) {return 0;}
    else                  {return value;}
  }

  private static int limit(int value, int limit) {
    if      (value<0    ) {return 0;}
    else if (value>limit) {return limit;}
    else                  {return value;}
  }






}