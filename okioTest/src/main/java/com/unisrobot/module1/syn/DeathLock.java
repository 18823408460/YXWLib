package com.unisrobot.module1.syn;

/**
 * Created by Administrator on 2018/4/3.
 */

/**
 *
 */
public class DeathLock {

        private static String A = "1212";

        private static String B = "121";  //如果a,b 的内容一致，则不会死锁。。


//        static Object A = new Object();
//        static Object B = new Object();

        public static void main(String[] args) {
                testDeathTest();
        }

        private static void testDeathTest() {

                System.out.println("A===" + A.hashCode());
                System.out.println("B===" + B.hashCode());
                Thread thread1 = new Thread(new Runnable() {
                        @Override
                        public void run() {
                                synchronized (A) { // 这个代码块执行完毕，才会释放A锁
                                        try {
                                                Thread.sleep(2000);
                                        }
                                        catch (InterruptedException e) {
                                                e.printStackTrace();
                                        }
                                        System.out.println("thread1 start getlock B");
                                        synchronized (B) {
                                                System.out.println("thread1...");

                                        }
                                }
                                System.out.println("thread1..end");
                        }
                }, "thread1");
                Thread thread2 = new Thread(new Runnable() {
                        @Override
                        public void run() {
                                synchronized (B) {
                                        try {
                                                Thread.sleep(3300);
                                        }
                                        catch (InterruptedException e) {
                                                e.printStackTrace();
                                        }
                                        System.out.println("thread2 start getlock A");
                                        synchronized (A) {
                                                System.out.println("thread2...");
                                        }
                                }
                                System.out.println("thread2...end");
                        }
                }, "thread2");

                thread1.start();
                thread2.start();
        }
}
