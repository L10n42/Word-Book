package com.kappdev.wordbook.core.presentation.common

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class StrokeStyle(
    val width: Dp = 2.dp,
    val strokeCap: StrokeCap = StrokeCap.Round,
    val glowRadius: Dp? = 4.dp
)

@Composable
fun CustomCircleLoader(
    isVisible: Boolean,
    color: Color,
    modifier: Modifier = Modifier,
    secondColor: Color? = color,
    diameter: Dp = 32.dp,
    tailLength: Float = 140f, // in degrees | 0f - 360f
    stokeStyle: StrokeStyle = StrokeStyle(),
    cycleDuration: Int = 1600,
) {
    var tailToDisplay by remember { mutableStateOf(0f) }

    LaunchedEffect(isVisible) {
        tailToDisplay = if (isVisible) tailLength else 0f
    }

    val animatedTail by animateFloatAsState(
        targetValue = tailToDisplay,
        animationSpec = tween(cycleDuration , easing = LinearEasing),
        label = "Tail Animation Transition"
    )

    val transition = rememberInfiniteTransition(label = "Circle Loader Transition")
    val spinAngel = transition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = cycleDuration,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "Spin Angle Transition"
    )

    Canvas(
        modifier = modifier
            .size(diameter)
            .rotate(spinAngel.value)
    ) {
        listOfNotNull(color, secondColor).forEachIndexed { index, color ->
            rotate(if (index == 0) 0f else 180f) {
                val brush = Brush.sweepGradient(
                    0f to Color.Transparent,
                    animatedTail / 360f to color,
                    1f to Color.Transparent
                )
                val paint = setupPaint(stokeStyle, brush)

                drawIntoCanvas { canvas ->
                    canvas.drawArc(
                        rect = size.toRect(),
                        startAngle = 0f,
                        sweepAngle = animatedTail,
                        useCenter = false,
                        paint = paint
                    )
                }
            }
        }
    }
}

private fun DrawScope.setupPaint(stokeStyle: StrokeStyle, brush: Brush): Paint = with(stokeStyle) style@{
    val paint = Paint().apply paint@{
        this@paint.isAntiAlias = true
        this@paint.style = PaintingStyle.Stroke
        this@paint.strokeWidth = this@style.width.toPx()
        this@paint.strokeCap = this@style.strokeCap

        brush.applyTo(size, this@paint, 1f)
    }

    glowRadius?.let { radius ->
        val frameworkPaint = paint.asFrameworkPaint()
        frameworkPaint.setShadowLayer(radius.toPx(), 0f, 0f, android.graphics.Color.WHITE)
    }

    return paint
}
