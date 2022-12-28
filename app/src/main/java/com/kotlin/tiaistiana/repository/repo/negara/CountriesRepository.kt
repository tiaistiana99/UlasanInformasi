package com.kotlin.tiaistiana.repository.repo.negara

import android.content.Context
import com.kotlin.tiaistiana.repository.db.negara.CountriesDao
import com.kotlin.tiaistiana.repository.model.negara.Country
import com.kotlin.tiaistiana.utils.CountryNameMapping
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton
/**
 * Repositori mengabstraksi logika pengambilan data dan mempertahankannya
 * luring. Mereka adalah sumber data sebagai satu-satunya sumber kebenaran.
 *
 */

@Singleton
class CountriesRepository @Inject constructor(
    private val countriesDao: CountriesDao,
    @ApplicationContext val context: Context
) {

    /**
     * Fetch the news articles from database if exist else fetch from web
     * and persist them in the database
     */
    suspend fun getCountries(): List<Country> {
        withContext(Dispatchers.IO) {
            val list: List<String>? = getListFromAssets()
            val listOfCountries = ArrayList<Country>()
            list?.forEach { item ->
                val country = Country().apply {
                    countryName = item
                    displayName = getDisplayName(item)
                    countryFagUrl = getFlagUrl(item)
                    countryKey = CountryNameMapping.getCountryKey(item)
                }
                listOfCountries.add(country)
            }
            countriesDao.deleteAllCountries()
            countriesDao.insertCountries(listOfCountries)
        }

        return countriesDao.getCountries()
    }


    private suspend fun getListFromAssets(): List<String>? = withContext(Dispatchers.IO) {
        val asList = context.assets.list("countries")?.asList<String>()
        asList
    }


    private fun getDisplayName(name: String): String =
        name.replace("_", " ").replace(".png", "")


    private fun getFlagUrl(name: String): String = "file:///android_asset/countries/$name"

}