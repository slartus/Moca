package ru.slartus.moca.data.repo

import coroutines.IO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.slartus.moca.db.MocaDatabase
import ru.slartus.moca.domain.models.CatalogSource
import ru.slartus.moca.domain.repositories.CatalogSourcesRepository

class CatalogSourcesRepositoryImpl(
    database: MocaDatabase
) : CatalogSourcesRepository {

    private val queries = database.catalogSourcesQueries

    override suspend fun getSources(): List<CatalogSource> = withContext(Dispatchers.IO) {
        queries.selectAll().executeAsList().map {
            CatalogSource(it.id, it.title, it.url)
        }
    }

    override suspend fun addSource(source: CatalogSource) =
        withContext(Dispatchers.IO) {
            queries.insert(source.title, source.url)
        }

    override suspend fun updateSource(source: CatalogSource) =
        withContext(Dispatchers.IO) {
            queries.update(
                source.title,
                source.url,
                source.id!!
            )
        }

    override suspend fun deleteSource(id: Long) =
        withContext(Dispatchers.IO) {
            queries.delete(id)
        }
}