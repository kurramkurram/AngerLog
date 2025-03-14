package io.github.kurramkurram.angerlog

import android.app.Application
import io.github.kurramkurram.angerlog.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AngerLogApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@AngerLogApplication)
            modules(appModule)
        }
    }
}
