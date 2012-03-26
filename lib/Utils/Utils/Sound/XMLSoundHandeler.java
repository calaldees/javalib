package Utils.Sound;

import Utils.XML.XMLLoad.DataWrapper;
import Utils.XML.XMLLoad.LoadManager;
import Utils.XML.XMLLoad.LoadProcessor;
import java.net.URI;

public class XMLSoundHandeler extends LoadProcessor<Sound> {
  public XMLSoundHandeler() {super(Sound.class, "Sound");}
  
  public Sound overlay(Sound sound, DataWrapper data) throws Exception {return create(data);}
  public Sound create(              DataWrapper data) throws Exception {
    URI uri = LoadManager.autoLocateFile(getLocation(), data.getString("file")).toURI();
    String ext = Utils.File.FileOperations.getExtension(uri.toString()).toLowerCase();
    Sound  s   = null;
    if (ext.equals("wav")) {s = new SoundSample(uri);}
    if (ext.equals("mid")) {s = new SoundMidi  (uri);}
    if (ext.equals("ogg")) {s = new SoundOgg   (uri);}
    return s;
  }
  

}
/*
 * 
class XMLJMenuBarProcessor extends LoadProcessor<JMenuBar> {
  public XMLJMenuBarProcessor() {super(JMenuBar.class, "JMenuBar");}
  
  public JMenuBar create(                   DataWrapper data) throws Exception {return overlay(new JMenuBar(), data);}
  public JMenuBar overlay(JMenuBar menubar, DataWrapper data) throws Exception {
    Collection<JMenu> menus = data.getObjectsOfType(JMenu.class);
    for (JMenu menu: menus) {menubar.add(menu);}
    return menubar;
  }
} 
 * 
 */
