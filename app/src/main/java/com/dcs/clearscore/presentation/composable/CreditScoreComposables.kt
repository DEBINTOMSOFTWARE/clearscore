package com.dcs.clearscore.presentation.composable

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun CreditScoreCircle(
    score: Int,
    maxScore: Int,
    modifier: Modifier = Modifier
) {

    val ratio = score.coerceIn(0, maxScore) / maxScore.toFloat()
    val sweep = 350f * ratio

    val outlineWidth = 2.dp
    val trackWidth = 4.dp
    val progressWidth = 4.dp
    val gapInsideTrack = 4.dp

    Box(modifier = modifier.size(220.dp)) {
        Canvas(Modifier.fillMaxSize()) {
            fun rect(inset: Float) = Rect(
                offset = Offset(inset, inset),
                size = Size(size.width - 2 * inset, size.height - 2 * inset)
            )

            val outlineInset = outlineWidth.toPx() / 2
            drawArc(
                color = Color.Black,
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                topLeft = rect(outlineInset).topLeft,
                size = rect(outlineInset).size,
                style = Stroke(outlineWidth.toPx(), cap = StrokeCap.Round)
            )

            // black track
            val trackInset = outlineInset + 6.dp.toPx()

            // yellow progress INSIDE the black track
            val progressInset =
                trackInset + (trackWidth.toPx() - progressWidth.toPx()) / 2 + gapInsideTrack.toPx()
            val progressStroke = Stroke(progressWidth.toPx(), cap = StrokeCap.Round)
            drawArc(
                color = Color(0xFFFFC107),
                startAngle = -90f,
                sweepAngle = sweep,
                useCenter = false,
                topLeft = rect(progressInset).topLeft,
                size = rect(progressInset).size,
                style = progressStroke
            )
        }

        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Your credit score is",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "$score",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "out of $maxScore",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}