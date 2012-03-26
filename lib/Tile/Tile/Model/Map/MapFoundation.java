package Tile.Model.Map;

import Utils.Types.Integer.*;

public class MapFoundation {

//-------------------------------------------------------------------------
// Variables
//-------------------------------------------------------------------------

  private MapFoundationTile[][][] map;
  private AbstractCube            size;
  private String                  name;
  
//-------------------------------------------------------------------------
// Constructors
//-------------------------------------------------------------------------
  public MapFoundation(Dimension3D d) {
    if (d==null || !d.isPositive()) {throw new IllegalArgumentException("Map created with invalid dimension");}
    map  = new MapFoundationTile[d.getHeight()][d.getLength()][d.getWidth()];
    size = CubeImmutable.valueOf(Point3DImmutable.ZERO,d);
  }

  protected MapFoundation(MapFoundation source_map) {
    if (source_map==null) {throw new IllegalArgumentException();}
    map  = source_map.map;
    size = source_map.size;
    //this(source_map.getDimension3D());
    //throw new RuntimeException("map arraycopy constructor needs implementing");
  }

  
//-------------------------------------------------------------------------
// Public methods
//-------------------------------------------------------------------------

  public void   setName(String name) {this.name = name;}
  public String getName()            {return name;}
  
  public AbstractDimension3D getDimension3D() {return size.getDimension3D();}

  public boolean inBounds(Point3D p)           {return size.inBounds(p);}
  public boolean inBounds(int x, int y, int z) {return size.inBounds(x,y,z);}
  
  public MapFoundationTile getTile(Point3D p          ) {return getTile(p.x(),p.y(),p.z());}
  public MapFoundationTile getTile(int x, int y, int z) {return map[z][y][x];}
  
  public MapFoundationTile extractTile(Point3D p,           TileOperationOptions options) {return extractTile(p.x(),p.y(),p.z(),options);}
  public MapFoundationTile extractTile(int x, int y, int z, TileOperationOptions options) {
    MapFoundationTile tile_extisting = getTile(x,y,z);
    if (tile_extisting==null) {return null;}
    if (options==null || options.allLayers()) {
      setTile(null,x,y,z);
      return tile_extisting;
    }
    else {
      MapFoundationTile tile_new = new MapFoundationTile();
      for (LayerIdentifyer layer : options.getActiveLayers()) {
        tile_new.setLayer(tile_extisting.extractLayer(layer),layer);
      }
      if (tile_extisting.layerCount()==0) {setTile(null,x,y,z);}
      return tile_new;
    }
  }
  
  public void setTile(MapFoundationTile tile, Point3D p          ) {setTile(tile, p.x(), p.y(), p.z());}
  public void setTile(MapFoundationTile tile, int x, int y, int z) {map[z][y][x] = tile;}

  public void mergeTile(MapFoundationTile tile,                               Point3D p           ) {mergeTile(tile, null, p);}
  public void mergeTile(MapFoundationTile tile, TileOperationOptions options, Point3D p           ) {mergeTile(tile, options, p.x(), p.y(), p.z());}
  public void mergeTile(MapFoundationTile tile, TileOperationOptions options, int x, int y, int z ) {
    if (tile==null) {return;}
    getTileSafe(x,y,z).mergeTile(tile,options);
  }

  public <T> T getLayer(Point3D p          , Class<T> c) {return  getLayer(p.x(),p.y(),p.z(),c);}
  public <T> T getLayer(int x, int y, int z, Class<T> c) {return getTileSafe(x,y,z).getLayer(c);}
  public Object getLayer(Point3D p          , LayerIdentifyer layer_id) {return getLayer(p.x(),p.y(),p.z(),layer_id);}
  public Object getLayer(int x, int y, int z, LayerIdentifyer layer_id) {try {return getTileSafe(x,y,z).getLayer(layer_id);} catch (Exception e) {return null;}}
  
  public void setLayer(Object o, Point3D p                                    ) {setLayer(o,p.x(),p.y(),p.z(),null);}
  public void setLayer(Object o, int x, int y, int z                          ) {getTileSafe(x,y,z).setLayer(o         );}
  public void setLayer(Object o, Point3D p          , LayerIdentifyer layer_id) {if (p==null) return; setLayer(o,p.x(),p.y(),p.z(),layer_id);}
  public void setLayer(Object o, int x, int y, int z, LayerIdentifyer layer_id) {MapFoundationTile tile=getTileSafe(x,y,z); if (tile!=null) tile.setLayer(o,layer_id);}

  public boolean mergeLayer(Object o,                               Point3D p                                    ) {return mergeLayer(o,null   ,p.x(),p.y(),p.z()         );}
  public boolean mergeLayer(Object o, TileOperationOptions options, Point3D p                                    ) {return mergeLayer(o,options,p.x(),p.y(),p.z()         );}
  public boolean mergeLayer(Object o, TileOperationOptions options, Point3D p          , LayerIdentifyer layer_id) {return mergeLayer(o,options,p.x(),p.y(),p.z(),layer_id);}
  public boolean mergeLayer(Object o,                               int x, int y, int z                          ) {return mergeLayer(o,null   ,  x ,   y  ,  z           );}
  public boolean mergeLayer(Object o, TileOperationOptions options, int x, int y, int z                          ) {if (o!=null) {return getTileSafe(x,y,z).mergeLayer(o,options         );} return true;}
  public boolean mergeLayer(Object o, TileOperationOptions options, int x, int y, int z, LayerIdentifyer layer_id) {if (o!=null) {return getTileSafe(x,y,z).mergeLayer(o,options,layer_id);} return true;}
  
//-------------------------------------------------------------------------
// Private Methods (Other)
//-------------------------------------------------------------------------
  private MapFoundationTile getTileSafe(int x, int y, int z) {
    if (!inBounds(x,y,z)) {return null;}
    MapFoundationTile tile = getTile(x,y,z);
    if (tile==null) {
      tile = new MapFoundationTile();
      setTile(tile,x,y,z);
    }
    return tile;
  }
  
  // Later when maps allow resizing funtions this method will come in handy
  //private void calcDimensionsFromArray() {
  //  size = Dimension3DImmutable.valueOf(map[0][0].length, map[0].length, map.length);
  //}

}