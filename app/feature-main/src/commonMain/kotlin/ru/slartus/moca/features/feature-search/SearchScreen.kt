package ru.slartus.moca.features.`feature-search`

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import org.kodein.di.compose.rememberInstance
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.slartus.moca.`core-ui`.theme.LocalAppStrings
import ru.slartus.moca.`core-ui`.views.AppNavigationIcon
import ru.slartus.moca.`core-ui`.views.TopBarView
import ru.slartus.moca.core_ui.theme.AppTheme
import ru.slartus.moca.domain.models.Movie


@Composable
fun SearchScreen() {
    val strings = LocalAppStrings.current
    val rootController = LocalRootController.current
    val searchBy = remember { mutableStateOf(TextFieldValue("")) }

    val searchViewModel by rememberInstance<SearchScreenViewModel<Movie>>()
    val searchState by searchViewModel.stateFlow.collectAsState()
    searchViewModel.onQueryChanged(searchBy.value.text)

    val scaffoldState = rememberScaffoldState(
        snackbarHostState = remember { SnackbarHostState() }
    )
    Scaffold(
        scaffoldState = scaffoldState,
        backgroundColor = AppTheme.colors.primaryBackground,
        topBar = {
            TopBarView(
                modifier = Modifier,
                title = {
                    SearchViewTextField(searchBy)
                },
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

        LazyColumn {
            searchState.data.items.forEach { item ->
                item {
                    Text(text = item.title, color = AppTheme.colors.primaryText)
                }
            }
        }
    }
}

@Composable
fun SearchViewTextField(state: MutableState<TextFieldValue>) {
    val strings = LocalAppStrings.current
    Box(
        modifier = Modifier
            .border(width = 1.dp, color = AppTheme.colors.primaryBackground)
            .fillMaxWidth()
    ) {
        BasicTextField(
            value = state.value,
            onValueChange = {
                state.value = it
            },
            modifier = Modifier
                .background(AppTheme.colors.primaryBackground)
                .height(38.dp)
                .fillMaxWidth(),
            singleLine = true,
            maxLines = 1,
            textStyle = TextStyle(color = AppTheme.colors.primaryText),
            cursorBrush = SolidColor(AppTheme.colors.primaryText),
            decorationBox = { innerTextField ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = strings.search,
                        tint = AppTheme.colors.highLight
                    )
                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (state.value == TextFieldValue(""))
                            Text(strings.search, color = AppTheme.colors.secondaryText)
                        innerTextField()
                    }
                    if (state.value != TextFieldValue("")) {
                        IconButton(
                            onClick = {
                                state.value = TextFieldValue("")
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
}