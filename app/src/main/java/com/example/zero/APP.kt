package com.example.zero

import android.app.Application
import org.koin.core.context.startKoin

class APP:Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(
                listOf(module)
            )
        }
    }
}