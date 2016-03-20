package com.dingup.onlinetest.bean;

import java.io.Serializable;

/**
 * Created by yanggavin on 16/3/19.
 */
public class HttpResContent<T> implements Serializable {
    private static final long serialVersionUID = -5764986016352461610L;
    private Integer code;
    private String message;
    private T result;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public void fillData(Integer code, String message, T result){
        this.code = code;
        this.message = message;
        this.result = result;
    }
}
