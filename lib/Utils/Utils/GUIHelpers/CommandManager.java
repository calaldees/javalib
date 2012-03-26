package Utils.GUIHelpers;

import java.lang.reflect.InvocationTargetException;
import java.util.Vector;
import java.lang.reflect.Method;
import javax.swing.event.*;
import java.awt.event.*;

import javax.swing.JSlider;
import javax.swing.AbstractButton;

import Utils.ErrorHandeler;


public class CommandManager implements ActionListener, 
                                       ItemListener, 
                                       ChangeListener, 
                                       //KeyListener,
                                       WindowListener {
//------------------------------------------------------------------------
// Constants
//------------------------------------------------------------------------  
  private final String exit_command = "exit";
  
//------------------------------------------------------------------------
// Variables
//------------------------------------------------------------------------  
  private Vector<Object> command_processors = new Vector<Object>();
  //private long           execute_time;
  //private int            execute_delay = 100000;
  
//------------------------------------------------------------------------
// Constructor
//------------------------------------------------------------------------  
  
  public CommandManager() {}
  
//------------------------------------------------------------------------
// Public
//------------------------------------------------------------------------  

  public void addCommandProcessor(Object command_processor) {
    command_processors.add(command_processor);
  }

//------------------------------------------------------------------------
// Event Listeners
//------------------------------------------------------------------------  

  // Listener for Button Press's
  public void actionPerformed(ActionEvent e) {
    execute(e.getActionCommand());
  }

  // Listener for Checkbox's and toggle buttons
  public void itemStateChanged(ItemEvent e) {
    AbstractButton source = (AbstractButton)(e.getSource());
    execute(source.getName(), source.isSelected());
  }
  
  // ChangeListener - Listener for Sliders
  public void stateChanged(ChangeEvent e) {
    JSlider slider = (JSlider)e.getSource();
    execute(slider.getName(), slider.getValue());
  }

  //KeyListener
  public void keyPressed(KeyEvent e)  {
    //Convert the key into a command (using a lookup list). List could be defined in XML :)
    //execute(lookupcommand);
  }
  public void keyReleased(KeyEvent e) {}
  public void keyTyped(KeyEvent e)    {}

  //WindowListener  
  public void windowActivated(WindowEvent e) {}
  public void windowClosed(WindowEvent e) {}
  public void windowClosing(WindowEvent e) {execute(exit_command);}
  public void windowDeactivated(WindowEvent e) {}
  public void windowDeiconified(WindowEvent e) {}
  public void windowIconified(WindowEvent e) {}
  public void windowOpened(WindowEvent e) {}

//------------------------------------------------------------------------
// Private
//------------------------------------------------------------------------

/*
  private void execute(String command) {
  }
  
  private void execute(String command, Boolean b) {
    //run command(boolean)
  }
  
  private void execute(String command, int value) {
    //run command(value)
  }
  */
  
  public void execute(String command, Object... args) {
    //if (System.nanoTime() > (execute_time + execute_delay)) {
    if (command!=null) {
      String command_name = command.toLowerCase().replaceAll(" ", "_");
             command_name = command_name.replaceAll("[ )(#-+}{]","");
      boolean execute_ok = false;
      //System.out.println("CP="+command_processors.size());
      for (Object command_processor: command_processors) {
        Method[] methods = command_processor.getClass().getDeclaredMethods();
        for (int i=0 ; i<methods.length ; i++) {
          if (methods[i].getName().toLowerCase().equals( command_name ) ) {
            try {
              //System.out.println(""+methods[i].getName()+" ARGS:"+args.length);
              methods[i].invoke(command_processor,args); 
              
              execute_ok=true;
              //System.out.println("EXECUTE="+execute_ok);
              //execute_time = System.nanoTime();
              break;
            }
            catch (InvocationTargetException itae) {itae.getCause().printStackTrace();}
            catch (IllegalArgumentException  iare) {}
            catch (IllegalAccessException    iace) {}
            catch (Exception                 e   ) {             e.printStackTrace();}
          }
        }
      }
      if (!execute_ok) {
        ErrorHandeler.error(this, "Error Executing Command", "Error executing the command - "+command);
      }
    }
  }
  
}
