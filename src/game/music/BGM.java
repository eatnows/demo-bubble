package game.music;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;

public class BGM {

    public BGM() {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(new File("sound/bgm.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(ais);

            // 소리 설정
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

            // 볼륨 조정
            gainControl.setValue(-30.0F);

            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
