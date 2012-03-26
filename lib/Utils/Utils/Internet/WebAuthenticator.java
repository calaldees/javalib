package Utils.Internet;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

public class WebAuthenticator extends Authenticator {
    
  private String username;
  private String password;

  public WebAuthenticator(String username, String password) {
    this.username = username;
    this.password = password;
    Authenticator.setDefault(this);
  }

  // This method is called when a password-protected URL is accessed
  protected PasswordAuthentication getPasswordAuthentication() {
    // Get information about the request
      //String promptString = getRequestingPrompt();
      //String hostname = getRequestingHost();
      //InetAddress ipaddr = getRequestingSite();
      //int port = getRequestingPort();
    System.out.println("Authenticating: user="+username);
    // Return the information
    return new PasswordAuthentication(username, password.toCharArray());
  }
  
}
