package com.stylefeng.guns.modular.system.utils;

import org.apache.commons.lang3.StringEscapeUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author gejinbiao@ucfgroup.com
 * @Title
 * @Description:
 * @Company: ucfgroup.com
 * @Created 2018-08-14
 */
public class Utils {


    public static void main(String[] args) {
        Utils tester = new Utils();

        List<Student> students = new ArrayList<>();
        Student student = new Student();
        student.setName("1");
        student.setCity("beijing");
        Student student1 = new Student();
        student1.setName("2");
        student1.setCity("beijing");
        students.add(student);
        students.add(student1);

        List<String> proNames = Arrays.asList(new String[]{"Ni","Hao","Lambda"});

        Optional<String> any = proNames.stream().findAny();
        any.isPresent();
        System.out.println(any.get());

    }


    static class Student {
        private String name;
        private String city;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }
    }

}


