package ru.slartus.moca

import android.app.Application
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

class App : Application() {

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        Napier.base(DebugAntilog())
    }

    companion object {
        lateinit var instance: App
            private set
    }
}