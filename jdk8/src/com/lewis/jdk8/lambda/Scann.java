package com.lewis.jdk8.lambda;

import java.io.File;
import java.io.FileFilter;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by zhangminghua on 2016/11/14.
 */
public class Scann {

    public static void main(String[] args) {
        String path = "D:\\java\\github\\springweb\\master\\src\\main\\java\\com\\lewis\\master\\common\\utils\\";
        File file = new File(path);
        //List<File> childFiles = getChildDirectory(file);
        List<File> childFiles = getChildFiles(file,null);
        childFiles.stream().filter(file1 -> file1.getName().endsWith("Util.java")).forEach(System.out::println);
    }


    public static List<File> getChildFiles(File rootFile, FileFilter fileFilter) {
        List<File> retList = new LinkedList<>();
        if (rootFile.isDirectory()) {
            File[] files = rootFile.listFiles(fileFilter);
            if (files != null && files.length > 0) {
                for (File file : files) {
                    retList.addAll(getChildFiles(file,fileFilter));
                }
            }
        }else{
            retList.add(rootFile);
        }
        return retList;
    }

    /**
     * 获取子目录
     *
     * @param rootFile
     * @return
     */
    public static List<File> getChildDirectory(File rootFile) {
        List<File> retList = new LinkedList<>();
        if (rootFile.isDirectory()) {
            retList.add(rootFile);
            File[] files = rootFile.listFiles(file -> file.isDirectory());
            if (files != null && files.length > 0) {
                for (File file : files) {
                    retList.addAll(getChildDirectory(file));
                }
            }
        }
        return retList;
    }
}
