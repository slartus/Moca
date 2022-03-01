package ru.slartus.moca.features.`feature-main`.views

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import icMovies
import ru.slartus.moca.`core-ui`.appClickable
import ru.slartus.moca.core_ui.theme.AppTheme


@Composable
internal fun DrawerMenuItem(modifier: Modifier = Modifier, title: String, onClick: () -> Unit) {
    Row(
        modifier = modifier.fillMaxWidth().height(54.dp).appClickable {
            onClick()
        }.padding(start = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            tint = AppTheme.colors.primaryText,
            painter = icMovies(),
            contentDescription = null
        )
        Text(
            text = title,
            color = AppTheme.colors.primaryText,
            modifier = Modifier.padding(start = 16.dp),
            maxLines = 1,
            fontWeight = FontWeight.Bold,
        )
    }
}