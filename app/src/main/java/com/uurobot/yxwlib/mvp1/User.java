package com.uurobot.yxwlib.mvp1;

/**
 * Created by Administrator on 2017/11/22.
 */

public class User {
        private String name ;
        private String age ;

        public User(String name, String age) {
                this.name = name;
                this.age = age;
        }

        public String getName() {
                return name;
        }

        public String getAge() {
                return age;
        }

        @Override
        public String toString() {
                return "User{" +
                        "name='" + name + '\'' +
                        ", age='" + age + '\'' +
                        '}';
        }
}
