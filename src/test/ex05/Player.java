package test.ex05;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

// class Player -> new 가능, 게임에 존재할 수 있음. (추상메서드를 가질 수 없다,)
@Getter
@Setter
public class Player extends JLabel implements Moveable {

    private BubbleFrame mContext;
    private List<Bubble> bubbles;

    // 위치 상태
    private int x;
    private int y;

    // 플레이어의 방향
    private PlayerDirection playerDirection;

    // 움직임 상태
    private boolean left;
    private boolean right;
    private boolean up;
    private boolean down;

    // 벽에 충돌한 상태
    private boolean leftWallCrash;
    private boolean rightWallCrash;

    // 플레이어 속도 상태
    private final int SPEED = 4;
    private final int JUMPSPEED = 2;

    private ImageIcon playerR, playerL;

    public Player(BubbleFrame mContext) {
        this.mContext = mContext;
        initObject();
        initSetting();
        initBackgroundPlayerService();
    }

    private void initObject() {
        playerR = new ImageIcon("image/playerR.png");
        playerL = new ImageIcon("image/playerL.png");
        bubbles = new ArrayList<>();
    }

    private void initSetting() {
        x = 80;
        y = 535;

        left = false;
        right = false;
        up = false;
        down = false;

        leftWallCrash = false;
        rightWallCrash = false;

        playerDirection = PlayerDirection.RIGHT;
        this.setIcon(playerR);
        setSize(50, 50);
        setLocation(x, y);
    }

    private void initBackgroundPlayerService() {
        new Thread(new BackgroundPlayerService(this)).start();
    }

    @Override
    public void attack() {
        new Thread(() -> {
            Bubble bubble = new Bubble(mContext);
            mContext.add(bubble);
            bubbles.add(bubble);
            if (playerDirection == PlayerDirection.LEFT) {
                bubble.left();
            } else {
                bubble.right();
            }
        }).start();
    }

    // 이벤트 핸들러
    @Override
    public void left() {
        System.out.println("left");
        playerDirection = PlayerDirection.LEFT;

        left = true;
        new Thread(() -> {
            while (left) {
                setIcon(playerL);
                x -= SPEED;
                setLocation(x, y);
                try {
                    Thread.sleep(10); // 0.01 초
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void right() {
        playerDirection = PlayerDirection.RIGHT;

        System.out.println("right");
        right = true;
        new Thread(() -> {
            while (right) {
                setIcon(playerR);
                x += SPEED;
                setLocation(x, y);
                try {
                    Thread.sleep(10); // 0.01 초
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    // left + up, right + up
    @Override
    public void up() {
        up = true;
        new Thread(() -> {
            for (int i = 0; i < 130 / JUMPSPEED; i++) {
                y -= JUMPSPEED;
                setLocation(x, y);
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            up = false;

            down();
        }).start();
    }

    @Override
    public void down() {
        System.out.println("down");
        down = true;
        new Thread(()-> {
            while (down) {
                y += JUMPSPEED;
                setLocation(x, y);
                try {
                    Thread.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            down = false;
        }).start();
    }
}
