package Tile.System;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class MessageManager {

  {retreveMessageBuffer();} //Static initaliser. retreveMessageBuffer creates messages list, I only wanted it created in one place in case I changed it's type later.

  private static List<Message> messages;

  public static void createTileMessage(Object o, Object value) {
	  Thread.currentThread().getStackTrace()[1].getMethodName();
    //messages.add();
  }

  public static Collection<Message> retreveMessageBuffer() {
    Collection<Message> message_buffer = messages;
    messages = new LinkedList<Message>();
    return message_buffer;
  }

}