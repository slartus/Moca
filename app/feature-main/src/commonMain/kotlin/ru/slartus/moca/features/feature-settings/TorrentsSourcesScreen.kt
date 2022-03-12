package ru.slartus.moca.features.`feature-settings`

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import org.kodein.di.compose.rememberInstance
import ru.alexgladkov.odyssey.compose.controllers.ModalSheetController
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.compose.navigation.bottom_sheet_navigation.ModalSheetConfiguration
import ru.slartus.moca.`core-ui`.theme.LocalAppStrings
import ru.slartus.moca.`core-ui`.views.AppNavigationIcon
import ru.slartus.moca.`core-ui`.views.TopBarView
import ru.slartus.moca.core_ui.theme.AppTheme

@Composable
fun TorrentsSourcesScreen() {

    val viewModel by rememberInstance<TorrentsSourcesScreenViewModel>()

    val viewState by viewModel.stateFlow.collectAsState()

    val strings = LocalAppStrings.current
    val scaffoldState = rememberScaffoldState(
        snackbarHostState = remember { SnackbarHostState() }
    )


    val modalSheetController = LocalRootController.current.findModalSheetController()

    Scaffold(
        scaffoldState = scaffoldState,
        backgroundColor = AppTheme.colors.primaryBackground,
        topBar = { ScreenTopBar(strings.settings) },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier,
                onClick = {
                    val modalSheetConfiguration =
                        ModalSheetConfiguration(maxHeight = 0.7f, cornerRadius = 4)
                    modalSheetController.presentNew(modalSheetConfiguration) {
                        AddSourceView(modalSheetController, viewModel)
                    }
                }) {
                Icon(Icons.Default.Add, strings.addTorrentSource)
            }
        }
    ) {
        LazyColumn(modifier = Modifier.fillMaxWidth())
        {
            viewState.data.forEach { torrentSource ->
                item {
                    Box(
                        modifier = Modifier
                            .padding(2.dp)
                            .fillMaxWidth()
                            .defaultMinSize(minHeight = 38.dp)
                            .background(AppTheme.colors.secondaryBackground)
                            .padding(2.dp)
                    ) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = torrentSource.title,
                            color = AppTheme.colors.primaryText
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun AddSourceView(
    modalSheetController: ModalSheetController,
    viewModel: TorrentsSourcesScreenViewModel
) {
    val strings = LocalAppStrings.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = AppTheme.colors.secondaryBackground)
            .padding(4.dp),
    ) {
        val title = remember { mutableStateOf(TextFieldValue("")) }
        val url = remember { mutableStateOf(TextFieldValue("")) }
        TextFieldView(
            modifier = Modifier.fillMaxWidth(),
            hint = strings.title,
            title
        )
        TextFieldView(
            modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
            hint = strings.url,
            url
        )
        Button(
            modifier = Modifier,
            onClick = {
                if (!title.value.text.isEmpty() && !url.value.text.isEmpty()) {
                    viewModel.addTorrentSource(title.value.text, url.value.text)
                    modalSheetController.removeTopScreen()
                }
            }
        ) {
            Text(text = "Добавить", color = AppTheme.colors.primaryText)
        }
    }

}

@Composable
private fun TextFieldView(
    modifier: Modifier = Modifier,
    hint: String, textFieldValue: MutableState<TextFieldValue>
) {
    val strings = LocalAppStrings.current
    BasicTextField(
        modifier = modifier
            .background(AppTheme.colors.primaryBackground)
            .height(38.dp),
        value = textFieldValue.value,
        textStyle = TextStyle(color = AppTheme.colors.primaryText),
        onValueChange = {
            textFieldValue.value = it
        },
        singleLine = true,
        cursorBrush = SolidColor(AppTheme.colors.primaryText),
        decorationBox = { innerTextField ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 10.dp)
            ) {
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.CenterStart
                ) {
                    if (textFieldValue.value == TextFieldValue(""))
                        Text(hint, color = AppTheme.colors.secondaryText)
                    innerTextField()
                }
                if (textFieldValue.value != TextFieldValue("")) {
                    IconButton(
                        onClick = {
                            textFieldValue.value = TextFieldValue("")
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = strings.clear,
                            tint = AppTheme.colors.highLight
                        )
                    }
                }
            }
        }
    )
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