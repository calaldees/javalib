import GameLib.GameFrame;

public class ScrollTest extends GameFrame {

  int background_x_pos = 0;

  public void timerEvent() {
    background_x_pos = background_x_pos - 1;
    putImage(background_x_pos,0,"CopterLevel1.png");
    repaintScreen();
  }

  public static void main(String[] args) {new ScrollTest();}
}