package com.lewis.master.common.utils;

import com.lewis.master.common.core.Preconditon;

import java.util.*;

/**
 * Created by zhangminghua on 2016/11/10.
 */
public class SearchUtil {

    private SearchUtil(){}

    public static  <T> T searchOne(Collection<T> collection, Preconditon<T> preconditon){
        if (CollectionUtil.isNotEmpty(collection) && preconditon != null) {
            for (T t : collection) {
                if (preconditon.evaluate(t)) {
                    return t;
                }
            }
        }
        return null;
    }

    public static <T> List<T> searchList(Collection<T> collection,Preconditon<T> preconditon){
        List<T> retList = new LinkedList<T>();
        if (CollectionUtil.isNotEmpty(collection) && preconditon != null) {
            for (T t : collection) {
                if (preconditon.evaluate(t)) {
                    retList.add(t);
                }
            }
        }
        return retList;
    }

    public static <T> Set<T> searchSet(Collection<T> collection, Preconditon<T> preconditon){
        Set<T> retSet = new HashSet<T>();
        if (CollectionUtil.isNotEmpty(collection) && preconditon != null) {
            for (T t : collection) {
                if (preconditon.evaluate(t)) {
                    retSet.add(t);
                }
            }
        }
        return retSet;
    }
}
