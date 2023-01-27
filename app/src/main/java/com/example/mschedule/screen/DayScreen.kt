package com.example.mschedule.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mschedule.entity.ScheduleItem
import com.example.mschedule.entity.ScheduleViewModel
import com.example.mschedule.entity.db_Select
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DayScreen(
    id: String = "1",
    navController: NavController,
    openDrawer: () -> Unit,
) {
    val sdf = SimpleDateFormat("yyyy-MM-dd")
    val formatter = DateTimeFormatter.ofPattern("MM月dd日 E", Locale.TAIWAN)
    val context = LocalContext.current
    var date = remember {
        mutableStateOf(sdf.parse("2023-01-$id").toInstant().atZone(
            ZoneId.systemDefault()).toLocalDate())
    }

    val scheduleVM= ScheduleViewModel(db_Select(date.value,context,))
    val scheduleList = scheduleVM.scheduleList
    Scaffold(
        topBar = {
            MTopBar("行程安排", {navController.popBackStack() }, icon = "c", openDrawer)
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
                Row(
                    modifier = Modifier
                        .padding(vertical = 6.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(text = "${date.value.format(formatter)}",
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(start = 20.dp, top = 20.dp, bottom = 15.dp)
                            .clickable {
                                datePicker(true, context, date.value, onDateSelect = {
                                    date.value = it
                                })
                            }
                    )
                    Icon(Icons.Outlined.Add,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(end = 20.dp)
                            .clickable { navController.navigate("Add") })
                }
                if (scheduleList.isEmpty()) {
                    Button(modifier = Modifier
                        .padding(top = 100.dp, start = 50.dp),
                        onClick = { navController.navigate("Add") }) {
                        Text("Add Schedule")
                    }
                } else {
                    Divider(color = MaterialTheme.colorScheme.secondary,
                        thickness = 1.dp,
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .padding(horizontal = 8.dp))
                    LazyColumn(modifier = Modifier.padding(bottom = 20.dp)) {
                        items(scheduleList) { schedule ->
                            ScheduleItemDisplay(schedule, navController)

                        }
                    }

                }
            }
        }
    }
}

@Composable
fun ScheduleItemDisplay(schedule: ScheduleItem, navController: NavController) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 8.dp)) {
        Row(
            modifier = Modifier
                .padding(vertical = 6.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "口 ${schedule.title.value}",
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(start = 20.dp),
            )
            Icon(Icons.Filled.Edit,
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 20.dp)
                    .size(24.dp)
                    .clickable { navController.navigate("Edit/${schedule.id}") })
        }

    }
}

@Preview
@Composable
fun DayPreview() {
    DayScreen("1", rememberNavController(), {})
}