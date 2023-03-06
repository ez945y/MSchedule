package neat.arrange.mschedule.entity

import androidx.compose.runtime.mutableStateOf

class Months : ArrayList<ArrayList<String>>() {
    private val ms: Map<Int, Int> = mapOf(0 to 35, 1 to 31,
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

    private fun addMoon(moon: Int): Int {
        var res = 0

        for (i in 0..moon) {
            res += ms[i]!!
        }
        return res
    }

    fun getMoon(year: Int = 2023, moon: Int): ArrayList<ArrayList<String>> {
        val sm = arrayListOf<ArrayList<String>>()
        val sw = arrayListOf<String>()
        val accu = mutableStateOf((year - 1994) + (year - 1997) / 4)
        val a = (addMoon(moon - 1) + accu.value) % 7
        val pre = ms[moon - 1]!! - a + 1

        for (i in pre..ms[moon - 1]!!) {
            sw.add("40")
        }
        for (i in 1..(7 - a)) {
            sw.add(i.toString())
        }

        sm.add(sw.clone() as ArrayList<String>)
        sw.clear()

        for (i in ((7 - a) + 1)..ms[moon]!!) { //ms[moon]!!
            sw.add(i.toString())
            if (sw.size % 7 == 0) {
                sm.add(sw.clone() as ArrayList<String>)
                sw.clear()
            }
        }

        if (sw.size != 0) {
            for (i in 1..(7 - sw.size)) {
                sw.add("40")
            }
            sm.add(sw)
        }

        return sm
    }
}
