package org.penistrong.jvm;

public class StaticDispatchExample {
    static abstract class Human {}

    static class Man extends Human {}

    static class Woman extends Human {}

    public void sayHello(Human human) {
        System.out.println("Hello, human.");
    }

    public void sayHello(Man man) {
        System.out.println("Hello, Mr.");
    }

    public void sayHello(Woman woman) {
        System.out.println("Hello, Miss.");
    }

    public static void main (String[] args) {
        Human man = new Man();
        Human woman = new Woman();
        StaticDispatchExample sde = new StaticDispatchExample();
        sde.sayHello(man);      // 运行结果: "Hello, human."
        sde.sayHello(woman);    // 运行结果: "Hello, human."
    }
}
