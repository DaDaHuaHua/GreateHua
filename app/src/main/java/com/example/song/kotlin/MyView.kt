package com.example.song.kotlin

import android.content.Context
import android.view.View
import android.view.ViewManager
import org.jetbrains.anko.custom.ankoView

/**
 * Created by PVer on 2017/7/24.
 */


inline fun ViewManager.myView(init: MyView.() -> Unit): MyView {
    return ankoView({ MyView(it) }, 0, init)
}

class MyView(context: Context?) : View(context) {
    fun test() {}
}