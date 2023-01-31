package com.example.mschedule.screen

import android.app.DatePickerDialog
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mschedule.entity.*
import com.example.mschedule.ui.theme.MScheduleTheme
import com.example.myapplication7.R
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import java.time.LocalDate
import java.time.ZoneId
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
    navController: NavController,
    openDrawer: () -> Unit,
) {
    val localDate = remember { mutableStateOf(LocalDate.now()) }
    val formatterMonth = DateTimeFormatter.ofPattern("MM", Locale.TAIWAN)
    val formatterYear = DateTimeFormatter.ofPattern("yyyy", Locale.TAIWAN)
    val formatterDay = DateTimeFormatter.ofPattern("dd", Locale.TAIWAN)
    val context = LocalContext.current
    val textState = remember { mutableStateOf(TextFieldValue("")) }
    var showAlertDialog by remember { mutableStateOf(false) }
    val systemUiController = rememberSystemUiController() //設定導航欄透明色
    var yearNum = remember {
        mutableStateOf(localDate.value.format(formatterYear).toInt())
    }
    var monthNum = remember {
        mutableStateOf(localDate.value.format(formatterMonth).toInt())
    }
    var dayNum = remember {
        mutableStateOf(localDate.value.format(formatterDay).toInt())
    }
    val months = Months()
    val month = months.getMoon(yearNum.value, monthNum.value)

    val horizontalCount = remember {
        mutableStateOf(0.0)
    }
    val state = rememberScrollableState {
        horizontalCount.value += it
        if (horizontalCount.value < -400.0 && monthNum.value < 12) {
            monthNum.value += 1
            horizontalCount.value = 0.0

        }
        if (horizontalCount.value > 400.0 && monthNum.value > 1) {
            monthNum.value -= 1
            horizontalCount.value = 0.0
        }
        it
    }
    val change = remember {
        mutableStateOf(0)
    }
    systemUiController.setNavigationBarColor(Color.Transparent, darkIcons = false)
    Scaffold(
        topBar = {
            if (showAlertDialog) {
                textState.value = TextFieldValue("")
                Column {
                    Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)) {
                        Spacer(modifier = Modifier
                            .fillMaxWidth()
                            .size(0.dp, 37.dp))
                    }
                    SearchScreen(textState) { showAlertDialog = false }
                }
            } else {
                MTopBar("日常", { showAlertDialog = true }, "s", openDrawer)
            }
        },

        ) { contentPadding ->
        Box(modifier = Modifier.padding(contentPadding)) {
            if (change.value == 0) {
                simpleScreen(dateId = "${yearNum.value}-${monthNum.value}-${dayNum.value}",
                    navController = navController)
            } else {
                if (change.value == 1) {
                    DayScreen(
                        dateId = "${yearNum.value}-${monthNum.value}-${dayNum.value}",
                        navController = navController,
                    )
                } else {
                    calenderScreen(yearNum = yearNum,
                        monthNum = monthNum,
                        context = context,
                        localDate = localDate,
                        navController = navController,
                        month = month,
                        state = state)
                }
            }

            /*
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
             */


        }
        IconButton(onClick = {
            change.value += 1
            change.value %= 3
        }, modifier = Modifier.padding(top = 790.dp, start = 170.dp)) {
            Icon(painterResource(R.drawable.resource_switch), null)
        }
        if (showAlertDialog) {
            ItemList(state = textState)
        }
    }
}

@Composable
fun calenderScreen(
    yearNum: MutableState<Int>,
    monthNum: MutableState<Int>,
    context: Context,
    localDate: MutableState<LocalDate>,
    navController: NavController,
    month: ArrayList<ArrayList<String>>,
    state: ScrollableState,
) {
    Card(modifier = Modifier
        .padding(8.dp)
        .padding(vertical = 15.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer)) {
        Row(Modifier
            .padding(top = 10.dp, bottom = 10.dp, start = 30.dp, end = 30.dp)
            .fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Icon(Icons.Filled.ArrowBack,
                modifier = Modifier.clickable { yearNum.value -= 1 },
                contentDescription = null)
            Text(text = "${yearNum.value}年 ${monthNum.value} 月",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(start = 30.dp, end = 30.dp)
                    .clickable {
                        datePicker(true, context, localDate.value, onDateSelect = {
                            localDate.value = it
                        })
                    })
            Icon(Icons.Filled.ArrowForward,
                modifier = Modifier.clickable { yearNum.value += 1 },
                contentDescription = null)
        }
        Divider(color = MaterialTheme.colorScheme.secondary,
            thickness = 1.dp,
            modifier = Modifier.padding(horizontal = 18.dp))
        WeekItem()
        //日曆
        LazyColumn(modifier = Modifier
            .padding(horizontal = 8.dp)
            .scrollable(
                state = state, orientation = Orientation.Horizontal,
            )) {
            items(month) { m ->
                Divider(color = MaterialTheme.colorScheme.secondary,
                    thickness = 1.dp,
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .padding(horizontal = 8.dp))
                DateItem(m,
                    navController,
                    yearNum.value.toString(),
                    monthNum.value.toString(),
                    context)
            }
        }
        Divider(color = MaterialTheme.colorScheme.secondary,
            thickness = 1.dp,
            modifier = Modifier
                .padding(horizontal = 18.dp)
                .padding(bottom = 32.dp))
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
                Text(text = week, textAlign = TextAlign.Center, color = if (idx == 0 || idx == 6) {
                    Color.Red
                } else {
                    Color.Unspecified
                })
            }
        }
    }
}

@Composable
fun DateItem(
    week: List<String>,
    navController: NavController,
    year: String,
    month: String,
    context: Context,
) {
    Card {
        LazyRow(modifier = Modifier
            .padding(horizontal = 8.dp)
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically) {
            itemsIndexed(week) { idx, date ->
                Column(horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.clickable { navController.navigate("Day/$year-$month-$date") }) {
                    Text(text = date,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 2.dp),
                        color = if (idx == 0 || idx == 6) {
                            Color.Red
                        } else {
                            Color.Unspecified
                        })
                    Column {
                        Box(modifier = Modifier.size(40.dp, 56.dp),
                            contentAlignment = Alignment.Center) {

                            if (db_Check(sdf.parse("$year-$month-$date").toInstant()
                                    .atZone(ZoneId.systemDefault()).toLocalDate(), context)
                            ) {
                                Icon(Icons.Filled.Done, null)
                            }

                        }
                    }

                }
            }
        }
    }
}


@Preview
@Composable
fun MainPreview() {
    MScheduleTheme {
        Surface {
            MainScreen(
                navController = rememberNavController(),
            ) {}

        }
    }
}