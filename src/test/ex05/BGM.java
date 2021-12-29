package test.ex05;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

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
