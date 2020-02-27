package com.seven.gwc.core.base;

import com.seven.gwc.core.state.ErrorEnum;
import lombok.Data;

import java.io.Serializable;

@Data
public class BaseResult <T extends Object> implements Serializable {

    public boolean success; //调用是否成功
    private T content;      //结果
    private String message; //信息
    private Integer code;   //错误码

    public BaseResult() {
    }

    public BaseResult(ErrorEnum e) {
        this.success = false;
        this.message = e.getMessage();
        this.code = e.getCode();
    }

    public BaseResult(T content) {
        this.content = content;
        success = true;
    }

    public BaseResult(Integer code, String message) {
        this.message = message;
        this.code = code;
        success = false;
    }

    public BaseResult(Boolean success, String message) {
        this.message = message;
        this.success = success;
    }

    public BaseResult(Boolean success, Integer code, String message) {
        this.message = message;
        this.code = code;
        this.success = success;
    }

    public BaseResult(Boolean success, Integer code, String message, T content) {
        this.message = message;
        this.code = code;
        this.content = content;
        this.success = success;
    }

    public BaseResult<T> success(String message) {
        this.setSuccess(true);
        this.setMessage(message);
        this.setCode(200);
        return this;
    }

    public BaseResult<T> failure(ErrorEnum e) {
        this.setSuccess(false);
        this.setMessage(e.getMessage());
        this.setCode(e.getCode());
        return this;
    }

    public BaseResult<T> content(T t) {
        this.setCode(200);
        this.setContent(t);
        this.setMessage("操作成功!");
        this.setSuccess(true);
        return this;
    }

    public BaseResult<T> content(String message,T t) {
        this.setCode(200);
        this.setContent(t);
        this.setMessage(message);
        this.setSuccess(true);
        return this;
    }

    public BaseResult<T> code(Integer code) {
        this.setCode(code);
        return this;
    }

    public BaseResult<T> successContent(T t) {
        this.content(t);
        this.success = true;
        return this;
    }

}