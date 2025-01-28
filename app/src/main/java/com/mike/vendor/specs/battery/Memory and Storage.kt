package com.mike.vendor.specs.battery

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
fun SystemStatsDisplay(
    memoryDetails: MemoryDetails,
    storageInfo: StorageInfo,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Memory Section
        ElevatedCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Memory Usage",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                // Memory Progress Indicators
                MemoryIndicator(
                    label = "RAM",
                    used = memoryDetails.used,
                    total = memoryDetails.total,
                    cache = memoryDetails.cacheSize
                )

                if (memoryDetails.swapTotal > 0) {
                    MemoryIndicator(
                        label = "Swap",
                        used = memoryDetails.swapUsed,
                        total = memoryDetails.swapTotal,
                        showCache = false
                    )
                }

                // Memory Details Grid
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    MemoryDetailItem(
                        label = "Total",
                        value = formatBytes(memoryDetails.total)
                    )
                    MemoryDetailItem(
                        label = "Available",
                        value = formatBytes(memoryDetails.available)
                    )
                    MemoryDetailItem(
                        label = "Cache",
                        value = formatBytes(memoryDetails.cacheSize)
                    )
                }
            }
        }

        // Storage Section
        ElevatedCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Storage",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                // Overall Storage Progress
                StorageIndicator(
                    used = storageInfo.usedSpace,
                    total = storageInfo.totalSpace
                )

                // Mount Points List
                storageInfo.mountPoints.forEach { mountPoint ->
                    MountPointItem(mountPoint)
                }
            }
        }
    }
}

@Composable
private fun MemoryIndicator(
    label: String,
    used: Long,
    total: Long,
    cache: Long = 0,
    showCache: Boolean = true
) {
    val usedPercentage = (used.toFloat() / total * 100).roundToInt()
    val cachePercentage = if (showCache) (cache.toFloat() / total * 100).roundToInt() else 0

    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "$usedPercentage%",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        LinearProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp),
            progress = used.toFloat() / total,
            color = MaterialTheme.colorScheme.primary,
            trackColor = if (showCache && cache > 0) {
                MaterialTheme.colorScheme.secondary.copy(alpha = 0.3f)
            } else {
                MaterialTheme.colorScheme.surfaceVariant
            }
        )
    }
}

@Composable
private fun StorageIndicator(
    used: Long,
    total: Long
) {
    val usedPercentage = (used.toFloat() / total * 100).roundToInt()
    val animatedProgress by animateFloatAsState(
        targetValue = used.toFloat() / total,
        label = "storageProgress"
    )

    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Used: ${formatBytes(used)}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "$usedPercentage%",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Total: ${formatBytes(total)}",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        LinearProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp),
            progress = animatedProgress,
            color = when {
                usedPercentage > 90 -> MaterialTheme.colorScheme.error
                usedPercentage > 75 -> MaterialTheme.colorScheme.warning
                else -> MaterialTheme.colorScheme.primary
            }
        )
    }
}

@Composable
private fun MountPointItem(mountPoint: MountPointDetails) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surfaceVariant,
        shape = MaterialTheme.shapes.small
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = mountPoint.path,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = mountPoint.filesystem,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Text(
                text = formatBytes(mountPoint.available),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
private fun MemoryDetailItem(
    label: String,
    value: String
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium
        )
    }
}

private fun formatBytes(bytes: Long): String {
    val units = arrayOf("B", "KB", "MB", "GB", "TB")
    var value = bytes.toDouble()
    var unitIndex = 0

    while (value >= 1024 && unitIndex < units.size - 1) {
        value /= 1024
        unitIndex++
    }

    return "%.1f %s".format(value, units[unitIndex])
}