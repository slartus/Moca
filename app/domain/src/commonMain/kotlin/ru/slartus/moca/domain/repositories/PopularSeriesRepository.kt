package ru.slartus.moca.domain.repositories

import kotlinx.coroutines.flow.SharedFlow
import ru.slartus.moca.domain.models.Series

interface PopularSeriesRepository {
    val items: SharedFlow<List<Series>>
    suspend fun reload()
    suspend fun loadMore()
}