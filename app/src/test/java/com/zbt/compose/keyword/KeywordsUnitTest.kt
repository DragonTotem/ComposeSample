package com.zbt.compose.keyword

import org.junit.Test

/**
 * Author: zbt
 * Date: 2024/4/11 上午11:20
 * Version: 1.0
 * Desc: KeywordsUnitTest
 */
class KeywordsUnitTest {

    @Test
    fun smart_isCorrect() {

        val car: Car = ElectricCar()
        if (car is ElectricCar) {
            car.run("132")
        }
    }
}