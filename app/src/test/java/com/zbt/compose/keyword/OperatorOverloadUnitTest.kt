package com.zbt.compose.keyword

import org.junit.Test

/**
 * Author: zbt
 * Date: 2024/4/11 下午6:32
 * Version: 1.0
 * Desc: OperatorOverloadUnitTest
 * 操作符重载：
 * 1、操作符 + - * / 等等，见关键字
 * 2、重载
 */
class OperatorOverloadUnitTest {

    @Test
    fun test() {
        val str1 = "1234"
        val str2 = "5678"
        println(str1 + str2)
    }

}

operator  fun  String.plus(s: String) = (this.length + s.length).toString()
