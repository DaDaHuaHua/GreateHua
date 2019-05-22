package com.example.song.kotlin

/**
 *  数据类、构造器
 */


/***
 * 会自动重写 toString /get /set
 */
data class MyDataClass(val name: String)

fun main(args: Array<String>) {
    User("1")
}

/**
 * 不添加主构造器的话会自动带有无参构造器
 * 除了添加一个主构造器以外，还可以添加无数个次构造器
 */
class User( name: String) {
    var name: String = ""
    var pwd: String = ""

    constructor(name: String, pwd: String) : this(name) {
        this.name = name
        this.pwd = pwd
    }

    //初始化构造器
    //当调用主构造器的时候，会调用init{},在init{}中可以拿到主构造器传递进来的参数
    init {
        this.name = name
    }
}