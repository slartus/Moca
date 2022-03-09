package ru.slartus.moca.domain.models

interface Product {
    val id: String
    val title: String
    val year: String?
    val originalTitle: String
    val posterUrl: String?
}

enum class ProductType {
    Movie, Series, AnimationMovie, AnimationSeries
}