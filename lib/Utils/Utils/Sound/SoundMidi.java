package Utils.Sound;

import java.net.URI;
import javax.sound.midi.*;


class SoundMidi extends Sound implements MetaEventListener{
  
  private static final int END_OF_TRACK = 47;
  
  private static Sequencer sequencer;
  private        Sequence  sequence;
  
  
  public SoundMidi(URI uri) throws Exception {
    super(uri);
    setMusic(true);
    if (sequencer==null) {initSequencer();}
    sequence = MidiSystem.getSequence(Utils.File.StreamManager.getInputStream(uri));
  }

  
  public void play() {
    try {
      super.play();
      sequencer.stop();
      sequencer.setSequence(sequence);
      sequencer.start();
    }
    catch (Exception e) {}
  }

  public void stop() {
    sequencer.stop();
    super.stop();
  }
  
  private void initSequencer() {
    try {
      Synthesizer sythesizer;
      
      sequencer = MidiSystem.getSequencer();
      sequencer.open();
      sequencer.addMetaEventListener(this);
      if (!(sequencer instanceof Synthesizer)) {
        sythesizer = MidiSystem.getSynthesizer();
        sythesizer.open();
        sequencer.getTransmitter().setReceiver(sythesizer.getReceiver());
      }
      else {
        sythesizer = (Synthesizer)sequencer;
      }
    }
    catch (Exception e) {}
  }
  

  public void meta(MetaMessage meta) {
    if (meta.getType() == END_OF_TRACK) {
      stop();
    }
  }
}
