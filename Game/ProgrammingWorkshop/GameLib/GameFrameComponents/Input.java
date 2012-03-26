package GameLib.GameFrameComponents;

import javax.swing.JOptionPane;

public class Input {
  
  private final GameFrameInterface game_frame;
  
  public Input(GameFrameInterface game_frame) {this.game_frame = game_frame;}
  
  public void msgBox(String message              ) {msgBox(message,"Message");}
  public void msgBox(String message, String title) {
    game_frame.timerPause(true);
    JOptionPane.showMessageDialog(game_frame.getFrame(), message, title , JOptionPane.PLAIN_MESSAGE);
    game_frame.timerPause(false);
  }
  
  public String inputBox(String message) {
    game_frame.timerPause(true);
    String input = JOptionPane.showInputDialog(game_frame.getFrame(), message, "Input", JOptionPane.QUESTION_MESSAGE);
    game_frame.timerPause(false);
    return input;
  }
  
  
}
