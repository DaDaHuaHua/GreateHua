package com.example.song.kotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.example.song.R
import kotlinx.coroutines.experimental.*

/***
 * 演示Kotlin中的协程
 */
class KotlinThread : AppCompatActivity() {

    suspend fun test() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_thread)
        val job = launch(CommonPool) {
            test()
            println("协程  异步线程 :${Thread.currentThread().name}")
            println("协程  当前线程挂起10s")
            delay(10_000)
            runOnUiThread {
                println("协程 回到主线程 :${Thread.currentThread().name}")
            }
        }
        //取消 类似Interrupt
        job.cancel()

//        launch(CommonPool) {
//            job.join()
//
//        }
//        val deferred = async(CommonPool ,CoroutineStart.LAZY) {
//            "finish"
//        }
//        deferred.await()
      
    }
}
