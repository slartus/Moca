package ru.slartus.moca.domain.repositories

import kotlinx.coroutines.flow.SharedFlow
import ru.slartus.moca.domain.models.Series

interface PopularSeriesRepository {
    val series: SharedFlow<List<Series>>
    suspend fun reload()
}