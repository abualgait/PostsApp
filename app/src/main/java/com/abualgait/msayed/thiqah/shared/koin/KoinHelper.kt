package com.abualgait.msayed.thiqah.shared.koin

import android.content.Context
import com.abualgait.msayed.thiqah.ui.postdetailsactivity.postDetailsActivityModule
import com.abualgait.msayed.thiqah.ui.postsactivity.postsActivityModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class KoinHelper {
    companion object {
        fun start(context: Context) {
            startKoin {
                androidContext(context)
                modules(
                        listOf(
                                appModule,
                                postsActivityModule,
                                postDetailsActivityModule


                        )
                )
            }
        }
    }
}