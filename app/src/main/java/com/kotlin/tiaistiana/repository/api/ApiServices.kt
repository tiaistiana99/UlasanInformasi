package com.kotlin.tiaistiana.repository.api

import androidx.lifecycle.LiveData
import com.kotlin.tiaistiana.repository.api.network.Resource
import com.kotlin.tiaistiana.repository.model.informasi.NewsSource
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ApiServices {
    /**
     * Ambil artikel berita dari berita Google menggunakan GET API Call di Url yang diberikan
     * Url akan seperti ini top-headlines?country=my&apiKey=XYZ
     */
    @GET("top-headlines")
    fun getNewsSource(@QueryMap options: Map<String, String>): LiveData<Resource<NewsSource>>

}
