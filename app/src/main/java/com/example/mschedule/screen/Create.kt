package com.example.mschedule.screen

import android.content.Context
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mschedule.entity.ScheduleItem
import com.example.mschedule.ui.theme.MScheduleTheme
import java.time.format.DateTimeFormatter
import java.util.*


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateScreen(
    ScheduleItemList: ScheduleItem = ScheduleItem(1),
    onSearchBarClick: () -> Unit,
    openDrawer: () -> Unit,
) {
    val formatter = DateTimeFormatter.ofPattern("yy/MM/dd", Locale.TAIWAN)
    val context = LocalContext.current
    var infos = listOf(
        ScheduleItemList.member,
        ScheduleItemList.schedule,
        ScheduleItemList.tag,
        ScheduleItemList.note
    )
    val placeholder = listOf(
        "新增成員",
        "設定行事曆",
        ScheduleItemList.tag,"設定標籤",
        ScheduleItemList.note,"新增備註"
    )
    val icons = listOf(
        Icons.Filled.AccountBox,
        Icons.Filled.MailOutline,
        Icons.Filled.Lock,
        Icons.Filled.Check
    )
    Scaffold(
        topBar = {
            MTopBar("新增行程", onSearchBarClick, icon = "c", openDrawer)
        },
        bottomBar = {
            MBottomBar()
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
                    .size(0.dp, 185.dp)
                    .padding(start = 16.dp, end = 16.dp, top = 20.dp)) {
                    Column() {
                        Row {
                            Icon(Icons.Filled.List,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.inverseSurface,
                                modifier = Modifier.padding(top = 28.dp, start = 16.dp))
                            TextField(
                                value = ScheduleItemList.title.value,
                                onValueChange = { ScheduleItemList.title.value = it },
                                placeholder = { Text("新增標題") },
                                textStyle = MaterialTheme.typography.titleLarge,
                            )
                        }
                        Row(modifier = Modifier.padding(top = 6.dp)) {
                            Icon(Icons.Filled.Lock,
                                modifier = Modifier
                                    .clickable {}
                                    .padding(start = 15.dp, top = 28.dp),
                                contentDescription = null)
                            Text(text = ScheduleItemList.startTime.value.format(formatter),
                                textAlign = TextAlign.Center,
                                fontSize = 20.sp,
                                modifier = Modifier
                                    .padding(top = 20.dp, start = 10.dp, end = 12.dp)
                                    .clickable {
                                        datePicker(true,
                                            context,
                                            ScheduleItemList.startTime.value,
                                            onDateSelect = {
                                                ScheduleItemList.startTime.value = it
                                            })
                                    }
                            )
                            Icon(Icons.Filled.ArrowForward,
                                modifier = Modifier
                                    .clickable {}
                                    .padding(top = 28.dp),
                                contentDescription = null)
                            Text(text = ScheduleItemList.endTime.value.format(formatter),
                                textAlign = TextAlign.Center,
                                fontSize = 20.sp,
                                modifier = Modifier
                                    .padding(top = 20.dp, start = 12.dp)
                                    .clickable {
                                        datePicker(true,
                                            context,
                                            ScheduleItemList.endTime.value,
                                            onDateSelect = {
                                                ScheduleItemList.endTime.value = it
                                            })
                                    }
                            )

                        }
                        Divider(color = MaterialTheme.colorScheme.secondary,
                            thickness = 1.dp,
                            modifier = Modifier
                                .padding(horizontal = 26.dp)
                                .padding(start = 20.dp, top = 6.dp))
                    }
                }
                Row(modifier = Modifier.padding(top = 6.dp)) {
                    Text(text = "整天",
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(top = 20.dp, start = 30.dp, end = 30.dp),
                        fontSize = 20.sp
                    )
                    Switch(checked = ScheduleItemList.isAllDay.value,
                        onCheckedChange = { ScheduleItemList.isAllDay.value = it },
                        modifier = Modifier.padding(start = 180.dp, top = 15.dp))
                }
                Row {
                    Text(text = "重複",
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(top = 20.dp, start = 30.dp, end = 30.dp),
                        fontSize = 20.sp
                    )
                    DropDownTest(Modifier.padding(start = 150.dp, top = 15.dp))
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
                                modifier = Modifier.padding(start = 16.dp, top = 16.dp))
                            Spacer(modifier = Modifier.padding(4.dp))
                            TextField(
                                value = info.value,
                                onValueChange = { info.value = it },
                                placeholder={ Text(text="${placeholder[idx]}")},
                                textStyle = MaterialTheme.typography.titleSmall,
                            )

                        }
                    }
                }
                Spacer(modifier = Modifier.size(40.dp))
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun CreatePreview() {
    MScheduleTheme {
        Surface {
            EditScreen(
                onSearchBarClick = { /*TODO*/ },
            ) {

            }

        }
    }
}
