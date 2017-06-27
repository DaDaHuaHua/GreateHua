package com.example.song.kotlin

import android.util.Log

/**
 * Created by zz on 2017/6/27.
 */

class CollectionTest {

    val TAG = "KotlinTAG"


    fun main() {
        // testIs()
        //testAs(this)
        // testIn(2)
        //testDownTo()
        //testStep()
        testString()
    }


    /***
     * is 操作符
     */
    fun testIs() {
        val str: Any
        str = "aaa"
        Log.i(TAG, "str is String " + (str is String))
    }


    /**
     * as 操作符
     */
    fun testAs(obj: Any) {
        obj as? String
        Log.i(TAG, "" + obj)
    }

    /**
     * 范围操作符 in
     */
    fun testIn(i: Int) {
        if (i in 0..10) {
            Log.i(TAG, " i in 0..10")
        }
    }

    /**
     * downTo
     */
    fun testDownTo() {
        for (i in 4 downTo 1) {
            Log.i(TAG, "i == $i")
        }
    }

    /**
     * step
     */
    fun testStep() {
        for (i in 1..4 step 2) {
            Log.i(TAG, "i == $i")
        }

        Log.i(TAG, "----------------------")

        for (i in 4 downTo 1 step 2) {
            Log.i(TAG, "i == $i")
        }
    }

    /***
     * 测试三引号
     */
    fun testString() {
        val str = """
                    one  two
                    three
                """
        val str2 = "one \n two"

        val str3 = "one" + "\n" + "two"

        Log.i(TAG , "str = $str , str1 = $str2 , str3  $str3")
    }

}