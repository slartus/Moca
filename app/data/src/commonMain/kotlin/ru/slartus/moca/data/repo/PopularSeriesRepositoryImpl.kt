package ru.slartus.moca.data.repo

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import ru.slartus.moca.domain.CatalogApi
import ru.slartus.moca.domain.models.Movie
import ru.slartus.moca.domain.models.Series
import ru.slartus.moca.domain.repositories.PopularSeriesRepository

class PopularSeriesRepositoryImpl(
    private val catalogApis: List<CatalogApi>
) : PopularSeriesRepository {
    private val catalog = catalogApis.first()
    private val _series = MutableSharedFlow<List<Series>>()
    override val series: SharedFlow<List<Series>> = _series.asSharedFlow()
    override suspend fun reload() {
        val movies = catalog.getPopularTv()
        _series.emit(movies)
    }
}