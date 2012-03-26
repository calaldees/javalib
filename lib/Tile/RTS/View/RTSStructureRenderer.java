package RTS.View;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.ImageObserver;
import RTS.GameObject.Structure.RTSStructure;
import RTS.View.SpriteSet.Structure2D;
import Utils.StringIndexedValues;
import Utils.Types.Integer.Dimension2DImmutable;



class RTSStructureRenderer extends AbstractRTSRenderer<RTSStructure> {

  public RTSStructureRenderer(Dimension2DImmutable tile_size) {super(tile_size);}

  public void render(Graphics g, ImageObserver io, RTSStructure s, StringIndexedValues options) {
    if (s==null) {return;}
    Structure2D structure = (Structure2D)sprite_sets.get(s.getTemplate().getName());
    if (structure==null) {return;}
    int frame = 0;
    if (options!=null) {frame = options.get(Integer.class,"frame");}
    //System.out.println("Frame="+frame);
    structure.getSprite(s.getMode(), frame).render(g, io);
  }

  public Dimension getComponentSize(RTSStructure s, StringIndexedValues options) {
    if (s==null) {return tile_size.getAWTDimension();}
    return new Dimension(s.getTemplate().getSize().getWidth()  * tile_size.getWidth(),
                         s.getTemplate().getSize().getLength() * tile_size.getLength());
  }


}