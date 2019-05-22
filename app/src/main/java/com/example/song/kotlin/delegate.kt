package com.example.song.kotlin

import java.util.*
import kotlin.properties.Delegates
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * 委托
 * kotlin中的委托 替代继承的一种方式
 */


/***
 * 类委托
 * MyList 继承Collection以后是需要实现所有方法的，
 * 通过代理的方式，将需要实现的方法交给传递进来的已经实现好的对象去实现
 */
class MyList(list: ArrayList<String>) : Collection<String> by list {

}

/***
 * 属性委托
 */


/****
 * lazy 委托
 *
 *  委托给lazy 返回的对象进行代理
 *  lazy 懒惰， 没有使用到list的时候不会创建lazy返回的对象
 *  在第一次使用list的时候才会创建lazy返回的对象
 *  以后再使用list的时候后也不会创建lazy返回的对象
 */
val list: ArrayList<String> by lazy {
    ArrayList<String>()
}

/**
 * Delegates.notNull 委托
 * 在声明一个成员变量的时候是需要初始化的
 * 通过Delegates.notNull代理以后就可以不用初始化了,就是用来占位的
 * 但是如果不初始化的话在使用到该成员变量的时候就会抛异常
 *
 */
//val str: String = ""
var str: String by Delegates.notNull<String>()


/****
 * Delegates.observable 委托
 * 当变量发生改变的时候，会触发闭包中的表达式
 * 可以获取到 属性、原来的值、改变后的值
 */
val str2: String by Delegates.observable("") {
    property, oldValue, newValue ->
    println("${property.name} old：${oldValue} new:${newValue}")
}


/****
 * Delegates.vetoable 委托
 * 与Delegate.observerble是相同的，唯一的区别：
 *   增加了一个返回值，如果返回true才会改变值，返回false就不会改变值
 * 当变量发生改变的时候，会触发闭包中的表达式
 * 可以获取到 属性、原来的值、改变后的值
 */
val str3: String by Delegates.vetoable("") {
    property, oldValue, newValue ->
    false
}


/***
 * 自定义委托
 *
 *
 */
class Delegates<T> : ReadWriteProperty<Any?, T> {

    var t: T? = null
    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return t?:throw Exception("")
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        t = value
    }
}

var p :String by Delegates<String>()





























