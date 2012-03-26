package UFO.XMLHandelers;

import Utils.XML.XMLLoad.LoadProcessor;
import Utils.XML.XMLLoad.DataWrapper;

import UFO.terrain.UFOTerrainComponent;

class XMLUFOMapTileComponentHandeler extends LoadProcessor<UFOTerrainComponent> {

//-------------------------------------------------------------------------
// Constructor
//-------------------------------------------------------------------------

  public XMLUFOMapTileComponentHandeler() {super(UFOTerrainComponent.class, "TILE");}

//-------------------------------------------------------------------------
// Create
//-------------------------------------------------------------------------

  public UFOTerrainComponent create(DataWrapper data) throws Exception {
    UFOTerrainComponent tile = new UFOTerrainComponent( Utils.ImageLoader.ImageLoader.getSprite(data.getInt("id")) );
    return super.overlay(tile, data);
  }

//-------------------------------------------------------------------------
// Overlay
//-------------------------------------------------------------------------

  public UFOTerrainComponent overlay(UFOTerrainComponent m, DataWrapper data) throws Exception {throw new Exception("Overlay not supported for "+UFOTerrainComponent.class.getName());}

}