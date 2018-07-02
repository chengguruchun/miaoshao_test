package com.cheng.exception;

import com.cheng.result.CodeMsg;

/**
 * @Author :cheng
 * @Description:
 * @Date: created in 19:44 2018/7/1
 * @Reference:
 */
public class GlobalException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    private CodeMsg cm;

    public GlobalException(CodeMsg cm) {
        super(cm.toString());
        this.cm = cm;
    }

    public CodeMsg getCm() {
        return cm;
    }

}
