package com.example.mschedule.screen

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mschedule.entity.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DayScreen(
    localDate: MutableState<LocalDate>,
    yearNum: MutableState<Int>,
    monthNum: MutableState<Int>,
    dayNum: MutableState<Int>,
    navController: NavController,
) {
    val formatter = DateTimeFormatter.ofPattern("MM月dd日 E", Locale.TAIWAN)
    val context = LocalContext.current
    val formatterMonth = DateTimeFormatter.ofPattern("MM", Locale.TAIWAN)
    val formatterYear = DateTimeFormatter.ofPattern("yyyy", Locale.TAIWAN)
    val formatterDay = DateTimeFormatter.ofPattern("dd", Locale.TAIWAN)
    yearNum.value = localDate.value.format(formatterYear).toInt()
    monthNum.value = localDate.value.format(formatterMonth).toInt()
    dayNum.value = localDate.value.format(formatterDay).toInt()
    var flag = remember { mutableStateOf(true) }
    if (flag.value) {
        db_Select(localDate.value, context)
        flag.value = false
    }

    val scheduleVM = ScheduleViewModel()
    val scheduleList = scheduleVM.scheduleList
    Card(modifier = Modifier
        .fillMaxWidth()
        .size(0.dp, 900.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .padding(vertical = 6.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(text = localDate.value.format(formatter),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(start = 20.dp, top = 20.dp, bottom = 15.dp)
                        .clickable {
                            datePicker(true, context, localDate.value, onDateSelect = {
                                localDate.value = it
                            })
                        }
                )
                Icon(Icons.Outlined.AddCircle,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(end = 20.dp)
                        .clickable { navController.navigate("Add/${yearNum.value}-${monthNum.value}-${dayNum.value}") })
            }
            if (scheduleList.isEmpty()) {
                Button(modifier = Modifier
                    .padding(top = 200.dp, start = 120.dp),
                    onClick = { navController.navigate("Add/${yearNum.value}-${monthNum.value}-${dayNum.value}") }) {
                    Icon(Icons.Outlined.AddCircle,
                        contentDescription = null,
                        modifier = Modifier.padding(end = 15.dp))
                    Text("新增行程",
                        modifier = Modifier
                            .padding(end = 5.dp)
                            .padding(vertical = 10.dp))
                }
            } else {

                LazyColumn(modifier = Modifier.padding(bottom = 20.dp)) {
                    item {
                        Divider(color = MaterialTheme.colorScheme.secondary,
                            thickness = 1.dp,
                            modifier = Modifier
                                .padding(top = 4.dp)
                                .padding(horizontal = 8.dp))
                    }
                    itemsIndexed(scheduleList) { idx, schedule ->
                        ScheduleItemDisplay(schedule, navController, context, idx
                        ) { scheduleVM.deleteSchedule(it) }
                    }
                }


            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleItemDisplay(
    schedule: ScheduleItem,
    navController: NavController,
    context: Context,
    idx: Int,
    scheduleVM: (Int) -> Unit,
) {
    Row(modifier = Modifier
        .padding(top = 8.dp)) {
        OutlinedTextField(
            value = schedule.clock.value,
            onValueChange = { schedule.clock.value = it },
            modifier = Modifier
                .padding(start = 20.dp,top = 0.dp).size(45.dp, 40.dp).background(Color.White, RoundedCornerShape(8.dp)),
            textStyle = TextStyle(fontSize = 8.sp),
            placeholder = {Text("24:00")},
            shape = CircleShape
        )
        Card(modifier = Modifier
            .fillMaxWidth().padding(horizontal = 16.dp)) {
            Row(
                modifier = Modifier
                    .padding(vertical = 6.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = schedule.title.value,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(start = 20.dp),
                )
                Row {
                    Icon(Icons.Filled.Delete,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(end = 20.dp)
                            .size(24.dp)
                            .clickable {
                                db_delete(id = schedule.id, context = context)
                                scheduleVM(idx)
                            })
                    Icon(Icons.Filled.Edit,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(end = 30.dp)
                            .size(24.dp)
                            .clickable { navController.navigate("Edit/$idx") })
                }
            }

        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun DayPreview() {
    DayScreen(mutableStateOf(LocalDate.now()),
        mutableStateOf(1),
        mutableStateOf(1),
        mutableStateOf(1), rememberNavController())
}