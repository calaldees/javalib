package PhysEd;

import Utils.GUIHelpers.GUIManager;
import Utils.GUIHelpers.JInternalFrameWithMenuItem;
import Utils.XML.XMLLoad.LoadManager;

import Physics.Simulation.Simulation;
import Physics.Simulation.Component.MassConstruct;
import Physics.Simulation.Component.Material;
import Physics.Simulation.Component.Spring;
import Physics.Simulation.Renderers.RendererBasic;
import Utils.ModelViewControllerFramework.DefaultModelViewImplementation;
import Utils.ModelViewControllerFramework.ViewControllerPair;
import Utils.ModelViewControllerFramework.ViewManager;
import java.awt.Dimension;


public class PhysEd {

  public PhysEd() {
    GUIManager gui = new GUIManager("Physics");
    GUIManager.addCommandProcessor(new GUICommandProcessor(gui));

    Simulation simulation = new Simulation();
    DefaultModelViewImplementation manager = new DefaultModelViewImplementation(simulation);
    populateSimulation(simulation);

    JInternalFrameWithMenuItem simulation_frame = new JInternalFrameWithMenuItem("Simulation");
    simulation_frame.setPreferredSize(new Dimension(500,500));
    simulation_frame.setMinimumSize(new Dimension(500,500));
    
    ViewControllerPair default_view_controller = new ViewControllerPair(simulation_frame.getContentPane());
    //simulation_frame.pack();
    manager.addView(default_view_controller);
    gui.addWindow(simulation_frame);
    manager.startSim();
  }
  

  public static void main(String[] args) {
    GUIManager.initGUIManagerXMLHandelers();
    initRenderers();
    new Utils.XML.XMLLoad.LoadProcessor<Material>(Material.class, "Material");
    //new Physics.Simulation.Component.XMLHandelers.XMLMaterialProcessor();
    LoadManager.open("gui.xml");
    LoadManager.open("materials.xml");
    //Utils.XML.XMLLoad.LoadManager.listLoaded();
    new PhysEd();
  }


  
  private void populateSimulation(Simulation s) {
    s.add(new Physics.Simulation.Utils.ParticleCreator());

    MassConstruct m1 = new MassConstruct( 20, 20,20);
    MassConstruct m2 = new MassConstruct(100,100,20);
    Spring s1 = new Spring(m1,m2,LoadManager.getItem(Material.class, "test"));
    s.add(m1);
    s.add(m2);
    s.add(s1);
  }

  private static void initRenderers() {
    ViewManager.addView(new RendererBasic());
  }
}