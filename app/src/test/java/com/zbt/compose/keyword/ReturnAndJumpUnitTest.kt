package com.zbt.compose.keyword

import org.junit.Test

/**
 * Author: zbt
 * Date: 2024/4/11 下午4:55
 * Version: 1.0
 * Desc: ReturnAndJumpUnitTest
 */
class ReturnAndJumpUnitTest {

    @Test
    fun continue_test() {
        continue_loop()
    }

    private fun continue_loop() {
        // 跳过条件
        for (k in 1..5) {
            if (k == 2) continue
            println("k: $k")
        }

        /**
         * 跳过内层条件
         */
        for (m in 1..5) {
            for (n in 1..5) {
                if (n == 2) continue
                println("m: $m n: $n")
            }
        }

        /**
         * 根据条件跳过内层循环
         */
        loop@ for (i in 1..5) {
            for (j in 1..5) {
                if (j == 2) continue@loop
                println("i: $i j: $j")
            }
        }
    }

    @Test
    fun break_test() {
        break_loop()
    }

    private fun break_loop() {
        // 根据条件跳出循环
        for (k in 1..5) {
            if (k == 2) break
            println("k: $k")
        }

        /**
         * 根据条件跳出内层循环
         */
        for (m in 1..5) {
            for (n in 1..5) {
                if (n == 2) break
                println("m: $m n: $n")
            }
        }
        /**
         * 根据条件跳出内层循环
         */
        for (e in 1..5) {
            loop@ for (f in 1..5) {
                if (f == 2) break@loop
                println("e: $e f: $f")
            }
        }

        /**
         * 根据条件跳出外层循环
         */
        loop@ for (i in 1..5) {
            for (j in 1..5) {
                if (j == 2) break@loop
                println("i: $i j: $j")
            }
        }
    }

    @Test
    fun return_test() {
        return_fun(null)
        return_fun("zbt")
        return_forEach()
        return_forEach2()
        return_forEach3()
        return_forEach4()
        return_forEach5()
    }

    private fun return_fun(name: String?) {
        print("name start")
        val nameStr = name ?: return
        print("nameStr : $nameStr end")
    }

    /**
     * 跳出方法
     */
    fun return_forEach() {
        listOf(1, 2, 3, 4, 5).forEach {
            if (it == 3) return // non-local return directly to the caller of return_forEach()
            println(it)
        }
        println("this point is unreachable")
    }

    /**
     * 类似于 for（）{ continue }
     */
    fun return_forEach2() {
        listOf(1, 2, 3, 4, 5).forEach lit@{
            if (it == 3) return@lit // local return to the caller of the lambda - the forEach loop
            println(it)
        }
        println(" done with explicit label")
    }

    /**
     * 类似于 for（）{ continue }
     */
    fun return_forEach3() {
        listOf(1, 2, 3, 4, 5).forEach {
            if (it == 3) return@forEach // local return to the caller of the lambda - the forEach loop
            println(it)
        }
        println(" done with implicit label")
    }

    /**
     * 跳出匿名方法，不跳出外层方法
     */
    fun return_forEach4() {
        listOf(1, 2, 3, 4, 5).forEach(fun(value: Int) {
            if (value == 3) return  // local return to the caller of the anonymous function - the forEach loop
            println(value)
        })
        println(" done with anonymous function")
    }

    /**
     * 跳到外层作用域
     */
    fun return_forEach5() {
        run loop@{
            listOf(1, 2, 3, 4, 5).forEach {
                if (it == 3) return@loop // non-local return from the lambda passed to run
                println(it)
            }
        }
        println(" done with nested loop")
    }

    fun return_value() {
        a@ {
            // 这意味着“返回 1 到 @a”，而不是“返回一个标签标注的表达式 (@a 1)”。
            return@a 1
        }
    }
}