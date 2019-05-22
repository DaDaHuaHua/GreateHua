package com.example.song.kotlin

/**
 * 测试Kotlin中的泛型
 */

/***
 *  in 只能消费，不能生产
 */
class In<in T> {
    fun set(t: T) {

    }

//    fun get():T?{
//        return null
//    }
}

/***
 *  out 只能生产T（只能返回），不能消费T
 */
class Out<out T> {
//    fun set(t:T){
//
//    }

    fun get(): T? {
        return null
    }
}

//泛型函数
fun <T> test(t: T) {}

//泛型约束
fun <T : Number> put(t: T) {}
interface OnClick
interface OnLongClick
class Impl :OnClick,OnLongClick
fun <T> put2(t: T) where T:OnClick ,T:OnLongClick{
}


fun main(args: Array<String>) {
    //java中的泛型
    //ArrayList<? extend E>
    //ArrayList<? super E>

    //ArrayList<?> 可以用* 替代 ？
    val list: ArrayList<*> = arrayListOf(1, 2, 3)

    //泛型约束
    put(1)
    put2(Impl())
}

