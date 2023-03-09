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
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditCalenderScreen(id: String = "1", navController: NavController) {
    val context = LocalContext.current
    val calenderItem = calenderItemList[id.toInt()]
    val showAlertDialog = remember { mutableStateOf(false) }
    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        ElevatedCard(modifier = Modifier
            .padding(top = 200.dp)) {
            Text(text = "編輯行事曆",
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
                DropDownColor(calenderItem.color, Modifier.padding(start = 40.dp, top = 15.dp))
            }

            Row(horizontalArrangement = Arrangement.SpaceAround, modifier = Modifier.padding(top=20.dp, bottom = 20.dp)) {
                Button(onClick = {
                    showAlertDialog.value = true
                }, modifier = Modifier.padding(start = 15.dp)) {
                    Text(text = "刪除行事曆")
                }
                Button(onClick = {
                    dbReplaceCalender(calenderItem, context)
                    currentCalender.value = calenderItem.name.value
                    navController.popBackStack()
                }, modifier = Modifier.padding(start = 15.dp)) {
                    Text(text = "完成編輯")
                }
            }
            if (showAlertDialog.value) {
                AlertDialog(
                    onDismissRequest = {
                        showAlertDialog.value = false
                    },
                    title = {
                        Text("確認")
                    },
                    text = {
                        Text("請問是否要刪除行事曆")
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                showAlertDialog.value = false
                                dbDeleteCalender(id = calenderItem.id, context = context)
                                navController.popBackStack()
                            },
                            modifier = Modifier.padding()
                        )
                        {
                            Text(text = "確認")
                        }
                    },
                    dismissButton = {
                        Button(onClick = { showAlertDialog.value = false },
                            modifier = Modifier.padding(end = 50.dp))
                        {
                            Text(text = "取消")
                        }
                    }
                )
            }
        }
    }
}