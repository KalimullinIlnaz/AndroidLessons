package com.a65apps.kalimullinilnazrafilovich.library.applicaiton

import kotlinx.coroutines.CoroutineDispatcher

class TestDispatchers(private val testDispatchers: CoroutineDispatcher) : MyDispatchers {
    override fun main() = testDispatchers

    override fun io() = testDispatchers
}
