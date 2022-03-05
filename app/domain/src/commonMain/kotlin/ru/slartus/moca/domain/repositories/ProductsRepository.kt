package ru.slartus.moca.domain.repositories

import kotlinx.coroutines.flow.Flow

interface ProductsRepository<T> {
    val items: Flow<List<T>>
    suspend fun reload()
    suspend fun loadMore()
}