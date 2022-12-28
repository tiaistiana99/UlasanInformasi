package com.kotlin.tiaistiana.repository.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kotlin.tiaistiana.repository.db.negara.CountriesDao
import com.kotlin.tiaistiana.repository.db.informasi.NewsDao
import com.kotlin.tiaistiana.repository.model.negara.Country
import com.kotlin.tiaistiana.repository.model.informasi.News

// Digunakan untuk mengakses database Negara

@Database(entities = [News::class, Country::class], version = 3, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun newsArticlesDao(): NewsDao

    abstract fun countriesDao(): CountriesDao
}