package com.example.song.kotlin

import android.util.Log

/**
 * 字符串
 * 正则表达式
 * null安全
 * 本地函数
 */
class StringTest {
    companion object {
        val TAG: String = "KotlinTAG"
    }

    fun main() {
//        testSplit()
//        testSubStr()
//         testRegex()
    }

    fun testSplit() {
        val str = "com.song.example"
        val split = str.split(".")
        split.forEach { it -> Log.i(TAG, "split element = $it") }
    }

    fun testSubStr() {
        val path = "c:/users/build.gradle"
        val dir = path.substringBeforeLast("/")
        val fullName = path.substringAfterLast("/")
        val fileName = fullName.substringBefore(".")
        val extendName = fullName.substringAfter(".")
        Log.i(TAG, "dir=$dir  fullName=$fullName fileName=$fileName extendName=$extendName ")
    }

    /**
     * 测试正则表达式
     */
    fun testRegex() {
        val path = "c:/users/build.gradle"
        val r1 = "(.+)/(.+)\\.(.+)".toRegex()
        val matchResult = r1.matchEntire(path)
        if (null != matchResult) {
            matchResult.destructured.toList()
        }

        val r2 = """(.+)/(.+)\.(.+)"""
        val str1 = """
        <activity android:name="com.example.song.EntranceActivity">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>"""

        val str2 = """'$'path"""
        val str3 = "\$path"

        Log.i(TAG, "str1 == $str1  \n str2 = $str2 \n str3 = $str3")
    }

}