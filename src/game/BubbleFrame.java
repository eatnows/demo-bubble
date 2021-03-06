package game;

import game.component.Enemy;
import game.component.Player;
import game.music.BGM;
import game.state.EnemyDirection;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class BubbleFrame extends JFrame {

    private BubbleFrame mContext = this;
    private JLabel backgroundMap;
    private Player player;
    private List<Enemy> enemys;

    public BubbleFrame() {
        initObject();
        initSetting();
        initListener();
        setVisible(true);
    }

    private void initObject() {
        backgroundMap = new JLabel(new ImageIcon("image/backgroundMap.png"));
        setContentPane(backgroundMap);
        player = new Player(this);
        add(player);
        enemys = new ArrayList<>();
        enemys.add(new Enemy(mContext, EnemyDirection.RIGHT));
        enemys.add(new Enemy(mContext, EnemyDirection.LEFT));
        for (Enemy enemy : enemys) {
            add(enemy);
        }
        new BGM();
    }

    private void initSetting() {
        setSize(1000, 640);
        setLayout(null); // absolute 레이아웃 (자유롭게 그림을 그릴 수 있다.)
        setLocationRelativeTo(null); // JFrame 가운데 배치하기
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // x버튼으로 창을 끌 때 JVM 같이 종료허기
    }

    private void initListener() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {

                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        if (!player.isLeft() && !player.isLeftWallCrash() && !player.isDie()) {
                            player.left();
                        }

                        break;
                    case KeyEvent.VK_RIGHT:
                        if (!player.isRight() && !player.isRightWallCrash() && !player.isDie()) {
                            player.right();
                        }
                        break;
                    case KeyEvent.VK_UP:
                        if (!player.isUp() && !player.isDown() && !player.isDie()) {
                            player.up();
                        }
                        break;
                    case KeyEvent.VK_SPACE:
                        if (!player.isDie())
                            player.attack();
                        break;
                }
            }

            // 키를 뗄때 상태값을 false로 변경
            @Override
            public void keyReleased(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        player.setLeft(false);
                        break;
                    case KeyEvent.VK_RIGHT:
                        player.setRight(false);
                        break;
                }
            }
        });

    }

    public static void main(String[] args) {
        new BubbleFrame();
    }
}
