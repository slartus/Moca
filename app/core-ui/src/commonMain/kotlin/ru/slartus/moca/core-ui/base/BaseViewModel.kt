package ru.slartus.moca.`core-ui`.base

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

abstract class BaseViewModel<State, A : Action, Event>(initialState: State) {
    protected val _stateFlow = MutableStateFlow(initialState)
    val stateFlow: StateFlow<State> = _stateFlow.asStateFlow()

    private val _actionsFlow = MutableStateFlow<List<A>>(emptyList())
    val actionsFlow: StateFlow<List<A>> = _actionsFlow.asStateFlow()

    protected val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        if (throwable.cause !is CancellationException) {
            onError(throwable)
        }
    }

    abstract fun obtainEvent(viewEvent: Event)

    protected fun callAction(action: A) {
        _actionsFlow.update { actions ->
            actions + action
        }
    }

    protected open fun onError(throwable: Throwable) {

    }

    fun actionReceived(messageId: String) {
        _actionsFlow.update { actions ->
            actions.filterNot { it.id == messageId }
        }
    }
}

interface Action {
    val id: String
}