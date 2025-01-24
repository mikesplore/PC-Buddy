package com.mike.vendor

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.mike.vendor.model.viewmodel.AppViewModel
import com.mike.vendor.model.viewmodel.DeviceViewModel
import com.mike.vendor.networkManager.AppManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AvailableAppsScreen(
    macAddress: String,
) {
    val appManager = AppManager()
    val appViewModel: AppViewModel = hiltViewModel()
    val apps by appViewModel.apps.collectAsState()
    val deviceViewModel: DeviceViewModel = hiltViewModel()
    val device by deviceViewModel.device.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        deviceViewModel.getDevice(macAddress)
        appViewModel.getApps()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Network App Manager") },
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            if (apps.isEmpty()) {
                item {
                    Text("No apps found", style = MaterialTheme.typography.bodyMedium)
                }
            }
            items(apps) { app ->
                device?.let {
                    AppItemRow(
                        app = app.toString(),
                        device = it ,
                        appManager = appManager
                    )
                }
            }
        }
    }
}



