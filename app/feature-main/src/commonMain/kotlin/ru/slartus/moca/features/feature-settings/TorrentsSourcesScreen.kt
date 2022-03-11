package ru.slartus.moca.features.`feature-settings`

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import org.kodein.di.compose.rememberInstance
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.slartus.moca.`core-ui`.theme.LocalAppStrings
import ru.slartus.moca.`core-ui`.views.AppNavigationIcon
import ru.slartus.moca.`core-ui`.views.TopBarView
import ru.slartus.moca.core_ui.theme.AppTheme

@Composable
fun TorrentsSourcesScreen() {
    val coroutineScope = rememberCoroutineScope()

    val viewModel by rememberInstance<TorrentsSourcesScreenViewModel>()

    val viewState by viewModel.stateFlow.collectAsState()

    val strings = LocalAppStrings.current
    val scaffoldState = rememberScaffoldState(
        snackbarHostState = remember { SnackbarHostState() }
    )
    Scaffold(
        scaffoldState = scaffoldState,
        backgroundColor = AppTheme.colors.primaryBackground,
        topBar = { ScreenTopBar(strings.settings) }
    ) {
        LazyColumn(modifier = Modifier.fillMaxWidth())
        {
            item {
                Row(modifier = Modifier.fillMaxWidth()) {
                    TextField(
                        modifier = Modifier.weight(1f),
                        value = TextFieldValue(""),
                        onValueChange = { textFieldValue: TextFieldValue ->

                        })
                    Button(
                        modifier = Modifier,
                        onClick = {

                        }) {
                        Text(text = "Добавить", color = AppTheme.colors.primaryText)
                    }
                }


            }
            viewState.data.forEach { torrentSource ->
                item {
                    Text(text = torrentSource.title, color = AppTheme.colors.primaryText)
                }
            }
        }
    }
}

@Composable
private fun ScreenTopBar(title: String) {
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