package mobdao.com.openquiz

import android.app.Application
import mobdao.com.openquiz.di.components.ApplicationComponent
import mobdao.com.openquiz.di.components.DaggerApplicationComponent

@Suppress("unused")
class OpenQuizApplication : Application() {

    val appComponent: ApplicationComponent = DaggerApplicationComponent.create()
}
