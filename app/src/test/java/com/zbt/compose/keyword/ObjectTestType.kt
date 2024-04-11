package com.zbt.compose.keyword

/**
 * Author: zbt
 * Date: 2024/4/11 上午11:20
 * Version: 1.0
 * Desc: ObjectTestType
 */

interface Vehiche {
    fun run(name: Any)
}

abstract class Car : Vehiche {
    override fun run(name: Any) {
        if (name is String)
            println(name.length)
    }
}

class ElectricCar : Car() {

}

class InternalCombustionEngineCar : Car() {

}