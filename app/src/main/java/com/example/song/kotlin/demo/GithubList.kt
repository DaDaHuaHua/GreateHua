package com.example.song.kotlin.demo

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.commonlibrary.util.ToastUtil
import com.example.song.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.*
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.sdk25.coroutines.onClick

class RecyclerUI : AnkoComponent<MyAdapter> {
    override fun createView(ui: AnkoContext<MyAdapter>): View = with(ui) {
        verticalLayout {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
            imageView {
                id = R.id.iv
                lparams {
                    rightMargin = dip(12)
                    width = dip(80)
                    height = dip(80)
                }
            }

            verticalLayout {
                textView {
                    id = R.id.tv1
                }
                textView {
                    id = R.id.tv2
                }
            }
        }
    }
}

class MyAdapter(val ctx: Context, val items: ArrayList<Resp>) : RecyclerView.Adapter<MyAdapter.MyHolder>() {

    override fun onBindViewHolder(holder: MyHolder?, position: Int) {
        //设置视图
        val resp = items[position]
        Glide.with(ctx).load(resp.owner.avatar_url).into(holder?.iv)
        holder?.tv1?.text = resp.name
        holder?.tv2?.text = resp.full_name
    }


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyHolder {

//        //with: 在给定对象上调用lambda中的所有方法
//        val view = with(ctx) {
//            //dsl布局
//            verticalLayout {
//                orientation = LinearLayout.HORIZONTAL
//                gravity = Gravity.CENTER_VERTICAL
//                imageView {
//                    //设置id
//                    id = R.id.iv
//                    lparams {
//                        //dip:获取dip值
//                        rightMargin = dip(12)
//                        width = dip(80)
//                        height = dip(80)
//                    }
//                }
//
//                verticalLayout {
//                    textView {
//                        id = R.id.tv1
//                    }
//                    textView {
//                        id = R.id.tv2
//                    }
//                }
//            }
//        }
//        val holder = MyHolder(view)
        val holder = MyHolder(RecyclerUI().createView(AnkoContext.Companion.createReusable(ctx, this)))
        return holder
    }

    override fun getItemCount(): Int = items.size


    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //在给定的view上查找指定id的视图
        val iv: ImageView = itemView.find(R.id.iv)
        val tv1: TextView = itemView.find(R.id.tv1)
        val tv2: TextView = itemView.find(R.id.tv2)
    }
}

//reified 具像化
inline fun <reified T> getType() = object : TypeToken<T>() {
}.type

class GithubList : AppCompatActivity() {

    lateinit var adapter: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val items = ArrayList<Resp>()
        adapter = MyAdapter(this, items)
        //activity使用dsl布局会自动的setContentView
        verticalLayout {
            textView {
                width = matchParent
                height = dip(50)
                backgroundColor = R.color.colorAccent
                onClick { ToastUtil.showMessage("点击textView") }
            }
            recyclerView {
                //设置参数 anko为Activity扩展了一个属性 act
                layoutManager = LinearLayoutManager(act)
//                layoutManager = LinearLayoutManager(this@MainActivity)
                padding = dip(20)
                adapter = this@GithubList.adapter
            }
        }

        //启动协程
        async(kotlinx.coroutines.experimental.android.UI) {
            //启动子线程
            val data: Deferred<ArrayList<Resp>> = bg {
                val input = assets.open("data.json")
                val bytes = input.readBytes()
                input.close()
                Log.i("GithubList",String(bytes))
                Gson().fromJson<ArrayList<Resp>>(String(bytes), getType<ArrayList<Resp>>())
            }
            items.clear()
            items.addAll(data.await())
            adapter.notifyDataSetChanged()

        }
    }
}















