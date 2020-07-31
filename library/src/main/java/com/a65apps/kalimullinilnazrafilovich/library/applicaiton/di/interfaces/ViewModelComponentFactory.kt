package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.di.interfaces

interface ViewModelComponentFactory {
    fun create(
        id: String
    ): ViewModelComponentContainer
}
