package Utils.Apps;

import Utils.Network.TCPMultiplexServer.ClientChannel;
import Utils.Network.TCPMultiplexServer.MultiplexServer;
import Utils.Network.TCPMultiplexServer.MultiplexServerListener;

public class MultiplexServerEcho implements MultiplexServerListener {

  MultiplexServer server;
  
  public MultiplexServerEcho() {
      try {server = new MultiplexServer();} catch (Exception e) {}
      server.addListener(this);
  }
  
  public static void main(String[] args) {new MultiplexServerEcho();}

  @Override
  public void newClient(ClientChannel client) {
    System.out.println("New"+client.getChannelRemoteAddress());
    client.sendMessage("Welcome "+client.getChannelRemoteAddress()+" to the test echo server (type exit to quit)");
  }

  @Override
  public void msgRecived(ClientChannel client, String msg) {
    if (msg.equals("exit")) {client.close();}
    else {
      System.out.println("msg:"+msg);
      for (ClientChannel c : server.getClients()) {
        c.sendMessage(msg);  
      }
    }
  }

  @Override
  public void closeClient(ClientChannel client) {
    System.out.println("CloseClient");
    client.sendMessage("Thanks for comming, please drop by again");
  }

  @Override
  public void closeServer() {
    System.out.println("CloseServer");
  }
}
