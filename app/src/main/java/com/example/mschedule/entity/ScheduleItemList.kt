package com.example.mschedule.entity

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
data class ScheduleItem (
    var id :Int,
    var title: MutableState<String> =mutableStateOf(""),
    var startTime: MutableState<LocalDate> =mutableStateOf(LocalDate.now()),
    var endTime: MutableState<LocalDate> =mutableStateOf(LocalDate.now()),
    var isAllDay: MutableState<Boolean> =mutableStateOf(false),
    var isRepeat: MutableState<Boolean> =mutableStateOf(false),
    var member:MutableState<String> =mutableStateOf(""),
    var schedule:MutableState<String> =mutableStateOf(""),
    var tag:MutableState<String> =mutableStateOf(""),
    var note:MutableState<String> =mutableStateOf(""),
)

class ScheduleViewModel() : ViewModel() { //items: List<ScheduleItem>

    private fun getSchedule(): SnapshotStateList<ScheduleItem> {
        return tempItemList.toMutableStateList()
    }

    private val _scheduleList = getSchedule().toMutableStateList()
    val scheduleList: List<ScheduleItem>
        get() = _scheduleList

    fun addSchedule(trainingItem: ScheduleItem) {
        _scheduleList.add(trainingItem)
    }

    fun updateSchedule(ScheduleItem: ScheduleItem) {
        _scheduleList[ScheduleItem.id] = ScheduleItem
    }

    fun deleteSchedule(ScheduleIndex: Int) {
        _scheduleList.removeAt(ScheduleIndex)
    }

}

