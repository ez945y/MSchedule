package com.example.mschedule.entity

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import java.time.LocalDate
import java.time.LocalTime

data class ScheduleItem (
    var id :Int,
    var title: MutableState<String> =mutableStateOf(""),
    var startDate: MutableState<LocalDate> =mutableStateOf(LocalDate.now()),
    var endDate: MutableState<LocalDate> =mutableStateOf(LocalDate.now()),
    var startTime: MutableState<LocalTime> =mutableStateOf(LocalTime.now()),
    var endTime: MutableState<LocalTime> =mutableStateOf(LocalTime.now()),
    var isAllDay: MutableState<Boolean> =mutableStateOf(false),
    var isRepeat: MutableState<Int> =mutableStateOf(0),
    var member:MutableState<String> =mutableStateOf(""),
    var schedule:MutableState<String> =mutableStateOf(""),
    var tag:MutableState<String> =mutableStateOf(""),
    var note:MutableState<String> =mutableStateOf(""),
    var alarm:MutableState<Int> =mutableStateOf(0),
    var done:MutableState<Boolean> =mutableStateOf(false),
)
class ScheduleViewModel() : ViewModel() { //items: List<ScheduleItem>

    private fun getSchedule(): SnapshotStateList<ScheduleItem> {
        return tempItemList.toMutableStateList()
    }

    private val _scheduleList = getSchedule().toMutableStateList()
    val scheduleList: List<ScheduleItem>
        get() = _scheduleList

    fun deleteSchedule(ScheduleIndex: Int) {
        _scheduleList.removeAt(ScheduleIndex)
    }

}

val searchItemList = mutableListOf("系學會", "華山","陽明山","羽球")

class SearchViewModel() : ViewModel() { //items: List<ScheduleItem>

    private fun getSearch(): SnapshotStateList<String> {
        return searchItemList.toMutableStateList()
    }

    private val _searchList = getSearch().toMutableStateList()
    val searchList: SnapshotStateList<String>
        get() = _searchList

}
