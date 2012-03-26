package Utils.Sound;

import Utils.XML.XMLLoad.Indexable;
import java.net.URI;


public abstract class Sound implements Indexable {
  private String  name;
  private URI     source;
  private boolean background_music = false;
  
  public Sound(URI uri             ) {this(uri, null);}
  public Sound(URI uri, String name) {
    source = uri;
    if (name==null) {this.name = Utils.File.FileOperations.getFilenameWithoutExtension(uri.toString());}
  }
  
  protected void setMusic(boolean background_music) {this.background_music = background_music;}
  
  public String  getName()   {return name;}
  public URI     getSource() {return source;}
  public boolean isMusic()   {return background_music;}
  
  public void play() {SoundManager.soundPlaying(this);}
  public void stop() {SoundManager.soundStoped (this);}
}
