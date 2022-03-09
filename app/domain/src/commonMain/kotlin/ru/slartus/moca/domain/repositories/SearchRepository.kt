package ru.slartus.moca.domain.repositories

import ru.slartus.moca.domain.models.Product

interface SearchRepository<T : Product> {
    suspend fun search(query: String): List<T>
}