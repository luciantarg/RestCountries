package unifor.br.restcountries.data.repository

import unifor.br.restcountries.data.model.Country
import unifor.br.restcountries.data.remote.CountriesApiService

class CountriesRepository {
    private val apiService = CountriesApiService.instance

    suspend fun getCountries(): List<Country> {
        return apiService.getAllCountries()
    }
}