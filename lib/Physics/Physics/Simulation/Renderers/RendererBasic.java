package Physics.Simulation.Renderers;

import Utils.ModelViewControllerFramework.ViewRenderer;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.awt.Color;

import Utils.Types.Physics.Mass;
import Physics.Simulation.Component.Spring;
import Physics.Simulation.Simulation;
import Utils.ModelViewControllerFramework.View;
import Utils.StringIndexedValues;
import java.awt.image.ImageObserver;


public class RendererBasic implements View {

  public String getName() {return "default";}
  
  public void render(Graphics g, ImageObserver io, Simulation sim, StringIndexedValues options) {
    g.setColor(Color.WHITE);
    g.fillRect(0, 0, sim.getSimulationSize().width, sim.getSimulationSize().height);
    g.setColor(Color.BLACK);
    for (Spring s : sim.getSprings()) {
      Point2D.Float p1 = s.getMass1().getPoint();
      Point2D.Float p2 = s.getMass2().getPoint();
      g.drawLine((int)p1.x,(int)p1.y,(int)p2.x,(int)p2.y);
    }
    for (Mass m : sim.getMasss()) {
      Point2D.Float p = m.getPoint();
      g.drawRect((int)p.x-1,(int)p.y-1,3,3);
    }
  }

  public <T> void render(Graphics g, ImageObserver io, T o, StringIndexedValues options) {
    throw new UnsupportedOperationException("Not supported yet.");
  }


  public <T> ViewRenderer<T> getRenderer(Class<T> c) {
    throw new UnsupportedOperationException("Not supported yet.");
  }
}
