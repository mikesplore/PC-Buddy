package com.mike.vendor.deviceControl

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mike.vendor.ui.theme.CommonComponents as CC
import androidx.compose.runtime.getValue

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun TopAppBarComponent(
    deviceName: String,
    onlineStatus: Boolean,
    onBackPressed: () -> Unit
) {
    val tintcolor by animateColorAsState(
        targetValue = if (onlineStatus) Color.Green else Color.Red,
        tween(500)
    )
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = onBackPressed) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBackIos,
                    tint = CC.textColor(),
                    contentDescription = "Back"
                )
            }
        },
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {

                Box(
                    modifier = Modifier.background(tintcolor.copy(0.2f), shape = MaterialTheme.shapes.small),
                    contentAlignment = Alignment.Center
                ){
                    Icon(Icons.Default.Computer, null, tint = tintcolor,
                        modifier = Modifier.padding(8.dp))
                }
                Text(
                    text = deviceName,
                    style = CC.titleLarge(),
                    modifier = Modifier.padding(8.dp),
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xffC4E1F6).copy(0.9f),
        )
    )
}