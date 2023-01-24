package com.example.mschedule.screen

import android.app.DatePickerDialog
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mschedule.entity.Month
import com.example.mschedule.ui.theme.MScheduleTheme
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


fun datePicker(
    showFlag: Boolean,
    context: Context,
    date: LocalDate,
    onDateSelect: (LocalDate) -> Unit,
) {
    val listener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
        onDateSelect(LocalDate.of(year, monthOfYear, dayOfMonth))
    }
    val datePickerDialog = DatePickerDialog(context, listener, //設置監聽，當選擇日期時要做的處理
        date.year, date.monthValue, date.dayOfMonth)//設置預設日期

    if (showFlag) {
        datePickerDialog.show()
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    ScheduleList: List<Int> = listOf(),
    onSearchBarClick: () -> Unit,
    onAddScheduleClick: () -> Unit,
    onScheduleClick: (Long) -> Unit,
    openDrawer: () -> Unit,
) {
    val localDate = remember { mutableStateOf(LocalDate.now()) }
    val formatter = DateTimeFormatter.ofPattern("yyyy年MM月", Locale.TAIWAN)
    val context = LocalContext.current

    Scaffold(
        topBar = {
            MTopBar("日常", onSearchBarClick, "s", openDrawer)
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { onAddScheduleClick() }, content = {
                Icon(Icons.Filled.Add, null)
            })
        },
        bottomBar = {
            MBottomBar()
        },
    ) { contentPadding ->
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(contentPadding)
                .padding(4.dp),
        ) {
            //日期
            Card(modifier = Modifier
                .padding(8.dp)
                .padding(vertical = 15.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer)
            ) {
                Row(Modifier
                    .padding(top = 10.dp, bottom = 10.dp, start = 30.dp, end = 30.dp)
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center) {
                    Icon(Icons.Filled.ArrowBack,
                        modifier = Modifier.clickable {},
                        contentDescription = null)
                    Text(text = localDate.value.format(formatter),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(start = 30.dp, end = 30.dp)
                            .clickable {
                                datePicker(true, context, localDate.value, onDateSelect = {
                                    localDate.value = it
                                })
                            }
                    )
                    Icon(Icons.Filled.ArrowForward,
                        modifier = Modifier.clickable {},
                        contentDescription = null)
                }
            }

            Divider(color = MaterialTheme.colorScheme.secondary,
                thickness = 1.dp,
                modifier = Modifier
                    .padding(horizontal = 18.dp))
            WeekItem()
            //日曆
            val months = Month()
            Box {
                LazyColumn(modifier = Modifier.padding(horizontal = 8.dp)) {
                    items(months.m) { m ->
                        Divider(color = MaterialTheme.colorScheme.secondary,
                            thickness = 1.dp,
                            modifier = Modifier
                                .padding(top = 4.dp)
                                .padding(horizontal = 8.dp))
                        DateItem(m, onAddScheduleClick)
                    }
                }
                Card(modifier = Modifier.padding(start = 130.dp, top = 140.dp),
                    border = BorderStroke(1.dp, Color.Black),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer)
                ) {
                    Text(" 小豬歷險記 ",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.clickable {})
                }
                Card(modifier = Modifier.padding(top = 45.dp, start = 65.dp, end = 65.dp),
                    border = BorderStroke(1.dp, Color.Black),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
                ) {
                    Text(" 期中考 ",
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {})
                }
                Card(modifier = Modifier.padding(top = 320.dp, start = 165.dp, end = 65.dp),
                    border = BorderStroke(1.dp, Color.Black),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
                ) {
                    Text(" 台南旅遊 ",
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {})
                }

            }
            Divider(color = MaterialTheme.colorScheme.secondary,
                thickness = 1.dp,
                modifier = Modifier
                    .padding(horizontal = 18.dp))
        }
    }
}

@Composable
fun WeekItem(
) {
    val weeks = listOf("日", "一", "二", "三", "四", "五", "六")
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .padding(top = 2.dp),
    ) {
        LazyRow(modifier = Modifier
            .padding(8.dp)
            .padding(vertical = 6.dp)
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically) {
            itemsIndexed(weeks) { idx, week ->
                Text(
                    text = week,
                    textAlign = TextAlign.Center,
                    color = if (idx == 0 || idx == 6) {
                        Color.Red
                    } else {
                        Color.Unspecified
                    }
                )
            }
        }
    }
}

@Composable
fun DateItem(
    week: List<String>,
    onScheduleClick: () -> Unit,
) {
    Card {
        LazyRow(modifier = Modifier
            .padding(horizontal = 8.dp)
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically) {
            itemsIndexed(week) { idx, date ->
                Column(horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.clickable { onScheduleClick()}) {
                    Text(
                        text = date,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 2.dp),
                        color = if (idx == 0 || idx == 6) {
                            Color.Red
                        } else {
                            Color.Unspecified
                        }
                    )
                    Column() {
                        Box(modifier = Modifier.size(40.dp, 56.dp))
                    }

                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun MainPreview() {
    MScheduleTheme {
        Surface {
            MainScreen(onSearchBarClick = { /*TODO*/ },
                onAddScheduleClick = { /*TODO*/ },
                onScheduleClick = {}
            ) {
            }

        }
    }
}