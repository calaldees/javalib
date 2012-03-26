package Tile.Model.Map.Terrain;

import Utils.XML.XMLLoad.DataWrapper;
import Utils.XML.XMLLoad.LoadProcessor;


public class XMLTileTemplateHandeler extends LoadProcessor<TileTemplate> {
  public XMLTileTemplateHandeler() {super(TileTemplate.class, "TILETEMPLATE",false);}
  public TileTemplate create(DataWrapper data) throws Exception {
    throw new UnsupportedOperationException();
  }
  public TileTemplate overlay(TileTemplate tileset, DataWrapper data) throws Exception {
    throw new UnsupportedOperationException();
  }
}