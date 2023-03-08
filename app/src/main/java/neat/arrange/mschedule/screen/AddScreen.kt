package neat.arrange.mschedule.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
fun AddScreen(
    ScheduleItem: ScheduleItem = ScheduleItem(777),
    navController: NavController,
    dateId: String = "2023-01-28",
    openDrawer: () -> Unit,
) {
    val context = LocalContext.current
    val date = remember {
        mutableStateOf(sdf.parse(dateId).toInstant().atZone(
            ZoneId.systemDefault()).toLocalDate())
    }
    ScheduleItem.startDate.value = date.value
    ScheduleItem.endDate.value = date.value
    val infos = listOf(
        ScheduleItem.member,
        ScheduleItem.schedule,
        ScheduleItem.tag,
        ScheduleItem.note
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
                    .size(0.dp, 290.dp)
                    .padding(start = 16.dp, end = 16.dp, top = 20.dp)) {
                    Column {
                        Row {
                            Icon(Icons.Filled.List,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.inverseSurface,
                                modifier = Modifier.padding(top = 28.dp, start = 16.dp))
                            TextField(
                                value = ScheduleItem.title.value,
                                onValueChange = { ScheduleItem.title.value = it },
                                placeholder = { Text("新增標題") },
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
                                    .padding(top = 20.dp, start = 12.dp, end = 30.dp),
                                fontSize = 20.sp
                            )
                            Switch(checked = ScheduleItem.isAllDay.value,
                                onCheckedChange = { ScheduleItem.isAllDay.value = it },
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
                                Text(text = ScheduleItem.startDate.value.format(formatter),
                                    textAlign = TextAlign.Center,
                                    fontSize = 20.sp,
                                    modifier = Modifier
                                        .padding(top = 20.dp)
                                        .clickable {
                                            datePicker(true,
                                                context,
                                                ScheduleItem.startDate.value,
                                                onDateSelect = {
                                                    ScheduleItem.startDate.value = it
                                                })
                                        }
                                )
                                if (!ScheduleItem.isAllDay.value) {
                                    Text(text = ScheduleItem.startTime.value.format(formatterTime),
                                        textAlign = TextAlign.Center,
                                        fontSize = 20.sp,
                                        modifier = Modifier
                                            .padding(top = 20.dp)
                                            .clickable {
                                                timePicker(true,
                                                    context,
                                                    ScheduleItem.startTime.value,
                                                    onTimeSelect = {
                                                        ScheduleItem.startTime.value = it
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
                                Text(text = ScheduleItem.endDate.value.format(formatter),
                                    textAlign = TextAlign.Center,
                                    fontSize = 20.sp,
                                    modifier = Modifier
                                        .padding(top = 20.dp, start = 12.dp)
                                        .clickable {
                                            datePicker(true,
                                                context,
                                                ScheduleItem.endDate.value,
                                                onDateSelect = {
                                                    ScheduleItem.endDate.value = it
                                                })
                                        }
                                )
                                if (!ScheduleItem.isAllDay.value) {
                                    Text(text = ScheduleItem.endTime.value.format(formatterTime),
                                        textAlign = TextAlign.Center,
                                        fontSize = 20.sp,
                                        modifier = Modifier
                                            .padding(top = 20.dp, start = 10.dp, end = 12.dp)
                                            .clickable {
                                                timePicker(true,
                                                    context,
                                                    ScheduleItem.endTime.value,
                                                    onTimeSelect = {
                                                        ScheduleItem.endTime.value = it
                                                    }
                                                )
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

                Row {
                    Text(text = "重複",
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(top = 20.dp, start = 30.dp, end = 30.dp),
                        fontSize = 20.sp
                    )
                    DropDown(ScheduleItem.isRepeat, Modifier.padding(start = 150.dp, top = 15.dp))
                }

                Card(modifier = Modifier
                    .fillMaxWidth()
                    .size(0.dp, 310.dp)
                    .padding(top = 30.dp, start = 16.dp, end = 16.dp)) {
                    infos.forEachIndexed { idx, info ->
                        Row(Modifier
                            .padding(bottom = 10.dp)
                            .clickable(onClick = { })) {
                            Icon(icons[idx],
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.inverseSurface,
                                modifier = Modifier
                                    .padding(start = 16.dp, top = 16.dp)
                                    .size(18.dp))
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
                    if (ScheduleItem.isAllDay.value) {
                        ScheduleItem.startTime.value = sdfTime.parse("00:00").toInstant().atZone(
                            ZoneId.systemDefault()).toLocalTime()
                        ScheduleItem.endTime.value = sdfTime.parse("23:59").toInstant().atZone(
                            ZoneId.systemDefault()).toLocalTime()
                    }
                    dbAdd(ScheduleItem, context)
                    navController.popBackStack()
                }, modifier = Modifier.padding(start = 140.dp)) {
                    Text(text = "完成新增")
                }
            }
        }
    }
}



@Preview
@Composable
fun CreatePreview() {
    MScheduleTheme {
        Surface {
            AddScreen(ScheduleItem(777), rememberNavController()
            ) {}
        }
    }
}
