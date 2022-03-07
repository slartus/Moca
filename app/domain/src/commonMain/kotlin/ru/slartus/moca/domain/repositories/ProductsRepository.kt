package ru.slartus.moca.domain.repositories

import kotlinx.coroutines.flow.Flow
import ru.slartus.moca.domain.models.Product

interface ProductsRepository<T : Product> {
    val items: Flow<List<T>>
    suspend fun reload()
    suspend fun loadMore()
}