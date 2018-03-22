package com.uurobot.yxwlib.localThread;

/**
 * Created by WEI on 2018/3/22.
 */
// ThreadLoacl是一个类似Map的数据结构，key是当前的线程名称，value是要传递的参数·

public class LocalInstance {
    private static ThreadLocal<String> threadLocal;

    private static LocalInstance instance;

    private LocalInstance() {
        threadLocal = new ThreadLocal<>();
    }

    public static LocalInstance getInstance() {
        if (instance == null) {
            synchronized (LocalInstance.class) {
                if (instance == null) {
                    instance = new LocalInstance();
                }
            }
        }
        return instance;
    }

    public void setData(String data) {
        threadLocal.set(data);
    }

    public String getData() {
        return threadLocal.get();
    }
}
