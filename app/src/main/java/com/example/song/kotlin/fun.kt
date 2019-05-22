package com.example.song.kotlin

/**
 * kotlin 源文件
 */
fun max(x: Int, y: Int) = if (x > y) {
    x
} else {
    y
}


fun max2(x: Int, y: Int) = when {
    x > y -> x
    else -> y
}

//顶层变量
val i = "1" //final
var j = "2"

//顶层函数
fun main(): Int {

    val z: String = "2"
    println("i: ${i}  j：${j}  z:${z}")
    val str = "i: ${i}  j：${j}  z:${z}"
    j = "2"

    val person = Person("张三")
    person.name = "11"
    person.isMaarryed = true
    person.isMaarryed

    when (Color.RED) {
        Color.BLUE -> println("11")
        Color.RED, Color.BLUE,
        Color.YELLOW -> println("222")
    }

    when {
        1 + 1 == 3 -> println("1")
        test() -> println("2")
    }
    //11次循环
    val range: IntRange = 0..10
    loop@ for (i in range) {
        if (i == 2) {
            //break@loop
            continue@loop
        }
    }

    return 1
}

fun test() = true

class Person(var name: String?) {
    var isMaarryed: Boolean = false
        get() = true
       set(value) {
            field = value
        }
}

//enum 软关键字 只有跟在class前面才是关键字 其他情况下可以当做变量名
enum class Color(val r: Int, val g: Int, val b: Int) {
    RED(255, 0, 0),
    YELLOW(1, 2, 3),
    BLUE(2, 3, 4);

    fun rgb() = r + g + b
}