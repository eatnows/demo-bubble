package game;

import game.component.Enemy;

/**
 *  default를 사용하면 인터페이스도 몸체가 있는 메서드를 만들 수 있다.
 *  (다중 상속이 필요하지만 다중 상속이 안되기 떄문에 새로 추가됨(adapter 패턴을 사용하여 추상 클래스를 상속받아야할 경우 등))
 *  어댑터 패턴보다는 default를 사용하는 것이 좋다.
 */
public interface Moveable {
    void left();
    void right();
    void up();

    default void down() {}
    default void attack() {

    }

    default void attack(Enemy enemy) {};
}
