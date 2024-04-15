package com.zbt.compose.coroutine

import org.junit.Test
import kotlin.concurrent.thread
import kotlin.coroutines.createCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class CoroutineSimpleUnit {

    suspend fun suspendFunc01() {

    }

    suspend fun suspendFunc02(a:String, b: String) = suspendCoroutine<Int> { continuation ->
        thread {
            continuation.resumeWith(Result.success(5))
        }
    }

    @Test
    fun func() {
        suspend {  }.createCoroutine()
    }
}