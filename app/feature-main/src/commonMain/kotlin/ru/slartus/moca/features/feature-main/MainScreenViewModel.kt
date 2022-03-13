package ru.slartus.moca.features.`feature-main`

import com.benasher44.uuid.uuid4
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.slartus.moca.`core-ui`.theme.AppResources
import ru.slartus.moca.domain.models.ProductType


class MainScreenViewModel(
    private val appResources: AppResources,
    private val scope: CoroutineScope
) : EventListener {
    private val _stateFlow = MutableStateFlow(
        ScreenState(
            title = appResources.strings.movies,
            subScreen = ProductType.Movie,
            drawerOpened = false,
            actions = emptyList(),
        )
    )

    val stateFlow: StateFlow<ScreenState> = _stateFlow.asStateFlow()

    override fun onEvent(event: Event) {
        scope.launch {
            when (event) {
                Event.MenuMoviesClick -> {
                    _stateFlow.update { screenState ->
                        ScreenState(
                            title = appResources.strings.movies,
                            subScreen = ProductType.Movie,
                            actions = screenState.actions,
                            drawerOpened = false
                        )
                    }
                }
                Event.MenuTvClick -> {
                    _stateFlow.update { screenState ->
                        ScreenState(
                            title = appResources.strings.series,
                            subScreen = ProductType.Series,
                            actions = screenState.actions,
                            drawerOpened = false
                        )
                    }
                }
                Event.MenuAnimationMoviesClick -> {
                    _stateFlow.update { screenState ->
                        ScreenState(
                            title = appResources.strings.animationMovies,
                            subScreen = ProductType.AnimationMovie,
                            actions = screenState.actions,
                            drawerOpened = false
                        )
                    }
                }
                Event.MenuAnimationTvClick -> {
                    _stateFlow.update { screenState ->
                        ScreenState(
                            title = appResources.strings.animationSeries,
                            subScreen = ProductType.AnimationSeries,
                            actions = screenState.actions,
                            drawerOpened = false
                        )
                    }
                }
                is Event.Error -> addAction(
                    Action.Error(
                        message = event.error.message ?: event.error.toString()
                    )
                )
                Event.MenuClick -> {
                    _stateFlow.update { screenState ->
                        ScreenState(
                            title = screenState.title,
                            subScreen = screenState.subScreen,
                            actions = screenState.actions,
                            drawerOpened = !screenState.drawerOpened
                        )
                    }
                }
                Event.OnDrawerClosed -> {
                    _stateFlow.update { screenState ->
                        ScreenState(
                            title = screenState.title,
                            subScreen = screenState.subScreen,
                            actions = screenState.actions,
                            drawerOpened = false
                        )
                    }
                }
                Event.RefreshClick -> addAction(Action.Refresh)
                Event.SearchClick -> addAction(Action.OpenSearchScreen)
                Event.MenuSettingsClick -> addAction(Action.OpenSettingsScreen)
            }
        }
    }

    private fun addAction(action: Action) {
        _stateFlow.update { screenState ->
            ScreenState(
                title = screenState.title,
                subScreen = screenState.subScreen,
                actions = screenState.actions + action,
                drawerOpened = screenState.drawerOpened
            )
        }
    }

    fun actionReceived(messageId: String) {
        _stateFlow.update { screenState ->
            ScreenState(
                title = screenState.title,
                subScreen = screenState.subScreen,
                actions = screenState.actions.filterNot { it.id == messageId },
                drawerOpened = screenState.drawerOpened
            )
        }
    }
}


data class ScreenState(
    val title: String,
    val subScreen: ProductType,
    val actions: List<Action>,
    val drawerOpened: Boolean
)

sealed class Action {
    val id: String = uuid4().toString()

    data class Error(val message: String) : Action()
    object OpenSearchScreen : Action()
    object Refresh : Action()
    object OpenSettingsScreen : Action()
}