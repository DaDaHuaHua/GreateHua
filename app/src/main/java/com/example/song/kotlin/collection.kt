package com.example.song.kotlin

/**
 * Created by PVer on 2017/6/25.
 *
 */

fun test(i: Int = 100, j: String = "") {

}

fun test(j: String = "") {
    val i = 100
}

fun test(vararg item: Int) {
    item.forEach { }
}

inline fun String.show() {

}

inline val String.lastChar: Char
    get() = get(length - 1)


fun main(args: Array<String>) {
    val list = listOf(0, 1, 2, 3, 4)

    list[0]
    list.last()

    for (i in list) {

    }

    list.forEach {
        item ->
        println(item)
    }

    list.forEachIndexed { index, i -> println("i=$i index=+$index") }
    list.joinToString()

    test()
    test(1)
    test(1, "11")
    test(j = "22")
    test(1, 2, 3, 4, 5, 6, 7)
    "".show()

    val map = mapOf(1 to "a", 2 to "b", "c" to 1)

    map[1]
    map["c"]
    //中坠调用 infix
    1 to "a"
    1.to("a")

    1 with "a"

    val pair = "a" to "b"
    //析构方式
    val (key, value) = pair

    val compile = "com.a.b.c"
    val (group, name, version) = compile.split(":")
}


infix fun <A, B> A.with(that: B): Pair<A, B> = Pair(this, that)