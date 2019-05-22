package com.example.song.kotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.sdk25.coroutines.onLongClick

/**
 */
class DSLActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        verticalLayout {
            lparams(matchParent, matchParent)
            backgroundColor = 323232
            val name = editText()
            button("sayHello") {
                onClick { toast("hello ${name.text}!") }
            }
            textView("text") {
                lparams(dip(120), dip(80))
                backgroundColor = 323232
                onLongClick { toast("onLongClick!") }
            }
            myView {
                test()
            }
        }

        MyView(this).onClick {  }
    }
}