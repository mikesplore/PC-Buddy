import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mike.vendor.AvailableDevicesScreen
import com.mike.vendor.NetworkDevice
import com.mike.vendor.ui.theme.DeviceControlScreen

@Composable
fun AppNavHost(
    discoveredDevices: List<NetworkDevice>,
    onSendCommand: (NetworkDevice, String) -> Unit,
) {
    val navController = rememberNavController()
    val startDestination = "home"
    NavHost(navController, startDestination) {
        composable("home") {
            AvailableDevicesScreen(
                discoveredDevices = discoveredDevices,
                onControlClick = { device ->
                    navController.navigate("device/${device.name}")
                }
            )
        }

        composable("device/{deviceName}") { backStackEntry ->
            val deviceName = backStackEntry.arguments?.getString("deviceName") ?: return@composable
            DeviceControlScreen(
                deviceName = deviceName,
                commands = listOf("shutdown", "restart", "sleep", "lock"),
                onCommandClick = { command ->
                    val device = discoveredDevices.find { it.name == deviceName }
                    device?.let { onSendCommand(it, command) }
                }
            )
        }
    }
}