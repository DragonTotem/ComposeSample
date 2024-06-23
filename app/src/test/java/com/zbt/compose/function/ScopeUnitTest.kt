package com.zbt.compose.function

import org.junit.Test

class ScopeUnitTest {
    @Test
    fun scopeTest() {
        funA {
            funB {
                visitA()
                visitB()
            }
        }
    }
}

class AScope {
    fun visitA() {
        println("A")
    }
}

class BScope{
    fun visitB() {
        println("B")
    }
}

fun funA(scope: AScope.() -> Unit) {
    scope(AScope())
}

fun funB(scope: BScope.() -> Unit) {
    scope(BScope())
}