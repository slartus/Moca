package ru.slartus.moca

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.CompositionLocalProvider
import navigation.generateGraph
import ru.alexgladkov.odyssey.compose.base.Navigator
import ru.alexgladkov.odyssey.compose.extensions.setupWithActivity
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder
import ru.alexgladkov.odyssey.compose.navigation.bottom_sheet_navigation.ModalSheetNavigator

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val rootController = RootComposeBuilder().apply { generateGraph() }.build()
        rootController.setupWithActivity(this)
        setContent {
            val providers = arrayOf(

                LocalRootController provides rootController
            )
            CompositionLocalProvider(
                *providers
            ) {

                    ModalSheetNavigator {
                        Navigator("main")
                    }

            }
        }
    }
}