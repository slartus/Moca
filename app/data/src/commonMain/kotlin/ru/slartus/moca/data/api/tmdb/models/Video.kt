package ru.slartus.moca.data.api.tmdb.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class Video(
    val id: String? = null,
    val iso_3166_1: String? = null,
    val iso_639_1: String? = null,
    val key: String? = null,
    val name: String? = null,
    val official: Boolean? = null,
    val published_at: String? = null,
    val site: VideoSite? = null,
    val size: Int? = null,
    val type: VideoType? = null
)

@Serializable
internal enum class VideoType(val value: String) {
    @SerialName("Trailer")
    Trailer("Trailer"),

    @SerialName("Teaser")
    Teaser("Teaser"),

    @SerialName("Clip")
    Clip("Clip"),

    @SerialName("Featurette")
    Featurette("Featurette"),

    @SerialName("Opening Credits")
    OpeningCredits("Opening Credits"),

    @SerialName("Behind the Scenes")
    BehindTheScenes("Behind the Scenes");
}

@Serializable
internal enum class VideoSite(val value: String) {
    @SerialName("YouTube")
    YouTube("YouTube"),

    @SerialName("Vimeo")
    Vimeo("Vimeo"),
}
