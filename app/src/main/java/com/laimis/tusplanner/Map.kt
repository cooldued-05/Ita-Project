package com.laimis.tusplanner

import android.os.Bundle
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
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
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Map(navController: NavController){
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

        maps()

        Text(
            text = "go back",
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            textDecoration = TextDecoration.None,
            letterSpacing = 0.sp,

            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .padding(top = 75.dp)
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
}

//source
//https://cloud.google.com/blog/products/maps-platform/compose-maps-sdk-android-now-available

@Composable
fun maps() {
    val context = LocalContext.current
    val mapView = rememberMapViewWithLifecycle()
    AndroidView({ mapView }, modifier = Modifier
        .size(400.dp, 1000.dp)
        .padding(top = 100.dp, start = 10.dp)) { mapView ->
        mapView.getMapAsync { googleMap ->
            val tusMoylishCollege = com.google.android.gms.maps.model.LatLng(52.6739738, -8.6479659)
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(tusMoylishCollege, 10f))
            googleMap.addMarker(MarkerOptions().position(tusMoylishCollege).title("Tus Moylish College"))
        }
    }
}

@Composable
fun rememberMapViewWithLifecycle(): MapView {
    val context = LocalContext.current
    val mapView = remember {
        MapView(context)
    }
    DisposableEffect(mapView) {
        mapView.onCreate(Bundle())
        mapView.onStart()
        onDispose {
            mapView.onStop()
            mapView.onDestroy()
        }
    }
    return mapView
}






