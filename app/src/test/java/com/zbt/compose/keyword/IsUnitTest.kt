package com.zbt.compose.keyword

import org.junit.Test

/**
 * Author: zbt
 * Date: 2024/4/11 下午3:21
 * Version: 1.0
 * Desc: IsUnitTest https://kotlinlang.org/docs/typecasts.html#smart-casts
 */
class IsUnitTest {

    @Test
    fun test() {
        is_SmartCast("123")
        is_SmartCast(Unit)
    }

    var test : Any = "123"

    /**
     * 检测一个值具有指定类型，及智能转换
     */
    private fun is_SmartCast(obj: Any) {
        if (obj is String) {
            println(obj.length)
        }

        if (obj !is String) { // Same as !(obj is String)
            println("Not a String")
        } else {
            print(obj.length)
        }
    }

    /**
     * 在 when 表达式中用于上述目的
     */
    private fun is_whenExpression(any: Any) {
        when (any) {
            is Int -> print(any + 1)
            is String -> print(any.length + 1)
            is IntArray -> print(any.sum())
        }
    }

    private fun is_errorSmartCast() {
        if (test is String) {
//            print(test.length) // 编译器报错
        }
    }

}