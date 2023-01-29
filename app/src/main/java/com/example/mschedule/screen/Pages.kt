package com.example.mschedule.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mschedule.entity.Months
import com.example.mschedule.ui.theme.MScheduleTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@Composable
fun Home(openDrawer: () -> Unit) {
    var showAlertDialog by remember { mutableStateOf(false) }
    Column(modifier = Modifier.fillMaxSize()) {
        if (showAlertDialog) {
            AlertDialog(onDismissRequest = {
                // 點擊 彈出視窗 外的區域觸發
                showAlertDialog = false
            }, title = {
                Text("Run AlertDialog")
            }, text = {
                Text("This is a dialog.")
            }, confirmButton = {
                Button(onClick = {
                    showAlertDialog = false
                }) {
                    Text(text = "確認按鈕")
                }
            }, dismissButton = {
                Button(onClick = { showAlertDialog = false }) {
                    Text(text = "取消按鈕")
                }
            })
        }
        MTopBar("Home", { showAlertDialog = true }, onButtonClicked = { openDrawer() })
        Column(modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Home Page content here.")
        }
    }
}

@Composable
fun Account(openDrawer: () -> Unit) {
    var showAlertDialog by remember { mutableStateOf(false) }
    Column(modifier = Modifier.fillMaxSize()) {
        if (showAlertDialog) {
            val textState = remember { mutableStateOf(TextFieldValue("")) }
            Column {
                SearchScreen(textState) { showAlertDialog = false }
                ItemList(state = textState)
            }
        } else {
            MTopBar("會員資訊", { showAlertDialog = true }, onButtonClicked = {
                openDrawer()
            })
        }
        Column(modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Account.", style = MaterialTheme.typography.titleSmall)
        }
    }

}

@Composable
fun Help(
) {
    val navController = rememberNavController()
    val context = LocalContext.current
    val year = "2023"
    val monthNum = remember {
        mutableStateOf(1)
    }
    val horizontalCount = remember {
        mutableStateOf(0.0)
    }
    val months = Months()
    val month = months.getMoon(monthNum.value)
    val state = rememberScrollableState {
        horizontalCount.value += it
        if(horizontalCount.value<-400.0 && monthNum.value<12){
            monthNum.value+=1
            horizontalCount.value = 0.0

        }
        if(horizontalCount.value>400.0 && monthNum.value>1){
            monthNum.value-=1
            horizontalCount.value = 0.0
        }
        it
    }
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 200.dp).scrollable(
            state = state, orientation = Orientation.Horizontal,
        )) {
        Column(modifier = Modifier
            .fillMaxWidth()) {
            Text("${monthNum.value}月")
            for (idx in 0 until month.size) {
                val m = month[idx]
                Divider(color = MaterialTheme.colorScheme.secondary,
                    thickness = 1.dp,
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .padding(horizontal = 8.dp))
                DateItem(m, navController, year, monthNum.value.toString(), context)
            }
        }
    }
}


@Preview
@Composable
fun HelpPreview() {
    MScheduleTheme {
        Surface {
            Help()
        }
    }
}
