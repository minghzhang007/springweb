package com.lewis.master.common.utils;

import com.lewis.master.domain.Student;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangminghua on 2016/11/4.
 */
public class Test {
    public static void main(String[] args) {

    }

    public void testProtostuffUtil(){
        Student s = new Student();
        s.setId(100);
        s.setName("lewis");
        s.setBirthday("2016-12-12");
        s.setGender(1);
        List<String> hobbys = new ArrayList<String>();
        hobbys.add("singing");
        hobbys.add("dancing");
        s.setHobbys(hobbys);
        System.out.println(s.toString());
        List<Student> list = new ArrayList<Student>();
        list.add(s);
        byte[] serialize = ProtostuffUtil.serialize(s);
        Student deserialzier = ProtostuffUtil.deserialzier(serialize, Student.class);
        list.add(deserialzier);
        System.out.println(deserialzier.toString());

        String x = list.toString();
        System.out.println(x);
        byte[] bytes = ProtostuffUtil.serializeList(list);
        List<Student> students = ProtostuffUtil.deserializeList(bytes, Student.class);
        System.out.println(students.toString());
    }

    public void testJsonUtil(){
        Student s = new Student();
        s.setId(100);
        s.setName("lewis");
        s.setBirthday("2016-12-12");
        s.setGender(1);
        List<String> hobbys = new ArrayList<String>();
        hobbys.add("singing");
        hobbys.add("dancing");
        s.setHobbys(hobbys);
        System.out.println(s.toString());
        System.out.println("====================");
        String x = JsonUtil.toString(s);
        System.out.println(x);
        Student student = JsonUtil.toBean(x, Student.class);
        System.out.println(student.toString());

        Map<String,Student> map = new HashMap<String, Student>();
        map.put("s1",student);
        String s1 = JsonUtil.toString(map);
        System.out.println(s1);
        Map x1 = JsonUtil.toBean(s1, Map.class);
        System.out.println(x1);
    }
}
