package com.kotlin.tiaistiana.repository.model.negara

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


/**
 * negara Article Model entitas yang merepresentasikan tabel dan menyimpan skema artikel Berita
 *
 * Secara default, Room menggunakan nama kelas sebagai nama tabel database. Jika Anda ingin tabel memiliki nama yang berbeda, atur tableName
 * properti anotasi @Entity, seperti yang ditampilkan dalam cuplikan kode berikut:
 * @Entity(namatabel = "tabel_berita")
 */

@Entity(tableName = "countries_table")
data class Country(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @SerializedName("countryName") var countryName: String? = null,
    @SerializedName("displayName") var displayName: String? = null,
    @SerializedName("countryKey") var countryKey: String? = null,
    @SerializedName("countryFagUrl") var countryFagUrl: String? = null
)