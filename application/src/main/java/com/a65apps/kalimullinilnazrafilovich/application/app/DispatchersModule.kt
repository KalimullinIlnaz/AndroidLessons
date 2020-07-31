package com.a65apps.kalimullinilnazrafilovich.application.app

import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.MyDispatchers
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.MyDispatchersProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DispatchersModule {
    @Provides
    @Singleton
    fun provideDispatchers(): MyDispatchers = MyDispatchersProvider()
}
