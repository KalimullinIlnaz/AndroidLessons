package com.a65apps.kalimullinilnazrafilovich.library.applicaiton

import kotlinx.coroutines.Dispatchers

class MyDispatchersProvider : MyDispatchers {
    override fun main() = Dispatchers.Main
    override fun io() = Dispatchers.IO
}
