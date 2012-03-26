package Tile.System.GameLogic;

import Tile.Model.GameObject.GameObject;
import Tile.Model.GameObject.GameObjectTemplate;
import Tile.Model.Map.MapFoundation;
import Tile.System.ClientID;
import Tile.System.Message;
import Utils.Types.Integer.Point3D;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class GameLogic  {

  private int iteration = 0;

  private final Map<Integer,GameObject> game_objects = new HashMap<Integer,GameObject>();
  private final MapFoundation           map          = null;

  public  synchronized int        getCurrentIterationNumber() {return iteration;  }
  private synchronized void incrementCurrentIterationNumber() {       iteration++;}

  // Overriding methods must call super()
  public void processNextIteration(List<Message> incomming_message_buffer) {
    incrementCurrentIterationNumber();
  }

  protected void newGameObject(GameObjectTemplate template, ClientID owner, Point3D pos) {
    GameObject game_object = new GameObject(template, owner);
    game_objects.put(game_object.getId(), game_object);
    map.setLayer(game_object, pos); //may need a layeridentifyer here
    //Send create to clients with LOS
  }

  protected void removeGameObject(Point3D pos) {
    //a position may not be enough here ... a unit may have to duplicate it's position in GameObject to successfull identify it
    //send remove to clients with LOS
  }

  /*
   * Holds a map and units list
   * list of owners of units and permissions
   * list of cursor/viewport's
   * 
   * recives messages
   * decides if the client request is valid (unit exits?, permissions?)
   * process's game logic
   * returns messages (does not have to log them)
   * 
   * keeps track of iteration number and iteration number auto incremented on message passed in
   *
   * totaly detaached from any timing - the server could throw all the messages at the logic to fast forward
   *
   *
   * send to clients
   * -----
   * update gameobject - position, direction, health, mode
   * attack (started/stopped) + position
   * new (gameobject) - type, color/side, position, direction
   * remove gameobject
   * tile - type, status (visable, remove, los)
   * player message
   * status update (credit, kill count, score?, endgame)
   *
   * receive from clients
   * -------
   * move request
   * direction chnage request
   * attack request
   * request new gameobject
   * request remove gameobject
   * player message
   * cursor/view update
   * change ownership (update permissions)
   * join request
   * leave request
   *
   */

}
