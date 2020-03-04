package com.seven.gwc.modular.lawrecord.data.export.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zzl
 *
 */
@Target({ElementType.FIELD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldName {

    /**
     * 值表格列名
     */
    String value() default "";

    /**
     * 标记 一般用于父类 多个子类
     *      此属性为那个子类的显示 标记那个属性的SimpleClass
     */
    String flag() default "";

    /**
     * 是否存在 不写默认为true 存在
     */
    boolean exist() default true;

}
