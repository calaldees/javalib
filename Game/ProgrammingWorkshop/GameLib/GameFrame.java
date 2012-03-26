package GameLib;

import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import GameLib.GameFrameComponents.*;
import GameLib.Net.*;


public class GameFrame implements GameFrameInterface, NetworkConnection {

  private final FullScreen                      screen;
  private final NetworkConnectionImplementation network_connection = new NetworkConnectionImplementation(this);
  private final Timer                           timer              = new Timer(this);
  private final Drawing                         draw               = new Drawing(this);
  private final ImageCashe                      image_cashe        = new ImageCashe(this);
  private final Controls                        controls           = new Controls(this);
  private final Input                           messages           = new Input(this);
  

  //----------------------------------------------------------
  // Constructors
  //----------------------------------------------------------
  
  public GameFrame()                                                       {this("GameFrame",320  ,240   ,false);}
  public GameFrame(String name, int width, int height)                     {this(name       ,width,height,false);}
  public GameFrame(String name, int width, int height, boolean fullscreen) {
    screen = new FullScreen(name,width,height,this);
    screen.setFullScreen(fullscreen);
    timer.startTimer();
  }
  
  //---------------------------------------------------------------------------------------
  // GameFrame API
  //---------------------------------------------------------------------------------------
  
  public void exit() {
    exitFinal();
  }
  
  public void exitFinal() {
    setFullScreen(false);
    networkDisconnect();
    timer.stopTimer();
    getFrame().dispose();
  }
  
  public int getWidth()  {return draw.getWidth(); }
  public int getHeight() {return draw.getHeight();}
  public int getImageWidth (String image_name) {return image_cashe.getImageWidth (image_name);}
  public int getImageHeight(String image_name) {return image_cashe.getImageHeight(image_name);}
  
  
  //----------------------------------------------------------
  // Overrideable Methods
  //----------------------------------------------------------
  
  
  public void timerEvent() {}


  //----------------------------------------------------------
  // Timer
  //----------------------------------------------------------
  public void timerPause(boolean pause) {timer.timerPause(pause);}
  public int  getElapsedTime()   {return timer.getElapsedTime();}
  public void resetElapsedTime() {timer.resetElapsedTime();}
  
  
  //----------------------------------------------------------
  // Screen Control
  //----------------------------------------------------------
  
  public void setFullScreen(boolean fullscreen) {screen.setFullScreen(fullscreen);}
  public void toggleFullScreen() {screen.toggleFullScreen();}

  //----------------------------------------------------------
  // Messages
  //----------------------------------------------------------
  public void     msgBox(String message) {       messages.  msgBox(message);}
  public String inputBox(String message) {return messages.inputBox(message);}
  
  //----------------------------------------------------------
  // Drawing
  //----------------------------------------------------------
  public void clearScreen()   {draw.clearScreen();}
  public void repaintScreen() {draw.repaintScreen();}
  
  public void  putPixel(int x, int y, Color c) {       draw.putPixel(x, y, c);}
  public Color getPixel(int x, int y         ) {return draw.getPixel(x, y   );}
  
  public void putImage(int x, int y, String filename          ) {draw.putImage(x, y, filename   );}
  public void putImage(int x, int y, String filename, double r) {draw.putImage(x, y, filename, r);}
  
  public void drawRectangle(Rectangle r, Color c) {draw.drawRectangle(r, c);}
  public void drawRectangle(int x, int y, int width, int height, Color c) {draw.drawRectangle(x,y,width,height,c);}
  public void drawCircleFilled(int x, int y, int size, Color c) {draw.drawCircleFilled(x,y,size,c);}
  public void drawLine(int x1, int y1, int x2, int y2, Color c) {draw.drawLine(x1, y1, x2, y2, c);}
  public void drawString(int x, int y, String s) {draw.drawString(x, y, s);}
  
  public Graphics getGraphics() {return draw.getGraphics();}
  
  //----------------------------------------------------------
  // Network
  //----------------------------------------------------------
  public boolean networkConnect    (String host_address) {return network_connection.networkConnect(host_address);}
  public void    networkDisconnect ()                    {network_connection.networkDisconnect();         }
  public void    networkReceive    (Object o)            {}
  public boolean networkSend       (Object o)            {return network_connection.networkSend(o);              }
  public boolean networkIsConnected()                    {return network_connection.networkIsConnected();        }

  //----------------------------------------------------------
  // Controls - Keyboard and Mouse
  //----------------------------------------------------------
  public boolean isKeyPressed(int keycode) {return controls.isKeyPressed(keycode);}
  public Point   getMousePosition()        {return controls.getMousePosition();}
  
  public void keyPressed (int keycode) {controls.keyPressed (keycode);}
  public void keyReleased(int keycode) {controls.keyReleased(keycode);}
  
  public void mousePressed(int x, int y) {}
  public void mouseMoved(int x, int y) {}
  public void mouseDragged(int x, int y) {}
  public void mouseWheelUp() {}
  public void mouseWheelDown() {}
  

  //----------------------------------------------------------
  // Internal Methods (for calls to other parts of the gameframe
  // these are not intended to be used as part of the normal API by users)
  //---------------------------------------------------------- 
  public JFrame        getFrame() {return screen.getFrame();}
  public BufferedImage getFrameImage(                         ) {return draw.getFrameImage();}
  public void          setFrameImage(BufferedImage frame_image) {draw.setFrameImage(frame_image);}
  public void resetKeysPressed() {controls.resetKeysPressed();}
  public Image getCashedImage(String name) {return image_cashe.getCacheImage(name);}
  public Controls getControlsListener() {return controls;}
  
  

}