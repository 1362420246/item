package com.qbk.entity.valid;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.qbk.result.ResultStatus;
import com.qbk.exception.BasicException;
import org.apache.commons.collections.MapUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.*;

/**
 * 校验器
 */
public class BeanValidator {

    /**
     * 校验工厂
     */
    private static ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();

    /**
     * 校验，抛出异常
     */
    public static void check(Object obj)throws BasicException {
        Map<String, String> result = BeanValidator.validateObject(obj);
        if(!MapUtils.isEmpty(result)){
            result.forEach((k,v) ->{
                throw new BasicException(ResultStatus.FAIL.getErrorCode(),v);
            });
        }
    }

    /**
     * 通用的校验
     */
    public static Map<String,String>validateObject(Object obj){
        if(obj instanceof Collection){
            return BeanValidator.validateList((Collection)obj);
        }else{
            return BeanValidator.validate(obj);
        }
    }

    /**
     * 校验集合
     */
    public static Map<String,String> validateList(Collection<?>collection){
        //校验collection是否为空
        Preconditions.checkNotNull(collection);
        Iterator<?> iterator = collection.iterator();
        Map<String,String> errors;
        do{
            if(!iterator.hasNext()){
                return Collections.emptyMap();
            }else{
                Object next = iterator.next();
                errors = validate(next);
            }
        }while(errors.isEmpty());
        return errors;
    }

    /**
     * 校验方法
     * @param t 校验对象
     * @param groups 参数类型
     * @return 返回错误字段和信息
     */
    public static <T>Map<String,String> validate(T t,Class... groups){
        Validator validator = validatorFactory.getValidator();
        Set result = validator.validate(t, groups);
        if(result.isEmpty()){
            return Collections.emptyMap();
        }else{
            LinkedHashMap<String,String> errors = Maps.newLinkedHashMap();
            for (Object aResult : result) {
                ConstraintViolation violation = (ConstraintViolation) aResult;
                errors.put(violation.getPropertyPath().toString(), violation.getMessage());
            }
            return errors;
        }
    }
}
