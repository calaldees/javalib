package Utils.InputBinding;

import java.util.EventListener;

public interface InputListener extends EventListener {
  public void inputPressed (InputEvent e);  
  public void inputReleased(InputEvent e);
}
