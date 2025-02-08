package com.mike.vendor.deviceControl

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.NightsStay
import androidx.compose.material.icons.filled.PowerOff
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mike.vendor.model.viewmodel.ServerViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.mike.vendor.ui.theme.CommonComponents as CC

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleScreen(
    macAddress: String,
    navController: NavController,
) {
    var selectedAction by remember { mutableStateOf(PowerAction.Shutdown) }
    var timeInput by remember { mutableStateOf("") }
    var selectedTimeUnit by remember { mutableStateOf(TimeUnit.Hours) }
    var isScheduleEnabled by remember { mutableStateOf(false) }

    val serverViewModel: ServerViewModel = hiltViewModel()
    val server by serverViewModel.server.collectAsState()

    val timeInSeconds = remember(timeInput, selectedTimeUnit) {
        try {
            when (selectedTimeUnit) {
                TimeUnit.Hours -> timeInput.toIntOrNull()?.times(3600) ?: 0
                TimeUnit.Minutes -> timeInput.toIntOrNull()?.times(60) ?: 0
                TimeUnit.Seconds -> timeInput.toIntOrNull() ?: 0
            }
        } catch (e: NumberFormatException) {
            0
        }
    }

    LaunchedEffect(Unit) {
        serverViewModel.getServer(macAddress)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Schedule Power Management for ${server?.name ?: "Unknown Server"}",
                            style = CC.titleSmall()
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = CC.primary(),
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(CC.primary())
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Action Selection Card
            AnimatedVisibility(visible = true) {
                CountdownBox(
                    totalTimeInSeconds = timeInSeconds,
                    deviceName = server?.name ?: "Unknown Server",
                    selectedAction = selectedAction,
                    modifier = Modifier
                        .fillMaxWidth(0.95f)
                )
            }
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = CC.secondary()
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Select Action",
                        style = CC.titleSmall(),
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    PowerAction.entries.forEach { action ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .selectable(
                                    selected = selectedAction == action,
                                    onClick = { selectedAction = action },
                                    role = Role.RadioButton
                                )
                                .padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = selectedAction == action,
                                onClick = null,
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = CC.primary(),
                                    unselectedColor = CC.textColor().copy(alpha = 0.6f)
                                )
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(
                                imageVector = action.icon,
                                contentDescription = null,
                                tint = CC.textColor()
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = action.title,
                                style = CC.subtitleSmall()
                            )
                        }
                    }
                }
            }

            // Time Input Card
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = CC.secondary()
                )
            ) {
                TimeInputSection(
                    timeInput = timeInput,
                    onTimeInputChange = { timeInput = it },
                    selectedTimeUnit = selectedTimeUnit,
                    onTimeUnitChange = { selectedTimeUnit = it }
                )
            }

            // Add countdown display before the Spacer
            CountdownDisplay(
                timeInSeconds = timeInSeconds,
                isEnabled = isScheduleEnabled
            )

            // Enable Switch
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = CC.secondary()
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Enable Schedule",
                            style = CC.titleSmall()
                        )
                        Text(
                            text = "Schedule will be active once enabled",
                            style = CC.subtitleSmall(),
                        )
                    }
                    Switch(
                        checked = isScheduleEnabled,
                        onCheckedChange = { isScheduleEnabled = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = CC.primary(),
                            checkedTrackColor = CC.primary().copy(alpha = 0.5f),
                            uncheckedThumbColor = CC.textColor(),
                            uncheckedTrackColor = CC.textColor().copy(alpha = 0.5f)
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Schedule Button
            Button(
                onClick = {
                    val timeInSeconds = when (selectedTimeUnit) {
                        TimeUnit.Hours -> timeInput.toInt() * 3600
                        TimeUnit.Minutes -> timeInput.toInt() * 60
                        TimeUnit.Seconds -> timeInput.toInt()
                    }
                    // Start countdown with timeInSeconds
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = CC.tertiary(),
                    disabledContainerColor = CC.tertiary().copy(alpha = 0.5f)
                ),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth(0.95f)
                    .height(56.dp),
                enabled = isScheduleEnabled,
                shape = RoundedCornerShape(10.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Schedule,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Schedule ${selectedAction.title}")
            }
        }
    }
}


