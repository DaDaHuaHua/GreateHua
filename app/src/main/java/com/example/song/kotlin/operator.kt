package com.example.song.kotlin

/**
 * Created by PVer on 2017/7/18.
 *  操作符重载
 */

class Rmb(var num :Int ){
    operator fun plus(rmb: Any) {
    }
}

fun main(args: Array<String>) {
    Rmb(1) + Rmb(1)
    Rmb(1)+100
    Rmb(1).plus(100)
}