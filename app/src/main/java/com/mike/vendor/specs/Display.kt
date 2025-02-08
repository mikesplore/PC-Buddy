package com.mike.vendor.specs

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AspectRatio
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Monitor
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mike.vendor.R
import com.mike.vendor.api.fetchDisplayInfo
import com.mike.vendor.model.dataClasses.DisplayInfo
import com.mike.vendor.model.viewmodel.DisplayViewModel
import com.mike.vendor.model.viewmodel.ServerViewModel
import com.mike.vendor.ui.theme.CommonComponents as CC

@Composable
fun DisplayInfoScreen(
    macAddress: String,
    context: Context,
) {
    var isRefreshing by remember { mutableStateOf(false) }
    val serverViewModel: ServerViewModel = hiltViewModel()
    val displayViewModel: DisplayViewModel = hiltViewModel()

    val server by serverViewModel.server.collectAsState()
    val displayInfo by displayViewModel.display.collectAsState()

    fun refreshData() {
        isRefreshing = true
        serverViewModel.getServer(macAddress)
    }

    // Data fetching effects
    LaunchedEffect(server, isRefreshing) {
        server?.let {
            Log.d("fetchDisplayInfo", "Server found: $it")
            fetchDisplayInfo(
                ipAddress = it.host,
                port = it.port,
                onSuccess = { displayInfoList ->
                    displayInfoList.forEach { displayInfo ->
                        displayViewModel.insertDisplayInfo(displayInfo.copy(macAddress = macAddress))
                    }
                    isRefreshing = false
                },
                onFailure = { error ->
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                    Log.d("fetchDisplayInfo", "Error fetching display info: $error")
                    isRefreshing = false
                }
            )
            displayViewModel.getDisplayInfo(macAddress)
        }
    }

    LaunchedEffect(Unit) {
        refreshData()
    }


        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(CC.primary())
        ) {
            // Header Section
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentAlignment = Alignment.Center
            ) {

                // Background Image
                Image(
                    painter = painterResource(R.drawable.display),
                    contentDescription = "PC Display Image",
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
                    text = "Display Information",
                    style = CC.titleLarge(),
                    color = CC.textColor(),
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(16.dp)
                )

            }


            // Display Info Cards
            displayInfo?.forEach { display ->
                DisplayMetricsGrid(display, modifier = Modifier.fillMaxWidth(0.9f).align(Alignment.CenterHorizontally))
                Spacer(modifier = Modifier.height(24.dp))
                QualityAssessmentCard(display, modifier = Modifier.fillMaxWidth(0.9f).align(Alignment.CenterHorizontally))
            }

            // Loading State
            if (isRefreshing) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }

}

@Composable
private fun DisplayMetricsGrid(display: DisplayInfo, modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),

        modifier = modifier
    ) {
        // Display Name Card
        item(span = { GridItemSpan(2) }) {
            MetricCard(
                icon = Icons.Rounded.Monitor,
                title = "Display Name",
                value = display.deviceName,
                subtitle = "Primary Display Device",
                tint = MaterialTheme.colorScheme.primary
            )
        }

        // Resolution Card
        item {
            MetricCard(
                icon = Icons.Default.AspectRatio,
                title = "Resolution",
                value = "${display.width} × ${display.height}",
                subtitle = getResolutionCategory(display.width, display.height),
                tint = MaterialTheme.colorScheme.secondary
            )
        }

        // Refresh Rate Card
        item {
            MetricCard(
                icon = Icons.Default.Speed,
                title = "Refresh Rate",
                value = "${display.refreshRate} Hz",
                subtitle = getRefreshRateDescription(display.refreshRate),
                tint = CC.primary()
            )
        }
    }
}

@Composable
private fun MetricCard(
    icon: ImageVector,
    title: String,
    value: String,
    subtitle: String,
    tint: Color
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(0.8f),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = CC.secondary()
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .background(
                        color = CC.tertiary(),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(8.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = tint
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = title,
                style = CC.titleMedium()
            )

            Text(
                text = value,
                style = CC.subtitleLarge(),
                color = Color.White,
                modifier = Modifier.padding(vertical = 4.dp)
            )

            Text(
                text = subtitle,
                style = CC.subtitleSmall(),
                color = Color.White
            )
        }
    }
}

@Composable
private fun QualityAssessmentCard(display: DisplayInfo, modifier: Modifier = Modifier) {
    ElevatedCard(
        modifier = modifier,
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = CC.secondary()
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Rounded.Info,
                contentDescription = null,
                tint = CC.primary(),
                modifier = Modifier.size(32.dp)
            )

            Column {
                Text(
                    text = "Display Quality Assessment",
                    style = CC.titleMedium(),
                    modifier = Modifier.padding(bottom = 5.dp)
                )

                Text(
                    text = buildString {
                        append("This display offers ")
                        append(getResolutionCategory(display.width, display.height))
                        append(" resolution with a ${display.refreshRate}Hz refresh rate, making it ")
                        append(
                            when {
                                display.refreshRate >= 144 -> "excellent for professional use and gaming"
                                display.refreshRate >= 100 -> "great for content creation and gaming"
                                else -> "suitable for general use"
                            }
                        )
                        append(".")
                    },
                    style = CC.subtitleSmall(),
                    color = Color.White,

                )
            }
        }
    }
}

private fun getResolutionCategory(width: Int, height: Int): String {
    return when {
        width >= 3840 && height >= 2160 -> "4K UHD"
        width >= 2560 && height >= 1440 -> "2K QHD"
        width >= 1920 && height >= 1080 -> "Full HD"
        width >= 1280 && height >= 720 -> "HD"
        else -> "Standard"
    }
}

private fun getRefreshRateDescription(refreshRate: Int): String {
    return when {
        refreshRate >= 144 -> "Excellent for competitive gaming"
        refreshRate >= 100 -> "Great for gaming"
        refreshRate >= 75 -> "Good for casual gaming"
        refreshRate >= 60 -> "Standard refresh rate"
        else -> "Basic refresh rate"
    }
}




