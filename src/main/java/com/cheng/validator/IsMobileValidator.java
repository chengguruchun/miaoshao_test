package com.cheng.validator;

import com.cheng.util.ValidatorUtil;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @Author :cheng
 * @Description:
 * @Date: created in 19:24 2018/7/1
 * @Reference:
 */
public class IsMobileValidator implements ConstraintValidator<IsMobile, String>{

    private boolean required = false;
    @Override
    public void initialize(IsMobile isMobile) {
        required = isMobile.required();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (required) {
            return ValidatorUtil.isMobile(s);
        }else {
            if (StringUtils.isEmpty(s)) {
                return true;
            }else {
                return ValidatorUtil.isMobile(s);
            }
        }

    }
}