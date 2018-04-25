package com.rukiasoft.codewars.di.modules

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.rukiasoft.codewars.di.interfaces.ViewModelKey
import com.rukiasoft.codewars.ui.search.SearchViewModel
import com.rukiasoft.codewars.viewmodel.CodeWarsViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by Raul on 16/11/2017.
 * Module for injection of ViewModels (if needed)
 */
@Suppress("unused")
@Module
internal abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    internal abstract fun bindSearchViewModel(searchViewModel: SearchViewModel): ViewModel



    @Binds
    internal abstract fun bindViewModelFactory(factory: CodeWarsViewModelFactory): ViewModelProvider.Factory
}