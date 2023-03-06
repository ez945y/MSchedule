package neat.arrange.mschedule.screen

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import neat.arrange.mschedule.entity.*
import neat.arrange.mschedule.ui.theme.MScheduleTheme
import neat.arrange.mschedule.R
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavController,
    openDrawer: () -> Unit,
) {
    val localDate = if (globalDate.value == "") {
        remember { mutableStateOf(LocalDate.now()) }
    } else {
        remember { mutableStateOf(
            sdf.parse(globalDate.value).toInstant().atZone(
                ZoneId.systemDefault()).toLocalDate()
        ) }
    }
    val formatterMonth = DateTimeFormatter.ofPattern("MM", Locale.TAIWAN)
    val formatterYear = DateTimeFormatter.ofPattern("yyyy", Locale.TAIWAN)
    val formatterDay = DateTimeFormatter.ofPattern("dd", Locale.TAIWAN)
    val context = LocalContext.current
    val textState = remember { mutableStateOf(TextFieldValue("")) }
    var showAlertDialog by remember { mutableStateOf(false) }
    val systemUiController = rememberSystemUiController() //設定導航欄透明色
    val yearNum = remember {
        mutableStateOf(localDate.value.format(formatterYear).toInt())
    }
    val monthNum = remember {
        mutableStateOf(localDate.value.format(formatterMonth).toInt())
    }
    val dayNum = remember {
        mutableStateOf(localDate.value.format(formatterDay).toInt())
    }
    val months = Months()
    val month = months.getMoon(yearNum.value, monthNum.value)

    val horizontalCount = remember {
        mutableStateOf(0.0)
    }
    val stateMonth = rememberScrollableState {
        horizontalCount.value += it
        if (horizontalCount.value < -400.0 && monthNum.value < 12) {
            monthNum.value += 1
            localDate.value =
                sdf.parse("${yearNum.value}-${monthNum.value}-${dayNum.value}").toInstant().atZone(
                    ZoneId.systemDefault()).toLocalDate()
            globalDate.value = localDate.value.format(formatterGobal)
            horizontalCount.value = 0.0

        }
        if (horizontalCount.value > 400.0 && monthNum.value > 1) {
            monthNum.value -= 1
            localDate.value =
                sdf.parse("${yearNum.value}-${monthNum.value}-${dayNum.value}").toInstant().atZone(
                    ZoneId.systemDefault()).toLocalDate()
            globalDate.value = localDate.value.format(formatterGobal)
            horizontalCount.value = 0.0

        }
        it
    }
    val stateDate = rememberScrollableState {
        horizontalCount.value += it
        if (horizontalCount.value < -400.0 && dayNum.value < 12) {
            dayNum.value += 1
            localDate.value =
                sdf.parse("${yearNum.value}-${monthNum.value}-${dayNum.value}").toInstant().atZone(
                    ZoneId.systemDefault()).toLocalDate()
            globalDate.value = localDate.value.format(formatterGobal)
            horizontalCount.value = 0.0
            dbSelect(localDate.value, context)

        }
        if (horizontalCount.value > 400.0 && dayNum.value > 1) {
            dayNum.value -= 1
            localDate.value =
                sdf.parse("${yearNum.value}-${monthNum.value}-${dayNum.value}").toInstant().atZone(
                    ZoneId.systemDefault()).toLocalDate()
            globalDate.value = localDate.value.format(formatterGobal)
            horizontalCount.value = 0.0
            dbSelect(localDate.value, context)
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
                MTopBar({ showAlertDialog = true }, "s", change, openDrawer)
            }
        },

        ) { contentPadding ->
        Box(modifier = Modifier.padding(contentPadding)) {
            if (change.value == 0) {
                simpleScreen(
                    localDate, yearNum, monthNum, dayNum,
                    navController = navController, state = stateDate,
                )
            } else {
                if (change.value == 1) {
                    DayScreen(
                        localDate = localDate,
                        yearNum = yearNum,
                        monthNum = monthNum,
                        dayNum = dayNum,
                        navController = navController,
                        state = stateDate,
                    )
                } else {
                    calenderScreen(
                        localDate = localDate,
                        yearNum = yearNum,
                        monthNum = monthNum,
                        dayNum = dayNum,
                        context = context,
                        month = month,
                        state = stateMonth,
                        change = change,
                    )
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
            change.value %= 2
        }, modifier = Modifier.padding(top = 770.dp, start = 170.dp)) {
            Icon(painterResource(R.drawable.resource_switch), null)
        }
        if (showAlertDialog) {
            ItemList(state = textState, LocalContext.current, navController) {showAlertDialog = false
                showAlertDialog = true}
        }
    }
}


fun datePicker(
    showFlag: Boolean,
    context: Context,
    date: LocalDate,
    onDateSelect: (LocalDate) -> Unit,
) {
    val listener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
        onDateSelect(LocalDate.of(year, monthOfYear + 1, dayOfMonth))
    }
    val datePickerDialog = DatePickerDialog(context, listener,
        date.year, date.monthValue - 1, date.dayOfMonth)

    if (showFlag) {
        datePickerDialog.show()
    }
}

fun timePicker(
    showFlag: Boolean,
    context: Context,
    time: LocalTime,
    onTimeSelect: (LocalTime) -> Unit,
) {
    val listener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
        onTimeSelect(LocalTime.of(hour, minute))
    }
    val timePickerDialog = TimePickerDialog(context, listener,
        time.hour, time.minute, true)//設置預設日期

    if (showFlag) {
        timePickerDialog.show()
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