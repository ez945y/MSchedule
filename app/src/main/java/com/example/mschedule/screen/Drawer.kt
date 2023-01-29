package com.example.mschedule.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

sealed class DrawerScreens(val title: String, val route: String) {
    object Main : DrawerScreens("行事曆", "Main")
    object Add : DrawerScreens("Add", "Add")
    object Edit : DrawerScreens("Edit", "Edit")
    object Home : DrawerScreens("測試", "home")
    object Account : DrawerScreens("會員", "account")
    object Help : DrawerScreens("幫助", "help")
    object Day : DrawerScreens("Day", "Day")
    object Login : DrawerScreens("會員", "Login")
    object Register : DrawerScreens("Register", "Register")
}

private val screens = listOf(
    DrawerScreens.Main,
    DrawerScreens.Home,
    DrawerScreens.Account,
    DrawerScreens.Help
)

@Composable
fun Drawer(
    modifier: Modifier = Modifier,
    onDestinationClicked: (route: String) -> Unit,
) {
    Card(modifier = Modifier.padding(end = 50.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiary)) {
        Column(
            modifier
                .fillMaxSize()
                .padding(start = 15.dp, top = 48.dp)
        ) {
            Row {
                Icon(Icons.Filled.Menu,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.background,
                    modifier = Modifier.padding(top = 2.dp))
                Spacer(modifier = Modifier.padding(4.dp))
                Text("選單",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.background,
                    modifier = Modifier.padding(end = 10.dp))
            }
            Card(modifier = Modifier.padding(top = 20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onPrimary)) {
                Row(modifier = Modifier.padding(10.dp)) {
                    Card(modifier = Modifier
                        .padding(5.dp)
                        .clickable {},
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary)) {
                        Text(
                            "工作",
                            color = MaterialTheme.colorScheme.background,
                            modifier = Modifier
                                .size(88.dp)
                                .padding(top = 30.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                    Card(modifier = Modifier
                        .padding(5.dp)
                        .clickable {},
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.inversePrimary)) {
                        Text(
                            "學校",
                            color = MaterialTheme.colorScheme.background,
                            modifier = Modifier
                                .size(88.dp)
                                .padding(top = 30.dp),
                            textAlign = TextAlign.Center,
                        )
                    }
                    Card(modifier = Modifier
                        .padding(5.dp)
                        .clickable {},
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.inverseOnSurface)) {
                        Text(
                            "女友",
                            color = MaterialTheme.colorScheme.inverseSurface,
                            modifier = Modifier
                                .size(88.dp)
                                .padding(top = 30.dp),
                            textAlign = TextAlign.Center,
                        )
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
    }
}