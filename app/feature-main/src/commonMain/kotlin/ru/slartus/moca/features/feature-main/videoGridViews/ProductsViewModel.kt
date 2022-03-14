package ru.slartus.moca.features.`feature-main`.videoGridViews

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import ru.slartus.moca.`core-ui`.base.BaseViewModel
import ru.slartus.moca.domain.models.Product
import ru.slartus.moca.domain.repositories.ProductsRepository

internal class ProductsViewModel<T : Product>(
    private val popularMoviesRepository: ProductsRepository<T>,
    scope: CoroutineScope
) : BaseViewModel<GridViewState<T>, Action, Any>(
    GridViewState(
        isLoading = true,
        data = emptyList()
    )
) {
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError(throwable)
    }
    private val scope = scope.plus(exceptionHandler + SupervisorJob())


    init {
        scope.launch {
            popularMoviesRepository.items
                .collect {
                    _stateFlow.update { _ ->
                        GridViewState(
                            isLoading = false,
                            data = it
                        )
                    }
                }
        }
        scope.launch {
            popularMoviesRepository.reload()
        }
    }

    fun reload() {
        scope.launch {
            _stateFlow.update { state ->
                GridViewState(
                    isLoading = true,
                    data = state.data
                )
            }
            popularMoviesRepository.reload()
        }
    }

    fun loadMore() {
        scope.launch {
            popularMoviesRepository.loadMore()
        }
    }


    private fun onError(exception: Throwable) {
        callAction(
            Action.Error(
                Exception(
                    exception.message,
                    exception
                )
            )
        )
    }

    override fun obtainEvent(viewEvent: Any) {
        TODO("Not yet implemented")
    }
}