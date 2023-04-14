package org.penistrong.jvm;

/**
 * Java静态多分派、动态单分派的演示
 */
public class StaticMultiDispatchWhileDynamicSingleDispatch {

    static class Vegetable {}

    static class Meat {}

    static class Human {
        public void eat (Vegetable arg) {
            System.out.println("Human eat vegetable");
        }

        public void eat (Meat arg) {
            System.out.println("Human eat meat");
        }
    }

    static class ModernHuman extends Human{
        @Override
        public void eat (Vegetable arg) {
            System.out.println("ModernHuman eat vegetable");
        }

        @Override
        public void eat (Meat arg) {
            System.out.println("ModernHuman eat meat");
        }
    }

    public static void main(String[] args) {
        Human ancestor = new Human();
        Human modernMan = new ModernHuman();
        ancestor.eat(new Vegetable());
        modernMan.eat(new Meat());
    }
}
