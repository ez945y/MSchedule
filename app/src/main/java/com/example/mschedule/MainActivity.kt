package com.example.mschedule

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mschedule.screen.*
import com.example.mschedule.ui.theme.MScheduleTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
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
                        },

                        ) {
                        NavHost(
                            navController = navController,
                            startDestination = "Main"
                        ) {
                            composable(route = "Main") {
                                MainScreen(onSearchBarClick = {},
                                    onAddScheduleClick = {navController.navigate("Add")},
                                    onScheduleClick = {navController.navigate("Add")},
                                    openDrawer = {
                                        openDrawer()
                                    })
                            }
                            composable(route = "Add") {
                                EditScreen(onSearchBarClick = {navController.navigate("Main")},
                                    openDrawer = {
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
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun MainPreview1() {
    Home{}
}