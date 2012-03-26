package RTS.View;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.ImageObserver;
import RTS.GameObject.Unit.RTSUnit;
import RTS.GameObject.Unit.RTSUnitTank;
import RTS.View.SpriteSet.Unit2D;
import RTS.View.SpriteSet.Unit2DSingle;
import RTS.View.SpriteSet.Unit2DTank;
import Tile.View.SpriteSet.GameObjectSpriteSet;
import Utils.StringIndexedValues;
import Utils.Types.Direction;
import Utils.Types.Integer.Dimension2DImmutable;
import Utils.Types.Sprite;


public class RTSUnitRenderer extends AbstractRTSRenderer<RTSUnit> {

  public RTSUnitRenderer(Dimension2DImmutable tile_size) {super(tile_size);}

  public void render(Graphics g, ImageObserver io, RTSUnit u, StringIndexedValues options) {
    if (u==null) {return;}
    GameObjectSpriteSet sprite_set = sprite_sets.get(u.getTemplate().getName());

    if (sprite_set instanceof Unit2DTank) {
      Unit2DTank  tank_sprite_set = ((Unit2DTank)sprite_set);
      Direction d = u.getDirection();
      if (u instanceof RTSUnitTank) {d = ((RTSUnitTank)u).getDirectionTracks();}
      tank_sprite_set.getSpriteTracks(d).render(g, io);
    }

    if (sprite_set instanceof Unit2D) {
      ((Unit2D)sprite_set).getSprite(u.getDirection()).render(g, io);
    }

    if (sprite_set instanceof Unit2DSingle) {
      Unit2DSingle unit_single = (Unit2DSingle)sprite_set;
      Sprite s = unit_single.getSprite(); //TODO: enhance rendered to play animations and modes
      AffineTransform rotate_tranform = new AffineTransform();
      rotate_tranform.rotate(u.getDirection().getRadians(),s.getImageWidth()/2d,s.getImageHeight()/2d);
      ((Graphics2D)g).drawImage(s.getImage(), rotate_tranform, io);
    }


    //smoke?
    //fire?
    //stelth?
    //fireing?
    //damaged?
  }

  public Dimension getComponentSize(RTSUnit u, StringIndexedValues options) {
    if (u==null) {return tile_size.getAWTDimension();}
    return new Dimension(u.getTemplate().getSize().getWidth()  * tile_size.getWidth(),
                         u.getTemplate().getSize().getLength() * tile_size.getLength());
  }

}