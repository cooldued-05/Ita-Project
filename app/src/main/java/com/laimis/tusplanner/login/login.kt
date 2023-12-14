package com.laimis.tusplanner.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.laimis.tusplanner.R

//Source
//https://www.youtube.com/watch?v=n7tUmLP6pdo&list=PLUPcj46QWTDUaeVc7qkO_9CADrnxfTvbO&index=16
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun login(navController: NavController,
          loginViewModel: loginViewModel? = null){

    val loginUIState = loginViewModel?.loginUIState
    val isError = loginUIState?.loginError != null
    val context = LocalContext.current

    Box(


        modifier = Modifier

            .fillMaxWidth()
            .height(800.dp)
            .clip(
                RoundedCornerShape(
                    topStart = 0.dp,
                    topEnd = 0.dp,
                    bottomStart = 0.dp,
                    bottomEnd = 0.dp
                )
            )
            .background(Color(red = 1f, green = 1f, blue = 1f, alpha = 1f))
            .padding(start = 0.dp, top = 0.dp, end = 0.dp, bottom = 0.dp)
            .alpha(1f)
    )


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(54.dp)
            .clip(
                RoundedCornerShape(
                    topStart = 0.dp,
                    topEnd = 0.dp,
                    bottomStart = 0.dp,
                    bottomEnd = 0.dp
                )
            )
            .background(
                Color(
                    red = 0.6313725709915161f,
                    green = 0.5803921818733215f,
                    blue = 0.40392157435417175f,
                    alpha = 1f
                )
            )
    ) {
        val image = painterResource(id = R.drawable.tus)

        Box(
            modifier = Modifier
                .width(83.dp)
                .height(40.dp)
                .clip(RoundedCornerShape(0.dp))
                .align(Alignment.Center)
        ) {
            Image(
                painter = image,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )
        }
    }



    Text(
        text = "Log in",
        textAlign = TextAlign.Center,
        fontSize = 30.sp,
        textDecoration = TextDecoration.None,
        letterSpacing = 0.sp,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier

            .padding(top = 180.dp)
            .padding(start = 150.dp)
            .alpha(1f),
        color = Color(red = 0f, green = 0f, blue = 0f, alpha = 1f),
        fontWeight = FontWeight.SemiBold,
        fontStyle = FontStyle.Normal,
    )
    if (isError){
        Text(text = loginUIState?.loginError ?: "unkown error", color = Color.Red,
            modifier = Modifier
            .padding(top = 260.dp)
            .padding(start = 55.dp))

    }


    Text(
        text = "Sign Up",
        textAlign = TextAlign.End,
        fontSize = 16.sp,
        textDecoration = TextDecoration.None,
        letterSpacing = 0.sp,

        overflow = TextOverflow.Ellipsis,
        modifier = Modifier
            .padding(top = 190.dp)
            .padding(start = 300.dp)
            .alpha(1f)
            .clickable { navController.navigate("sighnin") },
        color = Color(red = 0.6313725709915161f, green = 0.5803921818733215f, blue = 0.40392157435417175f, alpha = 1f),
        fontWeight = FontWeight.Medium,
        fontStyle = FontStyle.Normal,
    )


    OutlinedTextField(
        value = loginUIState?.userName ?: " ",
        onValueChange = { loginViewModel?.onUserNameChange(it)},
        leadingIcon = { Icon(imageVector = Icons.Default.Person, contentDescription = null)},
        label = { Text(text = "Email")},
        isError = isError,
        placeholder = { Text("Email (kNum.student.tus.ie)") },
        textStyle = TextStyle(
            textAlign = TextAlign.Start,
            fontSize = 16.sp,
            textDecoration = TextDecoration.None,
            letterSpacing = 0.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal,
        ),

        modifier = Modifier
            .padding(top = 300.dp, start = 60.dp)
            .width(283.dp)
            .clip(RoundedCornerShape(10.dp))
            .alpha(1f),
        colors = TextFieldDefaults.textFieldColors(
            cursorColor = Color.Black,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )

    OutlinedTextField(
        value = loginUIState?.password ?: " ",
        onValueChange = { loginViewModel?.onPasswordNameChange(it)},
        leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = null)},
        label = { Text(text = "Password")},
        visualTransformation = PasswordVisualTransformation(),
        isError = isError,
        placeholder = { Text("Password") },
        textStyle = TextStyle(
            textAlign = TextAlign.Start,
            fontSize = 16.sp,
            textDecoration = TextDecoration.None,
            letterSpacing = 0.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal,
        ),
        modifier = Modifier
            .padding(top = 360.dp, start = 60.dp)
            .width(283.dp)
            .clip(RoundedCornerShape(10.dp))
            .alpha(1f),
        colors = TextFieldDefaults.textFieldColors(
            cursorColor = Color.Black,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )

    Button(onClick = {
                        loginViewModel?.loginUser(context)
                     },
        modifier = Modifier
            .padding(top = 430.dp, start = 150.dp)
            .width(100.dp)
            .height(50.dp)
            .clip(RoundedCornerShape(10.dp))
            .clickable {
                navController.navigate("nextPage")
            }
            .alpha(1f)) {
        Text(
            text = "Submit",
            textAlign = TextAlign.Start,
            fontSize = 16.sp,
            textDecoration = TextDecoration.None,
            letterSpacing = 0.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal,
        )
    }

    if (loginUIState?.isLoading == true){
        CircularProgressIndicator(
            modifier = Modifier.offset(x = 170.dp, y = 500.dp)
        )
    }
    LaunchedEffect(key1 = loginViewModel?.hasUser){
        if (loginViewModel?.hasUser == true){
            navController.navigate("nextPage")
        }
    }

}

