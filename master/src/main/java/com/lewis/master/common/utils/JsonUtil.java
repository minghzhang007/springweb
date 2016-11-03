package com.lewis.master.common.utils;

import com.alibaba.fastjson.JSON;
import com.lewis.master.domain.Student;
import org.apache.commons.lang3.StringUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/3.
 */
public  class JsonUtil {
    private JsonUtil(){}

    public static String toString(Object obj){
        if (obj != null) {
            return JSON.toJSONString(obj);
        }
        return "";
    }

    public static <T> T toBean(String str,Class<T> clazzType){
        if (StringUtils.isNotEmpty(str) && clazzType != null) {
            return JSON.parseObject(str,clazzType);
        }
        return null;
    }

    public static void main(String[] args) {
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
    }
}
