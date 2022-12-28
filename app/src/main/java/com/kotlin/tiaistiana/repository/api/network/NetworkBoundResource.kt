package com.kotlin.tiaistiana.repository.api.network

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

abstract class NetworkResource<RequestType> @MainThread constructor() {


    private val result = MediatorLiveData<Resource<RequestType>>()

    init {
        // mengirimkan status pemuatan ke UI
        result.value = Resource.loading()
        fetchFromNetwork()
    }


    private fun fetchFromNetwork() {
        val apiResponse = createCall()
        // Melakukan papemangnggilan terhadap jaringan
        result.addSource(apiResponse) { response ->
            result.removeSource(apiResponse)
            // mengirimkan hasilnya
            response?.apply {
                when {
                    status.isSuccessful() -> setValue(this)
                    else -> setValue(
                        Resource.error(
                            errorMessage,
                            result.value?.retrofitAPICode ?: 0
                        )
                    )
                }
            }
        }
    }

    fun asLiveData(): LiveData<Resource<RequestType>> = result


    @MainThread
    private fun setValue(newValue: Resource<RequestType>) {
        if (result.value != newValue)
            result.value = newValue
    }

    @MainThread
    protected abstract fun createCall(): LiveData<Resource<RequestType>>
}