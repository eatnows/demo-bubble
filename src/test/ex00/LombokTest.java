package test.ex00;

import lombok.Getter;

@Getter
class Dog {
    private String name;
}

public class LombokTest {
    public static void main(String[] args) {
        Dog d = new Dog();
        System.out.println(d.getName());
    }
}