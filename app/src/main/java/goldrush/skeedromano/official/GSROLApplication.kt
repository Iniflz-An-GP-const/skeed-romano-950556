package goldrush.skeedromano.official

import android.app.Application
import goldrush.skeedromano.official.di.dataModule
import goldrush.skeedromano.official.di.dispatcherModule
import goldrush.skeedromano.official.di.viewModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class GSROLApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        val appModules = dataModule + viewModule + dispatcherModule

        startKoin {
            androidLogger()
            androidContext(this@GSROLApplication)
            modules(appModules)
        }
    }
}