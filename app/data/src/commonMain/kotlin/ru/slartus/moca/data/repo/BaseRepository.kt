package ru.slartus.moca.data.repo

import ru.slartus.moca.domain.CatalogApi
import ru.slartus.moca.domain.models.Product
import ru.slartus.moca.domain.repositories.ProductsRepository

abstract class BaseRepository<T : Product>(catalogApis: List<CatalogApi>) : ProductsRepository<T> {
    protected val catalog = catalogApis.first()
    private val pagedRepository: PagedRepository<T> = PagedRepository {
        getPageItems(it)
    }
    override val items = pagedRepository.items
    override suspend fun reload() = pagedRepository.reload()
    override suspend fun loadMore() = pagedRepository.loadMore()

    abstract suspend fun getPageItems(page: Int): List<T>
}