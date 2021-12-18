package test.ex05;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class BubbleFrame extends JFrame {

    private JLabel backgroundMap;
    private Player player;

    public BubbleFrame() {
        initObject();
        initSetting();
        initListener();
        setVisible(true);
    }

    private void initObject() {
        backgroundMap = new JLabel(new ImageIcon("image/backgroundMap.png"));
        setContentPane(backgroundMap);
//        backgroundMap.setSize(1000, 600);
//        backgroundMap.setLocation(300, 300);

//        add(backgroundMap); // JFrame에 JLabel이 그려진다.

        player = new Player();
        add(player);
    }

    private void initSetting() {
        setSize(1000, 640);
        setLayout(null); // absoulte 레이아웃 (자유롭게 그림을 그릴 수 있다.)
        setLocationRelativeTo(null); // JFrame 가운데 배치하기
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // x버튼으로 창을 끌 때 JVM 같이 종료허기
    }

    private void initListener() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {

                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        if (!player.isLeft()) player.left();

                        break;
                    case KeyEvent.VK_RIGHT:
                        if (!player.isRight()) {
                            player.right();
                        }
                        break;
                    case KeyEvent.VK_UP:
                        if (!player.isUp() && !player.isDown()) {
                            player.up();
                        }
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
