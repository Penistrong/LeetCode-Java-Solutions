package org.penistrong.jvm;

public class NonPolymorphicFieldExample {
    static class Human {
        public int age = 18;

        public Human () {
            age = 20;
            showTheAge();
        }

        public void showTheAge() {
            System.out.println("I am a human, now " + age + " years old");
        }
    }

    static class Man extends Human {
        public int age = 30;

        public Man () {
            age = 40;
            showTheAge();
        }

        @Override
        public void showTheAge() {
            System.out.println("I am a gentle man, now " + age + " years old");
        }
    }

    public static void main(String[] args) {
        Human man = new Man();
        System.out.println("The human is " + man.age + " years old");
    }
}
