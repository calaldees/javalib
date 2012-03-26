package Tile.Model.GameObject;

import Tile.System.ClientID;
import Utils.Types.Direction;
//import Utils.Types.Integer.Point3D;

public class GameObject {
  
  private static int next_id = 0;

  private final int          id;
  private ClientID           owner;
  private GameObjectTemplate template;
  //private Point3D            position;
  private Direction          direction = Direction.createDirection();
  private int                frame;              
  private int                health;
  
  public GameObject(GameObjectTemplate template) {
    this.template = template;
    id = next_id++;
  }
  public GameObject(GameObjectTemplate template, ClientID owner) {
    this(template);
    this.owner = owner;
    //position.setPoint(start_position);
  }

  public int                getId()        {return id;}
  public ClientID           getOwner()     {return owner;}
  public GameObjectTemplate getTemplate()  {return template; }
  //public Point3D            getPos()       {return position; }
  public Direction          getDirection() {return direction;}
  public int                getFrame()     {return frame;}
  public int                getHealth()    {return health;}
  
  public void               setDirection(Direction d) {direction = d;}
  public void               incrementFrame()          {frame++;}
}