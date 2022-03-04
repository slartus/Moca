package ru.slartus.moca.features.`feature-main`.di

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import ru.slartus.moca.features.`feature-main`.MainScreenViewModel

val mainScreenModule = DI.Module("mainScreenModule") {
    bindSingleton { MainScreenViewModel(instance(), instance()) }
}

