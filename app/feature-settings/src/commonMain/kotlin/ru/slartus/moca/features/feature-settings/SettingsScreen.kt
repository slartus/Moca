package ru.slartus.moca.features.`feature-settings`

import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.slartus.moca.`core-ui`.modifiers.appClickable
import ru.slartus.moca.`core-ui`.theme.AppTheme
import ru.slartus.moca.`core-ui`.theme.LocalAppStrings
import ru.slartus.moca.`core-ui`.views.AppNavigationIcon
import ru.slartus.moca.`core-ui`.views.TopBarView
import ru.slartus.moca.core.AppScreenName

@Composable
fun SettingsScreen() {
    val rootController = LocalRootController.current

    val strings = LocalAppStrings.current
    val scaffoldState = rememberScaffoldState(
        snackbarHostState = remember { SnackbarHostState() }
    )
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopBarView(
                title = strings.settings,
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
    ) {
        ContentView(
            onVideoSourcesClick = {
                rootController.launch(AppScreenName.VideoSources.name)
            },
            onTorrentsSourcesClick = {
                rootController.launch(AppScreenName.TorrentsSources.name)
            }
        )
    }

}

@Composable
private fun ContentView(
    onVideoSourcesClick: () -> Unit,
    onTorrentsSourcesClick: () -> Unit,
) {
    val strings = LocalAppStrings.current
    Column {
        SettingsItem(title = strings.videoSources){
            onVideoSourcesClick()
        }
        SettingsItem(title = strings.torrentsSources){
            onTorrentsSourcesClick()
        }
    }
}

@Composable
private fun SettingsItem(
    modifier: Modifier = Modifier,
    title: String,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth().height(54.dp).appClickable {
            onClick()
        }.padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
//        Icon(
//            modifier = Modifier.size(24.dp),
//            tint = AppTheme.colors.secondaryVariant,
//            painter = icon,
//            contentDescription = null
//        )
        Text(
            text = title,
            color = AppTheme.colors.secondaryVariant,
            modifier = Modifier.padding(start = 16.dp),
            maxLines = 1,
            fontWeight = FontWeight.Bold,
        )
    }
}
