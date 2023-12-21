package nikitafrolov.app

import android.app.Application
import nikitafrolov.exchanger.di.ExchangerModule
import nikitafrolov.network.di.NetworkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.ksp.generated.module
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        setUpKoin()
        setUpTimber()
    }

    private fun setUpTimber() {
        Timber.plant(Timber.DebugTree())
    }

    private fun setUpKoin() {
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(
                AppModule().module,
                ExchangerModule().module,
                NetworkModule().module,
            )
        }
    }

}