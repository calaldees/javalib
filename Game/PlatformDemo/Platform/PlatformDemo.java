package Platform;

import Utils.Display.BackbufferedSmoothScaleableComponent;
import Utils.Display.DisplayController;
import Utils.ModelViewControllerFramework.TimerHandeler;
import Utils.ModelViewControllerFramework.TimerThread;
import Utils.File.FileOperations;
import Utils.ImageLoader.ImageBlockLoader;
import Utils.InputBinding.InputBinding;

import Utils.Types.Integer.Dimension2DImmutable;
import Utils.Types.Sprite;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.io.File;


public class PlatformDemo implements TimerHandeler {

  private Scene      scene = new Scene();
  private Character  character;
  
  private TimerThread       thread  = new TimerThread(this);
  private DisplayController display = new DisplayController("PlatformDemo");
  private InputBinding      input   = new InputBinding();
  private BackbufferedSmoothScaleableComponent buffer;
  
  public static void main(String[] args) {new PlatformDemo();}
  
  public PlatformDemo() {
    loadImages();
    
    input.bind("exit"    , KeyEvent.VK_ESCAPE   );
    input.bind("right"   , KeyEvent.VK_RIGHT    );
    input.bind("left"    , KeyEvent.VK_LEFT     );
    input.bind("zoom_in" , KeyEvent.VK_PAGE_UP  );
    input.bind("zoom_out", KeyEvent.VK_PAGE_DOWN);
    input.bind("up"      , KeyEvent.VK_UP       );
    input.bind("down"    , KeyEvent.VK_DOWN     );
    //input.addInputListener(this);

    SceneComponent sc = new SceneComponent(scene);
    buffer = new BackbufferedSmoothScaleableComponent(sc);

    display.addDisplayMode(640, 480, 16);
    display.addKeyListener(input);
    display.setFullScreen(false);
    display.setComponent(buffer);
    
    thread.startTimerThread();
  }
  
  private void loadImages() {
    ImageBlockLoader.load(new File("background.png"));
    Background background = new Background();
    background.setLayers(ImageBlockLoader.getSpritesArray(0));
    scene.setBackground(background);
    /**
    ImageBlockLoader.load(new File("character.png"));
    CharacterTemplate character_template = new CharacterTemplate();
    character_template.setStationary(ImageBlockLoader.getSpritesArray(0));
    character_template.setJump      (ImageBlockLoader.getSpritesArray(1));
    character_template.setRun       (ImageBlockLoader.getSpritesArray(2));
    character = new Character(character_template);
    scene.add(character);
     */
    
    ImageBlockLoader.load(new File("tiles.png"));
    int i = 0;
    for (Sprite t : ImageBlockLoader.getSprites(0)) {
      new Tile(""+i,t);
      i++;
    }
    
    scene.setStage(loadStage("level.txt"));
  }

  private Stage loadStage(String filename) {
    Stage stage = new Stage(new Dimension2DImmutable(100,20));
    String s = FileOperations.readFile(filename);
    int x = 0;
    int y = 0;
    for (int i=0 ; i<s.length() ; i++) {
      String t = ""+s.charAt(i);
      if (t.equals("\n")) {y++; x=0;}
      else {
        stage.setTile(Tile.getTile(t), x, y);
        x++;
      }
    }
    return stage;
  }
  
  public int getTargetUpdatesPerSecond() {return 30;}
  public int getMaxFrameSkip()           {return  3;}

  public void processModel() {
    if (input.getState("exit") ) {
      display.closeDisplay();
      thread.stopTimerThread();
    }
    Rectangle view_target = buffer.getViewTarget();
    if (input.getState("right")   ) {view_target.x += +1;}
    buffer.setViewTarget(view_target);
    //if (input.getState("left")    ) {scene.getView().x += -1;}
    //if (input.getState("up")      ) {scene.getView().y += -1;}
    //if (input.getState("down")    ) {scene.getView().y +=  1;}
    
    //if (input.getState("zoom_in") ) {scene.setZoom( 0.1);}
    //if (input.getState("zoom_out")) {scene.setZoom(-0.1);}
  }
  

  public void processView() {
    /*
    Graphics g = display.getGraphics();
    if (g!=null) {
      scene.render(g, display.getComponent());
      display.flipBuffer();
    }
     *
     */ 
     display.render();
  }

}
