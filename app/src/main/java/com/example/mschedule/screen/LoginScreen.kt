package com.example.mschedule.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
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
import com.example.mschedule.ui.theme.MScheduleTheme
import com.example.mschedule.R
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.auth.ktx.auth
//import com.google.firebase.ktx.Firebase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(registerClick: () -> Unit) {
    var username = remember { mutableStateOf("") }
    var password = remember { mutableStateOf("") }
    var pwView = remember { mutableStateOf(true) }
    val showAlertDialog = remember { mutableStateOf(false) }
    val loginFlag = remember { mutableStateOf(false) }
    val context = LocalContext.current
    Box {
        Column(modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally) {
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
                                painterResource(id = R.drawable.hidden),
                                contentDescription = "View Icon",
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    },
                    modifier = Modifier.padding(start = 15.dp, top = 15.dp, end = 15.dp)
                )
                //var auth: FirebaseAuth = Firebase.auth
                ElevatedButton(onClick = {
                    //if (auth.signInWithEmailAndPassword(username.value,password.value).isSuccessful) {
                    //                        loginFlag.value = true
                    //                        showAlertDialog.value = true
                    //                    } else {
                    //                        loginFlag.value = false
                    //                        showAlertDialog.value = true
                    //                    }
                },
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
                    modifier = Modifier
                        .padding(top = 30.dp, start = 30.dp)
                        .clickable { registerClick() }
                )
                Text(
                    text = "忘記密碼?   點擊這裡",
                    textAlign = TextAlign.Center,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 20.dp, start = 30.dp, bottom = 20.dp)
                )
            }
            if (showAlertDialog.value) {
                if (loginFlag.value) {
                    AlertDialog(onDismissRequest = {
                        // 點擊 彈出視窗 外的區域觸發
                        showAlertDialog.value = false
                    }, title = {
                        Text("登入成功", modifier = Modifier.padding(start = 50.dp))
                    }, text = {
                        Text("歡迎光臨", modifier = Modifier.padding(start = 50.dp))
                    }, confirmButton = {
                    }, dismissButton = {
                        Button(onClick = {
                            showAlertDialog.value = false
                        }, modifier = Modifier.padding(end = 70.dp)) {
                            Text(text = "確認")
                        }
                    })

                } else {
                    AlertDialog(onDismissRequest = {
                        // 點擊 彈出視窗 外的區域觸發
                        showAlertDialog.value = false
                    }, title = {
                        Text("登入失敗", modifier = Modifier.padding(start = 50.dp))
                    }, text = {
                        Text("帳號或密碼錯誤", modifier = Modifier.padding(start = 50.dp))
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