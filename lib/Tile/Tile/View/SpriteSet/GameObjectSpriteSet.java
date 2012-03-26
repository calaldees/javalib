package Tile.View.SpriteSet;

import Utils.Types.Integer.AbstractDimension2D;
import Utils.XML.XMLLoad.Indexable;
import Utils.XML.XMLLoad.Validatable;


public abstract class GameObjectSpriteSet implements Indexable, Validatable {

  private String name;
  private String name_model;

  public String getName()            {return name;}
  public String getNameOfModelPair() {return name_model;}

  public abstract AbstractDimension2D getSize();
  //public abstract boolean isValid();
}