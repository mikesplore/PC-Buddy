package com.mike.pcbuddy.model.dataClasses

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp

@Composable
fun PieChart(
    chartData: List<PieChartData>,
    modifier: Modifier = Modifier
) {
    val configurations = LocalConfiguration.current
    val screenWidth = configurations.screenWidthDp.dp
    val screenHeight = configurations.screenHeightDp.dp

    Canvas(modifier = modifier
        .size(screenWidth * 0.4f, screenHeight * 0.25f)) {
        val totalValue = chartData.sumOf { it.value.toDouble() }
        var startAngle = 0f

        chartData.forEach { data ->
            val sweepAngle = (data.value / totalValue * 360).toFloat()
            drawArc(
                color = data.color,
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = true,
                size = Size(size.width, size.height)
            )
            startAngle += sweepAngle
        }
    }
}

@Composable
fun PieChartWithLegend(
    chartData: List<PieChartData>,
    modifier: Modifier = Modifier
) {
    val configurations = LocalConfiguration.current
    val screenWidth = configurations.screenWidthDp.dp
    val screenHeight = configurations.screenHeightDp.dp

    Row(modifier = modifier) {
        PieChart(
            chartData = chartData,
            modifier = Modifier
                .weight(1f)
                .height(screenHeight * 0.18f)
        )
        Spacer(modifier = Modifier.width(screenWidth * 0.05f))
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(screenWidth * 0.02f),
            verticalArrangement = Arrangement.spacedBy(screenHeight * 0.01f)
        ) {
            chartData.forEach { data ->
                LegendItem(data)
            }
        }
    }
}

@Composable
fun LegendItem(data: PieChartData) {
    val configurations = LocalConfiguration.current
    val screenWidth = configurations.screenWidthDp.dp

    Row(
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(screenWidth * 0.04f)
                .clip(CircleShape)
                .background(data.color)
        )
        Spacer(modifier = Modifier.width(screenWidth * 0.02f))
        Text(
            text = data.label,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

data class PieChartData(
    val label: String,
    val value: Float,
    val color: Color
)