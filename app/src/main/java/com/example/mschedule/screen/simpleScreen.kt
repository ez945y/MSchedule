package com.example.mschedule.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddCircle
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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mschedule.entity.scheduleItemList
import com.example.mschedule.entity.sdf
import com.example.mschedule.ui.theme.MScheduleTheme
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

@Composable
fun simpleScreen(dateId: String = "2023-01-28", navController: NavController,) {
    val formatter = DateTimeFormatter.ofPattern("MM月dd日 E", Locale.TAIWAN)
    val context = LocalContext.current
    var date = remember {
        mutableStateOf(sdf.parse(dateId).toInstant().atZone(
            ZoneId.systemDefault()).toLocalDate())
    }
    val tempList = scheduleItemList
    var index = remember { mutableStateOf(1) }
    Column {
        Row(
            modifier = Modifier
                .padding(vertical = 6.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(text = date.value.format(formatter),
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
            Icon(Icons.Outlined.AddCircle,
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 20.dp)
                    .clickable { navController.navigate("Add/$dateId") })
        }
        Box(modifier = Modifier.padding(top = 70.dp)) { //(horizontalAlignment = Alignment.CenterHorizontally)
            Card(modifier = Modifier
                .padding(start = 60.dp, end = 60.dp)
                .fillMaxWidth()
                .size(0.dp, 100.dp)
                .clickable {},
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiary)) {
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                    .padding(top = 16.dp)) {
                    Text("Past  ${tempList[index.value - 1].title.value}", modifier = Modifier
                        .padding(start = 30.dp), fontSize = 24.sp)
                }
            }

            Card(modifier = Modifier
                .padding(start = 60.dp, end = 60.dp, top = 350.dp)
                .fillMaxWidth()
                .size(0.dp, 100.dp)
                .clickable {},
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiary)) {
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                    .padding(top = 40.dp)) {
                    Text("Next  ${tempList[index.value + 1].title.value}", modifier = Modifier
                        .padding(start = 30.dp), fontSize = 24.sp)
                }
            }
            Card(modifier = Modifier
                .padding(top = 70.dp, start = 30.dp, end = 30.dp)
                .fillMaxWidth(),
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = 20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("10:45", modifier = Modifier
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
                    Text("別忘記帶自拍棒", modifier = Modifier
                        .padding(top = 25.dp, bottom = 25.dp), fontSize = 24.sp)
                }
            }
        }
    }
}

@Preview
@Composable
fun simplePreview() {
    MScheduleTheme {
        Surface {
            simpleScreen("2023-01-28",rememberNavController())
        }
    }

}