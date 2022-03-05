package ru.slartus.moca.features.`feature-main`.videoGridViews

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.slartus.moca.domain.models.Movie
import ru.slartus.moca.domain.repositories.PopularMoviesRepository

class PopularMoviesViewModel(
    private val popularMoviesRepository: PopularMoviesRepository,
    private val scope: CoroutineScope
) {
    private val _state = MutableStateFlow(
        GridViewState(
            isLoading = true,
            data = emptyList<Movie>()
        )
    )

    val state: StateFlow<GridViewState<Movie>> = _state.asStateFlow()

    init {
        scope.launch {
            popularMoviesRepository.items
                .collect {
                    _state.value = GridViewState(
                        isLoading = false,
                        data = it
                    )
                }
        }
        scope.launch {
            popularMoviesRepository.reload()
        }
    }

    fun reload() {
        scope.launch {
            _state.update { state ->
                GridViewState(
                    isLoading = true,
                    data = state.data
                )
            }
            popularMoviesRepository.reload()
        }
    }

    fun loadMore(){
        scope.launch {
            popularMoviesRepository.loadMore()
        }
    }
}