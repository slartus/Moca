package ru.slartus.moca.domain.repositories

import ru.slartus.moca.domain.models.CatalogSource

interface CatalogSourcesRepository {
    suspend fun getSources(): List<CatalogSource>
    suspend fun addSource(source: CatalogSource)
    suspend fun updateSource(source: CatalogSource)
    suspend fun deleteSource(id: Long)
}