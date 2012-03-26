package Utils.ModelViewControllerFramework;

import Utils.ErrorHandeler;
import Utils.StringIndexedValues;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.ImageObserver;
import java.util.HashMap;
import java.util.Map;

public class ViewWithMultipleRenderers implements View {
  private Map<Class,ViewRenderer> renderers = new HashMap<Class,ViewRenderer>();
  private String name;

  public ViewWithMultipleRenderers(String name) {
    this.name = name;
    ViewManager.addView(this);
  }
  
  public String getName()        {return name;}

  
  public void        addRenderer(Class c, ViewRenderer r) {       renderers.put(c,r   );   }
  public void     removeRenderer(Class c)                 {       renderers.put(c,null);   }
  public boolean     hasRenderer(Class c)                 {return renderers.containsKey(c);}
  
  @SuppressWarnings("unchecked")
  public <T> ViewRenderer<T> getRenderer(Class<T> c) {return findRenderer(c);}
  
  
  @SuppressWarnings("unchecked")
  public <T> void render(Graphics g, ImageObserver io, T o                             ) {render(g,io,o,null);}  
  public <T> void render(Graphics g, ImageObserver io, T o, StringIndexedValues options) {
    if (g==null || o==null) {throw new IllegalArgumentException();}
    ViewRenderer<T> r = findRenderer(o.getClass());
    if (r!=null) {r.render(g,io,o,options);}
  }
  
  public <T> Dimension getComponentSize(T o                             ) {return getComponentSize(o,null);}
  public <T> Dimension getComponentSize(T o, StringIndexedValues options) {
    if (o==null) {return null;}
    ViewRenderer<T> r = findRenderer(o.getClass());
    if (r!=null) {return r.getComponentSize(o, options);}
    //System.out.println("Class:"+o.getClass());
    //for (Class c: renderers.keySet()) {
    //  System.out.println("RClass:"+c.toString());
    //}
    return null;
  }

  //private <T> ViewRenderer<T> findRenderer(T o) {
  private <T> ViewRenderer<T> findRenderer(Class c ) {
    try {
      ViewRenderer<T> r = null;
      //Class       c = o.getClass();
//System.out.println("----------------------------------");
//System.out.println("Finding Renderer for "+c.getName());
      r = findRendererClass(c);
      if (r==null) {
        for (Class renderer_class : renderers.keySet()) {
//System.out.println("Checking against Renderers direct "+renderer_class.getName());
          r = findRendererClass(renderer_class.getSuperclass());
        }
      }
//if (r!=null) {System.out.println("RENDERER FIND COMEPLETE!");}
      return r;
    }
    catch (Exception e) {ErrorHandeler.error(this.getClass(),"Unable to Render: "+c.toString(),e);}
    return null;
  }
  
  private <T> ViewRenderer<T> findRendererClass(Class c) {
    ViewRenderer<T> r = null;
    while (r==null && c!=null) {
//System.out.println("Checking against Subclass of object "+c.getName());
      r = (ViewRenderer<T>)renderers.get(c);
      c = c.getSuperclass();
    }
//if (r!=null) {System.out.println("Located! (see previous for match)");}
    return r;
  }
}