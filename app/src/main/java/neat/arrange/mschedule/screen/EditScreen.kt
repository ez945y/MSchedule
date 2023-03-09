package neat.arrange.mschedule.screen

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import neat.arrange.mschedule.R
import neat.arrange.mschedule.entity.*
import neat.arrange.mschedule.ui.theme.MScheduleTheme
import java.time.ZoneId
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
    val context = LocalContext.current
    val infos = listOf(
        scheduleItem.member,
        scheduleItem.schedule,
        scheduleItem.tag,
        scheduleItem.note
    )
    val showAlertDialog = remember { mutableStateOf(false) }
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
                navController = navController,
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
                    .size(0.dp, 290.dp)
                    .padding(start = 16.dp, end = 16.dp, top = 20.dp)) {
                    Column {
                        Row {
                            Icon(Icons.Filled.List,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.inverseSurface,
                                modifier = Modifier
                                    .padding(top = 20.dp, start = 16.dp)
                                    .size(18.dp))
                            TextField(
                                value = scheduleItem.title.value,
                                onValueChange = { scheduleItem.title.value = it },
                                textStyle = MaterialTheme.typography.titleLarge,
                            )
                        }
                        Row(modifier = Modifier.padding(top = 6.dp)) {
                            Icon(painterResource(id = R.drawable.allday),
                                modifier = Modifier
                                    .padding(start = 15.dp, top = 30.dp)
                                    .size(18.dp),
                                contentDescription = null)
                            Text(text = "整天",
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .padding(top = 25.dp, start = 12.dp, end = 30.dp),
                                fontSize = 16.sp
                            )
                            Switch(checked = scheduleItem.isAllDay.value,
                                onCheckedChange = { scheduleItem.isAllDay.value = it },
                                modifier = Modifier.padding(start = 150.dp, top = 15.dp))
                        }
                        Row {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(painterResource(id = R.drawable.date),
                                    modifier = Modifier
                                        .padding(start = 15.dp, top = 28.dp)
                                        .size(18.dp),
                                    contentDescription = null)
                                Icon(painterResource(id = R.drawable.time),
                                    modifier = Modifier
                                        .padding(start = 15.dp, top = 40.dp)
                                        .size(18.dp),
                                    contentDescription = null)

                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .padding(start = 14.dp)) {
                                Text(text = scheduleItem.startDate.value.format(formatter),
                                    textAlign = TextAlign.Center,
                                    fontSize = 20.sp,
                                    modifier = Modifier
                                        .padding(top = 20.dp)
                                        .clickable {
                                            datePicker(true,
                                                context,
                                                scheduleItem.startDate.value,
                                                onDateSelect = {
                                                    scheduleItem.startDate.value = it
                                                })
                                        }
                                )
                                if (!scheduleItem.isAllDay.value) {
                                    Text(text = "${scheduleItem.startTime.value}",
                                        textAlign = TextAlign.Center,
                                        fontSize = 20.sp,
                                        modifier = Modifier
                                            .padding(top = 20.dp)
                                            .clickable {
                                                timePicker(true,
                                                    context,
                                                    scheduleItem.startTime.value,
                                                    onTimeSelect = {
                                                        scheduleItem.startTime.value = it
                                                    })
                                            }
                                    )
                                } else {
                                    Text(text = "00:00",
                                        textAlign = TextAlign.Center,
                                        fontSize = 20.sp,
                                        modifier = Modifier
                                            .padding(top = 20.dp))
                                }
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .padding(start = 14.dp)) {

                                Icon(Icons.Filled.ArrowForward,
                                    modifier = Modifier
                                        .padding(top = 28.dp),
                                    contentDescription = null)
                                Icon(Icons.Filled.ArrowForward,
                                    modifier = Modifier
                                        .padding(top = 28.dp),
                                    contentDescription = null)


                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
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
                                if (!scheduleItem.isAllDay.value) {
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
                                } else {
                                    Text(text = "23:59",
                                        textAlign = TextAlign.Center,
                                        fontSize = 20.sp,
                                        modifier = Modifier
                                            .padding(top = 20.dp))
                                }
                            }
                        }

                    }
                }
            }

            Row {
                Icon(painterResource(id = R.drawable.repeat),
                    modifier = Modifier
                        .padding(start = 25.dp, top = 30.dp)
                        .size(18.dp),
                    contentDescription = null)
                Text(text = "重複",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 25.dp, start = 10.dp),
                    fontSize = 16.sp
                )
                DropDown(scheduleItem.isRepeat, Modifier.padding(start = 10.dp, top = 15.dp))
                Icon(painterResource(id = R.drawable.notice),
                    modifier = Modifier
                        .padding(start = 25.dp, top = 30.dp)
                        .size(18.dp),
                    contentDescription = null)
                Text(text = "提醒",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 25.dp, start = 20.dp),
                    fontSize = 16.sp
                )
                DropDown(scheduleItem.isRepeat, Modifier.padding(start = 10.dp, top = 15.dp))
            }

            Card(modifier = Modifier
                .fillMaxWidth()
                .size(0.dp, 280.dp)
                .padding(top = 20.dp, start = 16.dp, end = 16.dp)) {
                infos.forEachIndexed { idx, info ->
                    Row(Modifier
                        .clickable(onClick = { })) {
                        Icon(icons[idx],
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.inverseSurface,
                            modifier = Modifier
                                .padding(start = 16.dp, top = 22.dp)
                                .size(18.dp))
                        Spacer(modifier = Modifier.padding(4.dp))
                        if(idx !=1){
                        TextField(
                            value = info.value.toString(),
                            onValueChange = { info.value = it },
                            placeholder = { Text(text = placeholder[idx]) },
                            textStyle = MaterialTheme.typography.titleSmall,
                        )
                        }else{
                            DropDownCalender(info, modifier = Modifier.padding(start = 10.dp, top = 15.dp))
                        }

                    }
                    if(idx ==1){
                        Divider(color = MaterialTheme.colorScheme.secondary,
                            thickness = 1.dp,
                            modifier = Modifier
                                .padding(top = 15.dp, start =42.dp,end = 40.dp))
                    }
                }
            }
            Spacer(modifier = Modifier.size(20.dp))
            Row(horizontalArrangement = Arrangement.SpaceAround) {
                Button(onClick = {
                    showAlertDialog.value = true
                }, modifier = Modifier.padding(start = 40.dp)) {
                    Text(text = "刪除行程")
                }
                Button(onClick = {
                    if (scheduleItem.isAllDay.value) {
                        scheduleItem.startTime.value =
                            sdfTime.parse("00:00").toInstant().atZone(
                                ZoneId.systemDefault()).toLocalTime()
                        scheduleItem.endTime.value = sdfTime.parse("23:59").toInstant().atZone(
                            ZoneId.systemDefault()).toLocalTime()
                    }
                    dbReplace(scheduleItem, context)
                    navController.popBackStack()
                }, modifier = Modifier.padding(start = 60.dp)) {
                    Text(text = "完成編輯")
                }
            }

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
                            showAlertDialog.value = false
                            dbDelete(id = scheduleItem.id, context = context)
                            navController.popBackStack()
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
    val expanded = remember {
        mutableStateOf(false)
    }
    val items = listOf("永不", "每天", "每周", "每月", "每年")
    val value = listOf(0, 1, 2, 3, 4)
    val text = remember { mutableStateOf("${items[data.value]}") }

    Box(
        modifier = modifier
    ) {
        Button(
            onClick = {
                expanded.value = true
            }, modifier = Modifier
                .padding(top = 10.dp)
                .size(70.dp, 30.dp)
        ) {
            Text(text = text.value,fontSize = 8.sp)
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


@Composable
fun DropDownNotification(
    data: MutableState<Int>,
    modifier: Modifier,
) {
    val expanded = remember {
        mutableStateOf(false)
    }
    val items = listOf("永不", "每天", "每周", "每月", "每年")
    val value = listOf(0, 1, 2, 3, 4)
    val text = remember { mutableStateOf("${items[data.value]}") }

    Box(
        modifier = modifier
    ) {
        Button(
            onClick = {
                expanded.value = true
            }, modifier = Modifier
                .padding(top = 10.dp)
                .size(70.dp, 30.dp)
        ) {
            Text(text = text.value,fontSize = 8.sp)
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

@Composable
fun DropDownCalender(
    text: MutableState<String>,
    modifier: Modifier,
) {
    val expanded = remember {
        mutableStateOf(false)
    }

    Box(
        modifier = modifier
    ) {
        Text(
            text = if(text.value == ""){
                currentCalender.value}else{text.value},
            modifier = Modifier.padding(top = 2.dp,start = 2.dp).clickable {expanded.value = true }
        )
        DropdownMenu(expanded = expanded.value, onDismissRequest = { expanded.value = false }) {
            calenderItemList.forEach { s ->
                DropdownMenuItem(
                    text = { Text(text = s.name.value) },
                    onClick = {
                        text.value = s.name.value
                        expanded.value = false
                    }
                )
            }
        }
    }
}