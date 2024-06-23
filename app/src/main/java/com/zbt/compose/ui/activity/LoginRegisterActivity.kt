package com.zbt.compose.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

/**
 * Author: zbt
 * Date: 2024/4/8 上午11:25
 * Version: 1.0
 * Desc: LoginRegisterActivity
 */
class LoginRegisterActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

        }
    }
}

object Destinations {
    const val LOGIN_ROUTE = "login"
    const val REGISTER_ROUTE = "register"
    const val SERVER_RESULT_ROUTE = "server_result"
}

@Composable
fun LoginNavHost(navController: NavController = rememberNavController()) {

}