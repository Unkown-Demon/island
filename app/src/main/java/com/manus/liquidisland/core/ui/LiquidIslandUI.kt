package com.manus.liquidisland.core.ui

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.dp

/**
 * Senior Engineer Note:
 * To achieve "Liquid Glass" on a low-end device like Redmi 10C:
 * 1. Use Canvas for drawing to avoid layout overhead.
 * 2. Use semi-transparent gradients instead of real-time blur (too heavy).
 * 3. Use Path for organic shapes that merge with the waterdrop notch.
 */
@Composable
fun LiquidIsland(
    isExpanded: Boolean,
    notchWidth: Float = 200f,
    notchHeight: Float = 80f
) {
    val transition = updateTransition(targetState = isExpanded, label = "IslandExpansion")
    
    val width by transition.animateFloat(label = "width") { if (it) 800f else 250f }
    val height by transition.animateFloat(label = "height") { if (it) 200f else 100f }
    val cornerRadius by transition.animateFloat(label = "corner") { if (it) 40f else 100f }

    Canvas(modifier = Modifier.fillMaxSize()) {
        drawLiquidBackground(width, height, cornerRadius, notchWidth, notchHeight)
    }
}

fun DrawScope.drawLiquidBackground(
    w: Float, h: Float, r: Float, 
    notchW: Float, notchH: Float
) {
    val centerX = size.width / 2
    val startY = 0f

    // Liquid Glass Material: Semi-transparent dark gradient
    val glassBrush = Brush.verticalGradient(
        colors = listOf(
            Color.Black.copy(alpha = 0.7f),
            Color.Black.copy(alpha = 0.5f)
        )
    )

    // Create organic path merging with waterdrop
    val path = Path().apply {
        moveTo(centerX - w / 2, startY)
        // Top edge
        lineTo(centerX + w / 2, startY)
        // Right side and bottom
        quadraticBezierTo(
            centerX + w / 2, startY + h,
            centerX, startY + h
        )
        quadraticBezierTo(
            centerX - w / 2, startY + h,
            centerX - w / 2, startY
        )
        close()
    }

    drawPath(path, brush = glassBrush)
    
    // Subtle border for "Glass" effect
    drawPath(
        path, 
        color = Color.White.copy(alpha = 0.1f),
        style = androidx.compose.ui.graphics.drawscope.Stroke(width = 2f)
    )
}
