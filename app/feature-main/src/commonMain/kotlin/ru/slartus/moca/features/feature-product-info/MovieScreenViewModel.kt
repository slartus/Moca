package ru.slartus.moca.features.`feature-product-info`

import com.benasher44.uuid.uuid4
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import ru.slartus.moca.`core-ui`.base.BaseViewModel
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
) : BaseViewModel<MovieViewState, Action, Any>(
    MovieViewState(
        isLoading = true,
        data = movie.mapToDetails(),
        hasTorrentsSources = false
    )
) {
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError(throwable)
    }
    private val scope = scope.plus(exceptionHandler + SupervisorJob())

    init {
        this.scope.launch {
            val details = repository.loadDetails(movie.id)
            _stateFlow.update { state ->
                MovieViewState(
                    isLoading = false,
                    data = details,
                    hasTorrentsSources = state.hasTorrentsSources
                )
            }
        }
        this.scope.launch {
            val sources = torrentsSourcesRepository.getSources()
            _stateFlow.update { state ->
                MovieViewState(
                    isLoading = state.isLoading,
                    data = state.data,
                    hasTorrentsSources = sources.any()
                )
            }
        }
    }

    private fun onError(exception: Throwable) {
        callAction(Action.Error(
            exception.message ?: exception.toString()
        ))
    }

    override fun obtainEvent(viewEvent: Any) {

    }
}

internal data class MovieViewState(
    val isLoading: Boolean,
    val data: MovieDetails,
    val hasTorrentsSources: Boolean
)

internal sealed class Action() : ru.slartus.moca.`core-ui`.base.Action {
    override val id: String = uuid4().toString()

    class Error(val message: String) : Action()
}