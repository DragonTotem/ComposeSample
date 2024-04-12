package com.zbt.compose.function

import org.junit.Test

/**
 * Author: quantao.zhu
 * Date: 2024/4/12 下午6:07
 * Version: 1.0
 * Desc: FuntionUnitTest
 */
class FunctionUnitTest {

    @Test
    fun test() {
        val args = arrayOf("1", "2", "3")
        // 传入包级别函数
        args.forEach(::println) //函数引用
        // 传入类成员函数
        args.filter(String::isNotEmpty)
        // 传入实例函数
        val t = QuoteTest()
        args.forEach(t::testName)
    }
}

/**
 * quote 示例
 * 函数引用：引用的函数名前加上 ::
 *
 * 有以下几种类型：
 * 类成员方法引用：类名::成员方法名
 * 扩展函数引用：类名::扩展函数名
 * 实例函数引用：实例名::成员方法名
 * 包级别函数引用：::函数名
 */
fun main(args: Array<String>) {
    // 传入包级别函数
    args.forEach(::println) //函数引用
    // 传入类成员函数
    args.filter(String::isNotEmpty)
    // 传入实例函数
    val t = QuoteTest()
    args.forEach(t::testName)
}

fun println(message: Any?) {
    System.out.println(message)
}

inline fun <T> Array<out T>.forEach(action: (T) -> Unit): Unit {
    for (element in this) action(element)
}

/**
 * 传入类成员函数
 */
inline fun <T> Array<out T>.filter(predicate: (T) -> Boolean): List<T> {
    return filterTo(ArrayList<T>(), predicate)
}

fun CharSequence.isNotEmpty(): Boolean = length > 0

class QuoteTest {
    fun testName(name: String) {
        println(name)
    }
}

/**
 * 闭包
 * 函数运行的环境
 * 持有函数运行状态
 * 函数内部可以定义函数/类
 */

fun add(x: Int): (Int) -> Int {
    return fun(y: Int): Int {
        return x + y
    }
}

fun main() {
    val add2 = add(2)
    println(add2(10))
}

/**
 * Higher-order functions lambda
 */
class HigherOrderFunctionUnitTest {

    private val add5 = { i: Int -> i + 5 }
    private val multiplyBy2 = { i: Int -> i * 2 }
    private val sum = { q: Int, w: Int -> q + w }

    //关键点1：
    private infix fun <P1, P2, R> Function1<P1, P2>.andThen(function: Function1<P2, R>): Function1<P1, R> {
        return fun(p1: P1): R {
            return function.invoke(this.invoke(p1))
        }
    }

    // 关键点2：
    private infix fun <P1, P2, R> Function1<P2, R>.compose(function: Function1<P1, P2>): Function1<P1, R> {
        return fun(p1: P1): R {
            return this.invoke(function.invoke(p1))
        }
    }

    //关键点3：
    private fun <P1, P2, P3, R> Function2<P2, P3, R>.toAllSum(
        function: Function1<P1, P2>,
        function1: Function1<P2, P3>
    ): Function2<P1, P2, R> {
        return fun(p1: P1, p2: P2): R {
            return this.invoke(function.invoke(p1), function1.invoke(p2))
        }
    }

    @Test
    fun main() {
        // 关键点3：
        val add5AndMulti2 = add5 andThen multiplyBy2
        // 关键点4：
        val add5ComposeMulti2 = add5 compose multiplyBy2
        //关键点5：
        val sum = sum.toAllSum(add5, multiplyBy2)

        println(add5AndMulti2(10))
        println(add5ComposeMulti2(10))
        println(sum(10, 10))
    }
}