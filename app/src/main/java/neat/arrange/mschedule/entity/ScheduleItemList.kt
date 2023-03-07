package neat.arrange.mschedule.entity

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId

data class ScheduleItem(
    var id: Int,
    var title: MutableState<String> = mutableStateOf(""),
    var startDate: MutableState<LocalDate> = mutableStateOf(LocalDate.now()),
    var endDate: MutableState<LocalDate> = mutableStateOf(LocalDate.now()),
    var startTime: MutableState<LocalTime> = mutableStateOf(LocalTime.now()),
    var endTime: MutableState<LocalTime> = mutableStateOf(LocalTime.now()),
    var isAllDay: MutableState<Boolean> = mutableStateOf(false),
    var isRepeat: MutableState<Int> = mutableStateOf(0),
    var member: MutableState<String> = mutableStateOf(""),
    var schedule: MutableState<String> = mutableStateOf(""),
    var tag: MutableState<String> = mutableStateOf(""),
    var note: MutableState<String> = mutableStateOf(""),
    var alarm: MutableState<Int> = mutableStateOf(0),
    var done: MutableState<Boolean> = mutableStateOf(false),
)

class ScheduleViewModel : ViewModel() { //items: List<ScheduleItem>

    private fun getSchedule(): SnapshotStateList<ScheduleItem> {
        return tempItemList.toMutableStateList()
    }

    private val _scheduleList = getSchedule().toMutableStateList()
    val scheduleList: List<ScheduleItem>
        get() = _scheduleList

//    fun deleteSchedule(ScheduleIndex: Int) {
//        _scheduleList.removeAt(ScheduleIndex)
//    }

}

val searchItemList = mutableListOf("系學會", "華山", "陽明山", "羽球")

class SearchViewModel : ViewModel() { //items: List<ScheduleItem>

    private fun getSearch(): SnapshotStateList<String> {
        return searchItemList.toMutableStateList()
    }

    private val _searchList = getSearch().toMutableStateList()
    val searchList: SnapshotStateList<String>
        get() = _searchList

}


data class CalenderItem(
    var id: Int,
    var name: MutableState<String> = mutableStateOf(""),
    var color: MutableState<String> = mutableStateOf(""),
)

val calenderItemList = mutableListOf(
    CalenderItem(
        id = 1,
        name = mutableStateOf("測試"),
        color = mutableStateOf("測試"),
    ), CalenderItem(
        id = 2,
        name = mutableStateOf("測試2"),
        color = mutableStateOf("測試2"),
    ), CalenderItem(
        id = 3,
        name = mutableStateOf("測試3"),
        color = mutableStateOf("測試3"),
    ), CalenderItem(
        id = 4,
        name = mutableStateOf("測試4"),
        color = mutableStateOf("測試4"),
    ))

class CalenderViewModel : ViewModel() { //items: List<ScheduleItem>

    private fun getCalender(): SnapshotStateList<CalenderItem> {
        return calenderItemList.toMutableStateList()
    }
    private val _calenderList = getCalender().toMutableStateList()
    val searchList: SnapshotStateList<CalenderItem>
        get() = _calenderList
}
