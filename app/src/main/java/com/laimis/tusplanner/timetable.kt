package com.laimis.tusplanner

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun timetable(navController: NavController){

    //https://firebase.google.com/docs/firestore/query-data/get-data

    var coursesUsed by remember { mutableStateOf<List<String>>(emptyList()) }

    val firestore = FirebaseFirestore.getInstance()

    data class course(
        val name: String,
    )

    firestore.collection("Course_Name")
        .get()
        .addOnCompleteListener { result ->
            if (result.isSuccessful) {
                val courses = result.result?.documents?.map { document ->
                    document.getString("name") ?: ""
                } ?: emptyList()

                coursesUsed = courses
            } else {

            }
        }

    Box(


        modifier = Modifier

            .fillMaxWidth()
            .height(800.dp)
            .clip(RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp, bottomStart = 0.dp, bottomEnd = 0.dp))
            .background(Color(red = 1f, green = 1f, blue = 1f, alpha = 1f))

            .padding(start = 0.dp, top = 0.dp, end = 0.dp, bottom = 0.dp)

            .alpha(1f)


    )


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(54.dp)
            .clip(RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp, bottomStart = 0.dp, bottomEnd = 0.dp))
            .background(Color(red = 0.6313725709915161f, green = 0.5803921818733215f, blue = 0.40392157435417175f, alpha = 1f))
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

    Column(modifier = Modifier.padding(55.dp)) {
        val daysOfWeek = listOf("Mon", "Tue", "Wed", "Thu", "Fri")
        val timeSlots = (8..19).toList()
        Row(modifier = Modifier.fillMaxWidth()) {
            Box(modifier = Modifier.weight(20f)) {}
            timeSlots.forEach { hour ->
                Box(
                    modifier = Modifier
                        .weight(20f)
                        .height(50.dp)
                        .border(1.dp, Color.Black),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = if (hour <= 12) "$hour:00 AM" else "${hour - 12}:00 PM")
                }
            }
        }

        daysOfWeek.forEach { day ->
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = day,
                    modifier = Modifier
                        .weight(20f)
                        .align(Alignment.CenterVertically)
                        .padding(1.dp),
                    fontWeight = FontWeight.Bold
                )
                timeSlots.forEach { hour ->
                    Box(
                        modifier = Modifier
                            .weight(20f)
                            .height(50.dp)
                            .border(1.dp, Color.Black)
                            .padding(1.dp)
                    ) {
                        coursesUsed.forEach { courseName ->
                            Text(text = courseName)
                        }
                    }
                }
            }
        }
    }



    Text(
        text = "go back",
        textAlign = TextAlign.Center,
        fontSize = 16.sp,
        textDecoration = TextDecoration.None,
        letterSpacing = 0.sp,

        overflow = TextOverflow.Ellipsis,
        modifier = Modifier
            .padding(top = 500.dp)
            .padding(start = 345.dp)
            .alpha(1f)
            .clickable {
                FirebaseAuth.getInstance().signOut()
                navController.navigate("nextPage")
            },
        color = Color(red = 0.6313725709915161f, green = 0.5803921818733215f, blue = 0.40392157435417175f, alpha = 1f),
        fontWeight = FontWeight.Medium,
        fontStyle = FontStyle.Normal,
    )


}



