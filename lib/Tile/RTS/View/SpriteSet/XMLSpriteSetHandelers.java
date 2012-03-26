package RTS.View.SpriteSet;

import RTS.GameObject.Structure.RTSStructureMode;
import Utils.ImageLoader.ImageBlockLoader;
import Utils.ImageLoader.SpriteArrayOpperations;
import Utils.Types.Sprite;
import Utils.XML.XMLLoad.DataWrapper;
import Utils.XML.XMLLoad.LoadProcessor;

public class XMLSpriteSetHandelers {
  public XMLSpriteSetHandelers() {
    new XMLUnit2DHandeler();
    new XMLUnit2DSingleHandeler();
    new XMLUnit2DTankHandeler();
    new XMLStructure2DHandeler();
  }
}


class XMLUnit2DHandeler extends LoadProcessor<Unit2D> {
  public XMLUnit2DHandeler() {super(Unit2D.class, "UNIT2D", true);}

  public Unit2D overlay(Unit2D unit, DataWrapper data) throws Exception {
    super.overlay(unit, data);
    if (data.containsKey("sprites")) {unit.setSprites( SpriteArrayOpperations.completeRotation(ImageBlockLoader.getSpritesArray(data.getInt("sprites"))) );}
    return unit;
  }

  public Unit2D create(DataWrapper data) throws Exception {
    return overlay(new Unit2D(), data);
  }
}



class XMLUnit2DSingleHandeler extends LoadProcessor<Unit2DSingle> {
  public XMLUnit2DSingleHandeler() {super(Unit2DSingle.class, "UNIT2DSINGLE", true);}

  public Unit2DSingle overlay(Unit2DSingle unit, DataWrapper data) throws Exception {
    super.overlay(unit, data);
    String sprite_mode = data.getString("sprite_mode");
    if (sprite_mode.equals("single"        )) {unit.setSpriteSingle     (ImageBlockLoader.getSprite(data.getPoint("sprites")));}
    if (sprite_mode.equals("single_animate")) {unit.setSpriteAnimation  (ImageBlockLoader.getSpritesArray(data.getInt("sprites")));}
    if (sprite_mode.equals("single_modes"  )) {unit.setSpriteSingleModes(ImageBlockLoader.getSpritesArray(data.getInt("sprites")));}
    return unit;
  }

  public Unit2DSingle create(DataWrapper data) throws Exception {
    return overlay(new Unit2DSingle(), data);
  }
}


class XMLUnit2DTankHandeler extends LoadProcessor<Unit2DTank> {
  public XMLUnit2DTankHandeler() {super(Unit2DTank.class, "UNIT2DTANK", true);}

  public Unit2DTank overlay(Unit2DTank unit, DataWrapper data) throws Exception {
    super.overlay(unit, data);
    if (data.containsKey("turret")) {unit.setSprites     ( SpriteArrayOpperations.completeRotation(ImageBlockLoader.getSpritesArray(data.getInt("turret"))) );}
    if (data.containsKey("tracks")) {unit.setSpriteTracks( SpriteArrayOpperations.completeRotation(ImageBlockLoader.getSpritesArray(data.getInt("tracks"))) );}
    return unit;
  }

  public Unit2DTank create(DataWrapper data) throws Exception {
    return overlay(new Unit2DTank(), data);
  }
}


class XMLStructure2DHandeler extends LoadProcessor<Structure2D> {
  public XMLStructure2DHandeler() {super(Structure2D.class, "STRUCTURE2D", true);}

  public Structure2D overlay(Structure2D s, DataWrapper data) throws Exception {
    super.overlay(s, data);
    for (RTSStructureMode mode : RTSStructureMode.values()) {
      String xml_name = mode.toString();
      Sprite[] sprites = ImageBlockLoader.getSpritesArray(data.getInt(xml_name));
      s.setSprites(mode, sprites);
    }
    return s;
  }

  public Structure2D create(DataWrapper data) throws Exception {
    return overlay(new Structure2D(), data);
  }


}