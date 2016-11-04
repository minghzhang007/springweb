package com.lewis.master.common.utils;

import org.apache.commons.collections.ListUtils;
import java.util.List;

/**
 * Created by zhangminghua on 2016/11/4.
 */
public class ListUtil extends ListUtils {

    private ListUtil(){}


    public  static <T> boolean  isEmpty(List<T> list){
        return list == null || list.size() == 0;
    }

    public  static <T> boolean  isNotEmpty(List<T> list){
        return !isEmpty(list);
    }

}
