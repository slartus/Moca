package ru.slartus.moca.domain.repositories

import ru.slartus.moca.domain.models.Series

interface SeriesRepository {
    suspend fun getPopular(): List<Series>
}