package com.lewis.master.common.utils.scanner;

import com.lewis.master.common.utils.ListUtil;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/13.
 */
public class Test {

    public static void main(String[] args) {
        List<Class> classes = ScannerClassUtil.scanPackage("com.lewis.master", new ClassFilter() {
            public boolean accept(Class clazz) {
                return clazz.getName().endsWith("Util");
            }
        });
        if (ListUtil.isNotEmpty(classes)) {
            for (Class aClass : classes) {
                System.out.println(aClass.getName());
            }
        }

    }


}
