package RTS.GameObject.Unit;


import Utils.XML.XMLLoad.DataWrapper;
import Utils.XML.XMLLoad.LoadProcessor;


public class XMLUnitHandelers {  
  public XMLUnitHandelers() {
    //new LoadProcessor(RTSUnitTemplate.class,"UNIT");
    new XMLUnitHandeler();
  }
}

//----------------------------------------
// Unit Handeler Class
//----------------------------------------

class XMLUnitHandeler extends LoadProcessor<RTSUnitTemplate> {
  
  private static final String xml_type        = "type";
  private static final String xml_type_normal = "normal";
  private static final String xml_type_tank   = "tank";
  private static final String xml_type_fly    = "fly";
  
  
  public XMLUnitHandeler() {super(RTSUnitTemplate.class, "UNIT");}
  public RTSUnitTemplate create(DataWrapper data) throws Exception {
    RTSUnitTemplate template = null;
    if      (data.getString(xml_type).equals(xml_type_normal)) {template = new RTSUnitTemplateStandard();}
    else if (data.getString(xml_type).equals(xml_type_tank)  ) {template = new RTSUnitTemplateTank();    }
    else if (data.getString(xml_type).equals(xml_type_fly)   ) {template = new RTSUnitTemplateFly();     }
    return overlay(template,data);
  }
}

//private static final Map<Integer,Sprite[]> completed_rotations = new HashMap<Integer,Sprite[]>();

  /*
  public RTSUnitTemplate overlay(RTSUnitTemplate o, DataWrapper data) throws Exception {
    if      (o instanceof RTSUnitTemplateFly ) {o.setSpriteSet(getSpriteSetUnitFly( data));}
    else if (o instanceof RTSUnitTemplateTank) {o.setSpriteSet(getSpriteSetUnitTank(data));}
    else if (o instanceof RTSUnitTemplate    ) {o.setSpriteSet(getSpriteSetUnit(    data));}
    return super.overlay(o, data);
  }
  
  //----------------------------------
  // Private
  //----------------------------------
  
  private SpriteSetUnit getSpriteSetUnit(DataWrapper data) {
    return new SpriteSetUnit(getSpritesCompleteRotation(data.getInt(xml_unit_sprite)));
  }
  private SpriteSetUnitTank getSpriteSetUnitTank(DataWrapper data) {
    return new SpriteSetUnitTank(
      getSpritesCompleteRotation(data.getInt(xml_tank_turret)) ,
      getSpritesCompleteRotation(data.getInt(xml_tank_tracks)) 
    );
  }
  private GameObjectSpriteSet getSpriteSetUnitFly(DataWrapper data) {
    return new SpriteSetSingle(ImageBlockLoader.getSpritesArray(data.getInt(xml_unit_sprite)));
  }
  
  private Sprite[] getSpritesCompleteRotation(int sprite_location) {
    if (completed_rotations.containsKey(sprite_location)) {return completed_rotations.get(sprite_location);}
    else {
      Sprite[] sprites_completed = Utils.ImageLoader.SpriteArrayOpperations.completeRotation( ImageBlockLoader.getSpritesArray(sprite_location) );
      completed_rotations.put(sprite_location, sprites_completed);
      return sprites_completed;
    }
  }
}
*/