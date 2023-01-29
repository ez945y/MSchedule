package com.example.mschedule.entity

data class Month(
    val m: List<List<String>> = listOf(
        listOf("1", "2", "3", "4", "5", "6", "7"),
        listOf("8", "9", "10", "11", "12", "13", "14"),
        listOf("15", "16", "17", "18", "19", "20", "21"),
        listOf("22", "23", "24", "25", "26", "27", "28"),
        listOf("29", "30", "31", "1", "2", "3", "4"),
    ),
    val m2: List<List<String>> = listOf(
        listOf("29", "30", "31", "1", "2", "3", "4"),
        listOf("5", "6", "7", "8", "9", "10", "11"),
        listOf("12", "13", "14", "15", "16", "17", "18"),
        listOf("19", "20", "21", "22", "23", "24", "25"),
        listOf("26", "27", "28", "1", "2", "3", "4"),
    ),
)

class Months() : ArrayList<ArrayList<String>>() {
    private val ms: Map<Int, Int> = mapOf(0 to 35,1 to 31,
        2 to 28,
        3 to 31,
        4 to 30,
        5 to 31,
        6 to 30,
        7 to 31,
        8 to 31,
        9 to 30,
        10 to 31,
        11 to 30,
        12 to 31)
    fun addMoon(moon: Int):Int{
        var res = 0
        for(i in 0 .. moon){
            res += ms[i]!!
        }
        return res
    }
    fun getMoon(moon: Int): ArrayList<ArrayList<String>> {

        var sm = arrayListOf<ArrayList<String>>()
        var sw = arrayListOf<String>()

        val a = addMoon(moon-1) % 7
        val pre = ms[moon - 1]!! - a + 1
            for (i in pre..ms[moon - 1]!!) {
                sw.add(i.toString())
            }
            for (i in 1..(7-a)) {
                sw.add(i.toString())
            }


        sm.add(sw.clone() as ArrayList<String>)
        sw.clear()

        for (i in ((7-a) + 1)..ms[moon]!!) { //ms[moon]!!
            sw.add(i.toString())
            if (sw.size % 7 == 0) {
                sm.add(sw.clone() as ArrayList<String>)
                sw.clear()
            }
        }

        if (sw.size != 0) {
            for (i in 1..(7 - sw.size)) {
                sw.add(i.toString())
            }
            sm.add(sw)
        }

        return sm
    }
}
