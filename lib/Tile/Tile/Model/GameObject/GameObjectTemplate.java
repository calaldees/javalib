package Tile.Model.GameObject;

import Utils.Types.Integer.AbstractDimension3D;
import Utils.Types.Integer.Dimension3DImmutable;
import Utils.Types.Integer.Point3D;
import Utils.XML.XMLLoad.Indexable;

public class GameObjectTemplate implements Indexable {
  
  private String              name;
  private AbstractDimension3D size = Dimension3DImmutable.ONE;
  private boolean             moveable;
  private int                 health;
  
  
  public GameObjectTemplate() {
    //setSpriteSet(spriteset); 
  }
  
  public GameObject createGameObject() {return new GameObject(this);}
  
  protected void          setName(String name) {this.name = name;}
  public    String        getName()            {return name;}
  
  //public    GameObjectSpriteSet getSpriteSet(                             ) {return spriteset;}
  //public    void                setSpriteSet(GameObjectSpriteSet spriteset) {this.spriteset = spriteset;}
  
  //protected void                 setSize(AbstractDimension3D size) {this.size = size;}
  public    AbstractDimension3D  getSize()                         {return size;     }
  public    boolean              isSolid(Point3D p)                {return getSize().inBounds(p);}
  public    boolean              isMoveable()                      {return moveable; }
  public    int                  getMaxHealth()                    {return health;   }
}