package neat.arrange.mschedule.screen

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import neat.arrange.mschedule.R
import neat.arrange.mschedule.entity.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@Composable
fun DayScreen(
    localDate: MutableState<LocalDate>,
    yearNum: MutableState<Int>,
    monthNum: MutableState<Int>,
    dayNum: MutableState<Int>,
    navController: NavController,
    state: ScrollableState,
) {
    val formatter = DateTimeFormatter.ofPattern("MM月dd日 E", Locale.TAIWAN)
    val context = LocalContext.current
    val formatterMonth = DateTimeFormatter.ofPattern("MM", Locale.TAIWAN)
    val formatterYear = DateTimeFormatter.ofPattern("yyyy", Locale.TAIWAN)
    val formatterDay = DateTimeFormatter.ofPattern("dd", Locale.TAIWAN)
    yearNum.value = localDate.value.format(formatterYear).toInt()
    monthNum.value = localDate.value.format(formatterMonth).toInt()
    dayNum.value = localDate.value.format(formatterDay).toInt()
    val flag = remember { mutableStateOf(true) }
    if (flag.value) {
        dbSelect(localDate.value, context)
        flag.value = false
    }

    val scheduleVM = ScheduleViewModel()
    val scheduleList = scheduleVM.scheduleList
    Card(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .scrollable(
            state = state, orientation = Orientation.Horizontal,
        ),
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
                        )
                        //{ scheduleVM.deleteSchedule(it) }
                    }
                }


            }
        }
    }

}


@Composable
fun ScheduleItemDisplay(
    schedule: ScheduleItem,
    navController: NavController,
    context: Context,
    idx: Int,
//    scheduleVM: (Int) -> Unit,
) {
    val showAlertDialog = remember { mutableStateOf(false) }
    Box {
        Row(modifier = Modifier
            .padding(top = 8.dp)) {
            Text(
                text = "${schedule.startTime.value}",
                modifier = Modifier.padding(start = 18.dp, top = 7.dp)
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
            ) {
                Row(
                    modifier = Modifier
                        .padding(vertical = 6.dp)
                        .fillMaxWidth()
                        .clickable { navController.navigate("Edit/$idx") },
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
                                .size(27.dp)
                                .clickable {
                                    showAlertDialog.value = true
                                    //scheduleVM(idx)
                                })
                        Icon(painterResource(id = R.drawable.edit),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(end = 20.dp)
                                .size(24.dp)
                                .clickable { navController.navigate("Edit/$idx") })
                        Icon(
                            painterResource(id = R.drawable.done), null, modifier = Modifier
                                .padding(end = 20.dp)
                                .size(24.dp)
                                .clickable {
                                    schedule.done.value = !schedule.done.value
                                    dbReplace(schedule, context)
                                })
                    }
                }

            }
        }
        if(schedule.done.value) {
            Divider(thickness = 2.dp, modifier = Modifier.padding(top = 28.dp,start = 10.dp,end=10.dp))
        }
        if (showAlertDialog.value) {
            AlertDialog(
                onDismissRequest = {
                    showAlertDialog.value = false
                },
                title = {
                    Text("確認")
                },
                text = {
                    Text("請問是否要刪除行程")
                },
                confirmButton = {
                    Button(
                        onClick = {
                            dbDelete(id = schedule.id, context = context)
                            showAlertDialog.value = false
                        },
                        modifier = Modifier.padding()
                    )
                    {
                        Text(text = "確認")
                    }
                },
                dismissButton = {
                    Button(onClick = { showAlertDialog.value = false },
                        modifier = Modifier.padding(end = 50.dp))
                    {
                        Text(text = "取消")
                    }
                }
            )
        }
    }
}
