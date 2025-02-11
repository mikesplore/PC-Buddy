package com.mike.pcbuddy

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
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

            PcBuddyTheme(darkTheme = true, dynamicColor = false) {
                val scope = rememberCoroutineScope()
                LaunchedEffect(Unit) {
                    fetchServers(serverDao, scope)
                }
                AppNavHost(this)
            }
        }
    }
}



@Composable
fun ActionButton(conntext: Context) {
    AppNavHost(conntext)
}

