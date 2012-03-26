package Utils.InputBinding;

import java.util.EventObject;

public class InputEvent extends EventObject {
  private static final long serialVersionUID = 0;

  private final String binding_name;
  
  public InputEvent(InputBinding source, String binding) {
    super(source);
    this.binding_name = binding;
  }
  
  public String  getName()  {return binding_name;}
  public boolean getState() {return getSource().getState(binding_name);}
  
  public InputBinding getSource() {return (InputBinding)super.getSource();}
  
  public String toString() {
    return "" + getName() + ":" + getState();
  }
}