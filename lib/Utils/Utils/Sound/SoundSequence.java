package Utils.Sound;

public class SoundSequence {

  public static void playSequence(String[] sound_name_sequence) {
    new SoundSequenceListener(sound_name_sequence);
  }
  
  public static void playSequence(Sound[]  sound_sequence) {
    String[] sound_name_sequence = new String[sound_sequence.length];
    for (int i=0 ; i<sound_sequence.length ; i++) {
      sound_name_sequence[i] = sound_sequence[i].getName();
    }
    playSequence(sound_name_sequence);
  }
}





class SoundSequenceListener implements SoundEventListener {
  private String[] sound_sequence_names;
  private int      current_sound_index;
  
  public SoundSequenceListener(String[] sound_sequence_names) {
    this.sound_sequence_names = sound_sequence_names;
    SoundManager.addSoundEventListener(this);
    playNextSound();
  }

  public void soundPlaying(Sound s) {}
  public void soundStoped (Sound s) {
    if (s.getName().equals(getCurrentSound())) {
      playNextSound();
    }
  }

  private void playNextSound() {
    String sound_name = getNextSound();
    if (sound_name!=null) {SoundManager.playSound(sound_name);}
    else                  {SoundManager.removeSoundEventListener(this);}
  }
  
  private String getCurrentSound() {
    try                 {return sound_sequence_names[current_sound_index];}
    catch (Exception e) {return null;}
  }
  private String getNextSound() {
     current_sound_index++;
     return getCurrentSound();
  }
}