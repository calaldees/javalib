package Utils.ModelViewControllerFramework;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.ImageObserver;
import Utils.StringIndexedValues;


public interface ViewRenderer<T> {
  
  //public ViewRenderer(Class<T> c) {
    //View.addRenderer(c,this);
  //}
  
  //public          void render(Graphics g, ImageObserver io, T t                             ) {render(g,io,t,null);}
  public abstract void render(Graphics g, ImageObserver io, T t, StringIndexedValues options);
  
  //public          Dimension getComponentSize(T t                             ) {return getComponentSize(t,null);}
  public abstract Dimension getComponentSize(T t, StringIndexedValues options);
}