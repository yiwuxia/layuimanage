package com.jin.domin;

import java.util.List;

/**
 * Created by wuxia on 2018/5/30.
 */
public class UploadResult<T> {
    private Integer code;
    private String msg;
    private List<T> data;

    public static UploadResult returnSuccessResult(){
        UploadResult result= new UploadResult();
        result.setCode(0);
        result.setMsg("");
        return  result;
    }
    public static UploadResult returnFailureResult(){
        UploadResult result= new UploadResult();
        result.setCode(1);
        result.setMsg("上传失败");
        result.setData(null);
        return  result;
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
