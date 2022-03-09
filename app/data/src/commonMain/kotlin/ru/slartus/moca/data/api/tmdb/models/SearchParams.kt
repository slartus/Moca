package ru.slartus.moca.data.api.tmdb.models

data class SearchParams(
    val query: String,
    val includeAdult: Boolean = false
) {
    fun toQueryParams(): String {
        val params = mapOf(
            "query" to query,
            "include_adult" to "$includeAdult"
        )

        return params.map { "${it.key}=${it.value}" }
            .joinToString("&")
    }
}