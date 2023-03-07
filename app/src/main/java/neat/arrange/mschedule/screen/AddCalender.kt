package neat.arrange.mschedule.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import neat.arrange.mschedule.R
import neat.arrange.mschedule.entity.*
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScreen(calenderItem: CalenderItem = CalenderItem(66), navController: NavController) {
    val context = LocalContext.current
    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        ElevatedCard(modifier = Modifier
            .padding(top = 200.dp)) {
            Text(text = "新增行事曆",
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(start = 20.dp, top = 15.dp)
            )
            OutlinedTextField(
                value = calenderItem.name.value,
                onValueChange = { calenderItem.name.value = it },
                shape = CircleShape,
                label = {
                    Text("Calender Name",
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 6.dp))
                },
                modifier = Modifier.padding(start = 15.dp, top = 20.dp, end = 15.dp)
            )
            Row {
                Icon(painterResource(id = R.drawable.repeat),
                    modifier = Modifier
                        .padding(start = 25.dp, top = 30.dp)
                        .size(18.dp),
                    contentDescription = null)
                Text(text = "識別顏色",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 25.dp, start = 10.dp),
                    fontSize = 16.sp
                )
                DropDownColor(calenderItem.color, Modifier.padding(start = 10.dp, top = 15.dp))
            }
            Spacer(modifier = Modifier.size(10.dp))
            Button(onClick = {
                dbAddCalender(calenderItem.name.value, calenderItem.color.value, context)
                navController.popBackStack()
            }, modifier = Modifier.padding(start = 140.dp)) {
                Text(text = "完成新增")
            }
        }
    }
}


@Composable
fun DropDownColor(
    colorCode: MutableState<String>,
    modifier: Modifier,
) {
    val colorItems = listOf("0xFFDAE2FF", "0xFFDAE2FF", "0xFFDAE2FF", "0xFFDAE2FF", "0xFFDAE2FF")
    val colorNameItems = listOf("藍色", "紅色", "綠色", "紫色", "橘色")
    val colorName = remember { mutableStateOf("${colorNameItems[0]}") }
    val expanded = remember { mutableStateOf(false) }

    Box(
        modifier = modifier
    ) {
        Button(
            onClick = {
                expanded.value = true
            }, modifier = Modifier
                .padding(top = 10.dp)
                .size(70.dp, 30.dp)
        ) {
            Text(text = colorName.value, fontSize = 8.sp)
        }

        DropdownMenu(expanded = expanded.value, onDismissRequest = { expanded.value = false }) {
            colorItems.forEachIndexed { idx, s ->
                DropdownMenuItem(
                    text = { Text(text = s) },
                    onClick = {
                        colorName.value = s
                        colorCode.value = colorItems[idx]
                        expanded.value = false
                    }
                )
            }
        }
    }
}