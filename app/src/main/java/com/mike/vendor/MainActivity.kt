package com.mike.vendor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.mike.vendor.model.Server
import com.mike.vendor.model.dao.ServerDao
import com.mike.vendor.networkManager.fetchServers
import com.mike.vendor.ui.theme.VendorTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var serverDao: ServerDao

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            enableEdgeToEdge()
            remember { mutableStateListOf<Server>() }
            VendorTheme(darkTheme = true) {
                val scope = rememberCoroutineScope()
                remember { mutableStateListOf<Server>() }
                LaunchedEffect(Unit) {
                    fetchServers(serverDao, scope)
                }
                AppNavHost(this)
            }
        }
    }
}

