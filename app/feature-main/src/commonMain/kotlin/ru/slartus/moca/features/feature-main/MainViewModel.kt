package ru.slartus.moca.features.`feature-main`

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.slartus.moca.`core-ui`.theme.AppStrings


internal class MainViewModel(
    private val appStrings: AppStrings,
    private val scope: CoroutineScope
) : EventListener {
    private val _stateFlow = MutableStateFlow(
        ScreenState(
            title = appStrings.movies,
            subScreen = SubScreen.Movies,
            error = null,
            drawerOpened = false
        )
    )
    val stateFlow: StateFlow<ScreenState> = _stateFlow.asStateFlow()

    override fun onEvent(event: Event) {
        scope.launch {
            when (event) {
                Event.MenuMoviesClick -> {
                    _stateFlow.emit(
                        ScreenState(
                            title = appStrings.movies,
                            subScreen = SubScreen.Movies,
                            error = null,
                            drawerOpened = false
                        )
                    )
                }
                Event.MenuTvClick -> {
                    _stateFlow.emit(
                        ScreenState(
                            title = appStrings.series,
                            subScreen = SubScreen.Tv,
                            error = null,
                            drawerOpened = false
                        )
                    )
                }
                is Event.Error -> {
                    _stateFlow.update { screenState ->
                        ScreenState(
                            title = screenState.title,
                            subScreen = screenState.subScreen,
                            error = event.error,
                            drawerOpened = !screenState.drawerOpened
                        )
                    }
                }
                Event.MenuClick -> {
                    _stateFlow.update { screenState ->
                        ScreenState(
                            title = screenState.title,
                            subScreen = screenState.subScreen,
                            error = null,
                            drawerOpened = !screenState.drawerOpened
                        )
                    }
                }
                Event.OnDrawerClosed -> {
                    _stateFlow.update { screenState ->
                        ScreenState(
                            title = screenState.title,
                            subScreen = screenState.subScreen,
                            error = null,
                            drawerOpened = false
                        )
                    }
                }
            }
        }
    }
}


internal data class ScreenState(
    val title: String,
    val subScreen: SubScreen,
    val error: Exception?,
    val drawerOpened: Boolean
)


internal enum class SubScreen {
    Movies, Tv
}