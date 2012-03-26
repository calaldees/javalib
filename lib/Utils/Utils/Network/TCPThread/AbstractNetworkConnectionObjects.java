package Utils.Network.TCPThread;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public abstract class AbstractNetworkConnectionObjects extends AbstractNetworkConnection {
  
  private ObjectInputStream        input;
  private ObjectOutputStream       output;
  
  public AbstractNetworkConnectionObjects(ConnectionManager manager, Socket socket) throws Exception {
    super(manager,socket);
  }
  
  /** 
   * Sends a Serializable object over the network
   * @see java.io.Serializable
   * @param o the object to send over the network
   */
  public void send(Object o) throws IOException {
    output.writeObject(o);
  }

  /** 
   * Called when an object is recived.
   * @param o the object being recived over the network
   */
  public abstract void receive(Object o);

  
}

// new ObjectOutputStream(outpu)
// new ObjectInputStream( input
