package com.example.song.kotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.example.commonlibrary.util.ToastUtil

import com.example.song.R
import com.tencent.bugly.proguard.v
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.android.UI
import org.jetbrains.anko.*
import org.jetbrains.anko.coroutines.experimental.bg


/***
 *  Anko的使用,
 *  1.Toast
 *  2.Intent
 *  3.Log
 *  android.util.Log	AnkoLogger
 *     v()	            verbose()
 *     d()	            debug()
 *     i()	            info()
 *     w()	            warn()
 *     e()	            error()
 *     wtf()	        wtf()
 *
 *
 *
 *
 */
class AnkoActivity : AppCompatActivity(), View.OnClickListener, AnkoLogger {

    fun getData(): String = ""
    fun showData(data: String) { }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anko)
        ButterKnife.bind(this)
    }

    @OnClick(R.id.btn_toast, R.id.btn_startActivity, R.id.btn_call, R.id.btn_log ,R.id.px2dp,R.id.coroutines)
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_toast -> toast("Hi there!")
            R.id.btn_startActivity -> startActivity(intentFor<KotlinActivity>("id" to 5).newTask())
            R.id.btn_call -> {
                toast("make call")
                makeCall("13260656511")
            }
            R.id.btn_log -> info("111")
            R.id.px2dp->ToastUtil.showMessage(dip(1024))
            R.id.coroutines->{

                async(UI) {
                    val data: Deferred<String> = bg {
                        // Runs in background
                        getData()
                    }
                    // This code is executed on the UI thread
                    showData(data.await())
                }
            }

        }
    }
}
