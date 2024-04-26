package com.zbt.compose

import org.junit.Test

import org.junit.Assert.*
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.roundToInt

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun ceil_test() {
        println("ceil -> 1.1 : ${ceil(1.1)}") // 2.0
        println("ceil -> 1.6 : ${ceil(1.6)}") // 2.0
        println("floor -> 1.1 : ${floor(1.1)}") // 1.0
        println("floor -> 1.6 : ${floor(1.6)}") // 1.0
        println("float -> 1.1 : ${1.1f.toInt()}") // 1
        println("float -> 1.6 : ${1.6f.toInt()}") // 1
        println("roundToInt -> 1.1 : ${1.1f.roundToInt()}") // 1
        println("roundToInt -> 1.6 : ${1.6f.roundToInt()}") // 2
    }
    /**
     *       StringBuilder var10000 = (new StringBuilder()).append("ceil -> 1.1 : ");
     *       double var1 = 1.1;
     *       String var3 = var10000.append(Math.ceil(var1)).toString();
     *       System.out.println(var3);
     *       var10000 = (new StringBuilder()).append("ceil -> 1.6 : ");
     *       var1 = 1.6;
     *       var3 = var10000.append(Math.ceil(var1)).toString();
     *       System.out.println(var3);
     *
     *       var10000 = (new StringBuilder()).append("floor -> 1.1 : ");
     *       var1 = 1.1;
     *       var3 = var10000.append(Math.floor(var1)).toString();
     *       System.out.println(var3);
     *       var10000 = (new StringBuilder()).append("floor -> 1.6 : ");
     *       var1 = 1.6;
     *       var3 = var10000.append(Math.floor(var1)).toString();
     *       System.out.println(var3);
     *
     *       var3 = "float -> 1.1 : 1";
     *       System.out.println(var3);
     *       var3 = "float -> 1.6 : 1";
     *       System.out.println(var3);
     *
     *       var3 = "roundToInt -> 1.1 : " + MathKt.roundToInt(1.1F);
     *       System.out.println(var3);
     *       var3 = "roundToInt -> 1.6 : " + MathKt.roundToInt(1.6F);
     *       System.out.println(var3);
     */
}