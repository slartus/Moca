package ru.slartus.moca.features.`feature-main`

import com.benasher44.uuid.uuid4
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
            drawerOpened = false,
            errorMessages = emptyList(),
        )
    )
    val stateFlow: StateFlow<ScreenState> = _stateFlow.asStateFlow()

    override fun onEvent(event: Event) {
        scope.launch {
            when (event) {
                Event.MenuMoviesClick -> {
                    _stateFlow.update { screenState ->
                        ScreenState(
                            title = appStrings.movies,
                            subScreen = SubScreen.Movies,
                            errorMessages = screenState.errorMessages,
                            drawerOpened = false
                        )
                    }
                }
                Event.MenuTvClick -> {
                    _stateFlow.update { screenState ->
                        ScreenState(
                            title = appStrings.series,
                            subScreen = SubScreen.Tv,
                            errorMessages = screenState.errorMessages,
                            drawerOpened = false
                        )
                    }
                }
                is Event.Error -> {
                    _stateFlow.update { screenState ->
                        ScreenState(
                            title = screenState.title,
                            subScreen = screenState.subScreen,
                            errorMessages = screenState.errorMessages + Message(
                                id = uuid4().toString(),
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
                            errorMessages = screenState.errorMessages,
                            drawerOpened = !screenState.drawerOpened
                        )
                    }
                }
                Event.OnDrawerClosed -> {
                    _stateFlow.update { screenState ->
                        ScreenState(
                            title = screenState.title,
                            subScreen = screenState.subScreen,
                            errorMessages = screenState.errorMessages,
                            drawerOpened = false
                        )
                    }
                }
            }
        }
    }

    fun errorShown(messageId: String) {
        _stateFlow.update { screenState ->
            ScreenState(
                title = appStrings.movies,
                subScreen = SubScreen.Movies,
                errorMessages = screenState.errorMessages.filterNot { it.id == messageId },
                drawerOpened = false
            )
        }
    }
}


internal data class ScreenState(
    val title: String,
    val subScreen: SubScreen,
    val errorMessages: List<Message>,
    val drawerOpened: Boolean
)


internal enum class SubScreen {
    Movies, Tv
}

internal data class Message(val id: String, val message: String)