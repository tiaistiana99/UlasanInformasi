package com.kotlin.tiaistiana.repository.api.network

data class Resource<ResultType>(
    var status: Status,
    var data: ResultType? = null,
    var retrofitAPICode: Int = 0,
    var errorMessage: String? = null
) {

    companion object {
        /**
         * Membuat objek [Sumber Daya] dengan status `SUCCESS` dan [data].
         * Mengembalikan objek Resource(Status.SUCCESS, data, null)
         * nilai terakhir adalah null jadi meneruskannya secara opsional
         *
         */
        fun <ResultType> success(data: ResultType, retrofitAPICode: Int): Resource<ResultType> =
            Resource(Status.SUCCESS, data, retrofitAPICode = retrofitAPICode)

        /**
         * Membuat objek [Sumber Daya] dengan status `LOADING` untuk memberi tahu
         * UI untuk menampilkan pemuatan.
         * Mengembalikan objek Resource(Status.SUCCESS, null, null)
         * dua nilai terakhir adalah null jadi meneruskannya secara opsional
         */
        fun <ResultType> loading(): Resource<ResultType> = Resource(Status.LOADING)

        /**
         * Membuat objek [Sumber Daya] dengan status `ERROR` dan [pesan].
         * Mengembalikan objek Resource(Status.ERROR, errorMessage = pesan)
         */
        fun <ResultType> error(message: String?, retrofitAPICode: Int): Resource<ResultType> =
            Resource(Status.ERROR, errorMessage = message, retrofitAPICode = retrofitAPICode)

    }
}