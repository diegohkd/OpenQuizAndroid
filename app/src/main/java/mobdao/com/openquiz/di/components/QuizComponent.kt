package mobdao.com.openquiz.di.components

import dagger.Component
import mobdao.com.openquiz.di.modules.FragmentFactoryModule
import mobdao.com.openquiz.di.modules.QuizViewModelModule
import mobdao.com.openquiz.di.scopes.AppSingleton
import mobdao.com.openquiz.modules.quiz.QuizFragment

@Component(
    modules = [
        QuizViewModelModule::class,
        FragmentFactoryModule::class
    ]
)
@AppSingleton
interface QuizComponent {
    fun inject(quizFragment: QuizFragment)
}

//@Component(
//    dependencies = [
//        AppComponent::class
//    ],
//    modules = [
//        WheelsModule::class,
//        PetrolEngineModule::class
//    ]
//)
//interface ActivityComponent {
//    fun inject(mainActivity: MainActivity)
//
//    @Component.Builder
//    interface Builder {
//
//        @BindsInstance
//        fun horsePower(@Named("horse_power") horsePower: Int): Builder
//
//        @BindsInstance
//        fun engineCapacity(@Named("engine_capacity") engineCapacity: Int): Builder
//
//        fun appComponent(appComponent: AppComponent): Builder
//
//        fun build(): ActivityComponent
//    }
//}