//package com.example.song.kotlin
//
//import android.util.Log
//
///**
// *  接口、抽象类、继承
// *  嵌套类、内部类、object与伴生对象
// *  访问控制
// */
//interface OnClickListener {
//    companion object {
//        val TAG: String = "KotlinTAG"
//    }
//
//    val name: String
//    fun click()
//    fun test() {
//        Log.i(TAG, "onClick Test")
//    }
//}
//
//interface OnClickListener2 {
//    fun click()
//    fun test() {
//
//    }
//}
//
//open class Button(override val name: String) : OnClickListener, OnClickListener2, Persons() {
//    /***
//     * 重写了test方法，是谁的？OnClickListener 还是 OnClickListener2的？
//     * 调用super< 接口名 >.test 来制定重写谁的
//     */
//    override fun test() {
//        super<OnClickListener>.test()
//    }
//
//    override fun click() {
//
//    }
//    open fun show(){
//
//    }
//}
//
//
//class DNButton(override val name: String) : Button(name){
//    override fun test() {
//        super.test()
//    }
//
//    override fun click() {
//        super.click()
//    }
//
//    override fun show() {
//        super.show()
//    }
//}
//
//abstract class Persons {
//    abstract fun test()
//
//}
//
///***
// * 继承抽象类要加括号
// */
//class Man : Persons() {
//    override fun test() {
//    }
//}
//
//
