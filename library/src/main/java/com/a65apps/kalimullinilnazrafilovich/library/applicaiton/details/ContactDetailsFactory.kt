package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.di.interfaces.AppContainer

class ContactDetailsFactory(
    private val id: String,
    private val container: AppContainer
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return container.viewModelComponentFactory().create(id).getViewModel() as T
    }
}
