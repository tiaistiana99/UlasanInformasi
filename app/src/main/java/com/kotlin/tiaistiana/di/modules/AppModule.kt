package com.kotlin.tiaistiana.di.modules

import android.content.Context
import androidx.room.Room
import com.kotlin.tiaistiana.BuildConfig
import com.kotlin.tiaistiana.repository.api.ApiServices
import com.kotlin.tiaistiana.repository.api.network.LiveDataCallAdapterFactoryForRetrofit
import com.kotlin.tiaistiana.repository.db.AppDatabase
import com.kotlin.tiaistiana.repository.db.negara.CountriesDao
import com.kotlin.tiaistiana.repository.db.informasi.NewsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    /**
     * Menyediakan klien ApiServices untuk Retrofit
     */
    @Singleton
    @Provides
    fun provideNewsService(): ApiServices {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactoryForRetrofit())
            .build()
            .create(ApiServices::class.java)
    }


    /**
     * Menyediakan aplikasi AppDatabase
     */

    @Singleton
    @Provides
    fun provideDb(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "news-db")
            .fallbackToDestructiveMigration().build()


    /**
     * Menyediakan objek NewsArticlesDao untuk mengakses tabel NewsArticles dari Database
     */

    // Untuk mengakses newsarticles dari database
    @Singleton
    @Provides
    fun provideUserDao(db: AppDatabase): NewsDao = db.newsArticlesDao()

    /**
     * Menyediakan objek CountriesDao untuk mengakses tabel Negara dari Database
     */

    @Singleton
    @Provides
    fun provideCountriesDao(db: AppDatabase): CountriesDao = db.countriesDao()
}
