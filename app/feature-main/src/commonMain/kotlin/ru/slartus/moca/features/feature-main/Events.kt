package ru.slartus.moca.features.`feature-main`


interface EventListener {
    fun onEvent(event: Event)
}

sealed class Event {
    object MenuClick : Event()
    object MenuMoviesClick : Event()
    object MenuTvClick : Event()
    object MenuAnimationMoviesClick : Event()
    object MenuAnimationTvClick : Event()
    object OnDrawerClosed : Event()
    object RefreshClick : Event()
    object SearchClick : Event()
    data class Error(val error: Exception) : Event()
}