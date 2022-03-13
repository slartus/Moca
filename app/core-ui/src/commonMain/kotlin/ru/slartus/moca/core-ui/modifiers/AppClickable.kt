package ru.slartus.moca.`core-ui`.modifiers

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.semantics.Role
import ru.slartus.moca.`core-ui`.theme.AppTheme

fun Modifier.appClickable(
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    onClick: () -> Unit
): Modifier =
    composed {
        this.clickable(
            enabled = enabled,
            onClickLabel = onClickLabel,
            role = role,
            interactionSource = remember { MutableInteractionSource() },
            indication = rememberRipple(color = AppTheme.colors.highLight),
            onClick = onClick
        )
    }