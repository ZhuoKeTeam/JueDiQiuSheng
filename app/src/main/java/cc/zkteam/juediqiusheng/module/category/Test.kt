package cc.zkteam.juediqiusheng.module.category

/**
 * Created by renxuelong on 17-10-27.
 * Description:
 */
class Test {

    fun main(args: Array<String>) {
        // 变量
        var x = 5
        x += 1

        // 常量
        val a: Int = 1
        val b = 2
        val c: Int
        c = 3

        // 字符串
        val sl = "a is $a"

        // 条件
        if (a > b) {

        } else if (a < b) {

        } else {

        }

        // 字符串
        println("a=$a,b=$b,c=$c")
    }

    // 函数
    fun sum(a: Int, b: Int): Int {
        return a + b
    }

    // 表达式作为函数体
    fun sum1(a: Int, b: Int) = a + b

    // 定义返回值可空
    fun parseInt(str: String): Int? {
        return str.toIntOrNull()
    }

    fun printProduct(arg1: Any, arg2: String) {

        // is 判断
        if (arg1 is String) {
            arg1.length
        }

        // 集合定义
        val items = listOf("apple", "orange", "kiwi")

        // 循环 in
        for (i in items) {

        }

        for (index in items.indices) {

        }

        var index = 0
        while (index < items.size) {
            index += 1
        }

        val x = 10
        for (x in 0..items.size) {

        }

        // in 判断
        if (x in 0..items.size) {

        }

        // 步进
        for (x in 1..items.size step 2) {

        }

        for (x in items.size downTo 0 step 2) {

        }

        // when 循环
        when {
            "orange" in items -> println("")
            "apple" in items -> println("")
        }
    }
}