package com.example.song.kotlin

/**
 * Created by PVer on 2017/7/19.
 * 注解与反射
 */

/**
 * 声明注解
 * annotation 也是软关键字，只在class前面才是关键字
 */

@Target(AnnotationTarget.FIELD,AnnotationTarget.CLASS ,AnnotationTarget.FUNCTION)
annotation class Path

@Path class UserPath(val p: String){

}
@Path fun userPath(){

}
var name = "1"
fun main(args: Array<String>) {
    String::class.java
    String.javaClass
    Int::class.java
    Int.javaClass

    //函数、属性引用
    listOf(1,2,3).forEach(::println)
    val p = ::name
    p.set("123")
    p.get()

    UserPath::p.get(UserPath("a"))
}