package com.mike.vendor.specs

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.BatteryFull
import androidx.compose.material.icons.rounded.Memory
import androidx.compose.material.icons.rounded.SettingsSystemDaydream
import androidx.compose.material.icons.rounded.Tv
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.mike.vendor.R
import com.mike.vendor.ui.theme.CommonComponents as CC

@Composable
fun ViewPCInformation(navController: NavController, macAddress: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CC.primary())
    ) {
        // Header Section
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {

            // Background Image
            Image(
                painter = painterResource(R.drawable.pcinfo),
                contentDescription = "PC Header Image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            // Gradient Overlay
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                CC.primary()
                            ),
                            startY = 100f
                        )
                    )
            )


            // Title
            Text(
                text = "PC Information",
                style = CC.titleLarge(),
                color = CC.textColor(),
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
            )

        }

        // Navigation Cards
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(getNavigationItems().size) { index ->
                val item = getNavigationItems()[index]
                NavigationCard(
                    icon = item.icon,
                    title = item.title,
                    description = item.description,
                    navController = navController,
                    macAddress = macAddress,
                    path = when (item.path) {
                        "battery" -> "battery"
                        "memoryAndStorage" -> "memoryAndStorage"
                        "display" -> "display"
                        "systemInfo" -> "systemInfo"
                        "schedule" -> "schedule"
                        else -> ""
                    },
                )
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

        }
    }
}

private data class NavigationItem(
    val icon: ImageVector,
    val title: String,
    val description: String,
    val path: String
)

private fun getNavigationItems() = listOf(
    NavigationItem(
        icon = Icons.Rounded.BatteryFull,
        title = "Battery",
        description = "View battery health and status",
        path = "battery"
    ),
    NavigationItem(
        icon = Icons.Rounded.Memory,
        title = "Memory and Storage",
        description = "Check RAM and storage capacity",
        path = "memoryAndStorage"
    ),
    NavigationItem(
        icon = Icons.Rounded.Tv,
        title = "Display",
        description = "Monitor specifications",
        path = "display"
    ),
    NavigationItem(
        icon = Icons.Rounded.SettingsSystemDaydream,
        title = "System Info",
        description = "View system information",
        path = "systemInfo"
    ),
    NavigationItem(
        icon = Icons.Rounded.SettingsSystemDaydream,
        title = "Schedule",
        description = "Schedule power management",
        path = "schedule"
    )


)

@Composable
private fun NavigationCard(
    icon: ImageVector,
    title: String,
    path: String,
    description: String,
    navController: NavController,
    macAddress: String,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = {
                navController.navigate("$path/$macAddress")
            }),
        colors = CardDefaults.cardColors(
            containerColor = CC.secondary()
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon with background
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = CC.secondary(),
                        shape = RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = CC.textColor(),
                    modifier = Modifier.size(24.dp)
                )
            }

            // Text content
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    style = CC.titleMedium(),
                    color = CC.textColor()
                )
                Text(
                    text = description,
                    style = CC.subtitleMedium(),
                    color = CC.textColor().copy(alpha = 0.7f)
                )
            }

            // Arrow icon
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                contentDescription = null,
                tint = CC.textColor(),
                modifier = Modifier.size(24.dp)
            )
        }
    }
}