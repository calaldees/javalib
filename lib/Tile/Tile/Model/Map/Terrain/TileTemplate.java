package Tile.Model.Map.Terrain;

import Utils.XML.XMLLoad.Indexable;

public class TileTemplate implements Indexable {
  private String               name;
  private AbstractTileComponent[][] template_layout;

  public String getName() {return name;}
}