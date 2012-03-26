package Tile.Model.Map;

import Tile.Model.Map.Terrain.AbstractTileComponent;
import Utils.Types.Integer.Point3D;
import Utils.XML.XMLLoad.LoadProcessor;
import Utils.XML.XMLLoad.DataWrapper;

public class XMLMapHandeler {
  
  static final String tag_map            = "MAP";
  static final String tag_map_foundation = "MAPFOUNDATION";
  static final String tag_map_tile       = "MAPTILE";
  
  public XMLMapHandeler() {
    new XMLMapFoundationProcessor();
    new XMLMapTileProcessor();
    new XMLMapProcessor();
  }
}

class XMLMapProcessor extends LoadProcessor<MapFoundation> {
  public XMLMapProcessor() {super(MapFoundation.class, XMLMapHandeler.tag_map, false);}
  public MapFoundation overlay(MapFoundation map, DataWrapper data) throws Exception {throw new IllegalStateException();}

  public MapFoundation create(DataWrapper data) throws Exception {
    //System.out.println("MAP:");
    //data.displayData();
    MapFoundation map = (MapFoundation)data.get(XMLMapHandeler.tag_map_foundation);
    if (map!=null) {map.setName(data.getName());}
//System.out.println("MAP LOADED: "+map.getName()+" Size:"+map.getDimension3D());
    return map;
  }
}



class XMLMapTileProcessor extends LoadProcessor<MapFoundationTile> {
  public XMLMapTileProcessor() {super(MapFoundationTile.class, XMLMapHandeler.tag_map_tile, false);}
  public MapFoundationTile overlay(MapFoundationTile map, DataWrapper data) throws Exception {throw new IllegalStateException();}
  
  public MapFoundationTile create(DataWrapper data) throws Exception {
    MapFoundation map = (MapFoundation)data.getParent().get(XMLMapHandeler.tag_map_foundation);
    Point3D       p   = data.getPoint3D("location");
    for (AbstractTileComponent tile: data.getObjectsOfType(AbstractTileComponent.class)) {
      map.mergeTile(tile.getMapFoundationTile(),p);
    }
    data.destroy();
    data.getParent().remove("maptile"); //HACK! data.destory should remove this item, problem is that data does not know what type it is :( and destroy would have to look though the arrays ... this is simpler for now
    return null;
  }
}

class XMLMapFoundationProcessor extends LoadProcessor<MapFoundation> {
  public XMLMapFoundationProcessor() {super(MapFoundation.class, XMLMapHandeler.tag_map_foundation, false);}
  public MapFoundation overlay(MapFoundation map, DataWrapper data) throws Exception {throw new IllegalStateException();}
  public MapFoundation create(DataWrapper data) throws Exception {
    return new MapFoundation(data.getDimension3D("size"));
  }
}