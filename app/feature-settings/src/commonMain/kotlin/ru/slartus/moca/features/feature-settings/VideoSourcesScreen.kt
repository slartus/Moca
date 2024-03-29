package ru.slartus.moca.features.`feature-settings`

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.kodein.di.compose.rememberInstance
import ru.alexgladkov.odyssey.compose.controllers.ModalController
import ru.alexgladkov.odyssey.compose.extensions.present
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.compose.navigation.modal_navigation.ModalSheetConfiguration
import ru.slartus.moca.`core-ui`.theme.AppTheme
import ru.slartus.moca.`core-ui`.theme.LocalAppStrings
import ru.slartus.moca.`core-ui`.views.AppNavigationIcon
import ru.slartus.moca.`core-ui`.views.TopBarView
import ru.slartus.moca.domain.models.VideoSource

@Composable
fun VideoSourcesScreen() {
    val viewModel by rememberInstance<VideoSourcesScreenViewModel>()

    val viewState by viewModel.stateFlow.collectAsState()

    val strings = LocalAppStrings.current
    val scaffoldState = rememberScaffoldState(
        snackbarHostState = remember { SnackbarHostState() }
    )

    val modalSheetController = LocalRootController.current.findModalController()

    Scaffold(
        scaffoldState = scaffoldState,
        backgroundColor = AppTheme.colors.background,
        topBar = { ScreenTopBar(strings.videoSources) },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier,
                onClick = {
                    modalSheetController.present(ModalSheetConfiguration()) {
                        AddSourceView(modalSheetController, viewModel, null)
                    }
                }) {

                Icon(Icons.Default.Add, strings.addTorrentSource)
            }
        }
    ) {
        LazyColumn(modifier = Modifier.fillMaxWidth())
        {
            items(viewState.data) { source ->
                VideoSourceView(source,
                    onSourceClick = {
                        modalSheetController.present(ModalSheetConfiguration()) {
                            AddSourceView(modalSheetController, viewModel, source)
                        }
                    },
                    onDeleteClick = {
                        viewModel.onDeleteClick(source)
                    })
            }
        }
    }
}

@Composable
private fun VideoSourceView(
    source: VideoSource,
    onSourceClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    val strings = LocalAppStrings.current
    Row(
        modifier = Modifier
            .padding(2.dp)
            .fillMaxWidth()
            .clickable {
                onSourceClick()
            }
            .defaultMinSize(minHeight = 38.dp)
            .background(AppTheme.colors.secondary)
            .padding(2.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .align(CenterVertically)
        ) {
            Text(
                modifier = Modifier,
                text = source.title,
                fontSize = 14.sp,
                color = AppTheme.colors.secondaryVariant
            )
            Text(
                modifier = Modifier,
                text = source.url,
                fontSize = 12.sp,
                color = AppTheme.colors.secondaryVariant
            )
        }

        IconButton(
            modifier = Modifier
                .align(CenterVertically),
            onClick = {
                onDeleteClick()
            },
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = strings.delete,
                tint = AppTheme.colors.highLight
            )
        }
    }
}

@Composable
private fun AddSourceView(
    modalSheetController: ModalController,
    viewModel: VideoSourcesScreenViewModel,
    source: VideoSource?
) {
    val strings = LocalAppStrings.current

    val isNew = remember {
        source == null
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = AppTheme.colors.secondary)
            .padding(4.dp)
            .padding(bottom = 12.dp),
    ) {
        val title = remember { mutableStateOf(TextFieldValue(source?.title.orEmpty())) }
        val url = remember { mutableStateOf(TextFieldValue(source?.url.orEmpty())) }
        TextFieldView(
            modifier = Modifier.fillMaxWidth(),
            hint = strings.title,
            singleLine = true,
            textFieldValue = title
        )
        TextFieldView(
            modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
            hint = strings.url,
            textFieldValue = url
        )
        Text(
            text = "Используй в запросе \$title, \$original_title, \$year и \$type. Например, http:\\\\mysite.ru?search=\$title / \$original_title&year=\$year&type=\$type",
            color = AppTheme.colors.secondaryVariant,
            fontSize = 12.sp
        )
        Button(
            modifier = Modifier.align(End),
            colors = AppTheme.buttonColors,
            onClick = {
                if (title.value.text.isNotEmpty() && url.value.text.isNotEmpty()) {
                    viewModel.addTorrentSource(source?.id, title.value.text, url.value.text)
                    modalSheetController.popBackStack()
                }
            }
        ) {
            Text(
                text = if (isNew) strings.add else strings.save,
                color = AppTheme.colors.secondaryVariant
            )
        }
    }

}

@Composable
private fun TextFieldView(
    modifier: Modifier = Modifier,
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    hint: String, textFieldValue: MutableState<TextFieldValue>
) {
    val strings = LocalAppStrings.current
    BasicTextField(
        modifier = modifier
            .background(AppTheme.colors.background)
            .defaultMinSize(minHeight = 38.dp),
        value = textFieldValue.value,
        textStyle = TextStyle(color = AppTheme.colors.secondaryVariant),
        onValueChange = {
            textFieldValue.value = it
        },
        singleLine = singleLine,
        maxLines = maxLines,
        cursorBrush = SolidColor(AppTheme.colors.secondaryVariant),
        decorationBox = { innerTextField ->
            Row(
                verticalAlignment = CenterVertically,
                modifier = Modifier.padding(horizontal = 10.dp)
            ) {
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.CenterStart
                ) {
                    if (textFieldValue.value == TextFieldValue(""))
                        Text(hint, color = AppTheme.colors.secondaryVariant)
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