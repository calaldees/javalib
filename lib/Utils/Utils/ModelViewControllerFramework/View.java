package Utils.ModelViewControllerFramework;


import Utils.XML.XMLLoad.Indexable;

import Utils.StringIndexedValues;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.ImageObserver;


public interface View extends Indexable {

  public <T> void  render(Graphics g, ImageObserver io, T o, StringIndexedValues options);
  //public <T> Dimension getComponentSize(T o, StringIndexedValues options);

  public <T> ViewRenderer<T> getRenderer(Class<T> c);
}