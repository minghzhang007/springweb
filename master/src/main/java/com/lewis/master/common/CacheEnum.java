package com.lewis.master.common;

/**
 * Created by Administrator on 2016/11/9.
 */
public enum CacheEnum {

    student("student",10000);

    //prefix of cache key
    private String keyPre;
    //expire time
    private int expire;

    CacheEnum(String keyPre, int expire) {
        this.keyPre = keyPre;
        this.expire = expire;
    }

    public String getKeyPre() {
        return keyPre;
    }

    public void setKeyPre(String keyPre) {
        this.keyPre = keyPre;
    }

    public int getExpire() {
        return expire;
    }

    public void setExpire(int expire) {
        this.expire = expire;
    }

    @Override
    public String toString() {
        return "CacheEnum{" +
                "keyPre='" + keyPre + '\'' +
                ", expire=" + expire +
                '}';
    }
}
