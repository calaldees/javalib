package Utils.ModelViewControllerFramework;

public interface TimerHandeler {
  public int  getTargetUpdatesPerSecond();
  public int  getMaxFrameSkip();
  public void processModel();
  public void processView();
}