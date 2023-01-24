package com.example.mschedule.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mschedule.entity.ScheduleItemList
import com.example.mschedule.ui.theme.MScheduleTheme
import java.time.format.DateTimeFormatter
import java.util.*


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditScreen(
    ScheduleItemList: ScheduleItemList = ScheduleItemList(),
    onSearchBarClick: () -> Unit,
    openDrawer: () -> Unit,
) {
    val formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd", Locale.TAIWAN)
    val context = LocalContext.current
    var infos = listOf(
        ScheduleItemList.member,
        ScheduleItemList.schedule,
        ScheduleItemList.tag,
        ScheduleItemList.note
    )
    var icons = listOf(
        Icons.Filled.AccountBox,
        Icons.Filled.MailOutline,
        Icons.Filled.Lock,
        Icons.Filled.Check
    )
    Scaffold(
        topBar = {
            MTopBar("編輯行程", onSearchBarClick, icon = "c", openDrawer)
        },
        bottomBar = {
            MBottomBar()
        },
    ) { contentPadding ->
        Card(modifier = Modifier
            .padding(contentPadding)
            .fillMaxWidth()
            .size(0.dp, 750.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer)
        ) {
            Column {
                Card(modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end=16.dp,top = 40.dp)) {
                    Row {
                        Icon(Icons.Filled.List,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.inverseSurface,
                            modifier = Modifier.padding(top = 20.dp, start = 16.dp))
                        TextField(value = ScheduleItemList.title.value,
                            onValueChange = { ScheduleItemList.title.value = it },
                            textStyle = MaterialTheme.typography.titleLarge,
                            )
                    }
                }
                Row(modifier = Modifier.padding(top = 6.dp)) {
                    Text(text = ScheduleItemList.startTime.value.format(formatter),
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp,
                        modifier = Modifier
                            .padding(top = 20.dp, start = 30.dp, end = 30.dp)
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
                            .padding(top = 20.dp, start = 10.dp, end = 10.dp),
                        contentDescription = null)
                    Text(text = ScheduleItemList.endTime.value.format(formatter),
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp,
                        modifier = Modifier
                            .padding(top = 20.dp, start = 30.dp, end = 30.dp)
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
                        .padding(horizontal = 20.dp)
                        .padding(top = 6.dp))
                Row(modifier = Modifier.padding(top = 6.dp)) {
                    Text(text = "重複",
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(top = 20.dp, start = 30.dp, end = 30.dp),
                        fontSize = 20.sp
                    )
                    Switch(checked = ScheduleItemList.isRepeat.value,
                        onCheckedChange = { ScheduleItemList.isRepeat.value = it },
                        modifier = Modifier.padding(start = 180.dp, top = 10.dp))
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
fun EditPreview() {
    MScheduleTheme {
        Surface {
            EditScreen(
                onSearchBarClick = { /*TODO*/ },
            ) {

            }

        }
    }
}