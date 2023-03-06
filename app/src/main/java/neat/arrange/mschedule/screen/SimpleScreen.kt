package neat.arrange.mschedule.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import neat.arrange.mschedule.entity.ScheduleViewModel
import neat.arrange.mschedule.entity.dbSelect
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@Composable
fun simpleScreen(
    localDate: MutableState<LocalDate>,
    yearNum: MutableState<Int>,
    monthNum: MutableState<Int>,
    dayNum: MutableState<Int>,
    navController: NavController,
    state: ScrollableState,
) {
    val formatter = DateTimeFormatter.ofPattern("MM月dd日 E", Locale.TAIWAN)
    val context = LocalContext.current
    val formatterMonth = DateTimeFormatter.ofPattern("MM", Locale.TAIWAN)
    val formatterYear = DateTimeFormatter.ofPattern("yyyy", Locale.TAIWAN)
    val formatterDay = DateTimeFormatter.ofPattern("dd", Locale.TAIWAN)
    yearNum.value = localDate.value.format(formatterYear).toInt()
    monthNum.value = localDate.value.format(formatterMonth).toInt()
    dayNum.value = localDate.value.format(formatterDay).toInt()

    var flag = remember { mutableStateOf(true) }
    if (flag.value) {
        dbSelect(localDate.value, context)
        flag.value = false
    }
    val scheduleVM = ScheduleViewModel()
    val tempList = scheduleVM.scheduleList
    val index = remember { mutableStateOf(0) }
    Card(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .scrollable(
            state = state, orientation = Orientation.Horizontal,
        ),
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
                Text(text = localDate.value.format(formatter),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(start = 20.dp, top = 20.dp, bottom = 15.dp)
                        .clickable {
                            datePicker(true, context, localDate.value, onDateSelect = {
                                localDate.value = it
                            })
                        }
                )
                Icon(Icons.Outlined.AddCircle,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(end = 20.dp)
                        .clickable { navController.navigate("Add/${yearNum.value}-${monthNum.value}-${dayNum.value}") })
            }
            if (tempList.isEmpty()) {
                Button(modifier = Modifier
                    .padding(top = 200.dp, start = 120.dp),
                    onClick = { navController.navigate("Add/${yearNum.value}-${monthNum.value}-${dayNum.value}") }) {
                    Icon(Icons.Outlined.AddCircle,
                        contentDescription = null,
                        modifier = Modifier.padding(end = 15.dp))
                    Text("新增行程",
                        modifier = Modifier
                            .padding(end = 5.dp)
                            .padding(vertical = 10.dp))
                }
            } else {
                Box(modifier = Modifier.padding(top = 70.dp)) { //(horizontalAlignment = Alignment.CenterHorizontally)
                    if (tempList.size > 1 && index.value > 0) {
                        Card(modifier = Modifier
                            .padding(start = 60.dp, end = 60.dp)
                            .fillMaxWidth()
                            .size(0.dp, 100.dp)
                            .clickable { index.value -= 1 },
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiary)) {

                            Column(horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .padding(top = 16.dp)) {
                                Text("Past  ${tempList[index.value - 1].title.value}",
                                    modifier = Modifier
                                        .padding(start = 30.dp),
                                    fontSize = 24.sp)
                            }
                        }
                    }
                    if (tempList.size > 1 && index.value < tempList.size - 1) {
                        Card(modifier = Modifier
                            .padding(start = 60.dp, end = 60.dp, top = 350.dp)
                            .fillMaxWidth()
                            .size(0.dp, 100.dp)
                            .clickable { index.value += 1 },
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiary)) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .padding(top = 40.dp)) {
                                Text("Next  ${tempList[index.value + 1].title.value}",
                                    modifier = Modifier
                                        .padding(start = 30.dp),
                                    fontSize = 24.sp)
                            }
                        }
                    }
                    Card(modifier = Modifier
                        .padding(top = 70.dp, start = 30.dp, end = 30.dp)
                        .fillMaxWidth().clickable { navController.navigate("Edit/${index.value}")  },
                        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 20.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("${tempList[index.value].startTime.value}", modifier = Modifier
                                .padding(top = 25.dp, bottom = 8.dp), fontSize = 24.sp)
                            Divider(color = MaterialTheme.colorScheme.surfaceVariant,
                                thickness = 1.dp,
                                modifier = Modifier
                                    .padding(horizontal = 50.dp))
                            Text("${tempList[index.value].title.value}", modifier = Modifier
                                .padding(top = 40.dp, bottom = 40.dp), fontSize = 35.sp)
                            Divider(color = MaterialTheme.colorScheme.surfaceVariant,
                                thickness = 1.dp,
                                modifier = Modifier
                                    .padding(horizontal = 50.dp))
                            if (tempList[index.value].note.value !=""){
                                Text("${tempList[index.value].note.value}", modifier = Modifier
                                    .padding(top = 25.dp, bottom = 25.dp), fontSize = 24.sp)
                            }else{
                                Text("尚無備註", modifier = Modifier
                                    .padding(top = 25.dp, bottom = 25.dp), fontSize = 24.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}
