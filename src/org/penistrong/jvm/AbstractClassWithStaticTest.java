package org.penistrong.jvm;

public abstract class AbstractClassWithStaticTest {

    static abstract class InnerClass {
        abstract void innerTest();
    }

    private static class InnerExtendedClass extends InnerClass {

        @Override
        void innerTest() {
            System.out.println("WTF");
        }
    }

    public void test() {
        InnerClass ins = new InnerExtendedClass();
        AbstractClassWithStaticTest.InnerClass sb = new InnerClass() {
            @Override
            void innerTest() {
                System.out.println("匿名内部类");
            }
        };
    }
}
