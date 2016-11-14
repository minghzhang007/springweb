package com.lewis.master.domain;

import java.util.List;

/**
 * Created by Administrator on 2016/11/3.
 */
public class Student {
    private Integer Id;

    private String name;

    private String birthday;

    private Integer gender;

    private List<String> hobbys;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public List<String> getHobbys() {
        return hobbys;
    }

    public void setHobbys(List<String> hobbys) {
        this.hobbys = hobbys;
    }
    // 额外的操作，方法
    public void call(String message) {
        System.out.println("丈夫的call：" + message);
    }

    public void look(){
        System.out.println("发现发廊");
    }

    public void playDOTA(){
        System.out.println("这个不用解释了吧~DOTA");
    }
    @Override
    public String toString() {
        return "Student{" +
                "Id=" + Id +
                ", name='" + name + '\'' +
                ", birthday='" + birthday + '\'' +
                ", gender=" + gender +
                ", hobbys=" + hobbys +
                '}';
    }
}
