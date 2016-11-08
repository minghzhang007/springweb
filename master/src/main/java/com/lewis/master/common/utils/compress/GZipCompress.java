package com.lewis.master.common.utils.compress;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Created by zhangminghua on 2016/11/8.
 * GZIP压缩、解压缩实现类
 */
public class GZipCompress implements IDataCompress {

    public static final int BUFFER_SIZE = 1024;

    public static final String EXT = ".gz";

    public byte[] compress(byte[] data) {
        byte[] outpupt = null;
        ByteArrayInputStream bais = null;
        ByteArrayOutputStream baos = null;
        try {
            bais = new ByteArrayInputStream(data);
            baos = new ByteArrayOutputStream();
            compress(bais, baos);
            outpupt = baos.toByteArray();
            baos.flush();
        } catch (Exception e) {

        } finally {
            close(bais);
            close(baos);
        }
        return outpupt;
    }

    private void compress(InputStream is, OutputStream os) throws Exception {
        GZIPOutputStream gos = new GZIPOutputStream(os);
        byte[] data = new byte[BUFFER_SIZE];
        int count;
        while ((count = is.read(data, 0, BUFFER_SIZE)) != -1) {
            gos.write(data, 0, count);
        }
        gos.finish();
        gos.flush();
        gos.close();
    }

    public String compress(File file) throws Exception {
        return compress(file, false);
    }

    public String compress(File file, boolean delete) throws Exception {
        FileInputStream fis = new FileInputStream(file);
        String compressFilePath = file.getPath() + EXT;
        FileOutputStream fos = new FileOutputStream(compressFilePath);
        compress(fis, fos);
        fis.close();
        fos.flush();
        fos.close();
        if (delete) {
            file.delete();
        }
        return compressFilePath;
    }

    public String compress(String path) throws Exception {
        return compress(path, false);
    }

    public String compress(String path, boolean delete) throws Exception {
        File file = new File(path);
        return compress(file, delete);
    }


    public byte[] decompress(byte[] data) {
        byte[] output = null;
        ByteArrayOutputStream baos = null;
        ByteArrayInputStream bais = null;
        try {
            bais = new ByteArrayInputStream(data);
            baos = new ByteArrayOutputStream();
            decompress(bais,baos);
            output = baos.toByteArray();
            baos.flush();
        } catch (Exception e) {

        } finally {
            close(bais);
            close(baos);
        }
        return output;
    }

    private void decompress(InputStream is, OutputStream os) throws Exception {
        GZIPInputStream gis = new GZIPInputStream(is);
        byte[] data = new byte[BUFFER_SIZE];
        int readCount;
        while ((readCount = gis.read(data, 0, BUFFER_SIZE)) != -1) {
            os.write(data, 0, readCount);
        }
        gis.close();
    }

    public String decompress(String path) throws Exception {
        return decompress(path,false);
    }

    public String decompress(String path, boolean delete) throws Exception {
        File file = new File(path);
        return decompress(file,delete);
    }

    public String decompress(File file) throws Exception {
        return decompress(file,false);
    }

    public String decompress(File file, boolean delete) throws Exception {
        FileInputStream fis = new FileInputStream(file);
        String decompressFilePath = file.getPath().replace(EXT, "");
        FileOutputStream fos = new FileOutputStream(decompressFilePath);
        decompress(fis,fos);
        fis.close();
        fos.flush();
        fos.close();
        if (delete) {
            file.delete();
        }
        return decompressFilePath;
    }

    private void close(InputStream is) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void close(OutputStream os) {
        if (os != null) {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
