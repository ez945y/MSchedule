package com.example.mschedule.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mschedule.entity.ScheduleItem
import com.example.mschedule.entity.ScheduleViewModel
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DayScreen(
    id: String = "1",
    navController: NavController,
    openDrawer: () -> Unit,
    scheduleVM: ScheduleViewModel = viewModel(),
) {
    val sdf = SimpleDateFormat("yyyy-MM-dd")
    val localDate = remember { mutableStateOf(LocalDate.now()) }
    val context = LocalContext.current
    var date = remember {
        mutableStateOf(sdf.parse("2023-01-$id").toInstant().atZone(
            ZoneId.systemDefault()).toLocalDate())
    }
    val scheduleList = scheduleVM.scheduleList
    Scaffold(
        topBar = {
            MTopBar("日曆安排", { }, icon = "c", openDrawer)
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
                Text(text = "日期: ${date.value.toString()}",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(start = 20.dp, top = 20.dp, bottom = 15.dp)
                        .clickable {
                            datePicker(true, context, date.value, onDateSelect = {
                                date.value = it
                            })
                        }
                )
                if (scheduleList.isEmpty()) {
                    Button(onClick = { navController.navigate("Add") }) {
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
                            Divider(color = MaterialTheme.colorScheme.secondary,
                                thickness = 1.dp,
                                modifier = Modifier
                                    .padding(top = 8.dp)
                                    .padding(horizontal = 8.dp))
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
                .padding(top = 6.dp, bottom = 6.dp)
                .clickable { navController.navigate("Edit/${schedule.id}") },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            Text(
                text = schedule.title.value,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(start = 20.dp),
            )
            Icon(Icons.Filled.Edit, contentDescription = null)
        }

    }
}

@Preview
@Composable
fun DayPreview() {
    DayScreen("1", rememberNavController(), {})
}