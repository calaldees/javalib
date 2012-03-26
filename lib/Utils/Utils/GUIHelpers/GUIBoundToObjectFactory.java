package Utils.GUIHelpers;

import Utils.Types.Direction;
import Utils.Types.DirectionDiscreetGroup;
import java.awt.Component;
import java.lang.reflect.Method;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class GUIBoundToObjectFactory {

  public static Component getComponent(Object o, String method_name, ChangeListener listener) {
    return getComponent(o,Utils.ReflectionUtils.getMethod(o,method_name),listener);
  }
  
  public static Component getComponent(Object o, Method method, ChangeListener listener) {
    Class[] param = method.getParameterTypes();
    if (param.length != 1) {return null;}
    return getComponent(o,method,param[0],listener);
  }
  
  private static Component getComponent(Object o, Method method, Class c, ChangeListener listener) {
    if (c.isEnum()) {
      JPanel panel = new JPanel();
      ButtonGroup group = new ButtonGroup();
      for (Object enum_constant : c.getEnumConstants()) {
        JRadioButton radio = new JRadioButton(enum_constant.toString());
        group.add(radio);
        panel.add(radio);
        panel.setBorder(BorderFactory.createTitledBorder(method.getName()));
        radio.addChangeListener(new ButtonMethodActivator(o,method,enum_constant));
        radio.addChangeListener(listener);
      }
      return panel;
    }
    if (c.equals(Direction.class)) {
      return new DirectionSliderInvolkerListener(o,method,listener).getSlider();
    }
    return null;
  }
  
}


class ButtonMethodActivator implements ChangeListener {
  private final Object o;
  private final Method method;
  private final Object param;
  
  public ButtonMethodActivator(Object o, Method method, Object param) {
    this.o      = o;
    this.method = method;
    this.param  = param;
    
  }
  
  public void stateChanged(ChangeEvent e) {
    if (((JToggleButton)e.getSource()).isSelected()==true) {
      try                  {method.invoke(o, param);}
      catch (Exception ex) {}
    }
  }
}


class DirectionSliderInvolkerListener implements ChangeListener {
  private final DirectionDiscreetGroup directions = DirectionDiscreetGroup.Way16;
  private final Object o;
  private final Method method;
  private final JSlider slider = new JSlider(0,directions.getNumberOfDirections(),0);
  
  public DirectionSliderInvolkerListener(Object o, Method method, ChangeListener listener) {
    this.o      = o;
    this.method = method;
    slider.addChangeListener(this);
    slider.addChangeListener(listener);
    slider.setBorder(BorderFactory.createTitledBorder(method.getName()));
    slider.setPaintLabels(true);
    slider.setPaintTicks(true);
    slider.setPaintTrack(true);
    slider.setSnapToTicks(true);
  }
  
  public JSlider getSlider() {return slider;}
  
  public void stateChanged(ChangeEvent e) {
    if (e.getSource()==slider) { //&& !slider.getValueIsAdjusting()
      try                  {method.invoke(o, directions.getDirectionAtIndex(slider.getValue()));}
      catch (Exception ex) {}
    }
  }
}