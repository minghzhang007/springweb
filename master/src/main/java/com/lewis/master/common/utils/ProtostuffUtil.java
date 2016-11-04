package com.lewis.master.common.utils;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import org.apache.commons.lang3.ArrayUtils;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangminghua on 2016/11/4.
 */
public final class ProtostuffUtil {

    private ProtostuffUtil() {
    }

    public static <T> byte[] serialize(T obj) {
        if (obj != null) {
            LinkedBuffer buffer = null;
            try {
                buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
                Schema<T> schema = RuntimeSchema.getSchema((Class<T>) obj.getClass());
                byte[] bytes = ProtostuffIOUtil.toByteArray(obj, schema, buffer);
                return bytes;
            } catch (Exception e) {

            } finally {
                if (buffer != null) {
                    buffer.clear();
                }
            }
        }
        return new byte[0];
    }

    public static <T> T deserialzier(byte[] valueBytes, Class<T> clazzType) {
        T t = null;
        if (ArrayUtil.isNotEmpty(valueBytes) && clazzType != null) {
            boolean accessible = false;
            Constructor<T> constructor = null;
            try {
                constructor = clazzType.getConstructor();
                accessible = constructor.isAccessible();
                if (!accessible) {
                    constructor.setAccessible(true);
                }
                t = constructor.newInstance();
                Schema<T> schema = RuntimeSchema.getSchema(clazzType);
                ProtostuffIOUtil.mergeFrom(valueBytes, t, schema);
            } catch (Exception e) {

            } finally {
                if (constructor != null) {
                    constructor.setAccessible(accessible);
                }
            }
        }
        return t;
    }

    public static <T> byte[] serializeList(List<T> list) {
        LinkedBuffer buffer = null;
        try {
            if (ListUtil.isNotEmpty(list)) {
                buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                Schema<T> schema = RuntimeSchema.getSchema((Class<T>) list.get(0).getClass());
                ProtostuffIOUtil.writeListTo(os, list, schema, buffer);
                byte[] retBytes = os.toByteArray();
                return retBytes;
            }
        } catch (Exception e) {

        } finally {
            if (buffer != null) {
                buffer.clear();
            }
        }
        return new byte[0];
    }

    public static <T> List<T> deserializeList(byte[] bytes, Class<T> clazz) {
        try {
            if (ArrayUtils.isNotEmpty(bytes) && clazz != null) {
                Schema<T> schema = RuntimeSchema.getSchema(clazz);
                List<T> retList = ProtostuffIOUtil.parseListFrom(new ByteArrayInputStream(bytes), schema);
                return retList;
            }
        } catch (Exception e) {

        }
        return new ArrayList<T>(0);
    }
}
