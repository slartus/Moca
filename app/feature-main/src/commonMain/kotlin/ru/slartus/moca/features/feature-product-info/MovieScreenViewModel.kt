package ru.slartus.moca.features.`feature-product-info`

import com.benasher44.uuid.uuid4
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import ru.slartus.moca.domain.models.Movie
import ru.slartus.moca.domain.models.MovieDetails
import ru.slartus.moca.domain.models.mapToDetails
import ru.slartus.moca.domain.repositories.MovieRepository
import ru.slartus.moca.domain.repositories.TorrentsSourcesRepository
import kotlin.math.acos

internal class MovieScreenViewModel(
    private val movie: Movie,
    private val repository: MovieRepository,
    private val torrentsSourcesRepository: TorrentsSourcesRepository,
    scope: CoroutineScope,
) {
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError(throwable)
    }
    private val scope = scope.plus(exceptionHandler + SupervisorJob())

    private val _state = MutableStateFlow(
        MovieViewState(
            isLoading = true,
            data = movie.mapToDetails(),
            actions = emptyList(),
            hasTorrentsSources = false
        )
    )

    val stateFlow: StateFlow<MovieViewState> = _state.asStateFlow()

    init {
        this.scope.launch {
            val details = repository.loadDetails(movie.id)
            _state.update { state ->
                MovieViewState(
                    isLoading = false,
                    data = details,
                    actions = state.actions,
                    hasTorrentsSources = state.hasTorrentsSources
                )
            }
        }
        this.scope.launch {
            val sources = torrentsSourcesRepository.getSources()
            _state.update { state ->
                MovieViewState(
                    isLoading = state.isLoading,
                    data = state.data,
                    actions = state.actions,
                    hasTorrentsSources = sources.any()
                )
            }
        }
    }

    fun actionReceived(messageId: String) {
        _state.update { screenState ->
            MovieViewState(
                isLoading = screenState.isLoading,
                data = screenState.data,
                actions = screenState.actions.filterNot { it.id == messageId },
                hasTorrentsSources = screenState.hasTorrentsSources
            )
        }
    }

    private fun onError(exception: Throwable) {
        _state.update { screenState ->
            MovieViewState(
                isLoading = false,
                data = screenState.data,
                actions = screenState.actions + Action.Error(
                    exception.message ?: exception.toString()
                ),
                hasTorrentsSources = screenState.hasTorrentsSources
            )
        }
    }
}

internal data class MovieViewState(
    val isLoading: Boolean,
    val data: MovieDetails,
    val actions: List<Action>,
    val hasTorrentsSources: Boolean
)

internal sealed class Action() {
    val id: String = uuid4().toString()

    class Error(val message: String) : Action()
}