package com.example.mschedule

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mschedule.entity.*
import com.example.mschedule.screen.*
import com.example.mschedule.ui.theme.MScheduleTheme
import com.example.mschedule.ui.theme.isLight
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch

//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.auth.ktx.auth
//import com.google.firebase.ktx.Firebase



class MainActivity : ComponentActivity() {
    //private lateinit var auth: FirebaseAuth
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            MScheduleTheme {
                Surface {
                    rememberSystemUiController().setStatusBarColor(
                        Color.Transparent, darkIcons = MaterialTheme.colorScheme.isLight())

                    //db_ReStart(LocalContext.current)
                    //auth = Firebase.auth
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
                            , context = LocalContext.current)
                        },

                        ) {
                        NavHost(
                            navController = navController,
                            startDestination = "Main"
                        ) {
                            composable(route = DrawerScreens.Main.route) {
                                MainScreen(
                                    navController = navController,
                                    openDrawer = {
                                        openDrawer()
                                    })
                            }

                            composable(route =DrawerScreens.Edit.route+"/{scheduleID}") { backStackEntry ->
                                    EditScreen(
                                        id= backStackEntry.arguments?.getString("scheduleID").orEmpty(),
                                        navController=navController,
                                        openDrawer = {
                                            openDrawer()
                                        },
                                    )
                            }
                            composable(DrawerScreens.Account.route) {
                                LoginScreen(registerClick = {navController.navigate("Register")})
                            }
                            composable(DrawerScreens.Register.route) {
                                RegisterScreen(register = true){navController.popBackStack() }
                            }
                            composable(DrawerScreens.Help.route) {
                                Help(
                                )
                            }
                            composable(route = DrawerScreens.Add.route+"/{dateID}") {backStackEntry ->
                                AddScreen(ScheduleItem(1),
                                    navController = navController,
                                    dateId = backStackEntry.arguments?.getString("dateID").orEmpty(),
                                    openDrawer = {
                                        openDrawer()
                                    })
                            }
                            /*
                            composable(DrawerScreens.Day.route+"/{dateID}") { backStackEntry ->
                                DayScreen(
                                    dateId = backStackEntry.arguments?.getString("dateID").orEmpty(),
                                    navController = navController,
                                )
                            }
                             */
                        }
                    }
                }
            }
        }
    }
    //fun createAccount() :Boolean{ //email: String, password: String
    //        var flag = false
    //        auth.createUserWithEmailAndPassword("","")
    //            .addOnCompleteListener(this) { task ->
    //                if (task.isSuccessful) {
    //                    Log.d(ContentValues.TAG, "createUserWithEmail:success")
    //                    flag = true
    //                } else {
    //                    Log.w(ContentValues.TAG, "createUserWithEmail:failure", task.exception)
    //                }
    //            }
    //        return true
    //    }

    //fun signIn(email: String, password: String) {
    //        // [START sign_in_with_email]
    //        auth.signInWithEmailAndPassword(email, password)
    //            .addOnCompleteListener(this) { task ->
    //                if (task.isSuccessful) {
    //                    // Sign in success, update UI with the signed-in user's information
    //                    Log.d(ContentValues.TAG, "signInWithEmail:success")
    //                    val user = auth.currentUser
    //                } else {
    //                    // If sign in fails, display a message to the user.
    //                    Log.w(ContentValues.TAG, "signInWithEmail:failure", task.exception)
    //                }
    //            }
    //    }
}


