package ru.slartus.moca.features.`feature-settings`

import com.benasher44.uuid.uuid4
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import ru.slartus.moca.`core-ui`.base.BaseViewModel
import ru.slartus.moca.domain.models.VideoSource
import ru.slartus.moca.domain.repositories.VideoSourcesRepository

internal class VideoSourcesScreenViewModel(
    scope: CoroutineScope,
    private val repository: VideoSourcesRepository
) : BaseViewModel<VideoSourcesViewState, VideoSourcesAction, Any>(
    VideoSourcesViewState(
        isLoading = false,
        data = emptyList()
    )
) {
    private val scope = scope.plus(exceptionHandler + SupervisorJob())

    init {
        reload()
    }

    private fun reload() {
        _stateFlow.update { state ->
            VideoSourcesViewState(
                isLoading = true,
                data = state.data
            )
        }

        scope.launch {
            val items = repository.getSources()
            _stateFlow.update { _ ->
                VideoSourcesViewState(
                    isLoading = false,
                    data = items
                )
            }
        }
    }

    override fun onError(throwable: Throwable) {
        callAction(
            VideoSourcesAction.Error(
                throwable.message ?: throwable.toString()
            )
        )
    }

    fun addTorrentSource(id: Long?, title: String, query: String) {
        scope.launch {
            if (id == null)
                repository.addSource(VideoSource(title = title, url = query))
            else
                repository.updateSource(VideoSource(id = id, title = title, url = query))

            reload()
        }
    }

    fun onDeleteClick(source: VideoSource) {
        scope.launch {
            repository.deleteSource(source.id!!)

            reload()
        }
    }

    override fun obtainEvent(viewEvent: Any) {

    }
}


internal data class VideoSourcesViewState(
    val isLoading: Boolean,
    val data: List<VideoSource>
)

internal sealed class VideoSourcesAction : ru.slartus.moca.`core-ui`.base.Action {
    override val id: String = uuid4().toString()

    class Error(val message: String) : VideoSourcesAction()
}