package ru.slartus.moca.features.`feature-product-info`

import com.benasher44.uuid.uuid4
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import ru.slartus.moca.`core-ui`.base.BaseViewModel
import ru.slartus.moca.domain.models.ProductDetails
import ru.slartus.moca.domain.models.Series
import ru.slartus.moca.domain.models.mapToDetails
import ru.slartus.moca.domain.repositories.MovieRepository
import ru.slartus.moca.domain.repositories.SerieRepository
import ru.slartus.moca.domain.repositories.TorrentsSourcesRepository

internal class SeriesScreenViewModel(
    private val series: Series,
    private val repository: SerieRepository,
    private val torrentsSourcesRepository: TorrentsSourcesRepository,
    scope: CoroutineScope,
) : BaseViewModel<SeriesViewState, SeriesAction, Any>(
    SeriesViewState(
        isLoading = true,
        data = series.mapToDetails(),
        hasTorrentsSources = false
    )
) {
    private val scope = scope.plus(exceptionHandler + SupervisorJob())

    init {
        this.scope.launch {
            val details = repository.loadDetails(series.id)
            _stateFlow.update { state ->
                SeriesViewState(
                    isLoading = false,
                    data = details,
                    hasTorrentsSources = state.hasTorrentsSources
                )
            }
        }
        this.scope.launch {
            val sources = torrentsSourcesRepository.getSources()
            _stateFlow.update { state ->
                SeriesViewState(
                    isLoading = state.isLoading,
                    data = state.data,
                    hasTorrentsSources = sources.any()
                )
            }
        }
    }

    override fun onError(throwable: Throwable) {
        callAction(SeriesAction.Error(
            throwable.message ?: throwable.toString()
        ))
    }

    override fun obtainEvent(viewEvent: Any) {

    }
}

internal data class SeriesViewState(
    val isLoading: Boolean,
    val data: ProductDetails,
    val hasTorrentsSources: Boolean
)

internal sealed class SeriesAction() : ru.slartus.moca.`core-ui`.base.Action {
    override val id: String = uuid4().toString()

    class Error(val message: String) : SeriesAction()
}