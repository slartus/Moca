package ru.slartus.moca.features.`feature-product-info`

import com.benasher44.uuid.uuid4
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.update
import ru.slartus.moca.`core-ui`.base.Action
import ru.slartus.moca.`core-ui`.base.BaseViewModel
import ru.slartus.moca.domain.models.Product
import ru.slartus.moca.domain.models.Video
import ru.slartus.moca.domain.repositories.VideoSourcesRepository

internal class VideoListViewModel(
    scope: CoroutineScope,
    private val repository: VideoSourcesRepository
) : BaseViewModel<VideoViewState, VideoAction, Any>(
    VideoViewState(
        isLoading = false,
        data = emptyList()
    )
) {
    private val scope = scope.plus(exceptionHandler + SupervisorJob())
    private var product: Product = Product()
    fun setProduct(product: Product) {
        if (this.product != product) {
            this.product = product
            _stateFlow.update { state ->
                VideoViewState(
                    isLoading = state.isLoading,
                    data = emptyList()
                )
            }

            reload()
        }
    }

    private fun reload() {
        _stateFlow.update { state ->
            VideoViewState(
                isLoading = true,
                data = state.data
            )
        }

        scope.launch {
            val sources = repository.getSources()
            sources.map { source ->
                launch(SupervisorJob() + exceptionHandler) {
                    val video = repository.findIn(source, product)
                    _stateFlow.update { state ->
                        VideoViewState(
                            isLoading = state.isLoading,
                            data = state.data + video.map { it.map() }
                        )
                    }
                }
            }.joinAll()
            _stateFlow.update { state ->
                VideoViewState(
                    isLoading = false,
                    data = state.data
                )
            }
        }

    }

    override fun onError(throwable: Throwable) {
        callAction(
            VideoAction.Error(
                throwable.message ?: throwable.toString()
            )
        )
    }

    fun onVideoClick(video: VideoItem) {
        callAction(VideoAction.ShowVideo(video.url))
    }

    override fun obtainEvent(viewEvent: Any) {

    }
}


internal data class VideoViewState(
    val isLoading: Boolean,
    val data: List<VideoItem>
)

internal sealed class VideoAction : Action {
    override val id: String = uuid4().toString()

    class ShowVideo(val url: String) : VideoAction()
    class Error(val message: String) : VideoAction()
}

internal data class VideoItem(
    val id: String = uuid4().toString(),
    val source: String,
    val title: String,
    val url: String,
    val isLoading: Boolean
)

private fun Video.map() = VideoItem(
    source = this.source,
    title = this.title,
    url = this.url,
    isLoading = false
)

private fun VideoItem.map() = Video(
    source = this.source,
    title = this.title,
    url = this.url,
)