import GameLib.Net.AbstractNetworkConnection;

public class ChatTest extends AbstractNetworkConnection {

  public ChatTest() {
   networkConnect("127.0.0.1");
  }

  public static void main(String[] args) {new ChatTest();}
}