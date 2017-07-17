package com.example.song.kotlin

import android.view.View

/**
 * Created by PVer on 2017/7/17.
 * 内部类 与 伴生对象 与 访问控制
 */
class AlertDialog {


    //伴生类 ， 可以指定名字，也可以不指定
    //在伴生类中定义类似java中的静态方法、静态属性
    // companion object A {
    companion object {
        fun show() {}
    }

    val str = "111"

    //嵌套类
    class Builder {
        //str 不能拿到AlertDialog的str，怎么办？
        //因此需要内部类
    }

    //内部类
    inner class Inner {
        fun test() {
            str.substring(0)
        }
    }

    //没有static 怎么创建静态内部类？
    //object类似静态类
    fun main() {
        Dialog.show()

        //不指定半生类名字用Companion.show()调用
        AlertDialog.Companion.show()
        //指定了名字用.A.show()
        //AlertDialog.A.show()

        //匿名内部类,要加括号
        val person = object : Persons() {
            override fun test() {
            }
        }

        val listener = View.OnClickListener{
            //只有一个参数 可以用it来操作 it代表view
            it.buildLayer()
        }
    }
}

object Dialog {
    fun show() {

    }
}


/***
 * 访问控制
 */
//public  默认 所有地方可见
//private 类内部 同文件可见（文件指的是kotlin文件）
//protected 子类内部
//default XX
//internal 模块内可见

private class P1{

}

private class P2{
    val p1= P1()
}


