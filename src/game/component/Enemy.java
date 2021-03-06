package game.component;

import game.BubbleFrame;
import game.Moveable;
import game.service.BackgroundEnemyService;
import game.state.EnemyDirection;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;

// class Player -> new 가능, 게임에 존재할 수 있음. (추상메서드를 가질 수 없다,)
@Getter
@Setter
public class Enemy extends JLabel implements Moveable {

    private BubbleFrame mContext;
    private Player player;

    // 위치 상태
    private int x;
    private int y;

    // 적군의 방향
    private EnemyDirection enemyDirection;

    // 움직임 상태
    private boolean left;
    private boolean right;
    private boolean up;
    private boolean down;

    private int state; // 0(살아있는 상태), 1(물방울에 갇힌 상태)

    // 적군 속도 상태
    private final int SPEED = 3;
    private final int JUMPSPEED = 1;

    private ImageIcon enemyR, enemyL;

    public Enemy(BubbleFrame mContext, EnemyDirection enemyDirection) {
        this.mContext = mContext;
        this.player = mContext.getPlayer();
        initObject();
        initSetting();
        initBackgroundEnemyService();
//        right();
        initEnemyDirection(enemyDirection);
    }

    private void initObject() {
        enemyR = new ImageIcon("image/enemyR.png");
        enemyL = new ImageIcon("image/enemyL.png");
    }

    private void initSetting() {
        x = 480;
        y = 178;

        left = false;
        right = false;
        up = false;
        down = false;

        state = 0;

//        enemyDirection = EnemyDirection.RIGHT;
//        setIcon(enemyR);

        setSize(50, 50);
        setLocation(x, y);
    }

    private void initBackgroundEnemyService() {
        new Thread(new BackgroundEnemyService(this)).start();
    }

    // 이벤트 핸들러

    @Override
    public void left() {
        enemyDirection = EnemyDirection.LEFT;

        left = true;
        new Thread(() -> {
            while (left) {
                setIcon(enemyL);
                x -= SPEED;
                setLocation(x, y);
                checkPlayerDie();
                try {
                    Thread.sleep(10); // 0.01 초
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private void checkPlayerDie() {
        if ((Math.abs(x - player.getX()) < 50 && Math.abs(y - player.getY()) < 50)
            && !player.isDie() && getState() == 0) {
            player.die();
        }
    }

    @Override
    public void right() {
        enemyDirection = EnemyDirection.RIGHT;

        right = true;
        new Thread(() -> {
            while (right) {
                setIcon(enemyR);
                x += SPEED;
                setLocation(x, y);
                checkPlayerDie();
                try {
                    Thread.sleep(10); // 0.01 초
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void up() {
        up = true;
        new Thread(() -> {
            for (int i = 0; i < 130 / JUMPSPEED; i++) {
                y -= JUMPSPEED;
                setLocation(x, y);
                checkPlayerDie();
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
        down = true;
        new Thread(()-> {
            while (down) {
                y += JUMPSPEED;
                setLocation(x, y);
                checkPlayerDie();
                try {
                    Thread.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            down = false;
        }).start();
    }

    private void initEnemyDirection(EnemyDirection enemyDirection) {
        if (EnemyDirection.RIGHT == enemyDirection) {
            enemyDirection = EnemyDirection.RIGHT;
            setIcon(enemyR);
            right();
        } else {
            enemyDirection = EnemyDirection.LEFT;
            setIcon(enemyL);
            left();
        }
    }
}
