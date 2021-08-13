package com.zdy.xiangxue.lesson02_annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import androidx.annotation.IntDef;

/**
 * 创建日期：2020-04-26 on 14:56
 * 描述：注解需要关键字  @interface
 *
 * 元注解： 注解自定义注解的注解
 * @Target：  没有指定的可以在java类的任何位置
 * ElementType.ANNOTATION_TYPE 可以应用于注解类型。
 * ElementType.CONSTRUCTOR 可以应用于构造函数。
 * ElementType.FIELD 可以应用于字段或属性。
 * ElementType.LOCAL_VARIABLE 可以应用于局部变量。
 * ElementType.METHOD 可以应用于方法级注解。
 * ElementType.PACKAGE 可以应用于包声明。
 * ElementType.PARAMETER 可以应用于方法的参数。
 * ElementType.TYPE 可以应用于类的任何元素。
 *
 * @Retention   保留这个注解到什么时候
 * RetentionPolicy.SOURCE - 标记的注解仅保留在源级别中，并被编译器忽略。
 * RetentionPolicy.CLASS - 标记的注解在编译时由编译器保留，但 Java 虚拟机(JVM)会忽略。
 * RetentionPolicy.RUNTIME - 标记的注解由 JVM 保留，因此运行时环境可以使用它。
 *
 * @IntDef 进行语法检查
 *
 * 注解使用场景：
 * 1：IDE语法检查
 * 2：APIT注解处理器
 * 注解能够设置类型元素(参数)，结合参数能实现更为丰富的场景，如:运行期权限判定等。
 *
 * 作者：zhudongyong
 */
@Target({ElementType.TYPE,ElementType.FIELD}) // 允许在类与类属性字段上标记该注解
@Retention(RetentionPolicy.SOURCE) //注解保留在源码中
public @interface Lance {

    String value();//无默认值

    int age() default 1;//有默认值

    //注意:在使用注解时，如果定义的注解中的类型元素无默认值，则必须进行传值。如：anotaiton中的使用

}
