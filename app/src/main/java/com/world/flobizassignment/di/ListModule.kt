package com.world.flobizassignment.di


import com.world.flobizassignment.network.Destination
import com.world.flobizassignment.repository.ListRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object ListModule {

    @Singleton
    @Provides
    fun provideListData(
        destination: Destination
    ) : ListRepository {
        return ListRepository(destination)
    }

}