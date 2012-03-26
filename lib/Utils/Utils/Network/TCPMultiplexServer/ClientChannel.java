package Utils.Network.TCPMultiplexServer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.logging.Level;
import java.util.logging.Logger;



public class ClientChannel {
  private static final int            BUFFER_SIZE = 1024;
  private static final Charset        charset = Charset.forName("ISO-8859-1");
  private static final CharsetDecoder decoder = charset.newDecoder();

  private final SocketChannel   channel; //Network connection chanel
  private final MultiplexServer server;  //Anoying this class must be coupled with the sever so the client can remove itself from the servers client list, other than that it should NOT be used!
  private final ByteBuffer      input;
  private final ByteBuffer      output;
  
  private final StringBuilder string_buffer_in  = new StringBuilder();
  private final StringBuilder string_buffer_out = new StringBuilder();
  
  public ClientChannel(SocketChannel channel, MultiplexServer server) {
    this.channel = channel;
    this.server  = server;
    input = ByteBuffer.allocateDirect(BUFFER_SIZE);
    input.clear();
    output = ByteBuffer.allocateDirect(BUFFER_SIZE);
  }
  
  //public SocketChannel getSocketChannel() {return channel;}
  public String        getChannelRemoteAddress() {return channel.socket().getRemoteSocketAddress().toString();}
  
  
  public String readMessage() {
    try {
      int bytes_to_read = channel.read(input);
      if (bytes_to_read == -1) {close();}
      return decodeBytesToString(input);
    }
    catch (IOException e) {
      close();
      return null;
    }
  }

  public void    bufferMessage(String msg) {string_buffer_out.append(msg);}
  public boolean bufferSend() {
    if (string_buffer_out.length()>0 && sendMessage(string_buffer_out.toString())) {
      string_buffer_out.delete(0, string_buffer_out.length());
      return true;
    }
    return false;
  }
  
  public boolean sendMessage(String msg) {
    if (msg==null || msg.length()==0) {return true;}
    output.clear();
    output.put(                   msg.getBytes(charset));
    output.put(server.getMessageEnd().getBytes(charset));
    output.flip();
    try {
      while (output.hasRemaining()) {channel.write(output);}
      return true;
    }
    catch (IOException e) {
      //close();  //this created an infiante loop as data ws being sent to a closed connections
      return false;
    }
  }
  
  public void close() {server.removeClient(this);} //remove client from server hashmap list, this also fire listener events

  
  void closeChannel() throws IOException {channel.close();}
  
  private String decodeBytesToString(ByteBuffer input) {
    input.limit(input.position());
    input.position(0);
    try {string_buffer_in.append(decoder.decode(input));} catch (CharacterCodingException ex) {Logger.getLogger(ClientChannel.class.getName()).log(Level.SEVERE, null, ex);}
//System.out.println("Decoded: "+msg.toString());
    input.clear();
    int end_position = string_buffer_in.lastIndexOf(server.getMessageEnd());
    if (end_position>=0) {
      String msg_complete = string_buffer_in.substring(0, end_position);
      string_buffer_in.delete(0, end_position+server.getMessageEnd().length());
      return msg_complete;
    }
    return null;
  }
}