package Tile.System.Server;

import Tile.System.ClientID;
import Tile.System.GameLogic.GameLogic;
import Tile.System.Message;
import Utils.ModelViewControllerFramework.TimerHandeler;
import Utils.ModelViewControllerFramework.TimerThread;
import Utils.Network.TCPMultiplexServer.ClientChannel;
import Utils.Network.TCPMultiplexServer.MultiplexServer;
import Utils.Network.TCPMultiplexServer.MultiplexServerListener;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

  /* TODO
   * given map to start with (could be passed as messages to logic?)
   *
   */

public class TileServer implements TimerHandeler, MultiplexServerListener {

  public static final String network_command_init            = "init:";
  public static final String network_command_server_filelist = "filelist:";

  private final MultiplexServer server;
  private final TimerThread     model_timer_thread;
  private final GameLogic       model;
  private final PrintWriter     message_log = null;

  private final Map<ClientChannel, ClientID> clients                  = new HashMap<ClientChannel,ClientID>();
  private       List<Message>                incomming_message_buffer = newMessageList();

  public TileServer(GameLogic model) throws Exception {
    this.model = model;
    model_timer_thread = new TimerThread(this);
    server = new MultiplexServer();
    server.addListener(this);
    model_timer_thread.startTimerThread();
  }

  public Collection<ClientID> getClientList() {return clients.values();}

//-----------------------------Network Listener---------------------------------

  public void newClient(ClientChannel client) {
    clients.put(client, null);
  }

  public void msgRecived(ClientChannel client, String msg) {
    ClientID c = clients.get(client);
    // If message has come from a live client (a client in the game with a name and units etc)
    if (c!=null) {
      Message m = Message.parseMessage(c, msg);
      queMessageForProcessing(m);
    }
    // Else connection is not a live client
    else if (msg.startsWith(network_command_init)) {
      c = new ClientID(msg.substring(network_command_init.length()));
      c.setClientChannel(client);
      // TODO: --Future Feature--
      // if new client ID name = same name of client ID that has exited, accociate the new connection with the old players units and status
      clients.put(client, c);
      //TODO: send alert message to message buffer joined
    }
    else if (msg.startsWith(network_command_server_filelist)) {
      //TODO: Send back filelist to load
      // These files could be served localy on port 80 with the mini webserver in the utils lib
    }
  }

  public void closeClient(ClientChannel client) {
    clients.remove(client);
    // TODO: may need to save ClientID for reconnection later
    // may want to have multiple real clients for a single clientID (co-op team)
    //send message alerting other clients to the model via the message buffer, this will be propergated by the model and be in the log
  }

  public void closeServer() {
    //TODO: Tell all clients goodbye + (should say to clients before this  "server shutting down/restarting + reaons" etc
    if (!incomming_message_buffer.isEmpty()) {logMessageGroupClose();}
    message_log.close();
  }


  //-------------------------Timer Handler Thead -------------------------------

  public int  getTargetUpdatesPerSecond() {return 1;}
  public int  getMaxFrameSkip() {return 0;}
  public void processView() {}
  public void processModel() {
    if (incomming_message_buffer.isEmpty()) {model.processNextIteration(null                                       );}
    else                                    {model.processNextIteration(prepareMessageRecievedBufferForProcessing());}
  }

  //---------------------Incomming Message Buffer-------------------------------

  //synchronizing methods like this locks at the class level
  //as there is only one instance of this class then thats a significant performance overhead

  private synchronized List prepareMessageRecievedBufferForProcessing() {
    List message_buffer_to_be_processed = incomming_message_buffer;
    incomming_message_buffer = newMessageList();
    logMessageGroupClose();
    return message_buffer_to_be_processed;
  }

  private synchronized void queMessageForProcessing(Message m) {
    if (m.logMessage()) {
      if (incomming_message_buffer.isEmpty()) {logMessageGroupOpen();}
      logMessage(m);
      incomming_message_buffer.add(m);
    }
  }

  //-----------------------------Log to File------------------------------------

  private void logMessageGroupOpen() {
    if (message_log!=null) {
      message_log.println("<messagegroup id='" + model.getCurrentIterationNumber() + "'>");
    }
  }
  private void logMessageGroupClose() {
    if (message_log!=null) {
      message_log.println("</messagegroup>");
    }
  }
  private void logMessage(Message m) {
    if (message_log!=null) {
      message_log.println(m.toString());
    }
  }

  //----------------------------Private Helpers---------------------------------

  //We do not want to define the new list code in more than one place.
  private List<Message> newMessageList() {return new ArrayList<Message>();}


}