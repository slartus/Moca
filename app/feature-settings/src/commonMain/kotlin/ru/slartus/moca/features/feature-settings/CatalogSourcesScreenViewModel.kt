package ru.slartus.moca.features.`feature-settings`

import com.benasher44.uuid.uuid4
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import ru.slartus.moca.`core-ui`.base.BaseViewModel
import ru.slartus.moca.domain.models.CatalogSource
import ru.slartus.moca.domain.repositories.CatalogSourcesRepository

internal class CatalogSourcesScreenViewModel(
    scope: CoroutineScope,
    private val repository: CatalogSourcesRepository
) : BaseViewModel<CatalogSourcesViewState, CatalogSourcesAction, Any>(
    CatalogSourcesViewState(
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
            CatalogSourcesViewState(
                isLoading = true,
                data = state.data
            )
        }

        scope.launch {
            val items = repository.getSources()
            _stateFlow.update { _ ->
                CatalogSourcesViewState(
                    isLoading = false,
                    data = items
                )
            }
        }
    }

    override fun onError(throwable: Throwable) {
        callAction(
            CatalogSourcesAction.Error(
                throwable.message ?: throwable.toString()
            )
        )
    }

    fun addTorrentSource(id: Long?, title: String, query: String) {
        scope.launch {
            if (id == null)
                repository.addSource(CatalogSource(title = title, url = query))
            else
                repository.updateSource(CatalogSource(id = id, title = title, url = query))

            reload()
        }
    }

    fun onDeleteClick(source: CatalogSource) {
        scope.launch {
            repository.deleteSource(source.id!!)

            reload()
        }
    }

    override fun obtainEvent(viewEvent: Any) {

    }
}

internal data class CatalogSourcesViewState(
    val isLoading: Boolean,
    val data: List<CatalogSource>
)

internal sealed class CatalogSourcesAction : ru.slartus.moca.`core-ui`.base.Action {
    override val id: String = uuid4().toString()

    class Error(val message: String) : CatalogSourcesAction()
}