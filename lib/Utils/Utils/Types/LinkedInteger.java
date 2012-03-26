package Utils.Types;

import java.util.Collection;
import java.util.Vector;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class LinkedInteger implements ChangeListener {

  private final String             name;
  private       int                value     = 0;
  private final Collection<Object> listeners = new Vector<Object>();
  private       int                value_min = Integer.MIN_VALUE;
  private       int                value_max = Integer.MAX_VALUE;
  
  public LinkedInteger(String name                             ) {this.name = name;}
  public LinkedInteger(String name, int value                  ) {this(name      ); this.value = value;}
  public LinkedInteger(String name, int value, int min, int max) {this(name,value); setRange(min,max);}
  
  public void    addValueChangeListener(Object o) {
    listeners.add(o);
    if (o instanceof JSlider) {((JSlider)o).addChangeListener(this);}
  }
  public void removeValueChangeListener(Object o) {
    listeners.remove(o);
    if (o instanceof JSlider) {((JSlider)o).removeChangeListener(this);}
  }
  
  public void setRange(int min, int max) {
    value_min = min;
    value_max = max;
    setValue(getValue());
  }
  
  public String getName()           {return name;}
  public int    getValue()          {return value;}
  public int    getValueMax()       {return value_max;}
  public int    getValueMin()       {return value_min;}
  public void   setValue(int value_new) {
    if      (value_new<value_min) {value_new=value_min;}
    else if (value_new>value_max) {value_new=value_max;}
    if (value!=value_new) {
      value = value_new;
      //tell all the other listeners! and update there value
      for (Object o : listeners) {
        if (o instanceof LinkedIntegerListener) {((LinkedIntegerListener)o).linkedValueUpdated(getName());}
        if (o instanceof JSlider              ) {((JSlider)o).setValue(getValue());}
        //Failing the LinkedIntegerListener ... try reflection to find set"name"()
         //... finish me!
      }
    }
  }

  public void stateChanged(ChangeEvent e) {
    setValue(((JSlider)e.getSource()).getValue());
  }
}