package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.presenters

import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.MyDispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher

class TestDispatchers : MyDispatchers {
    override fun main() = TestCoroutineDispatcher()

    override fun io() = TestCoroutineDispatcher()
}
