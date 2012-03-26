package Utils.ModelViewControllerFramework;

public class TimerThread implements Runnable {

  private TimerHandeler timer_handeler;
  private Thread        thread;
  
  private boolean state_running = true;
  private boolean state_paused  = false;
  private long    time_previous;
  
  private int processing_took_too_long;

  public TimerThread(TimerHandeler t) {
    this.timer_handeler = t;
  }
  
  public boolean startTimerThread() {
    if (thread!=null && thread.isAlive()) {return false;}
    state_running = true;
    state_paused  = false;
    try {
      thread = new Thread(this);
      thread.start();
      return true;
    }
    catch (IllegalThreadStateException e) {return false;}
  }

  public void pauseTimerThread() {
    state_paused = true;
  }
  
  public void stopTimerThread() {
    state_paused  = false;
    state_running = false;
    thread        = null;
  }
  
  private void safeSleep(                    ) {safeSleep(1000000000);}
  private void safeSleep(long sleep_nano_time) {
    if (sleep_nano_time<0) {sleep_nano_time=0;}
    try {Thread.sleep(sleep_nano_time/1000000);} catch (InterruptedException e) {}
  }

  //TODO - create methods for inspecting load based on updates per second
  // return a percentage of how much time it is taking to process the model
  // return a percentage of how much it is taking to process the view
  // these could be stored in a linked list and used to draw graphs, maybe even a JComponent graph can be retured, or image?

  public void run() {
    time_previous = System.nanoTime();
    while (state_running) {
      if (state_paused) {safeSleep();}
      else {
        long time_current = System.nanoTime();
        long time_passed = time_current - time_previous;
        time_previous = time_current;
        
        //time_outstanding += time_passed - (manager.getUpdatesPerSecond()*1000000);
        //manager.getUpdatesPerSecond()
        
        timer_handeler.processModel();
        if (processing_took_too_long >  timer_handeler.getMaxFrameSkip()) {processing_took_too_long=0;}
        if (processing_took_too_long == 0                  ) {timer_handeler.processView();        }
        
        time_passed  = System.nanoTime() - time_previous;
        long nano_seconds_per_frame = (long)((1/(double)timer_handeler.getTargetUpdatesPerSecond())*1000000000);
        
        long sleep_time = nano_seconds_per_frame - time_passed;
        if (sleep_time>0) {processing_took_too_long=0; safeSleep(sleep_time);}
        else              {processing_took_too_long++;                       }
        //System.out.println("Simulating - "+time_passed+ " Outstanding:"+time_outstanding);
      }
    }

  }
  
}