package Utils.Apps;

import Utils.File.StreamManager;
import Utils.Network.TCPThread.ConnectionManager;
import Utils.Network.TCPThread.AbstractNetworkConnection;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class WebServerNetworkConnection extends AbstractNetworkConnection {

  private static final String string_get_request = "GET";
  private static final String server_host_path = WebServer.host_path;
  private BufferedReader     input;
  private OutputStreamWriter output;

  public WebServerNetworkConnection(ConnectionManager manager, Socket socket) throws Exception {
    super(manager,socket);
    input  = new BufferedReader(new InputStreamReader(getInputStream()));
    output = new OutputStreamWriter(getOutputStream());
  }
  
  private void getRequest(String request_string) {
    request_string = server_host_path + request_string.replaceAll("%20", " ");
    File request = new File(request_string);
    log(request.toString());
    if (!request.exists()     ) {outHTML(request.toString()+" does not exisit");}
    if ( request.isFile()     ) {outFile(request);}
    if ( request.isDirectory()) {
      File[] files = request.listFiles();//FileOperations.getFileList(request, "*");
      StringBuilder html_file_list     = new StringBuilder();
      String       path_from_host_dir = request.getAbsolutePath().replaceAll("\\\\", "/").replaceFirst(server_host_path,"");
      //String parent = request.getName(); //.getParentFile()
      //System.out.println("path_from_host_dir="+path_from_host_dir);
      //System.out.println("parent="+parent);
      if (path_from_host_dir.equals("")) {}//parent = "";}
      else                               {html_file_list.append("<a href='..'>..</a><br>\n");} //"+request.getParentFile().getName()+"
      //parent += "/";
      for (File f : files) {
        String filename = f.getName();
        //System.out.println("Parent: "+parent+" Filename: "+filename);
        //parent = "";
        if (f.isDirectory()) {
          html_file_list.append("DIR ");
          filename += "/";
        }
        html_file_list.append("<a href='"+filename+"'>"+filename+"</a><br>\n"); //parent+
      }
      outHTML(html_file_list.toString());
    }
    close();
  }

  private void outHTML(String message) {
    try {
      output.write("HTTP/1.0 200 OK\r\n");
      output.write("Content-type: text/html; charset=utf-8\r\n");
      output.write("\r\n");
      output.write("<html><head><title>MiniServer</title></head><body>\n");
      output.write(message);
      output.write("</body></html>");
      output.flush();
    }
    catch (Exception e) {}
  }

  private void outFile(File f) {
    try                 {StreamManager.fileToOutputStream(f,getOutputStream());}
    catch (Exception e) {}
  }

  protected void checkInputStreamForData() {
    if (input==null) {return;}
    try {
      String get_path = null;
      while(input.ready()) {
        String line = input.readLine();
        if (line.startsWith(string_get_request)) {
          get_path = line.split(" ")[1];
        }
      }
      if (get_path!=null) {getRequest(get_path);}
    }
    catch (Exception e) {
      log("Error Processing Client Request");
      e.printStackTrace();
    }
  }

  private void log(String message) {
    System.out.println(getSocket().getInetAddress().getHostAddress()+": "+message);
  }
}