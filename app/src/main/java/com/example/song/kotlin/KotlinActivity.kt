package com.example.song.kotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.commonlibrary.util.ToastUtil
import com.example.song.R


class KotlinActivity : AppCompatActivity() {

    var mBtn: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)
        mBtn = findViewById(R.id.button) as Button

        val onClick = {view : View -> ToastUtil.showMessage("btn pressed !") }
        mBtn?.setOnClickListener(onClick)
    }
}
