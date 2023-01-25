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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mschedule.entity.Month
import com.example.mschedule.entity.ScheduleItem
import com.example.mschedule.entity.ScheduleViewModel
import com.example.mschedule.entity.scheduleItemList
import com.example.mschedule.ui.theme.MScheduleTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
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
    scheduleVM: ScheduleViewModel = viewModel(),
    onAddScheduleClick: () -> Unit,
    navController:NavController,
    openDrawer: () -> Unit,
) {
    val localDate = remember { mutableStateOf(LocalDate.now()) }
    val formatter = DateTimeFormatter.ofPattern("yyyy年MM月", Locale.TAIWAN)
    val context = LocalContext.current
    val textState = remember { mutableStateOf(TextFieldValue("")) }
    var showAlertDialog by remember { mutableStateOf(false) }
    val systemUiController = rememberSystemUiController() //設定導航欄透明色
    val scheduleList = scheduleVM.scheduleList
    systemUiController.setNavigationBarColor(Color.Transparent, darkIcons = false)
    Scaffold(
        topBar = {
            if (showAlertDialog) {
                textState.value = TextFieldValue("")
                Column {
                    Card(colors =CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)) {
                        Spacer(modifier = Modifier.fillMaxWidth().size(0.dp,37.dp))
                    }
                    SearchScreen(textState) { showAlertDialog = false }
                }
            } else {
                MTopBar("日常", { showAlertDialog = true }, "s", openDrawer)
            }
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
        Box(modifier = Modifier
            .padding(contentPadding)) {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
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
                            DateItem(m, navController)
                        }
                    }
                    Card(modifier = Modifier.padding(start = 130.dp, top = 140.dp),
                        border = BorderStroke(1.dp, Color.Black),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer)
                    ) {
                        Text("${scheduleList[0].title.value}",
                            textAlign = TextAlign.Center,
                            modifier = Modifier.size(80.dp,30.dp).clickable {navController.navigate("Edit"+"/"+"${scheduleList[0].id}") })
                    }
                    Card(modifier = Modifier.padding(top = 45.dp, start = 65.dp, end = 65.dp),
                        border = BorderStroke(1.dp, Color.Black),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
                    ) {
                        Text("${scheduleList[1].title.value}",
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {navController.navigate("Edit"+"/"+"${scheduleList[1].id}") })
                    }
                    Card(modifier = Modifier.padding(top = 320.dp, start = 165.dp, end = 65.dp),
                        border = BorderStroke(1.dp, Color.Black),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
                    ) {
                        Text("${scheduleList[2].title.value}",
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {navController.navigate("Edit"+"/"+"${scheduleList[2].id}") })
                    }

                }
                Divider(color = MaterialTheme.colorScheme.secondary,
                    thickness = 1.dp,
                    modifier = Modifier
                        .padding(horizontal = 18.dp))
            }
            if (showAlertDialog) {
                ItemList(state = textState)
            }
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
    navController:NavController,
) {
    Card {
        LazyRow(modifier = Modifier
            .padding(horizontal = 8.dp)
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically) {
            itemsIndexed(week) { idx, date ->
                Column(horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.clickable { }) {
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
            MainScreen(
                onAddScheduleClick = { /*TODO*/ },
                navController = rememberNavController()
            ) {
            }

        }
    }
}