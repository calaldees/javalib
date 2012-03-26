package PhyBlast;

import Utils.Display.DisplayController;
import Utils.InputBinding.InputBinding;
import Utils.ModelViewControllerFramework.TimerHandeler;
import Utils.ModelViewControllerFramework.TimerThread;
import java.awt.event.KeyEvent;

public class PhyBlast implements TimerHandeler {

  private TimerThread       thread   = new TimerThread(this);
  private InputBinding      input    = new InputBinding();
  private PhyBlastModel     model    = new PhyBlastModel(input);
  private PsyBlastRenderer  renderer = new PsyBlastRenderer(model);
  private DisplayController display  = new DisplayController(getClass().getSimpleName());

  public static void main(String[] args) {new PhyBlast();}

  public PhyBlast() {
    System.out.println("Init");

    input.bind("exit"    , KeyEvent.VK_ESCAPE   );
    input.bind("right"   , KeyEvent.VK_RIGHT    );
    input.bind("left"    , KeyEvent.VK_LEFT     );
    
    display.addDisplayMode(640, 480, 16);
    display.addKeyListener(input);
    display.setComponent(renderer);
    display.setFullScreen(false);

    thread.startTimerThread();
  }

  public int  getTargetUpdatesPerSecond() {return 60;}
  public int  getMaxFrameSkip()           {return 10;}
  public void processModel() {
    if (input.getState("exit")) {
      display.closeDisplay();
      thread.stopTimerThread();
    }
    model.updateModel();
  }
  public void processView()  {display.render();}

}