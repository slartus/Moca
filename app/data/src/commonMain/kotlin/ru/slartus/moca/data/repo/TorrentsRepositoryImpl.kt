package ru.slartus.moca.data.repo

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import ru.slartus.moca.domain.TorrentsApi
import ru.slartus.moca.domain.models.Torrent
import ru.slartus.moca.domain.repositories.TorrentsRepository

class TorrentsRepositoryImpl(private val apis: List<TorrentsApi>) : TorrentsRepository {
    private var data = listOf<Torrent>()
    private val mutex = Mutex()
    private val _items = MutableSharedFlow<List<Torrent>>()
    override val items = _items.asSharedFlow()

    override suspend fun find(title: String, originalTitle: String) {
        withContext(Dispatchers.Default) {
            apis.map { api ->
                launch(SupervisorJob()) {
                    val items = api.find(title, originalTitle)
                    mutex.withLock {
                        data = data + items
                        _items.emit(data)
                    }
                }
            }
        }
    }
}