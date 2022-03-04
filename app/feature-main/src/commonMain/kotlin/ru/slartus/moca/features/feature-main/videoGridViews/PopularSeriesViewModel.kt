package ru.slartus.moca.features.`feature-main`.videoGridViews

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.slartus.moca.domain.models.Movie
import ru.slartus.moca.domain.models.Series
import ru.slartus.moca.domain.repositories.PopularMoviesRepository
import ru.slartus.moca.domain.repositories.PopularSeriesRepository

class PopularSeriesViewModel(
    private val popularSeriesRepository: PopularSeriesRepository,
    private val scope: CoroutineScope
) {
    private val _state = MutableStateFlow(
        GridViewState(
            isLoading = true,
            data = emptyList<Series>()
        )
    )

    val state: StateFlow<GridViewState<Series>> = _state.asStateFlow()

    init {
        scope.launch {
            popularSeriesRepository.series
                .collect {
                    _state.value = GridViewState(
                        isLoading = false,
                        data = it
                    )
                }
        }
        scope.launch {
            popularSeriesRepository.reload()
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
            popularSeriesRepository.reload()
        }
    }
}