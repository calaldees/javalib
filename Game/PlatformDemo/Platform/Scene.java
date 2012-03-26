package Platform;

import java.util.ArrayList;

public class Scene {
  private       Background           background;
  private final ArrayList<Character> characters = new ArrayList<Character>();
  private       Stage                stage;
  

  public void setStage     (Stage      stage     ) {this.stage      = stage;     }
  public void setBackground(Background background) {this.background = background;}
  public void add(Character c) {characters.add(c);}

  Background getBackground() {return background;}
  Stage      getStage()      {return stage;}

}
