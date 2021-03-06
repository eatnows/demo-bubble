package test.ex05;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

// 메인스레드는 바쁨 - 키보드 이벤트를 처리하기 바쁨
// 백그라운드에서 계속 관찰
public class BackgroundPlayerService implements Runnable{

    private BufferedImage image;
    private Player player;
    private List<Bubble> bubbles;

    // 플레이어, 버블
    public BackgroundPlayerService(Player player) {
        this.player = player;
        this.bubbles = player.getBubbles();
        try {
            image = ImageIO.read(new File("image/backgroundMapService.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void run() {
        while (true) {

            // 1. 버블 충돌 체크
            for (int i = 0; i < bubbles.size(); i++) {
                Bubble bubble = bubbles.get(i);
                if (bubble.getState() == 1) {
                    if ((Math.abs(player.getX() - bubble.getX()) < 10)
                            && (Math.abs(player.getY() - bubble.getY()) > 0 && Math.abs(player.getY() - bubble.getY()) < 50)) {
                        System.out.println("적군 사살 완료");
                        bubble.clearBubbled();
                        break;
                    }
                }
            }

            // 2. 벽 충돌 체크
            // 색상 확인
            Color leftColor = new Color(image.getRGB(player.getX() - 10, player.getY() + 25));
            Color rightColor = new Color(image.getRGB(player.getX() + 50 + 10, player.getY() + 25));
            // -2가 나온다는 것은 바닥에 색깔이 없이 흰색
            int bottomColor = image.getRGB(player.getX(), player.getY() + 50) // -1
                    + image.getRGB(player.getX() + 50, player.getY() + 50); // -1
//            Color bottomColor = new Color(image.getRGB(player.getX() + 25, player.getY() + 50));

            // 바닥 충돌 확인
            if (bottomColor != -2) {
                player.setDown(false);
            } else {
                if (!player.isUp() && !player.isDown()) {
                    player.down();
                }
            }
//            if (!(bottomColor.getRed() == 255 && bottomColor.getGreen() == 255 && bottomColor.getBlue() == 255)) {
//                player.setDown(false);
//            }

            // 외벽 충돌 확인
            if (leftColor.getRed() == 255 && leftColor.getGreen() == 0 && leftColor.getBlue() == 0) {
//                System.out.println("왼쪽 벽에 충돌함");
                player.setLeftWallCrash(true);
                player.setLeft(false);
            } else if (rightColor.getRed() == 255 && rightColor.getGreen() == 0 && rightColor.getBlue() == 0) {
//                System.out.println("오른쪽 벽에 충돌함");
                player.setRightWallCrash(true);
                player.setRight(false);
            } else {
                player.setLeftWallCrash(false);
                player.setRightWallCrash(false);
            }

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
