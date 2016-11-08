package com.lewis.master.common.utils.compress;

import java.io.File;

/**
 * Created by zhangminghua on 2016/11/8.
 */
 interface IDataCompress {

    /**
     * 数据压缩
     * @param data
     * @return
     */
     byte[] compress(byte[] data);

    /**
     * 数据解压缩
     * @param data
     * @return
     */
     byte[] decompress(byte[] data);

    /**
     * 文件压缩
     * @param file
     * @throws Exception
     */
     String compress(File file) throws Exception;

    /**
     * 文件压缩
     * @param file
     * @param delete
     *            是否删除原始文件
     * @throws Exception
     */
     String compress(File file, boolean delete) throws Exception;

    /**
     * 文件压缩
     * @param path
     * @throws Exception
     */
     String compress(String path) throws Exception;

    /**
     * 文件压缩
     * @param path
     * @param delete
     *            是否删除原始文件
     * @throws Exception
     */
     String compress(String path, boolean delete) throws Exception;

    /**
     * 文件解压缩
     * @param path
     * @throws Exception
     */
     String decompress(String path) throws Exception;

    /**
     * 文件解压缩
     * @param path
     * @param delete
     *            是否删除原始文件
     * @throws Exception
     */
     String decompress(String path, boolean delete) throws Exception;

    /**
     * 文件解压缩
     * @param file
     * @throws Exception
     */
     String decompress(File file) throws Exception;

    /**
     * 文件解压缩
     * @param file
     * @param delete
     *            是否删除原始文件
     * @throws Exception
     */
     String decompress(File file, boolean delete) throws Exception;
}
