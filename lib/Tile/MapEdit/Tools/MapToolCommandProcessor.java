package MapEdit.Tools;

public class MapToolCommandProcessor {
  
  public void Brush(boolean selected)  {if (selected) MapTool.activate("Brush");}
  public void Select(boolean selected) {if (selected) MapTool.activate("Select");}
}
