package com.lewis.master.common.utils.compress;

/**
 * Created by zhangminghua on 2016/11/8.
 */
public class Test {
    public static void main(String[] args) {
        GZipCompress gZipCompress = new GZipCompress();
        try {
            String compress = gZipCompress.decompress("D:\\java\\technology_manual\\multiThread\\compress\\Java 7 Concurrency Cookbook.pdf.gz");
            System.out.println(compress);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
