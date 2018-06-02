package com.jin.domin;

import java.util.List;

/**
 * Created by wuxia on 2018/5/28.
 */
public class Result<T> {
    private boolean success;
    private String message;
    private String code;
    private int count;
    private List<T> data;

    public static  Result failureResult(){
        Result result=new Result();
        result.setSuccess(false);
        result.setMessage("失败");
        result.setCode("1");
        result.setData(null);
        result.setCount(0);
        return  result;
    }
    public static  Result successResult(){
        Result result=new Result();
        result.setSuccess(true);
        result.setMessage("成功");
        result.setCode("0");
        return  result;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
