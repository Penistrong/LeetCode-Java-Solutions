package org.penistrong.jvm;

public class DynamicDispatchExample {
    static abstract class Human {
        protected abstract void sayHello();
    }

    static class Man extends Human {
        @Override
        protected void sayHello() {
            System.out.println("Hello, Mr.");
        }
    }

    static class Woman extends Human {
        @Override
        protected void sayHello() {
            System.out.println("Hello, Miss.");
        }
    }

    public static void main (String[] args) {
        Human man = new Man();
        Human woman = new Woman();
        man.sayHello();         // 运行结果: "Hello, Mr."
        woman.sayHello();       // 运行结果: "Hello, Miss."
        man = new Woman();
        man.sayHello();      // 运行结果: "Hello, Miss."
    }
}
