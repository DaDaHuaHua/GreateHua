package com.example.song.kotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick

import com.example.song.R
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.makeCall
import org.jetbrains.anko.newTask
import org.jetbrains.anko.toast


/***
 *  Anko的使用,
 */
class AnkoActivity : AppCompatActivity(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anko)
        ButterKnife.bind(this)

    }

    @OnClick(R.id.btn_toast, R.id.btn_startActivity, R.id.btn_call)
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_toast -> toast("Hi there!")
            R.id.btn_startActivity -> startActivity(intentFor<KotlinActivity>("id" to 5).newTask())
            R.id.btn_call -> {
                toast("make call")
                makeCall("13260656511")
            }

        }
    }
}
