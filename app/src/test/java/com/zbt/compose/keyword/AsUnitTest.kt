package com.zbt.compose.keyword

import org.junit.Test

/**
 * Author: zbt
 * Date: 2024/4/11 下午4:02
 * Version: 1.0
 * Desc: AsUnitTest is是安全的类型转换，as则是不安全的类型 unsafe
 */
class AsUnitTest {

    @Test
    fun test() {
        as_cast1("123")
//        as_cast2(Unit) // class kotlin.Unit cannot be cast to class java.lang.String， 运行时出错
        as_cast2(null) // 2和3等同
        as_cast3(null)
    }

    private fun as_cast1(y: Any) {
        val x: String = y as String
        println("x: $x")
    }

    private fun as_cast2(y: Any?) {
        val x: String? = y as String?
        println("x: $x")
    }

    private fun as_cast3(y: Any?) {
        val x: String? = y as? String
        println("x: $x")
    }
}