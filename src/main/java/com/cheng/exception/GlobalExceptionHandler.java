package com.cheng.exception;

import com.cheng.result.CodeMsg;
import com.cheng.result.Result;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author :cheng
 * @Description:
 * @Date: created in 19:35 2018/7/1
 * @Reference:
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public Result<String> exceptionHandler(HttpServletRequest request, Exception e) {
          e.printStackTrace();
          if (e instanceof GlobalException) {
              GlobalException ex = (GlobalException) e;
              return Result.error(ex.getCm());

          } else if (e instanceof org.springframework.validation.BindException) {
                BindException ex = (BindException) e;
                List<ObjectError> errors = ex.getAllErrors();

                ObjectError error = errors.get(0);
                String msg = error.getDefaultMessage();
                return Result.error(CodeMsg.BIND_ERROR.fillArgs(msg));
            }else {
                return Result.error(CodeMsg.SERVER_ERROR);
            }
    }

}