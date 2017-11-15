package com.homeworks.MainTest;



import java.lang.annotation.*;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.PriorityQueue;

public class MainTest {

    public static void main(String[] args) throws Exception {
        //      Class c = ClassTest.class;
        Class c1 = ClassTest1.class;
        //    Method[]methods = c.getDeclaredMethods();
        Method[] methods1 = c1.getDeclaredMethods();
        ClassTest1 classTest1 = new ClassTest1();
        //   ClassTest classTest = new ClassTest();
//        for (Method m : methods) {
//            if (m.isAnnotationPresent(Test.class)||m.isAnnotationPresent(BeforeSuite.class)||m.isAnnotationPresent(AfterSuite.class)) {
//                m.invoke(classTest);
//                //System.out.println(m.getAnnotation(Test.class).priority());
//            }
//        }
        int count = 0;

        PriorityQueue<Integer> pq = new PriorityQueue<>(5);

        for (Method m1 : methods1) {

            if (m1.isAnnotationPresent(Test.class)) {

                pq.add(m1.getAnnotation(Test.class).priority());
            }
            if (m1.isAnnotationPresent(BeforeSuite.class)) {
                count++;
                if (count > 1) {
                    throw new RuntimeException("@BeforeSuite cannot be run more than once!");
                }

                    pq.add(m1.getAnnotation(BeforeSuite.class).priority());
                }
                if (m1.isAnnotationPresent(AfterSuite.class)) {

                    pq.add(m1.getAnnotation(AfterSuite.class).priority());
                }
            }

            Object[] arrays = pq.toArray();
            Arrays.sort(arrays);
//        for (Object o:arrays) {
//            System.out.println(o.toString());
//        }

            for (Object o : arrays) {
                for (Method m1 : methods1) {
                    if (m1.isAnnotationPresent(Test.class)) {

                        if (m1.getAnnotation(Test.class).priority() == Integer.parseInt(o.toString())) {
                            m1.invoke(classTest1);
                        }
                    }
                    if (m1.isAnnotationPresent(BeforeSuite.class)) {


                        //if (count>1){throw new RuntimeException("@BeforeSuite cannot be used more than once!");}


                        if (m1.getAnnotation(BeforeSuite.class).priority() == Integer.parseInt(o.toString())) {
                            m1.invoke(classTest1);
                        }
                    }
                    if (m1.isAnnotationPresent(AfterSuite.class)) {

                        if (m1.getAnnotation(AfterSuite.class).priority() == Integer.parseInt(o.toString())) {
                            m1.invoke(classTest1);

                        }

                    }
                }
            }

        }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public static @interface AfterSuite {
        int priority() ;
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public static @interface BeforeSuite {
        int priority() ;
    }

    public static class Cat {
        public String name;
        public int age;
        public int weight;

        public Cat() {
        }

    }

    public static class ClassTest {
        Cat cat;

        @BeforeSuite(priority = 1)
        public void beforeCatNameTest() {
            cat = new Cat();
            System.out.println("Before");
        }


        @Test(priority = 2)
        public void catNameTest() {
            System.out.println("1");
        }
        @Test(priority = 3)
        public void catNameTest1() {
            System.out.println("2");

        }
        @Test(priority = 4)
        public void catNameTest2() {
            System.out.println("3");

        }

        @AfterSuite(priority = 10)
        public void afterCatNameTest( ) {
            System.out.println("END of testing the Cat`s name...");

        }

    }

    public static class ClassTest1 {

            Cat cat;

            @BeforeSuite(priority = 1)
            public void beforeTestCatAge() {
                cat = new Cat();
                    System.out.println("before");

            }

            @Test(priority = 2)
            public void testCatAge() {
                    System.out.println("1");


            }
            @Test(priority = 3)
            public void testCatAge1() {
                    System.out.println("2");

            }
            @AfterSuite(priority = 4)
            public void afterTestCatAge() {
                System.out.println("END of Cat`s age testing");
            }
        }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public static @interface Test {
      int priority() ;
    }
}





