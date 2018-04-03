package com.unisrobot.module1.syn;

/**
 * Created by Administrator on 2018/4/3.
 */

public class SynTestUtil {

        private static Object object = new Object(); // 这个也是class.锁
        private  Object object2 = new Object();

        public synchronized static void merge() {
                long l = System.currentTimeMillis();
                for (int i = 0; i < 10; i++) {
                        System.out.println("name=" + Thread.currentThread().getName() + "  time=" + l);
                        try {
                                Thread.sleep(500);
                        }
                        catch (InterruptedException e) {
                                e.printStackTrace();
                        }
                }
        }

        public synchronized static void merge1() {
                long l = System.currentTimeMillis();
                for (int i = 0; i < 10; i++) {
                        System.out.println("1name=" + Thread.currentThread().getName() + "  1time=" + l);
                        try {
                                Thread.sleep(500);
                        }
                        catch (InterruptedException e) {
                                e.printStackTrace();
                        }
                }
        }


        public synchronized void merge3() {
                long l = System.currentTimeMillis();
                for (int i = 0; i < 10; i++) {
                        System.out.println("3name=" + Thread.currentThread().getName() + "  3time=" + l);
                        try {
                                Thread.sleep(500);
                        }
                        catch (InterruptedException e) {
                                e.printStackTrace();
                        }
                }
        }

        public synchronized void merge4() {
                long l = System.currentTimeMillis();
                for (int i = 0; i < 10; i++) {
                        System.out.println("4name=" + Thread.currentThread().getName() + "  4time=" + l);
                        try {
                                Thread.sleep(500);
                        }
                        catch (InterruptedException e) {
                                e.printStackTrace();
                        }
                }
        }


        public void merge5() {
                synchronized (SynTestUtil.class) {
                        long l = System.currentTimeMillis();
                        for (int i = 0; i < 10; i++) {
                                System.out.println("5name=" + Thread.currentThread().getName() + "  5time=" + l);
                                try {
                                        Thread.sleep(500);
                                }
                                catch (InterruptedException e) {
                                        e.printStackTrace();
                                }
                        }
                }
        }

        public void merge6() {
                synchronized (SynTestUtil.class) {
                        long l = System.currentTimeMillis();
                        for (int i = 0; i < 10; i++) {
                                System.out.println("6name=" + Thread.currentThread().getName() + "  6time=" + l);
                                try {
                                        Thread.sleep(500);
                                }
                                catch (InterruptedException e) {
                                        e.printStackTrace();
                                }
                        }
                }

        }

        public void merge7() {
                synchronized (SynTestUtil.object) {
                        long l = System.currentTimeMillis();
                        for (int i = 0; i < 10; i++) {
                                System.out.println("5name=" + Thread.currentThread().getName() + "  5time=" + l);
                                try {
                                        Thread.sleep(500);
                                }
                                catch (InterruptedException e) {
                                        e.printStackTrace();
                                }
                        }
                }
        }

        public void merge8() {
                synchronized (SynTestUtil.object) {
                        long l = System.currentTimeMillis();
                        for (int i = 0; i < 10; i++) {
                                System.out.println("6name=" + Thread.currentThread().getName() + "  6time=" + l);
                                try {
                                        Thread.sleep(500);
                                }
                                catch (InterruptedException e) {
                                        e.printStackTrace();
                                }
                        }
                }

        }

        public void merge9() {
                synchronized (object2) {
                        long l = System.currentTimeMillis();
                        for (int i = 0; i < 10; i++) {
                                System.out.println("9name=" + Thread.currentThread().getName() + "  9time=" + l);
                                try {
                                        Thread.sleep(500);
                                }
                                catch (InterruptedException e) {
                                        e.printStackTrace();
                                }
                        }
                }
        }

        public void merge10() {
                synchronized (object2) {
                        long l = System.currentTimeMillis();
                        for (int i = 0; i < 10; i++) {
                                System.out.println("10name=" + Thread.currentThread().getName() + "  10time=" + l);
                                try {
                                        Thread.sleep(500);
                                }
                                catch (InterruptedException e) {
                                        e.printStackTrace();
                                }
                        }
                }

        }
}
