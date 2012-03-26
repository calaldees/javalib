package Utils.ModelViewControllerFramework;


import java.awt.Component;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Collection;

public class DefaultModelViewImplementation implements TimerHandeler {

  private static final int default_updates_per_second_target = 60;

  //---------------------------------------------------------------------------------------
  // Variables
  //---------------------------------------------------------------------------------------
  private int updates_per_second_target = default_updates_per_second_target;


  private final Collection<ViewControllerPair> views = new ArrayList<ViewControllerPair>();

  private TimerThread thread;
  private Model       m;

  public DefaultModelViewImplementation(Model m) {
    this.m = m;
  }

  //---------------------------------------------------------------------------------------
  // Public Set Methods
  //---------------------------------------------------------------------------------------
  public void       addView(ViewControllerPair view         ) {       views.add(view);   }
  public boolean removeView(ViewControllerPair view         ) {return views.remove(view);}

  //---------------------------------------------------------------------------------------
  // Thread Control
  //---------------------------------------------------------------------------------------
  public void startSim() {
    if (thread==null) {thread = new TimerThread(this);}
    thread.startTimerThread();
  }
  public void stopSim() {
    thread.stopTimerThread();
    thread = null;
  }
  public void pauseSim() {
    if (thread!=null) {thread.pauseTimerThread();}
  }

  public void setTargetUpdatesPerSecond(int i) {updates_per_second_target = i;}
  public int  getTargetUpdatesPerSecond()      {return updates_per_second_target;}

  public int getMaxFrameSkip() {return 10;}

  public void processModel() {
    m.updateModel();
  }

  public void processView() {
    for (ViewControllerPair vc : views) {
      updateViewControllerPair(vc);
    }
  }

  private void updateViewControllerPair(ViewControllerPair vc) {
    Graphics     graphics;
    Component    component  = vc.getComponent();
    Controller   control    = vc.getController();
    ViewRenderer renderer   = vc.getViewRenderer();
    
    if (component!=null && renderer!=null) {
      try {
        graphics = component.getGraphics();
        renderer.render(graphics,component,m,null);
        if (control!=null) {
          //control.manipulateView(renderer);
          control.manipulateModel(m);
          control.renderFeedback(graphics);
        }
        Toolkit.getDefaultToolkit().sync();
        graphics.dispose();
      }
      catch (Exception e) {System.err.println("GFX Render Error: "+e);}
    }
  }

}
