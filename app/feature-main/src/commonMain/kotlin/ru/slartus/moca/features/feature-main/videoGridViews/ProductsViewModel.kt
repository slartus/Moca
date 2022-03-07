package ru.slartus.moca.features.`feature-main`.videoGridViews

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.slartus.moca.domain.models.Product
import ru.slartus.moca.domain.repositories.ProductsRepository

internal class ProductsViewModel<T : Product>(
    private val popularMoviesRepository: ProductsRepository<T>,
    private val scope: CoroutineScope
) {
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError(throwable)
    }
    private val coroutineContext = exceptionHandler + SupervisorJob()
    private val _state = MutableStateFlow(
        GridViewState(
            isLoading = true,
            data = emptyList<T>(),
            actions = emptyList()
        )
    )

    val state: StateFlow<GridViewState<T>> = _state.asStateFlow()

    init {
        scope.launch(coroutineContext) {
            popularMoviesRepository.items
                .collect {
                    _state.update { state ->
                        GridViewState(
                            isLoading = false,
                            data = it,
                            actions = state.actions
                        )
                    }
                }
        }
        scope.launch(coroutineContext) {
            popularMoviesRepository.reload()
        }
    }

    fun reload() {
        scope.launch(coroutineContext) {
            _state.update { state ->
                GridViewState(
                    isLoading = true,
                    data = state.data,
                    actions = state.actions
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

    fun actionReceived(messageId: String) {
        _state.update { screenState ->
            GridViewState(
                isLoading = screenState.isLoading,
                data = screenState.data,
                actions = screenState.actions.filterNot { it.id == messageId }
            )
        }
    }

    private fun onError(exception: Throwable) {
        _state.update { screenState ->
            GridViewState(
                isLoading = false,
                data = screenState.data,
                actions = screenState.actions + Action.Error(
                    Exception(
                        exception.message,
                        exception
                    )
                )
            )
        }
    }
}