package com.kotlin.tiaistiana.repository.db.informasi

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kotlin.tiaistiana.repository.model.informasi.News
// Digunakan untuk mengakses database berita atau informasi
/**
 * Abstrak akses ke database berita
 */

@Dao
interface NewsDao {

    /**
     * Masukkan artikel ke dalam database
     */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArticles(articles: List<News>): List<Long>
    /**
     * Dapatkan semua artikel dari database
     */
    @Query("SELECT * FROM news_table")
    fun getNewsArticles(): LiveData<List<News>>

    @Query("DELETE FROM news_table")
    abstract fun deleteAllArticles()
}