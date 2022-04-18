package ru.slartus.moca.domain.models

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@Immutable
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

@Serializable
class ProductEmpty(
    override val id: String = "",
    override val title: String = "",
    override val year: String? = null,
    override val originalTitle: String = "",
    override val posterUrl: String? = null
) : Product

@Serializable
enum class ProductType {
    Movie, Series, AnimationMovie, AnimationSeries
}