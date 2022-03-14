package ru.slartus.moca.features.`feature-main`

import com.benasher44.uuid.uuid4
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.update
import ru.slartus.moca.`core-ui`.base.BaseViewModel
import ru.slartus.moca.`core-ui`.theme.AppResources
import ru.slartus.moca.domain.models.ProductType


class MainScreenViewModel(
    private val appResources: AppResources,
    scope: CoroutineScope
) : BaseViewModel<ScreenState, Action, Event>(
    ScreenState(
        title = appResources.strings.movies,
        subScreen = ProductType.Movie,
        drawerOpened = false
    )
), EventListener {
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        callAction(
            Action.Error(
                message = throwable.message ?: throwable.toString()
            )
        )
    }
    private val scope = scope.plus(exceptionHandler + SupervisorJob())

    override fun obtainEvent(viewEvent: Event) {
        scope.launch {
            when (viewEvent) {
                Event.MenuMoviesClick -> {
                    _stateFlow.update { screenState ->
                        ScreenState(
                            title = appResources.strings.movies,
                            subScreen = ProductType.Movie,
                            drawerOpened = false
                        )
                    }
                }
                Event.MenuTvClick -> {
                    _stateFlow.update { screenState ->
                        ScreenState(
                            title = appResources.strings.series,
                            subScreen = ProductType.Series,
                            drawerOpened = false
                        )
                    }
                }
                Event.MenuAnimationMoviesClick -> {
                    _stateFlow.update { screenState ->
                        ScreenState(
                            title = appResources.strings.animationMovies,
                            subScreen = ProductType.AnimationMovie,
                            drawerOpened = false
                        )
                    }
                }
                Event.MenuAnimationTvClick -> {
                    _stateFlow.update { screenState ->
                        ScreenState(
                            title = appResources.strings.animationSeries,
                            subScreen = ProductType.AnimationSeries,
                            drawerOpened = false
                        )
                    }
                }
                is Event.Error -> callAction(
                    Action.Error(
                        message = viewEvent.error.message ?: viewEvent.error.toString()
                    )
                )
                Event.MenuClick -> {
                    _stateFlow.update { screenState ->
                        ScreenState(
                            title = screenState.title,
                            subScreen = screenState.subScreen,
                            drawerOpened = !screenState.drawerOpened
                        )
                    }
                }
                Event.OnDrawerClosed -> {
                    _stateFlow.update { screenState ->
                        ScreenState(
                            title = screenState.title,
                            subScreen = screenState.subScreen,
                            drawerOpened = false
                        )
                    }
                }
                Event.RefreshClick -> callAction(Action.Refresh)
                Event.SearchClick -> callAction(Action.OpenSearchScreen)
                Event.MenuSettingsClick -> callAction(Action.OpenSettingsScreen)
            }
        }
    }
}


data class ScreenState(
    val title: String,
    val subScreen: ProductType,
    val drawerOpened: Boolean
)

sealed class Action : ru.slartus.moca.`core-ui`.base.Action {
    override val id: String = uuid4().toString()

    data class Error(val message: String) : Action()
    object OpenSearchScreen : Action()
    object Refresh : Action()
    object OpenSettingsScreen : Action()
}