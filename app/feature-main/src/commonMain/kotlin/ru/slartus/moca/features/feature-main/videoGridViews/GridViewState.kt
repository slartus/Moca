package ru.slartus.moca.features.`feature-main`.videoGridViews

import com.benasher44.uuid.uuid4


internal data class GridViewState<T>(
    val isLoading: Boolean,
    val data: List<T>
)


internal sealed class Action() : ru.slartus.moca.`core-ui`.base.Action {
    override val id: String = uuid4().toString()

    class Error(val ex: Exception) : Action()
}