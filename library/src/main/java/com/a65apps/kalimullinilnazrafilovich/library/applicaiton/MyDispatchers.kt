package com.a65apps.kalimullinilnazrafilovich.library.applicaiton

import kotlinx.coroutines.CoroutineDispatcher

interface MyDispatchers {
    fun main(): CoroutineDispatcher
    
    fun io(): CoroutineDispatcher
}
