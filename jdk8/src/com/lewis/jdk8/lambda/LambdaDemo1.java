package com.lewis.jdk8.lambda;

import java.util.Arrays;
import java.util.List;

/**
 * Created by zhangminghua on 2016/11/14.
 */
public class LambdaDemo1 {
    public static void main(String[] args) {
        Integer[] array = {4,2,6,8,3,1,8};
        Arrays.sort(array,(first,second)-> Integer.compare(second,first));
    /*    for (Integer integer : array) {
            System.out.println(integer);
        }*/
       // File[] files = rootFile.listFiles(file -> file.getName().endsWith("Util.java"));
        List<Integer> list = Arrays.asList(array);
        Runnable r = () ->
        {
            System.out.println("zzz");
            try {;
                Thread.sleep(400);
            } catch (InterruptedException e) {

            }
        };
        String[] a = {"A","C","F","E","B","D"};
        Arrays.sort(a,String::compareToIgnoreCase);
        for (String s : a) {
            System.out.println(s);
        }
        Son son = new Son();
        son.hello();

        Student ss = new Student();
        System.out.println(ss.getName());

    }

   static class Father {
        public void greet(){
            System.out.println("hello, i am your father !");
        }
    }

    static class Son extends Father {
        public void hello(){
            Thread t = new Thread(super::greet);
            t.start();
        }
    }

     interface Person{
        int getId();

       default String getName(){
            return "lewis!";
        }
    }

     interface Named{
        default String getName(){
            return "named name !";
        }
    }

   static   class Student implements Person,Named{

        @Override
        public int getId() {
            return 0;
        }

        public String getName() {
            return Named.super.getName();
        }
    }
}
