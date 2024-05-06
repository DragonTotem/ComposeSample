package com.zbt.compose.coroutine

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.junit.Test
import kotlin.concurrent.thread
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
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
        val continuation = suspend {  }.createCoroutine(object : Continuation<Int>{
            override val context: CoroutineContext
                get() = EmptyCoroutineContext

            override fun resumeWith(result: Result<Int>) {
            }

        })
        MainScope().launch {
            withContext(Dispatchers.Default){

            }
        }
    }

    suspend fun notSuspend() = suspendCoroutine<Int> {
        it.resume(1000)
    }
}