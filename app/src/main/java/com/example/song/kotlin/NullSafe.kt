package com.example.song.kotlin

/**
 * Created by PVer on 2017/7/3.
 * Kotlin的null安全
 * 由编译器来保证
 */
class NullSafe {
    fun main() {
        test(null)
    }

    fun test(str: String?) {
        //报错，str可能为空
        //str.substring(0)
        //?. 安全操作。如果str为null就不会执行substring()方法
        str?.substring(0)
        // !! 需要我们自己来保证str不能为空，如果为空就抛出异常
        str!!.substring(0)
    }


}