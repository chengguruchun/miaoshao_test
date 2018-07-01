package com.cheng.result;

/**
 * @Author :cheng
 * @Description:
 * @Date: created in 14:16 2018/6/27
 * @Reference:
 */
public class Result<T> {
    private int code;
    private String msg;
    private T data;

    private Result(T data) {
        this.code = 0;
        this.msg = "success";
        this.data = data;
    }

    private Result(CodeMsg cm) {
        if (cm == null) {
            return;
        }
        this.code = cm.getCode();
        this.msg = cm.getMsg();

    }
    public static <T> Result<T> success(T data) {

        return new Result<T>(data);
    }

    /**
     * 调用失败的时候返回
     */
    public static <T> Result<T> error(CodeMsg cm) {
        return new Result<T>(cm);
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }
}