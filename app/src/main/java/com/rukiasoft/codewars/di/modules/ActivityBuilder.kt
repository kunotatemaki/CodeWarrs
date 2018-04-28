package com.rukiasoft.codewars.di.modules

import com.firefly.studentplanner.di.interfaces.CustomScopes
import com.rukiasoft.codewars.ui.challenges.ChallengesActivity
import com.rukiasoft.codewars.ui.details.DetailsActivity
import com.rukiasoft.codewars.ui.search.SearchActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Raul on 16/11/2017.
 * Builder for activities
 */
@Suppress("unused")
@Module
abstract class ActivityBuilder {

    @CustomScopes.ActivityScope
    @ContributesAndroidInjector
    abstract fun bindSearchScreen(): SearchActivity

    @CustomScopes.ActivityScope
    @ContributesAndroidInjector
    abstract fun bindChallengesScreen(): ChallengesActivity

    @CustomScopes.ActivityScope
    @ContributesAndroidInjector
    abstract fun bindDetailsScreen(): DetailsActivity


}