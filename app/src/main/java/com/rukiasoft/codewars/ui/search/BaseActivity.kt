package com.rukiasoft.codewars.ui.search

import android.arch.lifecycle.ViewModelProvider
import com.rukiasoft.codewars.utils.ResourcesManager
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

abstract class BaseActivity : DaggerAppCompatActivity() {

    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.Factory


    @Inject
    protected lateinit var resourcesManager: ResourcesManager
}