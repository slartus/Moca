package ru.slartus.moca.features.`feature-main`.views

import androidx.compose.foundation.shape.CornerSize
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

/**
 * !TODO: клик обрабатывает по старому размеру (на десктопе норм)
 * @see androidx.compose.foundation.shape.RoundedCornerShape
 * @see androidx.compose.foundation.shape.CornerBasedShape
 */
internal fun customDrawerShape(width: Dp) = object : Shape {
    val topStart: CornerSize = CornerSize(4.dp)
    val topEnd: CornerSize = CornerSize(4.dp)
    val bottomEnd: CornerSize = CornerSize(4.dp)
    val bottomStart: CornerSize = CornerSize(4.dp)
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val widthPx = with(density) { width.toPx() }
        val newSize = size.copy(width = widthPx)
        var topStart = topStart.toPx(newSize, density)
        var topEnd = topEnd.toPx(newSize, density)
        var bottomEnd = bottomEnd.toPx(newSize, density)
        var bottomStart = bottomStart.toPx(newSize, density)
        val minDimension = newSize.minDimension
        if (topStart + bottomStart > minDimension) {
            val scale = minDimension / (topStart + bottomStart)
            topStart *= scale
            bottomStart *= scale
        }
        if (topEnd + bottomEnd > minDimension) {
            val scale = minDimension / (topEnd + bottomEnd)
            topEnd *= scale
            bottomEnd *= scale
        }
        require(topStart >= 0.0f && topEnd >= 0.0f && bottomEnd >= 0.0f && bottomStart >= 0.0f) {
            "Corner size in Px can't be negative(topStart = $topStart, topEnd = $topEnd, " +
                "bottomEnd = $bottomEnd, bottomStart = $bottomStart)!"
        }
        return if (topStart + topEnd + bottomEnd + bottomStart == 0.0f) {
            Outline.Rectangle(newSize.toRect())
        } else {
            Outline.Rounded(
                RoundRect(
                    rect = newSize.toRect(),
                    topLeft = CornerRadius(if (layoutDirection == LayoutDirection.Ltr) topStart else topEnd),
                    topRight = CornerRadius(if (layoutDirection == LayoutDirection.Ltr) topEnd else topStart),
                    bottomRight = CornerRadius(if (layoutDirection == LayoutDirection.Ltr) bottomEnd else bottomStart),
                    bottomLeft = CornerRadius(if (layoutDirection == LayoutDirection.Ltr) bottomStart else bottomEnd)
                )
            )
        }
    }
}
