package com.example.mschedule.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.NavController


@Composable
fun Home(openDrawer: () -> Unit) {
    var showAlertDialog by remember { mutableStateOf(false) }
    Column(modifier = Modifier.fillMaxSize()) {
        if (showAlertDialog) {
            AlertDialog(
                onDismissRequest = {
                    // 點擊 彈出視窗 外的區域觸發
                    showAlertDialog = false;
                },
                title = {
                    Text("RuyutAlertDialog")
                },
                text = {
                    Text("This is a dialog.")
                },
                confirmButton = {
                    Button(
                        onClick = {
                            showAlertDialog = false
                        }
                    )
                    {
                        Text(text = "確認按鈕")
                    }
                },
                dismissButton = {
                    Button(onClick = { showAlertDialog = false })
                    {
                        Text(text = "取消按鈕")
                    }
                }
            )
        }
        MTopBar("Home", { showAlertDialog = true },
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
    var showAlertDialog by remember { mutableStateOf(false) }
    Column(modifier = Modifier.fillMaxSize()) {
        if (showAlertDialog) {
            val textState = remember { mutableStateOf(TextFieldValue("")) }
            Column {
                SearchScreen(textState) { showAlertDialog = false }
                ItemList(state = textState)
            }
        } else {
            MTopBar(
                "會員資訊",
                { showAlertDialog = true },
                onButtonClicked = {
                    openDrawer()
                }
            )
        }
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
        MTopBar("幫助", {},
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