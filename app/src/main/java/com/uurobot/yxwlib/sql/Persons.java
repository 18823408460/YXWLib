package com.uurobot.yxwlib.sql;

/**
 * Created by Administrator on 2018/3/27.
 */

public class Persons {
        private int id ;
        private String name ;
        private int age ;

        public Persons(int id, String name, int age) {
                this.id = id;
                this.name = name;
                this.age = age;
        }

        @Override
        public String toString() {
                return "Persons{" +
                        "id=" + id +
                        ", name='" + name + '\'' +
                        ", age=" + age +
                        '}';
        }

        public Persons() {
        }

        public int getId() {
                return id;
        }

        public void setId(int id) {
                this.id = id;
        }

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public int getAge() {
                return age;
        }

        public void setAge(int age) {
                this.age = age;
        }
}