enum class PowerAction(
    val title: String,
    val icon: ImageVector
) {
    Shutdown("Shutdown", Icons.Default.PowerSettingsNew),
    PowerOff("Power Off", Icons.Default.PowerOff),
    Restart("Restart", Icons.Default.Refresh),
    Hibernate("Hibernate", Icons.Default.NightsStay)
}

@Composable
fun TimePickerDialog(
    onDismissRequest: () -> Unit,
    confirmButton: @Composable () -> Unit,
    dismissButton: @Composable () -> Unit,
    content: @Composable () -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Surface(
            shape = MaterialTheme.shapes.extraLarge,
            tonalElevation = 6.dp,
            modifier = Modifier
                .width(IntrinsicSize.Min)
                .height(IntrinsicSize.Min)
                .background(
                    shape = MaterialTheme.shapes.extraLarge,
                    color = MaterialTheme.colorScheme.surface
                )
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                content()
                Row(
                    modifier = Modifier
                        .height(40.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    dismissButton()
                    confirmButton()
                }
            }
        }
    }
}

@Composable
fun TimeInputSection(
    timeInput: String,
    onTimeInputChange: (String) -> Unit,
    selectedTimeUnit: TimeUnit,
    onTimeUnitChange: (TimeUnit) -> Unit
) {
    var isDropdownExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Enter Time",
            style = CC.titleSmall(),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = timeInput,
                onValueChange = { input ->
                    // Only allow numbers
                    if (input.isEmpty() || input.all { it.isDigit() }) {
                        onTimeInputChange(input)
                    }
                },
                label = { Text("Time") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                singleLine = true,
                modifier = Modifier.weight(1f),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = CC.secondary(),
                    focusedContainerColor = CC.secondary(),
                    unfocusedLabelColor = CC.textColor().copy(alpha = 0.7f),
                    focusedLabelColor = CC.primary()
                )
            )

            Box {
                OutlinedButton(
                    onClick = { isDropdownExpanded = true },
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = CC.textColor()
                    ),
                    border = BorderStroke(1.dp, CC.textColor().copy(alpha = 0.3f))
                ) {
                    Text(selectedTimeUnit.name)
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Select time unit"
                    )
                }

                DropdownMenu(
                    expanded = isDropdownExpanded,
                    onDismissRequest = { isDropdownExpanded = false }
                ) {
                    TimeUnit.entries.forEach { unit ->
                        DropdownMenuItem(
                            text = { Text(unit.name) },
                            onClick = {
                                onTimeUnitChange(unit)
                                isDropdownExpanded = false
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CountdownDisplay(
    timeInSeconds: Int,
    isEnabled: Boolean
) {
    if (timeInSeconds > 0 && isEnabled) {
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            colors = CardDefaults.cardColors(
                containerColor = CC.secondary()
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Scheduled In",
                    style = CC.titleSmall(),
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TimeUnit.entries.forEach { unit ->
                        val value = when (unit) {
                            TimeUnit.Hours -> timeInSeconds / 3600
                            TimeUnit.Minutes -> (timeInSeconds % 3600) / 60
                            TimeUnit.Seconds -> timeInSeconds % 60
                        }
                        if (value > 0 || unit == TimeUnit.Seconds) {
                            TimeUnitDisplay(value, unit.name)
                            if (unit != TimeUnit.Seconds) {
                                Text(
                                    text = ":",
                                    style = CC.titleLarge(),
                                    modifier = Modifier.padding(horizontal = 4.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CountdownBox(
    totalTimeInSeconds: Int,
    deviceName: String,
    selectedAction: PowerAction,
    modifier: Modifier = Modifier
) {
    var remainingTime by remember {mutableStateOf(totalTimeInSeconds) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            while (remainingTime > 0) {
                delay(1000L)
                remainingTime--
            }
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .background(CC.tertiary(), shape = RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Text("$deviceName will be ${selectedAction.title} in: $remainingTime seconds")
    }
}



@Composable
private fun TimeUnitDisplay(value: Int, unit: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value.toString().padStart(2, '0'),
            style = CC.titleLarge(),
            color = CC.primary()
        )
        Text(
            text = unit,
            style = CC.subtitleSmall(),
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

enum class TimeUnit(val displayName: String, val shortName: String) {
    Hours("Hours", "h"),
    Minutes("Minutes", "m"),
    Seconds("Seconds", "s")
}