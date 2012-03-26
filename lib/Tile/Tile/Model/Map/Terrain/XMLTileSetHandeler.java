package Tile.Model.Map.Terrain;

import Utils.XML.XMLLoad.LoadProcessor;
import Utils.XML.XMLLoad.DataWrapper;

import Tile.Model.Map.MapFoundation;


public class XMLTileSetHandeler extends LoadProcessor<TileSet> {

  public XMLTileSetHandeler() {super(TileSet.class, "TILESET");}

  public TileSet create(DataWrapper data) throws Exception {
    return overlay(new TileSet(data.getName()), data);
  }

  //Other XML Handelers should process the following into objects within the DataWrapper 
  // - TerrainComponents (individual tiles)
  // - Maps (prefabs)
  // - Templates
  public TileSet overlay(TileSet tileset, DataWrapper data) throws Exception {
    for (AbstractTileComponent tile: data.getObjectsOfType(AbstractTileComponent.class)) {
      // Manage variables for individual tiles
        //tile.setDamagedTile( (Tile)getItem(Tile.class,tile.getDamagedName() ) );
        //tile.setAnimTile(    (Tile)getItem(Tile.class,tile.getNextAnimName()) );
      tileset.addTerrainComponent(tile);
    }
    for (MapFoundation map : data.getObjectsOfType(MapFoundation.class)) {
      tileset.addPrefab(map);
    }
    for (TileTemplate template : data.getObjectsOfType(TileTemplate.class)) {
      tileset.addTemplate(template);
    }
    return tileset;
  }

}