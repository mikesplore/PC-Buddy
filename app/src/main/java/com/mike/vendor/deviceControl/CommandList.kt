package com.mike.vendor.deviceControl

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.mike.vendor.api.Command
import com.mike.vendor.api.commands
import com.mike.vendor.ui.theme.CommonComponents as CC

@Composable
fun CommandList(
    selectedCommand: Command?,
    onCommandClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    gridColumns: Int = 2
) {
    Text("Control the PC with these commands", style = CC.subtitleMedium(), modifier = Modifier.padding(16.dp, 8.dp))
    LazyVerticalGrid(
        columns = GridCells.Fixed(gridColumns),
        contentPadding = PaddingValues(16.dp, 8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        items(
            items = commands,
            key = { it.name }
        ) { command ->
            CommandCard(
                command = command,
                isSelected = command == selectedCommand,
                onClick = { if (selectedCommand == null) onCommandClick(command.name) }
            )
        }
    }
}

@Composable
private fun CommandCard(
    command: Command,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val configurations = LocalConfiguration.current
    val density = LocalDensity.current
    val screenWidth = configurations.screenWidthDp.dp
    val textSize: TextUnit = with(density) {
        screenWidth.toSp()
    }

    ElevatedCard(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium),
        colors = CardDefaults.elevatedCardColors(
            containerColor = Color(0xffC4E1F6)
        ),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = if (isSelected) 8.dp else 4.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(screenWidth * 0.13f)
                    .clip(MaterialTheme.shapes.small),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = command.icon,
                    contentDescription = command.name,
                    tint = command.color,
                    modifier = Modifier.size(screenWidth * 0.07f)
                )
            }

            Text(
                text = command.name.replaceFirstChar { it.uppercase() },
                style = CC.subtitleMedium(),
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = command.description,
                style = CC.subtitleSmall(),
                textAlign = TextAlign.Center,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}