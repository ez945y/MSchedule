package com.example.mschedule.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mschedule.ui.theme.MScheduleTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(registerClick: () -> Unit) {
    var username = remember { mutableStateOf("") }
    var password = remember { mutableStateOf("") }
    var pwView = remember { mutableStateOf(true) }
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        ElevatedCard(modifier = Modifier
            .padding(top = 200.dp)) {
            Text(text = "登入",
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(start = 20.dp, top = 15.dp)
            )
            OutlinedTextField(
                value = username.value,
                onValueChange = { username.value = it },
                shape = CircleShape,
                label = {
                    Text("Username",
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 6.dp))
                },
                modifier = Modifier.padding(start = 15.dp, top = 20.dp, end = 15.dp)
            )

            OutlinedTextField(
                value = password.value,
                onValueChange = { password.value = it },
                label = {
                    Text("Password",
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 6.dp))
                },
                shape = CircleShape,
                visualTransformation = if (pwView.value) {
                    PasswordVisualTransformation()
                } else {
                    VisualTransformation.None
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    IconButton(
                        onClick = { pwView.value = !pwView.value },
                        modifier = Modifier.padding(end = 6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = "Delete Icon"
                        )
                    }
                },
                modifier = Modifier.padding(start = 15.dp, top = 15.dp, end = 15.dp)
            )
            ElevatedButton(onClick = { /*TODO*/ },
                modifier = Modifier.padding(top = 25.dp, start = 110.dp)) {
                Text(
                    text = "登入",
                    textAlign = TextAlign.Center,

                    )
            }
            Text(
                text = "尚無帳號?   註冊",
                textAlign = TextAlign.Center,
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 30.dp, start = 30.dp)
                    .clickable{registerClick()}
            )
            Text(
                text = "忘記密碼?   點擊這裡",
                textAlign = TextAlign.Center,
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 20.dp, start = 30.dp, bottom = 20.dp)
            )
        }
    }
}

@Preview
@Composable
fun LoginPreview() {
    MScheduleTheme {
        Surface {
            LoginScreen {}
        }
    }
}