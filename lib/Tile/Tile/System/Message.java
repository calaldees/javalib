package Tile.System;

import java.util.ArrayList;
import java.util.Collection;


public class Message {

  //-------------------------Static Managers------------------------------------

  public static Message createNewMessage(String command) {
    Message m = new Message();
    m.setCommand(command);
    return m;
  }

  public static Message parseMessage(ClientID source, String message) {
    Message m = new Message();
    m.setSource(source);
    m.decodeMessage(message);
    return m;
  }

  //----------------------------- Variables-------------------------------------

  private ClientID source;
  private String   command;
  private String   message;
  private boolean  log_message = true;

  private Message() {}

  public String   getCommand() {return command;}
  public ClientID getSource()  {return source; }

  public boolean logMessage() {
    //TODO: If the message has errored in decoding or is a view request (will not affect the model) then return false
    return log_message;
  }

  public <T> T getOne(Class<T> c) {
    return null;
  }

  public <T> Collection<T> getAll(Class<T> c) {
    return new ArrayList<T>();
  }

  public void add(Object o) {
    message = null;
  }

  public String toString() {
    if (message==null) {encodeMessage();}
    return message;
  }

  private void setSource(ClientID source) {
    
  }

  private void setCommand(String command) {
    this.command = command;
  }

  private void encodeMessage() {
    //go though object collections
    //set message
  }
  private void decodeMessage(String message) {
    this.message = message;
    //go through string, relinking objects
  }
}