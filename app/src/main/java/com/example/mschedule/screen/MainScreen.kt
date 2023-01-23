package com.example.mschedule.screen

import android.app.DatePickerDialog
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mschedule.entity.Month
import com.example.mschedule.ui.theme.MScheduleTheme
import com.example.myapplication7.R
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

fun datePicker(
    showFlag: Boolean,
    context: Context,
    date: LocalDate,
    onDateSelect: (LocalDate) -> Unit,
) {
    val style = android.app.AlertDialog.THEME_HOLO_LIGHT//1~5

    val listener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
        onDateSelect(LocalDate.of(year, monthOfYear, dayOfMonth))
    }

    val datePickerDialog = DatePickerDialog(context, style, listener, //設置監聽，當選擇日期時要做的處理
        date.year, date.monthValue, date.dayOfMonth)//設置預設日期

    if (showFlag) {
        datePickerDialog.show()
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    ScheduleList: List<Int> = listOf(),
    onSearchBarClick: () -> Unit,
    onAddScheduleClick: () -> Unit,
    onScheduleClick: (Long) -> Unit,
    openDrawer: () -> Unit,
) {
    val localDate = remember { mutableStateOf(LocalDate.now()) }
    val formatter = DateTimeFormatter.ofPattern("yyyy年MM月", Locale.TAIWAN)
    val context = LocalContext.current

    Scaffold(
        topBar = {
            MTopBar(onSearchBarClick, openDrawer)
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
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(contentPadding)
                .padding(4.dp),
        ) {
            //日期
            Card(modifier = Modifier
                .padding(8.dp)
                .padding(vertical = 15.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer)
            ) {
                Text(text = localDate.value.format(formatter),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 10.dp, bottom = 10.dp, start = 30.dp, end = 30.dp)
                        .fillMaxWidth()
                        .clickable {
                            datePicker(true, context, localDate.value, onDateSelect = {
                                localDate.value = it
                            })
                        }
                )
            }
            Divider(color = MaterialTheme.colorScheme.secondary,
                thickness = 1.dp,
                modifier = Modifier
                    .padding(horizontal = 18.dp))
            WeekItem()
            //日曆
            val months = Month()
            LazyColumn(modifier = Modifier.padding(horizontal = 8.dp)) {
                items(months.m) { m ->
                    Divider(color = MaterialTheme.colorScheme.secondary,
                        thickness = 1.dp,
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .padding(horizontal = 8.dp))
                    DateItem(m)
                }
            }
        }
    }
}

@Composable
fun MBottomBar() {
    BottomAppBar(modifier = Modifier
        .padding(16.dp)
        .padding(bottom = 20.dp)) {
        IconButton(onClick = { /*TODO*/ }, modifier = Modifier.weight(1f)) {
            Icon(Icons.Filled.Edit, null)
        }
        IconButton(onClick = { /*TODO*/ }, modifier = Modifier.weight(1f)) {
            Icon(Icons.Filled.Build, null)
        }
        IconButton(onClick = { /*TODO*/ }, modifier = Modifier.weight(1f)) {
            Icon(Icons.Filled.Share, null)
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
) {
    Card{
        LazyRow(modifier = Modifier
            .padding(horizontal = 8.dp)
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically) {
            itemsIndexed(week) { idx, date ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
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
                    Spacer(modifier = Modifier.padding(vertical = 35.dp))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MTopBar(
    onSearchBarClick: () -> Unit,
    onButtonClicked: () -> Unit,
) {
    CenterAlignedTopAppBar(
        title = {
            Text(text = "日常", textAlign = TextAlign.Center)
        },
        navigationIcon = {
            IconButton(onClick = { onButtonClicked() }) {
                Icon(Icons.Filled.Menu, null)
            }
        },
        actions = {
            IconButton(onClick = { onSearchBarClick() } //do something
            ) {
                Icon(Icons.Filled.Search, null)
            }
        },
        modifier = Modifier
            .padding(8.dp)
            .padding(horizontal = 8.dp),
    )
}

sealed class DrawerScreens(val title: String, val route: String) {
    object Home : DrawerScreens("Home", "home")
    object Account : DrawerScreens("Account", "account")
    object Help : DrawerScreens("Help", "help")
}

private val screens = listOf(
    DrawerScreens.Home,
    DrawerScreens.Account,
    DrawerScreens.Help
)

@Composable
fun Drawer(
    modifier: Modifier = Modifier,
    onDestinationClicked: (route: String) -> Unit,
) {
    Column(
        modifier
            .fillMaxSize()
            .padding(start = 24.dp, top = 48.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = "App icon"
        )
        screens.forEach { screen ->
            Spacer(Modifier.height(24.dp))
            Text(
                text = screen.title,
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.clickable {
                    onDestinationClicked(screen.route)
                }
            )
        }
    }
}

@Composable
fun Home(openDrawer: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        MTopBar({},
            onButtonClicked = { openDrawer() }
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Home Page content here.")
        }
    }
}

@Composable
fun Account(openDrawer: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        MTopBar(
            {},
            onButtonClicked = { openDrawer() }
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Account.", style = MaterialTheme.typography.titleSmall)
        }
    }
}

@Composable
fun Help(navController: NavController) {
    Column(modifier = Modifier.fillMaxSize()) {
        MTopBar({},
            onButtonClicked = { navController.popBackStack() }
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Help.", style = MaterialTheme.typography.titleSmall)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun MainPreview() {
    MScheduleTheme {
        Surface {
            val navController = rememberNavController()
            val drawerState = rememberDrawerState(DrawerValue.Closed)
            val scope = rememberCoroutineScope()
            val openDrawer = {
                scope.launch {
                    drawerState.open()
                }
            }
            ModalNavigationDrawer(
                drawerState = drawerState,
                gesturesEnabled = drawerState.isOpen,
                drawerContent = {
                    Drawer(
                        onDestinationClicked = { route ->
                            scope.launch {
                                drawerState.close()
                            }
                            navController.navigate(route) {
                                popUpTo = navController.graph.startDestinationId
                                launchSingleTop = true
                            }
                        }
                    )
                }
            ) {
                NavHost(
                    navController = navController,
                    startDestination = "Main"
                ) {
                    composable(route = "Main"){
                        MainScreen(onSearchBarClick = {}, onAddScheduleClick = {}, onScheduleClick = {},openDrawer = {
                            openDrawer()
                        })
                    }
                    composable(DrawerScreens.Home.route) {
                        Home(
                            openDrawer = {
                                openDrawer()
                            }
                        )
                    }
                    composable(DrawerScreens.Account.route) {
                        Account(
                            openDrawer = {
                                openDrawer()
                            }
                        )
                    }
                    composable(DrawerScreens.Help.route) {
                        Help(
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}