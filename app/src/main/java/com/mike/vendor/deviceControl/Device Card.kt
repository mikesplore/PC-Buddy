package com.mike.vendor.deviceControl

import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Computer
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mike.vendor.model.Server


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun DeviceCard(
    device: Server,
    navController: NavController,
) {
    var isHovered by remember { mutableStateOf(false) }
    val status = device.onlineStatus

    val borderColor: Color by animateColorAsState(
        targetValue = if (status) Color.Green else Color.Red,
        animationSpec = tween(500)
    )

    val textColor: Color by animateColorAsState(
        targetValue = if (status) Color.Green else Color.Red,
        animationSpec = tween(500)
    )

    val backgroundColor: Color by animateColorAsState(
        targetValue = if (status) Color.Green.copy(alpha = 0.2f) else Color.Red.copy(alpha = 0.2f),
        animationSpec = tween(500)
    )


    val animatedElevation by animateDpAsState(
        targetValue = if (isHovered) 8.dp else 2.dp,
        animationSpec = tween(500)
    )

    ElevatedCard(
        modifier = Modifier
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = MaterialTheme.shapes.medium
            )
            .fillMaxWidth()
            .clickable {
                isHovered = !isHovered
                navController.navigate("device/${device.macAddress}")
                Log.d("DeviceCard", "Clicked on device: ${device.macAddress}")
            },
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = animatedElevation
        ),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        )
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Rounded.Computer,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(32.dp)
                    )

                    Column {
                        Text(
                            text = device.name,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(
                            color = backgroundColor,
                        )
                        .border(
                            1.dp,
                            color = borderColor,
                            shape = RoundedCornerShape(10.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (status) "Online" else "Offline",
                        modifier = Modifier.padding(5.dp),
                        color = textColor,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}