package com.mike.vendor.deviceControl

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mike.vendor.ui.theme.CommonComponents as CC

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DocumentationScreen(navController: NavController) {
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    // Title
                    Text(
                        text = "PC Documentation",
                        style = CC.titleMedium()
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.AutoMirrored.Default.ArrowBackIos,
                            contentDescription = "Back",
                            tint = CC.textColor()
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = CC.extra()
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .background(CC.primary())
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(scrollState)
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            // Platform Compatibility Section
            SectionTitle("Platform Compatibility")
            PlatformStatus("Windows", "Fully functional", true)
            PlatformStatus("Kali", "Partially works", false)
            PlatformStatus("macOS", "Not tested", null)
            Spacer(modifier = Modifier.height(16.dp))

            // Repository Links
            SectionTitle("Related Repositories")
            Text(
                "Main Application: github.com/mikesplore/Vendor",
                style = CC.subtitleSmall(),
                modifier = Modifier.padding(start = 16.dp)
            )
            Text(
                "Server Component: github.com/mikesplore/PC-Controller",
                style = CC.subtitleSmall(),
                modifier = Modifier.padding(start = 16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Known Limitations
            SectionTitle("Known Limitations")
            BulletPoint("Primary functionality is currently limited to Windows systems")
            BulletPoint("Linux (Kali) support is partial and may have unexpected behavior")
            BulletPoint("macOS compatibility has not been tested or implemented")
            Spacer(modifier = Modifier.height(16.dp))

            // Future Improvements
            SectionTitle("Future Improvements")
            BulletPoint("Implementation of Linux (Kali) support")
            BulletPoint("Enhanced cross-platform compatibility")
            BulletPoint("Expanded system information retrieval capabilities")
            Spacer(modifier = Modifier.height(16.dp))

            // Contact Section
            SectionTitle("Contact & Support")
            Text(
                "For feature requests, bug reports, or general inquiries about app improvements, please open an issue in the GitHub repository or contact the developer directly.",
                style = CC.subtitleSmall(),
                modifier = Modifier.padding(start = 16.dp)
            )
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun SectionTitle(title: String) {
    Text(
        text = title,
        style = CC.titleMedium(),
        modifier = Modifier.padding(start = 16.dp)
    )
}

@Composable
private fun BulletPoint(text: String) {
    Row(modifier = Modifier.padding(start = 20.dp, bottom = 8.dp)) {
        Text("• ", style = CC.subtitleSmall())
        Text(text, style = CC.subtitleSmall())

    }
}

@Composable
private fun PlatformStatus(
    platform: String,
    status: String,
    isSupported: Boolean?
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        val icon = when (isSupported) {
            true -> "✅"
            false -> "⚠️"
            null -> "❓"
        }
        Text(
            "$icon $platform: $status",
            style = CC.subtitleSmall(),
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}
