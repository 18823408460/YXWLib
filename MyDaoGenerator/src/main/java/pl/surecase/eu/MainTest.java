package pl.surecase.eu;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Created by Administrator on 2017/11/25.
 */

public class MainTest {

        public static final Object obj = new Object();

        private static int m = 0;

        public static void main(String[] args) {
                Out.StaticInner staticInner = new Out.StaticInner();


                Out.Inner inner = new Out().new Inner();
                String temp = "";
                String tex = "a" + "&&" + "b" + "&&" + "&";
                String[] split = tex.split("&");
                System.out.print(split.length + "\n");
                for (String e : split
                        ) {
                        System.out.print(e + "\n");
                }
                System.out.print(".............end..................");

                String a = null;
                String b = a + "1";
                System.out.println("............" + b);
                System.out.println("............" + add());


//                testSingle();
                testList();

        }

        private static void testSingle() {
                new Thread(new Runnable() {
                        @Override
                        public void run() {
                                Msg.getInstance().delete();
                        }
                }).start();
                new Thread(new Runnable() {
                        @Override
                        public void run() {
                                Msg.getInstance().delete();
                        }
                }).start();
        }


        private static void testList() {
                ArrayList<String> arrayList = new ArrayList<>();
                new Thread(new Runnable() {
                        @Override
                        public void run() {
                                for (String temp : arrayList
                                        ) {
                                        System.out.println("............" + temp);
                                        try {
                                                Thread.sleep(50);
                                        }
                                        catch (InterruptedException e) {
                                                e.printStackTrace();
                                        }
                                }
                        }
                }).start();

                new Thread(new Runnable() {
                        @Override
                        public void run() {
                           arrayList.add("11");
                           arrayList.add("11");
                           arrayList.add("11");
                           arrayList.add("11");
                           arrayList.add("11");
                           arrayList.add("11");
                           arrayList.add("11");
                        }
                }).start();
        }


        public static class Consumer implements Runnable {

                @Override
                public synchronized void run() {
                        // TODO Auto-generated method stub
                        int count = 10;
                        while (count > 0) {
                                synchronized (MainTest.obj) {

                                        System.out.println("B");
                                        count--;
                                        MainTest.obj.notify(); // 主动释放对象锁

                                        try {
                                                MainTest.obj.wait();

                                        }
                                        catch (InterruptedException e) {
                                                // TODO Auto-generated catch block
                                                e.printStackTrace();
                                        }
                                }

                        }
                }
        }

        public static class Produce implements Runnable {

                @Override
                public void run() {
                        // TODO Auto-generated method stub
                        int count = 10;
                        while (count > 0) {
                                synchronized (MainTest.obj) {

                                        //System.out.print("count = " + count);
                                        System.out.println("A");
                                        count--;
                                        MainTest.obj.notify();

                                        try {
                                                MainTest.obj.wait(4000);  //也就是说，这里会阻塞 。
                                                System.out.println("A==================");
                                        }
                                        catch (InterruptedException e) {
                                                // TODO Auto-generated catch block
                                                e.printStackTrace();
                                        }
                                }

                        }

                }

        }

        private static int add() {

                int a;
                try {
                        a = 1;
                        System.out.println("1............" + a);
                        return a;
                }
                finally {
                        a = 2;
                        System.out.println("2............" + a);
                }
        }
}
