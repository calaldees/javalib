package Utils.InputBinding;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

import Utils.PairHashMap;
import Utils.PairMap;
import java.util.ArrayList;
import java.util.List;

public class InputBinding implements KeyListener {

  private PairMap<String,Object > bindings    = new PairHashMap<String,Object >();
  private     Map<Object,Boolean> input_state = new     HashMap<Object,Boolean>();
  private  String                 bind_key    = null;
  
  private List<InputListener> listeners = new ArrayList<InputListener>();
  
  public InputBinding() {}
  
  public void    addInputListener(InputListener listener) {listeners.add   (listener);}
  public void removeInputListener(InputListener listener) {listeners.remove(listener);}
  
  public void bindNextKey(String key              ) {bind_key = key;}
  public void bind       (String key, Object input) {bindings.put(key, input);}
  
  public boolean getState(String key) {
    if (bindings.containsKey(key)) {
      Object input_id = bindings.get(key);
      if (input_state.containsKey(input_id)) {
        return input_state.get(input_id);
      }
    } 
    return false;
  }

  public void inputPressed(Object key) {
    if (bind_key!=null) {
      bind(bind_key,key);
      bind_key=null;
    }
    else {
      if (bindings.containsValue(key)) {
        if (!input_state.containsKey(key) || input_state.get(key)==false) {
          input_state.put(key, true);
          InputEvent e = new InputEvent(this, bindings.getValueKey(key));
          for (InputListener listener : listeners) {listener.inputPressed(e);}
        }
      }
    }
    
  }
  
  private void inputRelased(Object key) {
    if (bindings.containsValue(key)) {
      input_state.remove(key);
      InputEvent e = new InputEvent(this, bindings.getValueKey(key));
      for (InputListener listener : listeners) {listener.inputPressed(e);}
    }
  }
  
  public void keyPressed(KeyEvent e)  {inputPressed(e.getKeyCode());}
  public void keyReleased(KeyEvent e) {inputRelased(e.getKeyCode());}
  
  public void keyTyped(KeyEvent e) {}

}