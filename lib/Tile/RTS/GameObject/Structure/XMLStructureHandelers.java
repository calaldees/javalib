package RTS.GameObject.Structure;

import Utils.XML.XMLLoad.DataWrapper;
import Utils.XML.XMLLoad.LoadProcessor;

public class XMLStructureHandelers {
  public XMLStructureHandelers() {
    //new XMLStructureHandeler();
    new LoadProcessor(RTSStructureTemplate.class,"STRUCTURE");
  }
}
/*
class XMLStructureHandeler extends LoadProcessor<RTSStructureTemplate> {
  public XMLStructureHandeler() {super(RTSStructureTemplate.class,"STRUCTURE");}
  
  public RTSStructureTemplate create(DataWrapper data) throws Exception {
    RTSStructureTemplate template = new RTSStructureTemplate(getSpriteSet(data));
    return overlay(template,data);
  }
  
  public RTSStructureTemplate overlay(RTSStructureTemplate o, DataWrapper data) throws Exception {
    return super.overlay(o, data);
  }
  
  private SpriteSetStructure getSpriteSet(DataWrapper data) {
    Sprite[][] sprites = new Sprite[RTSStructureMode.getNumberOfModes()][];
    for (RTSStructureMode mode : RTSStructureMode.values()) {
      sprites[mode.getIndex()] = ImageBlockLoader.getSpritesArray(data.getInt(mode.name()));
    }
    return new SpriteSetStructure(sprites);
  }
}
 */