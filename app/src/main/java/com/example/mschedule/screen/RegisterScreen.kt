package com.example.mschedule.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mschedule.entity.db_Login
import com.example.mschedule.entity.db_Register
import com.example.mschedule.ui.theme.MScheduleTheme
import com.example.myapplication7.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(back: () -> Unit) {
    var username = remember { mutableStateOf("") }
    var password = remember { mutableStateOf("") }
    var checkPassword = remember { mutableStateOf("") }
    var pwView = remember { mutableStateOf(true) }
    val showAlertDialog = remember { mutableStateOf(false) }
    val registerFlag = remember { mutableStateOf(false) }
    val context = LocalContext.current
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        ElevatedCard(modifier = Modifier
            .padding(top = 200.dp)) {
            Row(modifier = Modifier
                .padding(top = 15.dp)) {
                Text(text = "註冊",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(start = 20.dp)
                )
                IconButton(
                    onClick = { back() },
                    modifier = Modifier.padding(start = 200.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Close Icon",
                        modifier = Modifier.padding(bottom = 15.dp)
                    )
                }
            }
            TextField(
                value = username.value,
                onValueChange = { username.value = it },
                label = { Text("信箱", fontSize = 12.sp, modifier = Modifier.padding(start = 6.dp)) },
                modifier = Modifier.padding(start = 15.dp, end = 15.dp, top = 6.dp)
            )

            TextField(
                value = password.value,
                onValueChange = { password.value = it },
                label = { Text("密碼", fontSize = 12.sp, modifier = Modifier.padding(start = 6.dp)) },
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
                            painterResource(id = R.drawable.hidden),
                            contentDescription = "View Icon",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                },
                modifier = Modifier.padding(start = 15.dp, top = 15.dp, end = 15.dp)
            )
            TextField(
                value = checkPassword.value,
                onValueChange = { checkPassword.value = it },
                label = {
                    Text("重複密碼",
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 6.dp))
                },
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
                            painterResource(id = R.drawable.hidden),
                            contentDescription = "View Icon",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                },
                modifier = Modifier.padding(start = 15.dp, top = 15.dp, end = 15.dp)
            )
            ElevatedButton(onClick = {
                if (db_Register(email = username.value,
                        password = password.value,
                        repeat = checkPassword.value,
                        context = context)
                ) {
                    registerFlag.value = true
                    showAlertDialog.value = true
                } else {
                    registerFlag.value = false
                    showAlertDialog.value = true
                }
            },
                modifier = Modifier.padding(top = 25.dp, start = 110.dp, bottom = 20.dp)) {
                Text(
                    text = "註冊",
                    textAlign = TextAlign.Center,

                    )
            }
        }
        if (showAlertDialog.value) {
            if (registerFlag.value) {
                AlertDialog(onDismissRequest = {
                    // 點擊 彈出視窗 外的區域觸發
                    showAlertDialog.value = false
                    back()
                }, title = {
                    Text("註冊成功", modifier = Modifier.padding(start = 50.dp))
                }, text = {
                    Text("歡迎加入", modifier = Modifier.padding(start = 50.dp))
                }, confirmButton = {
                }, dismissButton = {
                    Button(onClick = {
                        showAlertDialog.value = false
                        back()
                    }, modifier = Modifier.padding(end = 70.dp)) {
                        Text(text = "確認")
                    }
                })

            } else {
                AlertDialog(onDismissRequest = {
                    // 點擊 彈出視窗 外的區域觸發
                    showAlertDialog.value = false
                }, title = {
                    Text("註冊失敗", modifier = Modifier.padding(start = 50.dp))
                }, text = {
                    Text("兩次密碼不同", modifier = Modifier.padding(start = 50.dp))
                }, confirmButton = {
                }, dismissButton = {
                    Button(onClick = {
                        showAlertDialog.value = false
                    }, modifier = Modifier.padding(end = 70.dp)) {
                        Text(text = "確認")
                    }
                })
            }

        }
    }
}

@Preview
@Composable
fun RegisterPreview() {
    MScheduleTheme {
        Surface {
            RegisterScreen(back = {})
        }
    }
}