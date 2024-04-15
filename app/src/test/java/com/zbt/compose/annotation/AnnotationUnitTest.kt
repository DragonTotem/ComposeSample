package com.zbt.compose.annotation

/**
 * Author: quantao.zhu
 * Date: 2024/4/15 下午2:41
 * Version: 1.0
 * Desc: AnnotationUnitTest
 */
class AnnotationUnitTest {
    public enum class AnnotationTarget {
        CLASS, //表示作用对象有类、接口、object对象表达式、注解类
        ANNOTATION_CLASS,//表示作用对象只有注解类
        TYPE_PARAMETER,//表示作用对象是泛型类型参数(暂时还不支持)
        PROPERTY,//表示作用对象是属性
        FIELD,//表示作用对象是字段，包括属性的幕后字段
        LOCAL_VARIABLE,//表示作用对象是局部变量
        VALUE_PARAMETER,//表示作用对象是函数或构造函数的参数
        CONSTRUCTOR,//表示作用对象是构造函数，主构造函数或次构造函数
        FUNCTION,//表示作用对象是函数，不包括构造函数
        PROPERTY_GETTER,//表示作用对象是属性的getter函数
        PROPERTY_SETTER,//表示作用对象是属性的setter函数
        TYPE,//表示作用对象是一个类型，比如类、接口、枚举
        EXPRESSION,//表示作用对象是一个表达式
        FILE,//表示作用对象是一个File
        @SinceKotlin("1.1")
        TYPEALIAS//表示作用对象是一个类型别名
    }
}