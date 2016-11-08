package com.lewis.master.common.vo;

/**
 * Created by zhangminghua on 2016/11/8.
 */
public class ResponseVo {
    private boolean isSuccess;

    private String msg;

    private int resultCode;

    private Object data;

    public ResponseVo() {
    }

    public ResponseVo(Object data) {
        this.data = data;
    }

    public ResponseVo(boolean isSuccess, Object data) {
        this.isSuccess = isSuccess;
        this.data = data;
    }

    public ResponseVo(Object data, boolean isSuccess, String msg) {
        this.data = data;
        this.isSuccess = isSuccess;
        this.msg = msg;
    }

    public ResponseVo(boolean isSuccess, String msg, int resultCode, Object data) {
        this.isSuccess = isSuccess;
        this.msg = msg;
        this.resultCode = resultCode;
        this.data = data;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseVo{" +
                "isSuccess=" + isSuccess +
                ", msg='" + msg + '\'' +
                ", resultCode=" + resultCode +
                ", data=" + data +
                '}';
    }
}
