package Utils.Sound;

import java.net.URI;
import javax.sound.sampled.*;


class SoundOgg extends Sound {

  private static SoundPlayThread thread;
  
  public SoundOgg(URI uri) {
    super(uri);
    setMusic(true);
    //TODO: check ogg ok here .. or throw exception ...
  }

  
  public void play() {
    if (thread!=null) {stop();}
    try {
      super.play();
      thread = new SoundPlayThread(getSource());
      thread.soundPlay();
    } catch (Exception e) {}
  }

  public void stop() {
    if (thread!=null) {
      thread.soundStop();
      thread = null;
      super.stop();
    }
  }
}


class SoundPlayThread extends Thread {
  private final  AudioInputStream stream_decoded;
  private final  SourceDataLine   line;
  private        boolean          playing;

  public SoundPlayThread(URI uri) throws Exception {
    AudioInputStream stream        = AudioSystem.getAudioInputStream(Utils.File.StreamManager.getInputStream(uri));  
	  AudioFormat      format_ogg    = stream.getFormat();
		AudioFormat      format_decode = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, format_ogg.getSampleRate(), 16, format_ogg.getChannels(), format_ogg.getChannels() * 2, format_ogg.getSampleRate(), false);
		stream_decoded = AudioSystem.getAudioInputStream(format_decode, stream);
    line           = (SourceDataLine)AudioSystem.getLine(new DataLine.Info(SourceDataLine.class, format_decode));
	  line.open(format_decode);
    //System.out.println("Source Format : "+format_ogg.toString());
		//System.out.println("Target Format : "+format_decode.toString());
  }
  
  public void run() {
    int numRead = 0;
    byte[] buffer = new byte[line.getBufferSize()];
    line.start();
    try {
      int offset;
      while (playing==true && (numRead = stream_decoded.read(buffer,0,buffer.length)) >=0 ) {
        offset = 0;
        while (playing==true && offset<numRead) {offset += line.write(buffer,offset,numRead-offset);}
      }
    } catch (Exception e) {}
    line.drain();
    line.stop();
    line.close();
    try                 {stream_decoded.close();}
    catch (Exception e) {}
  }

  public boolean soundPlay() {
    playing = true;
    try                                   {start(); return true; }
    catch (IllegalThreadStateException e) {         return false;}
  }
  
  public void soundStop() {
    playing = false;
  }

}