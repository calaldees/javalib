import GameLib.GameFrame;
import java.awt.event.KeyEvent;

public class PhysicsTest extends GameFrame {

  private final double move_speed  = 0.10;
  private final double drag_factor = 0.01;
  private final double gravity     = 0.01;

  private double ship_x_pos = 60;
  private double ship_y_pos = 45;
  private double ship_x_speed = 0;
  private double ship_y_speed = 0;

  public void timerEvent() {
    if (isKeyPressed(KeyEvent.VK_UP   )) {ship_y_speed +=-move_speed;}
    if (isKeyPressed(KeyEvent.VK_DOWN )) {ship_y_speed += move_speed;}
    if (isKeyPressed(KeyEvent.VK_LEFT )) {ship_x_speed +=-move_speed;}
    if (isKeyPressed(KeyEvent.VK_RIGHT)) {ship_x_speed += move_speed;}

    clearScreen();
    ship_x_speed = ship_x_speed - (ship_x_speed * drag_factor);
    ship_y_speed = ship_y_speed - (ship_y_speed * drag_factor);
    ship_y_speed = ship_y_speed + gravity;

    if (ship_y_pos > getHeight()) {ship_y_speed = -ship_y_speed;}
    if (ship_x_pos <           0) {ship_x_speed = -ship_x_speed;}
    if (ship_x_pos >  getWidth()) {ship_x_speed = -ship_x_speed;}

    ship_x_pos = ship_x_pos + ship_x_speed;
    ship_y_pos = ship_y_pos + ship_y_speed;
    putImage((int)ship_x_pos, (int)ship_y_pos,"ship.gif");
    repaintScreen();
  }

  public static void main(String[] args) {new PhysicsTest();}
}
