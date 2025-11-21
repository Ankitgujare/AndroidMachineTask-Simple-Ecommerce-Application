package com.example.androidmachinetask.di

import com.example.androidmachinetask.data.local.AppDatabase
import com.example.androidmachinetask.data.remote.api.ProductApiService
import com.example.androidmachinetask.data.repository.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://fakestoreapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideProductApiService(retrofit: Retrofit): ProductApiService {
        return retrofit.create(ProductApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(app: android.app.Application): AppDatabase {
        return AppDatabase.getDatabase(app)
    }

    @Provides
    @Singleton
    fun provideProductRepository(
        apiService: ProductApiService,
        appDatabase: AppDatabase
    ): ProductRepository {
        return ProductRepository(apiService, appDatabase)
    }
}