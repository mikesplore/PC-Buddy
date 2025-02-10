package com.mike.pcbuddy.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

object CommonComponents {

    @Composable
    fun textColor(): Color {
        return MaterialTheme.colorScheme.onPrimary
    }

    @Composable
    fun titleLarge(): TextStyle{
        return TextStyle(
            color = textColor(),
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )
    }

    @Composable
    fun titleMedium(): TextStyle{
        return TextStyle(
            color = textColor(),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
    }

    @Composable
    fun titleSmall(): TextStyle{
        return TextStyle(
            color = textColor(),
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
    }

    @Composable
    fun subtitleLarge(): TextStyle{
        return TextStyle(
            color = textColor(),
            fontWeight = FontWeight.Normal,
            fontSize = 20.sp
        )
    }

    @Composable
    fun subtitleMedium(): TextStyle{
        return TextStyle(
            color = textColor(),
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp
        )
    }

    @Composable
    fun subtitleSmall(): TextStyle{
        return TextStyle(
            color = textColor(),
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp
        )
    }

    @Composable
    fun labelMedium(): TextStyle{
        return TextStyle(
            color = textColor(),
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp
        )
    }

    @Composable
    fun primary(): Color {
        return MaterialTheme.colorScheme.primary
    }

    @Composable
    fun secondary(): Color {
        return MaterialTheme.colorScheme.secondary
    }

    @Composable
    fun tertiary(): Color {
        return MaterialTheme.colorScheme.tertiary
    }

    @Composable
    fun extra(): Color {
        return MaterialTheme.colorScheme.onSecondary
    }


}