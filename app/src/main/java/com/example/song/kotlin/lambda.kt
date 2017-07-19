package com.example.song.kotlin

import android.view.View
import android.widget.Button

/**
 * Created by PVer on 2017/7/18.
 * lambda 表达式
 */
fun main(args: Array<String>) {
    val action = { i: Int, j: Int ->

    }
    action(1, 2)

    val list = arrayListOf(1, 2, 3)
    list.forEach { value -> }

    val button: Button? = null
    val onClick = { view: View -> print("view") }
    button?.setOnClickListener(onClick)
}