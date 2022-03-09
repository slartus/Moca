package ru.slartus.moca.features.`feature-product-info`.views

import AsyncImage
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.slartus.moca.`core-ui`.theme.LocalAppStrings
import ru.slartus.moca.`core-ui`.views.AppNavigationIcon
import ru.slartus.moca.`core-ui`.views.TopBarView
import ru.slartus.moca.core_ui.theme.AppTheme
import ru.slartus.moca.domain.models.Rate


@Composable
internal fun TopBar(title: String) {
    val rootController = LocalRootController.current
    val strings = LocalAppStrings.current
    TopBarView(
        title = title,
        navigationIcon = {
            AppNavigationIcon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = strings.back
            ) {
                rootController.popBackStack()
            }
        }
    )
}

@Composable
internal fun Title(title: String) {
    Text(
        modifier = Modifier.padding(top = 4.dp).fillMaxWidth(),
        text = title,
        fontSize = 25.sp,
        fontWeight = FontWeight.Bold,
        color = AppTheme.colors.strongText
    )
}

@Composable
internal fun OriginalTitle(originalTitle: String) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = originalTitle,
        fontSize = 25.sp,
        fontWeight = FontWeight.Bold,
        color = AppTheme.colors.secondaryText
    )
}

@Composable
internal fun PosterView(posterUrl: String?, year: String?, rates: List<Rate>) {
    Row(modifier = Modifier.padding(8.dp).fillMaxWidth()) {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth(fraction = 0.4f)

        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height((maxWidth.value * 1.5).dp)
            ) {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxSize(),
                    imageUrl = posterUrl ?: "",
                    contentDescription = "Poster",
                    contentScale = ContentScale.FillWidth
                )
            }
        }
        Column(modifier = Modifier.padding(start = 8.dp)) {
            year?.let { year ->
                CategoryText("Год:", year)
            }
            rates
                .filter { it.rateCount > 0 }
                .forEach { rate ->
                    CategoryText("${rate.title}:", "${rate.rate} (${rate.rateCount})")
                }
        }
    }
}

@Composable
internal fun Description(overview: String?) {
    overview?.let {
        Text(
            modifier = Modifier.padding(start = 4.dp),
            text = overview,
            color = AppTheme.colors.secondaryText,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
internal fun CategoryText(title: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        CategoryTitleText(title)
        CategoryValueText(value)
    }
}

@Composable
internal fun CategoryTitleText(title: String) {
    Text(
        modifier = Modifier.padding(start = 4.dp),
        text = title,
        color = AppTheme.colors.strongText,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold
    )
}

@Composable
internal fun CategoryValueText(title: String) {
    Text(
        modifier = Modifier.padding(start = 4.dp),
        text = title,
        color = AppTheme.colors.secondaryText,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold
    )
}

