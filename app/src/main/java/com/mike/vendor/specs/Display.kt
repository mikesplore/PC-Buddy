package com.mike.vendor.specs

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.mike.vendor.api.fetchDisplayInfo
import com.mike.vendor.model.viewmodel.DisplayViewModel
import com.mike.vendor.model.viewmodel.ServerViewModel
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.AspectRatio
import androidx.compose.material.icons.filled.Update
import androidx.compose.material.icons.rounded.HighQuality
import androidx.compose.material.icons.rounded.Tv
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import com.mike.vendor.ui.theme.CommonComponents as CC

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DisplayInfoScreen(
    macAddress: String,
    context: Context,
    navController: NavController
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

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Display Specifications", style = CC.titleMedium()) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Default.ArrowBackIos, "Back", tint = CC.textColor())
                    }
                },
                actions = {
                    IconButton(onClick = { refreshData() }, enabled = !isRefreshing) {
                        if (isRefreshing) {
                            CircularProgressIndicator(modifier = Modifier.size(24.dp))
                        } else {
                            Icon(Icons.Default.Refresh, "Refresh", tint = CC.textColor())
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = CC.extra())
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(CC.primary())
                .padding(padding)
        ) {
            // Hero Image Section
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            ) {
                AsyncImage(
                    model = "https://speechi.com/wp-content/uploads/2023/08/interactive-screen-display-interactive-digital-display.jpg",
                    contentDescription = "Display Image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                // Gradient overlay
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    CC.primary()
                                )
                            )
                        )
                )
            }

            // Display Info Section
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                displayInfo?.forEach { display ->
                    // Display Name Section
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            InfoCard(
                                modifier = Modifier.weight(1f),
                                icon = Icons.Rounded.Tv,
                                title = "Display",
                                value = display.deviceName,
                                description = "Primary Display Device"
                            )
                        }
                    }

                    // Resolution Section
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            InfoCard(
                                modifier = Modifier.weight(1f),
                                icon = Icons.Default.AspectRatio,
                                title = "Resolution",
                                value = "${display.width} × ${display.height}",
                                description = "Screen Resolution",
                                displayWidth = display.width,
                                displayHeight = display.height
                            )
                        }
                    }

                    // Refresh Rate Section
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            InfoCard(
                                modifier = Modifier.weight(1f),
                                icon = Icons.Default.Update,
                                title = "Refresh Rate",
                                value = "${display.refreshRate} Hz",
                                description = getRefreshRateDescription(display.refreshRate)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun InfoCard(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    title: String,
    value: String,
    description: String,
    displayWidth: Int = 0,
    displayHeight: Int = 0
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = CC.extra()),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background( CC.extra(), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = CC.textColor(),
                        modifier = Modifier.size(28.dp)
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = title,
                        style = CC.titleMedium(),
                        color = CC.textColor()
                    )
                    Text(
                        text = description,
                        style = CC.subtitleSmall(),
                    )
                }

                if (title == "Resolution") {
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = getResolutionCategory(displayWidth, displayHeight),
                        style = CC.subtitleSmall(),
                        fontWeight = FontWeight.Bold,
                        color = CC.textColor()
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Text(
                    text = value,
                    style = CC.titleLarge(),
                    modifier = Modifier.align(Alignment.CenterStart)
                )
            }
        }
    }
}



private fun getRefreshRateDescription(refreshRate: Int): String {
    return when {
        refreshRate >= 144 -> "Excellent for competitive gaming and ultra-smooth motion"
        refreshRate >= 100 -> "Great for gaming and smooth animations"
        refreshRate >= 75 -> "Good for casual gaming and general use"
        refreshRate >= 60 -> "Standard refresh rate for everyday computing"
        else -> "Basic refresh rate for general use"
    }
}

private fun getResolutionDescription(width: Int, height: Int): String {
    return when {
        width >= 3840 && height >= 2160 -> "4K Ultra HD - Perfect for content creation and high-end gaming"
        width >= 2560 && height >= 1440 -> "2K QHD - Great for gaming and professional work"
        width >= 1920 && height >= 1080 -> "Full HD - Good for most everyday tasks and entertainment"
        else -> "Standard resolution suitable for basic computing tasks"
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