package com.kotlin.tiaistiana.repository.model.informasi

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName



/**
 * News Article Model entitas yang merepresentasikan tabel dan menyimpan skema artikel Berita
 *
 * Secara default, Room menggunakan nama kelas sebagai nama tabel database. Jika Anda ingin tabel memiliki nama yang berbeda, atur tableName
 * properti anotasi @Entity, seperti yang ditampilkan dalam cuplikan kode berikut:
 * @Entity(namatabel = "tabel_berita")
 */

@Entity(tableName = "news_table")
data class News(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @SerializedName("author") var author: String? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("url") var url: String? = null,
    @SerializedName("urlToImage") var urlToImage: String? = null,
    @SerializedName("publishedAt") var publishedAt: String? = null
)