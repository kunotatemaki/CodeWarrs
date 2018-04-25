package com.rukiasoft.codewars.di.modules

import android.content.Context
import com.rukiasoft.codewars.CodeWarsApplicationBase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Raul on 16/11/2017.
 * Module for the app component
 */
@Module(includes = [(ViewModelModule::class)])
@Singleton
class CodeWarsModule {


    @Provides
    fun providesContext(application: CodeWarsApplicationBase): Context {
        return application.applicationContext
    }



}