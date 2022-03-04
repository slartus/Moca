package ru.slartus.moca.features.`feature-main`


interface EventListener {
    fun onEvent(event: Event)
}

sealed class Event {
    object MenuClick : Event()
    object MenuMoviesClick : Event()
    object MenuTvClick : Event()
    object OnDrawerClosed : Event()
    data class Error(val error: Exception) : Event()
}