package com.kotlin.tiaistiana.ui.negara

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlin.tiaistiana.repository.model.negara.Country
import com.kotlin.tiaistiana.repository.repo.negara.CountriesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

// File yang berisi view model dari negara
@HiltViewModel
class CountriesViewModel @Inject constructor(val countriesRepository: CountriesRepository) :
    ViewModel() {

    /**
     * Memuat artikel berita dari internet dan database
     */

    private var countries: MutableLiveData<List<Country>> = MutableLiveData()

    fun getCountries(): LiveData<List<Country>> {
        viewModelScope.launch {
            val result = countriesRepository.getCountries()
            countries.postValue(result)
        }
        return countries
    }
}