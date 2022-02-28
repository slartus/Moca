package ru.slartus.moca.features.`feature-main`


internal interface EventListener {
    fun onEvent(event: Event)
}

internal sealed class Event {
    object MenuMoviesClick : Event()
}