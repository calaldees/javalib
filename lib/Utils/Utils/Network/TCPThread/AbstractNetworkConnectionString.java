package Utils.Network.TCPThread;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public abstract class AbstractNetworkConnectionString extends AbstractNetworkConnection {

  private BufferedReader     input;
  private OutputStreamWriter output;


  public AbstractNetworkConnectionString(String host, int port) throws Exception {
    super(host, port);
    initConnection();
  }
  
  private void initConnection() {
    input  = new BufferedReader(new InputStreamReader(getInputStream()));
    output = new OutputStreamWriter(getOutputStream());
  }

  public void sendMessage(String message) {
    try {
      output.write(message);
      output.write(Character.LINE_SEPARATOR);
      output.flush();
    }
    catch (Exception e) {close();}
  }
  
  public abstract void reciveMessage(String message);

  protected void checkInputStreamForData() {
    if (input==null) {return;}
    try {
      StringBuilder message = new StringBuilder();
      while(input.ready()) {
        String line = input.readLine();
        message.append(line+"\n");
      }
      if (message.length()>0) {
//System.out.println("RECIVED="+message.toString());
        reciveMessage(message.toString().trim());
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
}