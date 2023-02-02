package com.example.mschedule.entity

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.time.ZoneId

val sdf = SimpleDateFormat("yyyy-MM-dd")

val scheduleItemList = listOf(
    //TODO extract data
    ScheduleItem(
        id = 0,
        title = mutableStateOf("期中考"),
        startTime = mutableStateOf(sdf.parse("2023-01-25").toInstant().atZone(
            ZoneId.systemDefault()).toLocalDate()),
        endTime = mutableStateOf(sdf.parse("2023-01-25").toInstant().atZone(
            ZoneId.systemDefault()).toLocalDate()),
        member = mutableStateOf("您"),
        schedule = mutableStateOf("日常"),
    ),
    ScheduleItem(
        id = 1,
        title = mutableStateOf("小豬歷險記"),
        startTime = mutableStateOf(sdf.parse("2023-01-25").toInstant().atZone(
            ZoneId.systemDefault()).toLocalDate()),
        endTime = mutableStateOf(sdf.parse("2023-01-25").toInstant().atZone(
            ZoneId.systemDefault()).toLocalDate()),
        member = mutableStateOf("您"),
        schedule = mutableStateOf("日常"),
    ),
    ScheduleItem(
        id = 2,
        title = mutableStateOf("台南旅遊"),
        startTime = mutableStateOf(sdf.parse("2023-01-25").toInstant().atZone(
            ZoneId.systemDefault()).toLocalDate()),
        endTime = mutableStateOf(sdf.parse("2023-01-25").toInstant().atZone(
            ZoneId.systemDefault()).toLocalDate()),
        member = mutableStateOf("您"),
        schedule = mutableStateOf("日常"),
    ),
)

val searchItemList = listOf("系學會", "華山","陽明山","羽球")

val tempItemList = mutableListOf(
    //TODO extract data
    ScheduleItem(
        id = 3,
        title = mutableStateOf("測試"),
        startTime = mutableStateOf(sdf.parse("2023-01-25").toInstant().atZone(
            ZoneId.systemDefault()).toLocalDate()),
        endTime = mutableStateOf(sdf.parse("2023-01-25").toInstant().atZone(
            ZoneId.systemDefault()).toLocalDate()),
        member = mutableStateOf("您"),
        schedule = mutableStateOf("日常"),
    ),
    ScheduleItem(
        id = 4,
        title = mutableStateOf("測試2"),
        startTime = mutableStateOf(sdf.parse("2023-01-25").toInstant().atZone(
            ZoneId.systemDefault()).toLocalDate()),
        endTime = mutableStateOf(sdf.parse("2023-01-25").toInstant().atZone(
            ZoneId.systemDefault()).toLocalDate()),
        member = mutableStateOf("您"),
        schedule = mutableStateOf("日常"),
    ),
    ScheduleItem(
        id = 5,
        title = mutableStateOf("測試3"),
        startTime = mutableStateOf(sdf.parse("2023-01-25").toInstant().atZone(
            ZoneId.systemDefault()).toLocalDate()),
        endTime = mutableStateOf(sdf.parse("2023-01-25").toInstant().atZone(
            ZoneId.systemDefault()).toLocalDate()),
        member = mutableStateOf("您"),
        schedule = mutableStateOf("日常"),
    ),
)