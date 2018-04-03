package com.unisrobot.module1.syn;

/**
 * Created by Administrator on 2018/4/3.
 */

public class TestDeadLock implements Runnable {

        int flag;

        static Object o1 = new Object();

        static Object o2 = new Object();

        @Override
        public void run() {
                System.out.println(":" + Thread.currentThread().getName() + "  start==flag ="+flag);
                if (flag == 0) {
                        synchronized (o1) {
                                try {
                                        Thread.sleep(500);
                                }
                                catch (InterruptedException e) {
                                        e.printStackTrace();
                                }
                                System.out.println(":" + Thread.currentThread().getName() +"  sleep end");
                                synchronized (o2) {
                                        System.out.println("flag==0");
                                }
                        }
                }
                else if (flag == 1) {
                        synchronized (o2) {
                                try {
                                        Thread.sleep(500);
                                }
                                catch (InterruptedException e) {
                                        e.printStackTrace();
                                }
                                System.out.println(":" + Thread.currentThread().getName() +"  sleep end");
                                synchronized (o1) {
                                        System.out.println("flag==1");
                                }
                        }
                }
                System.out.println(":" + Thread.currentThread().getName() + "  end");
        }


        public static void main(String[] args) {
                TestDeadLock td1 = new TestDeadLock();
                TestDeadLock td2 = new TestDeadLock();
                td1.flag = 0;
                td2.flag = 1;
                Thread t1 = new Thread(td1, "thread1");
                Thread t2 = new Thread(td2, "thread2");
                t1.start();
                t2.start();
        }
}
