package com.unisrobot.module1;

import com.unisrobot.module1.syn.SynTestUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

import okio.BufferedSink;
import okio.BufferedSource;
import okio.ByteString;
import okio.Okio;
import okio.Sink;
import okio.Source;

/**
 * Created by Administrator on 2018/3/15.
 */

public class OkioTest {

        public static void main(String[] args) {
//                testOKio();

//                int data = 11;
//                ArrayList<Integer> index = getIndex(data);
//                System.out.println("index=" + index);

                testAyn();
        }

        private static void testAyn() {
                Thread thread1 = new Thread(new Runnable() {
                        @Override
                        public void run() {
                                new SynTestUtil().merge9();
                        }
                }, "thread1");
                Thread thread2 = new Thread(new Runnable() {
                        @Override
                        public void run() {
                                new SynTestUtil().merge10();
                        }
                }, "thread2");
                thread1.start();
                thread2.start();
        }


        private static ArrayList<Integer> getIndex(int data) {
                ArrayList<Integer> ints = new ArrayList<>();
                for (int i = 0; i < 12; i++) {
                        if ((data & 0x01) == 1) {
                                ints.add(i);
                        }
                        data >>= 1;
                }
                return ints;
        }


        private static void testThreadLocal() {
        }

        private static void testOKio() {
                File file = new File("test.txt");
                if (!file.exists()) {
                        try {
                                file.createNewFile();
                        }
                        catch (IOException e) {
                                e.printStackTrace();
                        }
                }
                else {
                        System.out.println("exist");
                }

                try {
                        Sink sink = Okio.sink(file);
                        BufferedSink buffer = Okio.buffer(sink);
                        ByteString byteString = ByteString.encodeUtf8("hello byte String");

                        buffer.write(byteString);
                        buffer.writeString("hello", Charset.defaultCharset());
                        buffer.close();
                }
                catch (FileNotFoundException e) {
                        e.printStackTrace();
                        System.out.println("e==" + e);
                }
                catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("e2==" + e);
                }

                try {
                        Source source = Okio.source(file);
                        BufferedSource buffer = Okio.buffer(source);
                        String s = buffer.readString(Charset.defaultCharset());


                        System.out.println("read===" + s);
                }
                catch (FileNotFoundException e) {
                        e.printStackTrace();
                }
                catch (IOException e) {
                        e.printStackTrace();
                }
        }
}
