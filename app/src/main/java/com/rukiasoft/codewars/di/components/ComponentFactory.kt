package com.rukiasoft.codewars.di.components

import com.rukiasoft.codewars.CodeWarsApplicationBase

object ComponentFactory {

    fun component(context: CodeWarsApplicationBase): CodeWarsComponent {
        return DaggerCodeWarsComponent.builder()
                .application(context)
                .build()
    }

}