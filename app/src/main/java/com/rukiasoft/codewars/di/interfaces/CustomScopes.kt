package com.rukiasoft.codewars.di.interfaces

import javax.inject.Scope

/**
 * Created by Raul on 16/11/2017.
 * Custom scopes for injection of activities and fragments
 */
interface CustomScopes {

    @Scope
    annotation class ActivityScope

    @Scope
    annotation class FragmentScope

}