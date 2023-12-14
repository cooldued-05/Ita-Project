package com.laimis.tusplanner

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun nextPage(navController: NavController){
    val phonenum = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(800.dp)
            .clip(RoundedCornerShape(0.dp))
            .background(Color(1f, 1f, 1f, 1f))
            .padding(0.dp)
            .alpha(1f)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp)
                .clip(RoundedCornerShape(0.dp))
                .background(Color(0.6313725709915161f, 0.5803921818733215f, 0.40392157435417175f, 1f))
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

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Spacer(modifier = Modifier.height(100.dp))

            val items = listOf("IT", "Medicine", "Science", "Sports")
            var expanded by remember { mutableStateOf(false) }
            var selectedItem by remember { mutableStateOf(items[0]) }

            Text(text = "Select your department")

            Column() {
                Button(onClick = { expanded = true }) {
                    Text(text = selectedItem)


                }

                if (expanded) {
                    Popup(onDismissRequest = { expanded = false }) {
                        Column(
                            horizontalAlignment = Alignment.Start
                        ) {
                            items.forEach { course ->
                                Button(
                                    onClick = {
                                        selectedItem = course
                                        expanded = false
                                    },
                                    modifier = Modifier.width(200.dp)
                                ) {
                                    Text(text = course)
                                }
                            }
                        }
                    }
                }

            }
        }

        Button(
            onClick = { navController.navigate("timetable") },
            modifier = Modifier.offset(x = 140.dp, y = 170.dp)
        ) {
            Text(text = "Go to Timetable")
        }

        IconButton(
            onClick = {
                FirebaseAuth.getInstance().signOut()
                navController.navigate("login")
            },
            modifier = Modifier
                .padding( start = 350.dp)
                .alpha(1f)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.logout),
                contentDescription = "Logout",
            )
        }

        IconButton(
            onClick = {
                FirebaseAuth.getInstance().signOut()
                navController.navigate("Map")
            },
            modifier = Modifier
                .padding(start = 10.dp)
                .alpha(1f)
                .align(Alignment.BottomStart)
        ) {
            Icon(
                imageVector = Icons.Default.Place,
                contentDescription = "Map",
                modifier = Modifier
                    .size(150.dp, 150.dp)

            )
        }


        IconButton(
            onClick = {
                val phone = Intent(Intent.ACTION_DIAL).apply {
                    data = Uri.parse("tel:0876012214")
                }
                phonenum.startActivity(phone)
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .alpha(1f)

        ) {
            Icon(
                imageVector = Icons.Default.Call,
                contentDescription = "Contact us",
                        modifier = Modifier
                        .size(150.dp, 150.dp)
            )
        }





    }

    @Composable
    fun Map() {
        val context = LocalContext.current
        val firestore = FirebaseFirestore.getInstance()

        val items = listOf("IT", "Medicine", "Science", "Sports")
        var expanded by remember { mutableStateOf(false) }
        var selectedItem by remember { mutableStateOf(items[0]) }
        var departmentInfo by remember { mutableStateOf<List<String>>(emptyList()) }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(100.dp))

            Text(text = "Select your department")

            Column() {
                Button(onClick = { expanded = true }) {
                    Text(text = selectedItem)
                }

                if (expanded) {
                    Popup(onDismissRequest = { expanded = false }) {
                        Column(
                            horizontalAlignment = Alignment.Start
                        ) {
                            items.forEach { course ->
                                Button(
                                    onClick = {
                                        selectedItem = course
                                        expanded = false

                                        firestore.collection("Course_Name")
                                            .get()
                                            .addOnCompleteListener { result ->
                                                if (result.isSuccessful) {
                                                    val info = result.result?.documents?.map { document ->
                                                        document.getString("name") ?: ""
                                                    } ?: emptyList()
                                                    departmentInfo = info
                                                } else {
                                                    println("Error fetching data: ${result.exception}")
                                                }
                                            }
                                    },
                                    modifier = Modifier.width(200.dp)
                                ) {
                                    Text(text = course)
                                }
                            }
                        }
                    }
                }
            }

            departmentInfo.forEach { info ->
                Text(text = info)
            }
        }

    }

}
