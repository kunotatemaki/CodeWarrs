package com.rukiasoft.codewars.di.components

import com.rukiasoft.codewars.CodeWarsApplicationBase
import com.rukiasoft.codewars.di.modules.ActivityBuilder
import com.rukiasoft.codewars.di.modules.FragmentsProvider
import com.rukiasoft.codewars.di.modules.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Main component of the student-planner app
 */

@Singleton
@Component(modules = [(AndroidSupportInjectionModule::class),
    (ActivityBuilder::class),
    (FragmentsProvider::class), (FragmentsProvider::class),
    (ViewModelModule::class)])
interface CodeWarsComponent : AndroidInjector<CodeWarsApplicationBase>  {

    override fun inject(fireflyApp: CodeWarsApplicationBase)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: CodeWarsApplicationBase): Builder

        fun build(): CodeWarsComponent
    }

}