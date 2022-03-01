package ru.slartus.moca.features.`feature-main`


internal interface EventListener {
    fun onEvent(event: Event)
}

internal sealed class Event {
    object MenuClick : Event()
    object MenuMoviesClick : Event()
    object MenuTvClick : Event()
    data class Error(val error: Exception) : Event()
}