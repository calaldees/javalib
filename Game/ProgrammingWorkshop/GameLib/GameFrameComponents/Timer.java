package GameLib.GameFrameComponents;

public class Timer {
  
  private TimerThread timer_thread;
  private int         timer_interval = 10;

  private final GameFrameInterface game_frame;
  
  public Timer(GameFrameInterface game_frame) {this.game_frame = game_frame;}
  
  public void timerEvent() {game_frame.timerEvent();}
  
  public int getElapsedTime() {
    if (timer_thread!=null) {return timer_thread.getElapsedTime();}
    else                    {return 0;}
  }
  
  public void resetElapsedTime() {if (timer_thread!=null) {timer_thread.resetElapsedTime();}}
  
  public int getTimerInterval() {return timer_interval;}
  
  public void stopTimer()  {setTimerInterval(0);}
  public void startTimer() {setTimerInterval(timer_interval);}
  
  public void timerPause(boolean pause) {
    game_frame.resetKeysPressed();
    try {timer_thread.pauseThread(pause);} catch (Exception e) {}
  }
  
  public void setTimerInterval(int timer_interval) {
    if (timer_interval>0) {
      this.timer_interval = timer_interval;
      if (timer_thread==null) {timer_thread = new TimerThread(this);}
    }
    else {
      this.timer_interval = 0;
      timer_thread.safeStopThread();
      timer_thread = null;
    }
  }
}


class TimerThread extends Thread {

  private final Timer     timer;
  private       int       elapsed_time = 0;
  private       boolean   stop_thread = false;
  private       boolean   pause       = false;
  
  public TimerThread(Timer game_frame) {
    super();
    this.timer = game_frame;
    start();
  }

  public void run() {
    while (!stop_thread && timer.getTimerInterval()>0) {
      if (!pause) {
        try {
          timer.timerEvent();
          sleep(timer.getTimerInterval());
          elapsed_time++;
          if (elapsed_time==Integer.MAX_VALUE) {resetElapsedTime();}
        }
        catch (Exception e) {e.printStackTrace();}
      }
    }
  }
  
  public void pauseThread(boolean pause)    {this.pause = pause;}
  public void safeStopThread() {stop_thread = true;}
  
  public int  getElapsedTime()   {return elapsed_time;}
  public void resetElapsedTime() {elapsed_time=0;}
}