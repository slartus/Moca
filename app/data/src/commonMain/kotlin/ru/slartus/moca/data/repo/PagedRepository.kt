package ru.slartus.moca.data.repo

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class PagedRepository<T>(private val loadPage: suspend (page: Int) -> List<T>) {
    private var page = 1
    private var data = listOf<T>()
    private val _items = MutableSharedFlow<List<T>>()
    val items = _items.asSharedFlow()

    suspend fun reload() {
        page = 1
        val items = loadPage(page)
        data = items
        _items.emit(data)
    }

    suspend fun loadMore() {
        val items = loadPage(++page)

        data = data + items
        _items.emit(data)
    }
}