package Utils.Sound;

import Utils.XML.XMLLoad.LoadManager;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;


public class SoundManager {

  private static final Map<String,Sound> sound_clips = LoadManager.getGroup(Sound.class);

  private static final Collection<Sound>              sound_playing = new ArrayList<Sound>();
  private static final Collection<SoundEventListener> listeners     = new ArrayList<SoundEventListener>();

 static {
    addSoundEventListener(new BackgroundMusicManager());
  }
  private SoundManager() {} //enforce non-instability

  public static void    addSoundEventListener(SoundEventListener listener) {listeners.add   (listener);}
  public static void removeSoundEventListener(SoundEventListener listener) {listeners.remove(listener);}
  
  public static void playSound    (String name          ) {sound_clips.get(name).play();}
  public static void playSoundLoop(String name          ) {playSound(name);}
  public static void playSoundLoop(String name, int loop) {playSound(name);}
  
  public static void stopAll() {for (Sound s : sound_playing) {s.stop();}}
  
  public static Collection<Sound>  getSounds()       {return sound_clips.values();}
  public static Collection<String> getSoundNames()   {return sound_clips.keySet();}
  public static Collection<Sound>  getSoundPlaying() {return sound_playing;}

  static void soundPlaying(Sound s) {
    sound_playing.add(s);
    for (SoundEventListener listener : listeners) {listener.soundPlaying(s);}
  }
  static void soundStoped(Sound s)  {
    sound_playing.remove(s);
    for (SoundEventListener listener : listeners) {listener.soundStoped(s);}
  }

}