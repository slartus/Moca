package ru.slartus.moca.data.api.tmdb.models

import ru.slartus.moca.data.api.tmdb.Genres

data class FilterParams(
    val sortBy: SortBy = SortBy.PopularityDesc,
    val includeAdult: Boolean = false,
    val withoutGenres: List<Genres> = emptyList(),
    val withGenres: List<Genres> = emptyList(),
    val voteCountGte: Int = 0
) {
    init {
        require(voteCountGte >= 0)
    }

    fun toQueryParams(): String {
        var params = mapOf(
            "sort_by" to sortBy.param,
            "include_adult" to "$includeAdult",

            "vote_count.gte" to "$voteCountGte"
        )
        if (withoutGenres.any()) {
            params = params + mapOf(
                "without_genres" to withoutGenres.joinToString(",") { it.id.toString() },
            )
        }
        if (withGenres.any()) {
            params = params + mapOf(
                "with_genres" to withGenres.joinToString(",") { it.id.toString() },
            )
        }

        return params.map { "${it.key}=${it.value}" }
            .joinToString("&")
    }
}


enum class SortBy(val param: String) {
    PopularityAsc("popularity.asc"),
    PopularityDesc("popularity.desc"),
    ReleaseDateAsc("release_date.asc"),
    ReleaseDateDesc("release_date.desc"),
    RevenueAsc("revenue.asc"),
    RevenueDesc("revenue.desc"),
    PrimaryReleaseDateAsc("primary_release_date.asc"),
    PrimaryReleaseDateDesc("primary_release_date.desc"),
    OriginalTitleAsc("original_title.asc"),
    OriginalTitleDesc("original_title.desc"),
    VoteAverageAsc("vote_average.asc"),
    VoteAverageDesc("vote_average.desc"),
    VoteCountAsc("vote_count.asc"),
    VoteCountDesc("vote_count.desc")
}