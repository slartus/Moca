package ru.slartus.moca.domain.repositories

import ru.slartus.moca.domain.models.Tv

interface SeriesRepository {
    suspend fun getPopular(): List<Tv>
}