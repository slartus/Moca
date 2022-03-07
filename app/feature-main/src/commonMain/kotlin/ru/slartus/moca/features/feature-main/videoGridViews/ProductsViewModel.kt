package ru.slartus.moca.features.`feature-main`.videoGridViews

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.slartus.moca.domain.models.Product
import ru.slartus.moca.domain.repositories.ProductsRepository

class ProductsViewModel<T : Product>(
    private val popularMoviesRepository: ProductsRepository<T>,
    private val scope: CoroutineScope
) {
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->

    }
    private val coroutineContext = exceptionHandler + SupervisorJob()
    private val _state = MutableStateFlow(
        GridViewState(
            isLoading = true,
            data = emptyList<T>()
        )
    )

    val state: StateFlow<GridViewState<T>> = _state.asStateFlow()

    init {
        scope.launch(coroutineContext) {
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
        scope.launch(coroutineContext) {
            _state.update { state ->
                GridViewState(
                    isLoading = true,
                    data = state.data
                )
            }
            popularMoviesRepository.reload()
        }
    }

    fun loadMore() {
        scope.launch(coroutineContext) {
            popularMoviesRepository.loadMore()
        }
    }


}