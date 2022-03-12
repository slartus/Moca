package ru.slartus.moca.domain.models

interface Product {
    val id: String
    val title: String
    val year: String?
    val originalTitle: String
    val posterUrl: String?

    companion object {
        operator fun invoke(): Product = ProductEmpty()
    }
}

class ProductEmpty(
    override val id: String = "",
    override val title: String = "",
    override val year: String? = null,
    override val originalTitle: String = "",
    override val posterUrl: String? = null
) : Product

enum class ProductType {
    Movie, Series, AnimationMovie, AnimationSeries, Empty
}