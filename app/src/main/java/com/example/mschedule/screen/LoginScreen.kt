package com.example.mschedule.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import com.example.mschedule.ui.theme.MScheduleTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen() {
    var username = remember { mutableStateOf("") }
    var password = remember { mutableStateOf("") }
    Column() {
        TextField(
            value = username.value,
            onValueChange = { username.value = it },
            label = { Text("Username") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = "Username Icon"
                )
            }
        )

        TextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text("Password") },
            trailingIcon = {
                IconButton(
                    onClick = { password.value = "" }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "Delete Icon"
                    )
                }
            }
        )
    }
}

@Preview
@Composable
fun LoginPreview() {
    MScheduleTheme {
        Surface {
            LoginScreen()
        }
    }
}