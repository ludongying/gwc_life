package com.seven.gwc.core.dictmap.factory;

import com.seven.gwc.core.exception.ServiceException;
import com.seven.gwc.core.factory.CacheFactory;
import com.seven.gwc.core.factory.ICacheFactory;
import com.seven.gwc.core.state.ErrorEnum;

import java.lang.reflect.Method;

/**
 * 字典字段的包装器(从ConstantFactory中获取包装值)
 *
 * @author GD
 */
public class DictFieldWarpperFactory {

    public static Object createFieldWarpper(Object parameter, String methodName) {
        ICacheFactory constantFactory = CacheFactory.me();
        try {
            Method method = ICacheFactory.class.getMethod(methodName, parameter.getClass());
            return method.invoke(constantFactory, parameter);
        } catch (Exception e) {
            try {
                Method method = ICacheFactory.class.getMethod(methodName, Long.class);
                return method.invoke(constantFactory, Long.parseLong(parameter.toString()));
            } catch (Exception e1) {
                throw new ServiceException(ErrorEnum.ERROR_ILLEGAL_PARAMS);
            }
        }
    }

}
