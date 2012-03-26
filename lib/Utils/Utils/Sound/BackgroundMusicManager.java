package Utils.Sound;


public class BackgroundMusicManager implements SoundEventListener {

  private Sound background_music;
    
  public BackgroundMusicManager() {}

  public void soundPlaying(Sound s) {
    if (s.isMusic()) {
      if (background_music!=null) {background_music.stop();}
      background_music = s;
    }
  }

  public void soundStoped(Sound s) {
    if (background_music == s) {background_music=null;}
  }
  
}
