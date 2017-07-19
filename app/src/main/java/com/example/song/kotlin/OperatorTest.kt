package com.example.song.kotlin

import android.util.Log

/**
 * Created by zz on 2017/6/28.
 *
 * 常用操作符 let apply  run with
 *
 */

open class OperatorTest {

    companion object {
        val TAG: String = "KotlinTAG"
    }

    fun main() {
//        main1()
//        main2()
//        main3()
//          main4()
    }

    /***
     *  let
     *
     *  首先let()的定义是这样的，默认当前这个对象作为闭包的it参数，
     *  返回值是函数里面最后一行，或者指定return
     *
     *  public inline fun <T, R> T.let(block: (T) -> R): R = block(this)
     */

    fun main1(): Unit {
        val list: MutableList<String> = mutableListOf("A", "B", "C")
        val change: Any
        change = list.let {
            it.add("D")
            it.add("E")
//             it.size
        }
        Log.i(TAG, "list = $list")
        Log.i(TAG, "change = $change")
    }

    /***
     * apply
     *
     * apply函数是这样的，调用某对象的apply函数，在函数范围内，可以任意调用该对象的任意方法，并返回该对象,注意apply返回当前自己的对象。
     * public inline fun <T> T.apply(block: T.() -> Unit): T { block(); return this
     */

    fun main2(){
        val list: MutableList<String> = mutableListOf("A", "B", "C")
        val change: MutableList<String>
        change = list.apply {
            add("D")
            add("E")
            this.add("F")
        }

        Log.i(TAG, "list = $list")
        Log.i(TAG, "change = $change")
    }

    /***
     * run
     * run函数和apply函数很像，只不过run函数是使用最后一行的返回，apply返回当前自己的对象。
     * fun <T, R> T.run(f: T.() -> R): R = f()
     */

    fun main3() {
        val list: MutableList<String> = mutableListOf("A", "B", "C")
        val change: Int
        change = list.run {
            add("D")
            add("E")
            add("F")
            size
        }
        Log.i(TAG, "list = $list")
        Log.i(TAG, "change = $change")
    }

    /***
     * with
     * with函数是一个单独的函数，并不是Kotlin中的extension，
     * 所以调用方式有点不一样，返回是最后一行，然后可以直接调用对象的方法，感觉像是let和apply的结合。
     *
     * fun <T, R> with(receiver: T, f: T.() -> R): R = receiver.f()
     */
    fun main4() {
        val list: MutableList<String> = mutableListOf("A", "B", "C")
        val change: Any
        change = with(list) {
            add("D")
            add("E")
            add("F")
            size
        }
        Log.i(TAG, "list = $list")
        Log.i(TAG, "change = $change")
    }

}