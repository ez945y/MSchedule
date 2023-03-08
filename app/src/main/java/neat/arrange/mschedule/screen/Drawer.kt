package neat.arrange.mschedule.screen

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import neat.arrange.mschedule.R
import neat.arrange.mschedule.entity.calenderItemList
import neat.arrange.mschedule.entity.dbAddCalender
import neat.arrange.mschedule.entity.dbReStart
import neat.arrange.mschedule.entity.dbSelectCalender

sealed class DrawerScreens(val title: String, val route: String) {
    object Main : DrawerScreens("行事曆", "Main")
    object Add : DrawerScreens("Add", "Add")
    object AddCalender : DrawerScreens("AddCalender", "AddCalender")
    object Edit : DrawerScreens("Edit", "Edit")
    object EditCalender : DrawerScreens("EditCalender", "EditCalender")
    object Test : DrawerScreens("測試", "help")
    object Account : DrawerScreens("會員", "account")
    object Register : DrawerScreens("Register", "Register")
}

private val screens = listOf(
    DrawerScreens.Main,
    DrawerScreens.Account,
    DrawerScreens.Test,
)

@Composable
fun Drawer(
    modifier: Modifier = Modifier,
    onDestinationClicked: (route: String) -> Unit,
    context: Context,
) {
    val showAlertDialog = remember { mutableStateOf(false) }
    Card(modifier = Modifier.padding(end = 50.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiary)) {
        Column(
            modifier
                .fillMaxSize()
                .padding(start = 15.dp, top = 48.dp)
        ) {
            Row {
                Icon(painterResource(id = R.drawable.menu),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.background,
                    modifier = Modifier
                        .size(24.dp)
                        .padding(top = 3.dp))
                Spacer(modifier = Modifier.padding(4.dp))
                Text("選單",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.background,
                    modifier = Modifier.padding(start = 10.dp))
                Text("新增", modifier = Modifier.padding(start = 120.dp).clickable {onDestinationClicked(DrawerScreens.AddCalender.route) })
                Text("清空", modifier = Modifier.padding(start = 10.dp).clickable {showAlertDialog.value = true})
            }
            dbSelectCalender(context)
            if(calenderItemList.size<1){
                dbAddCalender("Main","0xFFDAE2FF",context)
            }
            Card(modifier = Modifier.padding(top = 20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onPrimary)) {
                LazyRow(modifier = Modifier.fillMaxWidth().padding(10.dp).padding(end=5.dp)) {
                    itemsIndexed(calenderItemList){ idx,calender ->
                        Card(modifier = Modifier
                            .padding(5.dp).padding(end=6.dp)
                            .clickable {onDestinationClicked("EditCalender/${idx}") },
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary)) {
                            Text(
                                calender.name.value,
                                color = MaterialTheme.colorScheme.background,
                                modifier = Modifier
                                    .size(88.dp)
                                    .padding(top = 30.dp),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
            Divider(color = MaterialTheme.colorScheme.background,
                thickness = 1.dp, modifier = Modifier.padding(top = 20.dp))
            screens.forEach { screen ->
                Spacer(Modifier.height(24.dp))
                Row(Modifier
                    .padding(end = 20.dp)
                    .clickable(onClick = { onDestinationClicked(screen.route) })) {
                    Icon(Icons.Outlined.Add,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.background,
                        modifier = Modifier.padding(top = 2.dp))
                    Spacer(modifier = Modifier.padding(4.dp))
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = screen.title,
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.background
                    )

                }
            }
            Divider(color = MaterialTheme.colorScheme.background,
                thickness = 1.dp, modifier = Modifier.padding(top = 40.dp))
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
                    Text("請問是否要重置資料庫")
                },
                confirmButton = {
                    Button(
                        onClick = {
                            dbReStart(context)
                            showAlertDialog.value = false
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