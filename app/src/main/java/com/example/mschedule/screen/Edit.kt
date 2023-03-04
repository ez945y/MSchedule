package com.example.mschedule.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mschedule.R
import com.example.mschedule.entity.db_Replace
import com.example.mschedule.entity.tempItemList
import com.example.mschedule.ui.theme.MScheduleTheme
import java.time.format.DateTimeFormatter
import java.util.*


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditScreen(
    id: String = "1",
    navController: NavController,
    openDrawer: () -> Unit,

    ) {
    val scheduleItem = tempItemList[id.toInt()]
    val formatter = DateTimeFormatter.ofPattern("yy/MM/dd", Locale.TAIWAN)
    val formatterTime = DateTimeFormatter.ofPattern("HH:mm", Locale.TAIWAN)
    val context = LocalContext.current
    val infos = listOf(
        scheduleItem.member,
        scheduleItem.schedule,
        scheduleItem.tag,
        scheduleItem.note
    )
    val placeholder = listOf(
        "新增成員",
        "設定行事曆",
        "設定標籤",
        "新增備註"
    )
    val icons = listOf(
        painterResource(id = R.drawable.team),
        painterResource(id = R.drawable.notebook),
        painterResource(id = R.drawable.tag),
        painterResource(id = R.drawable.note)
    )
    Scaffold(
        topBar = {
            MTopBar(onSearchBarClick = { navController.popBackStack() },
                icon = "c",
                onButtonClicked = openDrawer)
        },
    ) { contentPadding ->
        Card(modifier = Modifier
            .padding(contentPadding)
            .fillMaxWidth()
            .size(0.dp, 900.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer)
        ) {
            Column {
                Card(modifier = Modifier
                    .fillMaxWidth()
                    .size(0.dp, 320.dp)
                    .padding(start = 16.dp, end = 16.dp, top = 20.dp)) {
                    Column {
                        Row {
                            Icon(Icons.Filled.List,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.inverseSurface,
                                modifier = Modifier.padding(top = 20.dp, start = 16.dp))
                            TextField(
                                value = scheduleItem.title.value,
                                onValueChange = { scheduleItem.title.value = it },
                                textStyle = MaterialTheme.typography.titleLarge,
                            )
                        }
                        Row(modifier = Modifier.padding(top = 6.dp)) {
                            Icon(painterResource(id = R.drawable.allday),
                                modifier = Modifier
                                    .padding(start = 15.dp, top = 28.dp).size(18.dp),
                                contentDescription = null)
                            Text(text = "整天",
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .padding(top = 20.dp, start = 12.dp, end = 30.dp),
                                fontSize = 20.sp
                            )
                            Switch(checked = scheduleItem.isAllDay.value,
                                onCheckedChange = { scheduleItem.isAllDay.value = it },
                                modifier = Modifier.padding(start = 150.dp, top = 15.dp))
                        }
                        Row{
                            Icon(painterResource(id = R.drawable.date),
                                modifier = Modifier
                                    .padding(start = 15.dp, top = 28.dp)
                                    .size(18.dp),
                                contentDescription = null)
                            Text(text = scheduleItem.startDate.value.format(formatter),
                                textAlign = TextAlign.Center,
                                fontSize = 20.sp,
                                modifier = Modifier
                                    .padding(top = 20.dp, start = 10.dp, end = 12.dp)
                                    .clickable {
                                        datePicker(true,
                                            context,
                                            scheduleItem.startDate.value,
                                            onDateSelect = {
                                                scheduleItem.startDate.value = it
                                            })
                                    }
                            )
                            Icon(Icons.Filled.ArrowForward,
                                modifier = Modifier
                                    .padding(top = 28.dp),
                                contentDescription = null)
                            Text(text = scheduleItem.endDate.value.format(formatter),
                                textAlign = TextAlign.Center,
                                fontSize = 20.sp,
                                modifier = Modifier
                                    .padding(top = 20.dp, start = 12.dp)
                                    .clickable {
                                        datePicker(true,
                                            context,
                                            scheduleItem.endDate.value,
                                            onDateSelect = {
                                                scheduleItem.endDate.value = it
                                            })
                                    }
                            )

                        }
                        Divider(color = MaterialTheme.colorScheme.secondary,
                            thickness = 1.dp,
                            modifier = Modifier
                                .padding(horizontal = 26.dp)
                                .padding(start = 20.dp, top = 6.dp)
                        )
                        Row(modifier = Modifier.padding(top = 6.dp)) {
                            Icon(painterResource(id = R.drawable.time),
                                modifier = Modifier
                                    .padding(start = 15.dp, top = 28.dp)
                                    .size(18.dp),
                                contentDescription = null)
                            Text(text = "${scheduleItem.startTime.value}",
                                textAlign = TextAlign.Center,
                                fontSize = 20.sp,
                                modifier = Modifier
                                    .padding(top = 20.dp, start = 10.dp, end = 12.dp)
                                    .clickable {
                                        timePicker(true,
                                            context,
                                            scheduleItem.startTime.value,
                                            onTimeSelect = {
                                                scheduleItem.startTime.value = it
                                            })
                                    }
                            )
                            Icon(Icons.Filled.ArrowForward,
                                modifier = Modifier
                                    .padding(top = 28.dp),
                                contentDescription = null)
                            Text(text = "${scheduleItem.endTime.value}",
                                textAlign = TextAlign.Center,
                                fontSize = 20.sp,
                                modifier = Modifier
                                    .padding(top = 20.dp, start = 10.dp, end = 12.dp)
                                    .clickable {
                                        timePicker(true,
                                            context,
                                            scheduleItem.endTime.value,
                                            onTimeSelect = {
                                                scheduleItem.endTime.value = it
                                            })
                                    }
                            )
                        }
                        Divider(color = MaterialTheme.colorScheme.secondary,
                            thickness = 1.dp,
                            modifier = Modifier
                                .padding(horizontal = 26.dp)
                                .padding(start = 20.dp, top = 6.dp)
                        )
                    }
                }

                Row {
                    Icon(painterResource(id = R.drawable.repeat),
                        modifier = Modifier
                            .padding(start = 30.dp, top = 30.dp)
                            .size(18.dp),
                        contentDescription = null)
                    Text(text = "重複",
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(top = 20.dp, start = 10.dp, end = 30.dp),
                        fontSize = 20.sp
                    )
                    DropDown(scheduleItem.isRepeat, Modifier.padding(start = 140.dp, top = 15.dp))
                }

                Card(modifier = Modifier
                    .fillMaxWidth()
                    .size(0.dp, 270.dp)
                    .padding(top = 10.dp, start = 16.dp, end = 16.dp)) {
                    infos.forEachIndexed { idx, info ->
                        Row(Modifier
                            .clickable(onClick = { })) {
                            Icon(icons[idx],
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.inverseSurface,
                                modifier = Modifier.padding(start = 16.dp, top = 16.dp).size(18.dp))
                            Spacer(modifier = Modifier.padding(4.dp))
                            TextField(
                                value = info.value,
                                onValueChange = { info.value = it },
                                placeholder = { Text(text = placeholder[idx]) },
                                textStyle = MaterialTheme.typography.titleSmall,
                            )

                        }
                    }
                }
                Spacer(modifier = Modifier.size(10.dp))
                Button(onClick = {
                    db_Replace(scheduleItem, context)
                    navController.popBackStack()
                }, modifier = Modifier.padding(start = 140.dp)) {
                    Text(text = "完成編輯")
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun EditPreview() {
    MScheduleTheme {
        Surface {
            EditScreen(
                "1", rememberNavController()
            ) {}
        }
    }
}


@Composable
fun DropDown(
    data: MutableState<Int>,
    modifier: Modifier,
) {
    var expanded = remember {
        mutableStateOf(false)
    }
    val items = listOf("永不", "每天", "每周", "每月", "每年")
    val value = listOf(0, 1, 2, 3, 4)
    var text = remember { mutableStateOf("${items[data.value]}") }

    Box(
        modifier = modifier
    ) {
        Button(
            onClick = {
                expanded.value = true
            },
        ) {
            Text(text = text.value)
        }

        DropdownMenu(expanded = expanded.value, onDismissRequest = { expanded.value = false }) {
            items.forEachIndexed { idx, s ->
                DropdownMenuItem(
                    text = { Text(text = s) },
                    onClick = {
                        text.value = s
                        data.value = value[idx]
                        expanded.value = false
                    }
                )
            }
        }
    }
}