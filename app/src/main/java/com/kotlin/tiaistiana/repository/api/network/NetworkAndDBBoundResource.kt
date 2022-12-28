package com.kotlin.tiaistiana.repository.api.network

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.kotlin.tiaistiana.app.AppExecutors

abstract class NetworkAndDBBoundResource<ResultType, RequestType> @MainThread
constructor(private val appExecutors: AppExecutors) {
    /**
     * perjalanan akhir dari live data
     */
    private val result = MediatorLiveData<Resource<ResultType?>>()

    init {
        // Send loading state to UI
        result.value = Resource.loading()

        val dbSource = this.loadFromDb()

        result.addSource(dbSource) { data ->

            result.removeSource(dbSource)

            // Setelah selesai memuat data, hapuslah sumber

            when {
                shouldFetch(data) -> fetchFromNetwork(dbSource)
                else -> {
                    result.addSource(dbSource) { newData ->
                        setValue(Resource.success(newData, result.value?.retrofitAPICode ?: 0))
                    }
                }
            }
        }
    }

    /**
     * mengambil data dari jaringan dan lanjutkan ke DB lalu
     * kirim kembali ke UI.
     */

    private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {
        val apiResponse = createCall()
        // we re-attach dbSource as a new source, it will dispatch its latest value quickly
        result.addSource(dbSource) {
            result.setValue(Resource.loading())
        }

        result.addSource(apiResponse) { response ->
            result.removeSource(dbSource)
            result.removeSource(apiResponse)

            response?.apply {
                when {
                    status.isSuccessful() -> {
                        appExecutors.diskIO().execute {

                            processResponse(this)?.let { requestType ->
                                saveCallResult(requestType)
                            }
                            appExecutors.mainThread().execute {
                                // meminta secara khusus meminta data langsung baru,
                                // jika tidak, kita akan segera mendapatkan nilai cache terakhir,
                                // yang mungkin tidak akan diperbarui dengan hasil terbaru yang diterima
                                // dari jaringan.
                                result.addSource(loadFromDb()) { newData ->
                                    setValue(
                                        Resource.success(
                                            newData,
                                            result.value?.retrofitAPICode ?: 0
                                        )
                                    )
                                }
                            }
                        }
                    }
                    else -> {
                        result.addSource(dbSource) {
                            result.setValue(
                                Resource.error(
                                    errorMessage,
                                    result.value?.retrofitAPICode ?: 0
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    @MainThread
    private fun setValue(newValue: Resource<ResultType?>) {
        if (result.value != newValue) result.value = newValue
    }


    fun asLiveData(): LiveData<Resource<ResultType?>> = result

    @WorkerThread
    private fun processResponse(response: Resource<RequestType>): RequestType? = response.data

    @WorkerThread
    protected abstract fun saveCallResult(item: RequestType)

    @MainThread
    protected abstract fun shouldFetch(data: ResultType?): Boolean

    @MainThread
    protected abstract fun loadFromDb(): LiveData<ResultType>

    @MainThread
    protected abstract fun createCall(): LiveData<Resource<RequestType>>
}