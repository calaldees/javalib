package Utils.Network.PostOfficeServer;

import Utils.Network.TCPMultiplexServer.ClientChannel;

abstract class PostOfficeServerCommandHandeler {
  protected final PostOfficeServer server;
  protected final String           command_name;
  
  public PostOfficeServerCommandHandeler(String command_name, PostOfficeServer server) {
    this.command_name = command_name;
    this.server       = server;
    server.registerCommandHandeler(this);
  }
  
  public String getCommandName() {return command_name;}
  
  public abstract void process(ClientChannel client, String msg);
  
}