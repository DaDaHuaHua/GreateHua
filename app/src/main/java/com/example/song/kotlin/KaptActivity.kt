package com.example.song.kotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import butterknife.BindView
import butterknife.ButterKnife

import com.example.song.R


/***
 * apt 注解处理器 annotation processing tool， kapt kotlin 注解处理器
 *
 *  build.gradle 原来java项目中我们使用atp 或 annotationProcessor +'com.jakewharton:butterknife-compiler:8.7.0'
 *  如果引入kotlin以后我们要加上kapt   kapt +'com.jakewharton:butterknife-compiler:8.7.0'
 *
 */
class KaptActivity : AppCompatActivity() {

    @BindView(R.id.button)
    lateinit var back: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kapt)
        ButterKnife.bind(this)

        // !!表示 我确定back不是空的，是空的你只管抛异常
//        back!!.text = "回到java世界"
        // ？表示为空就不处理?后面的
//        back?.text = "回到java世界"
        //现在改成lateinit 就不需要写 ？ 和 ！！了

        back.text = "回到java世界"

    }
}
