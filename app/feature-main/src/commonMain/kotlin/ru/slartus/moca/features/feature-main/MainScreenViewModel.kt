package ru.slartus.moca.features.`feature-main`

import com.benasher44.uuid.uuid4
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.slartus.moca.core_ui.theme.AppResources


class MainScreenViewModel(
    private val appResources: AppResources,
    private val scope: CoroutineScope
) : EventListener {
    private val _stateFlow = MutableStateFlow(
        ScreenState(
            title = appResources.strings.movies,
            subScreen = SubScreen.Movies,
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
                            subScreen = SubScreen.Movies,
                            actions = screenState.actions,
                            drawerOpened = false
                        )
                    }
                }
                Event.MenuTvClick -> {
                    _stateFlow.update { screenState ->
                        ScreenState(
                            title = appResources.strings.series,
                            subScreen = SubScreen.Tv,
                            actions = screenState.actions,
                            drawerOpened = false
                        )
                    }
                }
                is Event.Error -> {
                    _stateFlow.update { screenState ->
                        ScreenState(
                            title = screenState.title,
                            subScreen = screenState.subScreen,
                            actions = screenState.actions + Action.Error(
                                message = event.error.message ?: event.error.toString()
                            ),
                            drawerOpened = screenState.drawerOpened
                        )
                    }
                }
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
                Event.RefreshClick -> {
                    _stateFlow.update { screenState ->
                        ScreenState(
                            title = screenState.title,
                            subScreen = screenState.subScreen,
                            actions = screenState.actions + Action.Refresh(),
                            drawerOpened = screenState.drawerOpened
                        )
                    }
                }
            }
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
    val subScreen: SubScreen,
    val actions: List<Action>,
    val drawerOpened: Boolean
)


enum class SubScreen {
    Movies, Tv
}

sealed class Action() {
    val id: String = uuid4().toString()

    class Error(val message: String) : Action()
    class Refresh() : Action()
}