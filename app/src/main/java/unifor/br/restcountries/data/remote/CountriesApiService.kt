package unifor.br.restcountries.data.remote

import unifor.br.restcountries.data.model.Country
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface CountriesApiService {
    @GET("all?fields=name,capital,continents,region,population,area,currencies,languages,flags")
    suspend fun getAllCountries(): List<Country>

    companion object {
        private const val BASE_URL = "https://restcountries.com/v3.1/"

        val instance: CountriesApiService by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CountriesApiService::class.java)
        }
    }
}