package com.zbt.compose.keyword

import org.junit.Test

/**
 * Author: zbt
 * Date: 2024/4/11 下午6:32
 * Version: 1.0
 * Desc: OperatorOverloadUnitTest
 * 操作符重载：
 * 1、操作符 + - * / 等等，见关键字
 * 2、重载：编译器根据调用参数调用不同的方法
 * https://kotlinlang.org/docs/operator-overloading.html#comparison-operators
 */
class OperatorOverloadUnitTest {

    @Test
    fun test() {
        val str1 = "1234"
        val str2 = "5678"
        println(str1.plus(str2))
        println(str1 + str2)
        // 上述两个打印的结果都是 12345678 而不是8

        println(Operator("123") + Operator("321"))
        println(Operator("123").plus(Operator("123")))
    }

    /**
     * Extension is shadowed by a member: public final operator fun plus(other: Any?): String
     * 扩展方法被成员方法屏蔽，因为String.kt 方法中有上述扩展方法，调用的是无论是 str1 + str2 都是调用字符串的拼接。
     */
    operator fun String.plus(s: String): Int {
        return this.length + s.length
    }

    var operator: OperatorGenerator<*, *>?= null
    @Test
    fun test_operator() {
        val operatorGenerator = OperatorObject("第一个")
        val operatorGenerator1 = OperatorObject("第er个")
        val operatorGenerator2 = OperatorObject("第三个")

        operatorGenerator + operatorGenerator1
        operatorGenerator1 + operatorGenerator2

        operator = operatorGenerator2
        do {
            operator?.let {
                println(it.produce())
                operator = it.lastOperator
            }?:run {
                println("operator is null")
            }

        } while (operator != null)
    }
}

class Operator(val name: String) {
    operator fun plus(operator: Operator): String = name + operator.name
}

interface IOperator<R> {
    fun produce(): R
}

abstract class OperatorGenerator<T, R> : IOperator<R> {
    var lastOperator: OperatorGenerator<*, T>? = null
}

class OperatorObject(private val name: String) : OperatorGenerator<String, String>() {
    override fun produce(): String {
        return name
    }
}

//// Assignments are not expressions, and only expressions are allowed in this context
//// 赋值不是表达式，this上下文中只允许表达式中使用
//operator fun <T, R> OperatorGenerator<*, T>.plus(nextOperator: OperatorGenerator<T, R>) =
//    nextOperator.lastOperator = this

operator fun <T, R> OperatorGenerator<*, T>.plus(nextOperator: OperatorGenerator<T, R>): OperatorGenerator<T, R> {
    nextOperator.lastOperator = this
    return nextOperator
}

fun <T, R> OperatorGenerator<*, T>.plus1(nextOperator: OperatorGenerator<T, R>) =
    nextOperator.apply {
//        // Variable 'lastOperator' is assigned to itself
//        lastOperator = this.lastOperator
        lastOperator = this@plus1.lastOperator as OperatorGenerator<*, T>
    }

fun <T, R> OperatorGenerator<*, T>.plus2(nextOperator: OperatorGenerator<T, R>) =
    nextOperator.also {
        it.lastOperator = this
    }