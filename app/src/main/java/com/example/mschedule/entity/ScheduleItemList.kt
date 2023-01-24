package com.example.mschedule.entity

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import java.time.LocalDate
data class ScheduleItemList (
    var title: MutableState<String> =mutableStateOf("編輯"),
    var startTime: MutableState<LocalDate> =mutableStateOf(LocalDate.now()),
    var endTime: MutableState<LocalDate> =mutableStateOf(LocalDate.now()),
    var isRepeat: MutableState<Boolean> =mutableStateOf(false),
    var member:MutableState<String> =mutableStateOf("您"),
    var schedule:MutableState<String> =mutableStateOf("日常"),
    var tag:MutableState<String> =mutableStateOf("無標籤"),
    var note:MutableState<String> =mutableStateOf("備註"),
)