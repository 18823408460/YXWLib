package pl.surecase.eu;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Administrator on 2017/12/15.
 */

public class Msg {
        public static Msg instance ;
        public int count = 10 ;

        public static Msg getInstance(){
                if (instance == null){
                        synchronized (Msg.class){
                                if (instance == null) {
                                        instance = new Msg();
                                }
                        }
                }
                return instance ;
        }

        public synchronized  void delete(){
//                ConcurrentHashMap
                while (count>0){
                        count--;
                        System.out.println(Thread.currentThread().getName()+">--------count=="+count);
                }
        }

}
