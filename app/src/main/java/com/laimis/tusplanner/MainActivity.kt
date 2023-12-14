package com.laimis.tusplanner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.laimis.tusplanner.ui.theme.TUSPlannerTheme
import androidx.navigation.compose.rememberNavController
import com.google.firebase.FirebaseApp
import com.laimis.tusplanner.login.login
import com.laimis.tusplanner.login.loginViewModel
import com.laimis.tusplanner.sighup.sighin

class MainActivity : ComponentActivity() {
    private val loginViewModel: loginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirebaseApp.initializeApp(this)

            TUSPlannerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting(loginViewModel)
                }
            }
        }
    }
}

@Composable
fun Greeting(loginViewModel: loginViewModel) {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "nextPage") {
        composable("login") { login(navController = navController, loginViewModel = loginViewModel) }
        composable("sighnin") { sighin(navController = navController, loginViewModel = loginViewModel) }
        composable("nextPage") {nextPage(navController = navController) }
        composable("timetable") {timetable(navController = navController) }
        composable("Map") {Map(navController = navController) }
    }
}