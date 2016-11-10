package com.lewis.master.common.utils;

import org.apache.commons.collections.CollectionUtils;
import java.util.Collection;

/**
 * Created by zhangminghua on 2016/11/10.
 */
public class CollectionUtil extends CollectionUtils {

    public static <T> boolean isEmpty(Collection<T> collection) {
        return collection == null || collection.size() == 0;
    }


    public static <T> boolean isNotEmpty(Collection<T> collection) {
        return !isEmpty(collection);
    }
}
