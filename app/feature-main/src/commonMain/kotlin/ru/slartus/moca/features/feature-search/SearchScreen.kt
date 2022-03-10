package ru.slartus.moca.features.`feature-search`

import AsyncImage
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.kodein.di.compose.rememberInstance
import ru.alexgladkov.odyssey.compose.RootController
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.core.animations.AnimationType
import ru.slartus.moca.`core-ui`.theme.AppStrings
import ru.slartus.moca.`core-ui`.theme.LocalAppStrings
import ru.slartus.moca.`core-ui`.views.AppNavigationIcon
import ru.slartus.moca.`core-ui`.views.TopBarView
import ru.slartus.moca.core.AppScreenName
import ru.slartus.moca.core_ui.theme.AppTheme
import ru.slartus.moca.domain.models.Movie
import ru.slartus.moca.domain.models.Product
import ru.slartus.moca.domain.models.ProductType
import ru.slartus.moca.domain.models.Series


@Composable
fun SearchScreen(productType: ProductType) {
    val strings = LocalAppStrings.current
    val rootController = LocalRootController.current
    val coroutineScope = rememberCoroutineScope()
    val model by when (productType) {
        ProductType.Movie -> rememberInstance<SearchScreenViewModel<Movie>>(tag = "movies")
        ProductType.Series -> rememberInstance<SearchScreenViewModel<Series>>(tag = "series")
        ProductType.AnimationMovie -> rememberInstance<SearchScreenViewModel<Movie>>(tag = "animation.movies")
        ProductType.AnimationSeries -> rememberInstance<SearchScreenViewModel<Series>>(tag = "animation.series")
    }
    val searchViewModel by remember(productType) {
        mutableStateOf(model)
    }

    val searchState by searchViewModel.stateFlow.collectAsState()
    val searchBy = remember { mutableStateOf(TextFieldValue(searchState.query)) }
    searchViewModel.onQueryChanged(searchState.query, searchBy.value.text)

    val scaffoldState = rememberScaffoldState(
        snackbarHostState = remember { SnackbarHostState() }
    )
    searchState.actions.firstOrNull()?.let {
        searchViewModel.actionReceived(it.id)
        when (it) {
            is Action.Error ->
                coroutineScope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(it.message)
                }
        }
    }
    Scaffold(
        scaffoldState = scaffoldState,
        backgroundColor = AppTheme.colors.primaryBackground,
        topBar = {
            ScreenTopBarView(searchBy, strings, rootController)
        }
    ) {
        if (searchState.isLoading)
            ProgressView()
        SearchResultView(searchState.data.items, productType, rootController)
    }
}

@Composable
private fun ScreenTopBarView(
    searchBy: MutableState<TextFieldValue>,
    strings: AppStrings,
    rootController: RootController
) {
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
                //searchViewModel.onQueryChanged("")
                rootController.popBackStack()
            }
        }
    )
}

@Composable
private fun ProgressView() {
    Box(modifier = Modifier.fillMaxWidth()) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center),
            color = AppTheme.colors.highLight,
        )
    }
}

@Composable
private fun <T : Product> SearchResultView(
    items: List<T>,
    productType: ProductType,
    rootController: RootController
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items.forEach { item ->
            item {
                SearchResultItemView(item) {
                    val screenName = when (productType) {
                        ProductType.Movie, ProductType.AnimationMovie -> AppScreenName.MovieInfo.name
                        ProductType.AnimationSeries, ProductType.Series -> AppScreenName.SeriesInfo.name
                    }
                    rootController.launch(
                        screenName,
                        params = item,
                        animationType = AnimationType.Push(300)
                    )
                }
            }
        }
    }
}

@Composable
private fun <T : Product> SearchResultItemView(
    item: T,
    onItemClick: (item: T) -> Unit
) {
    Row(
        modifier = Modifier
            .height(128.dp)
            .fillMaxWidth()
            .clickable {
                onItemClick(item)

            }
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .width(96.dp)
        ) {
            val poster = item.posterUrl
            if (poster != null)
                AsyncImage(
                    modifier = Modifier
                        .fillMaxSize(),
                    imageUrl = poster,
                    contentDescription = item.title,
                    contentScale = ContentScale.FillWidth
                )
        }

        Column(
            modifier = Modifier
                .padding(horizontal = 4.dp)
                .fillMaxWidth(),
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = "${item.title} ${item.year ?: ""}",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = AppTheme.colors.primaryText,
                fontWeight = FontWeight.Bold,
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = item.originalTitle,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = AppTheme.colors.primaryText
            )
        }

    }
}

@Composable
fun SearchViewTextField(state: MutableState<TextFieldValue>) {
    val focusRequester = remember { FocusRequester() }
    val strings = LocalAppStrings.current
    Box(
        modifier = Modifier
            .border(width = 1.dp, color = AppTheme.colors.primaryBackground)
            .fillMaxWidth()
    ) {
        BasicTextField(
            modifier = Modifier
                .focusRequester(focusRequester)
                .background(AppTheme.colors.primaryBackground)
                .height(38.dp)
                .fillMaxWidth(),
            value = state.value,
            onValueChange = {
                state.value = it
            },
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

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}