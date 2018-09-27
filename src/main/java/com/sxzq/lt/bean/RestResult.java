package com.sxzq.lt.bean;

import org.apache.commons.lang.exception.ExceptionUtils;

import java.io.Serializable;

/**
 * 统一返回结果RestResult
 * Created by feizi on 2018/6/14.
 */
public class RestResult<T extends Object> implements Serializable {
    private static final long serialVersionUID = 1740108068781197216L;

    /**
     * 调用成功状态码
     */
    public static final int SUCCESS_CODE = 200;

    /**
     * 调用失败状态码
     */
    public static final int ERROR_CODE = 500;

    /**
     * 状态编码
     */
    private int code = SUCCESS_CODE;

    /**
     * 提示消息
     */
    private String message;

    /**
     * 返回数据（调用成功后返回）
     */
    private T data;

    /**
     * 异常信息封装
     */
    private String exception;

    public RestResult() {
    }

    public RestResult(T data) {
        this.data = data;
    }

    public RestResult(String message, Exception exception) {
        this.code = ERROR_CODE;
        this.message = message;
        this.exception = ExceptionUtils.getRootCauseMessage(exception);
    }

    public RestResult(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public RestResult(String message, T data){
        this.code = SUCCESS_CODE;
        this.message = message;
        this.data = data;
    }

    public RestResult(int code, String message, Exception exception) {
        this.code = code;
        this.message = message;
        this.exception = ExceptionUtils.getRootCauseMessage(exception);
    }


    public RestResult<T> error(String message, Exception exception){
        this.code = ERROR_CODE;
        this.message = message;
        this.data = null;
        //调用工具类获取异常信息
        this.exception = ExceptionUtils.getRootCauseMessage(exception);
        return this;
    }

    public RestResult<T> success(T data){
        this.code = SUCCESS_CODE;
        this.message = "";
        this.data = data;
        return this;
    }

    public static RestResult build(){
        return new RestResult();
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        if (null != exception && exception.length() > 512) {
            exception = exception.substring(0, 512);
        }
        this.exception = exception;
    }

    @Override
    public String toString() {
        return "RestResult{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", exception='" + exception + '\'' +
                '}';
    }
}
