package com.mike.pcbuddy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import com.mike.pcbuddy.networkManager.fetchServers
import com.mike.pcbuddy.ui.theme.PcBuddyTheme
import com.mike.pcbuddy.model.dao.ServerDao
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var serverDao: ServerDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            enableEdgeToEdge()

            PcBuddyTheme(darkTheme = true) {
                val scope = rememberCoroutineScope()
                LaunchedEffect(Unit) {
                    fetchServers(serverDao, scope)
                }
                AppNavHost(this)
            }
        }
    }
}