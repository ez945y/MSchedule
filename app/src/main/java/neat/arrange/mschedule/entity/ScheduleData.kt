package neat.arrange.mschedule.entity

import androidx.compose.runtime.mutableStateOf
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

val sdf = SimpleDateFormat("yyyy-MM-dd")
val sdfTime = SimpleDateFormat("HH:mm")
val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yy/MM/dd", Locale.TAIWAN)
val formatterGobal: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.TAIWAN)
val formatterTime: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm", Locale.TAIWAN)
val globalDate = mutableStateOf("")
val currentCalender = mutableStateOf("主行事曆")
val currentIndex = mutableStateOf(0)

var tempItemList = mutableListOf(
    //TODO extract data
    ScheduleItem(
        id = 3,
        title = mutableStateOf("測試"),
        startDate = mutableStateOf(sdf.parse("2023-01-25").toInstant().atZone(
            ZoneId.systemDefault()).toLocalDate()),
        endDate = mutableStateOf(sdf.parse("2023-01-25").toInstant().atZone(
            ZoneId.systemDefault()).toLocalDate()),
        member = mutableStateOf("您"),
        schedule = mutableStateOf("日常"),
    ),
    ScheduleItem(
        id = 4,
        title = mutableStateOf("測試2"),
        startDate = mutableStateOf(sdf.parse("2023-01-25").toInstant().atZone(
            ZoneId.systemDefault()).toLocalDate()),
        endDate = mutableStateOf(sdf.parse("2023-01-25").toInstant().atZone(
            ZoneId.systemDefault()).toLocalDate()),
        member = mutableStateOf("您"),
        schedule = mutableStateOf("日常"),
    ),
    ScheduleItem(
        id = 5,
        title = mutableStateOf("測試3"),
        startDate = mutableStateOf(sdf.parse("2023-01-25").toInstant().atZone(
            ZoneId.systemDefault()).toLocalDate()),
        endDate = mutableStateOf(sdf.parse("2023-01-25").toInstant().atZone(
            ZoneId.systemDefault()).toLocalDate()),
        member = mutableStateOf("您"),
        schedule = mutableStateOf("日常"),
    ),
)