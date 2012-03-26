package Utils.Sound;

import java.net.URI;
import javax.sound.sampled.*;

class SoundSample extends Sound implements LineListener {

  Clip clip;
  
  public SoundSample(URI uri) throws Exception {
    super(uri);
    //System.out.println("Load:"+getName());
    AudioInputStream stream = AudioSystem.getAudioInputStream(Utils.File.StreamManager.getInputStream(uri));
    clip = (Clip)AudioSystem.getLine(new DataLine.Info(Clip.class, stream.getFormat()));
    clip.addLineListener(this);
    clip.open(stream);
    stream.close();
  }
  
  public void play() {
    super.play();
    clip.start();
  }

  public void stop() {
    super.stop();
    clip.stop();
    clip.setFramePosition(0);
  }

  public void update(LineEvent lineEvent) {
    if (lineEvent.getType() == LineEvent.Type.STOP) {
      stop();
    }
    //System.out.println("line event on "+getName());
  }

}