package com.world.flobizassignment.di


import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.world.flobizassignment.network.Destination
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton
import retrofit2.converter.gson.GsonConverterFactory


@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

   @Singleton
   @Provides
   fun provideGsonBuilder() : Gson{
       return GsonBuilder()
           .create()
   }


    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson) : Retrofit.Builder{
        return Retrofit.Builder()
            .baseUrl("https://api.stackexchange.com")
            .addConverterFactory(GsonConverterFactory.create(gson))


    }

    @Singleton
    @Provides
    fun provideDataService(retrofit : Retrofit.Builder) : Destination {
        return retrofit
            .build()
            .create(Destination::class.java)
    }

}