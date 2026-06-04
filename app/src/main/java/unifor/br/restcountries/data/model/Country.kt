package unifor.br.restcountries.data.model

data class Country(
    val name: CountryName,
    val capital: List<String>?,
    val continents: List<String>,
    val region: String,
    val population: Long,
    val area: Double,
    val currencies: Map<String, Currency>?,
    val languages: Map<String, String>?,
    val flags: Flags
)

data class CountryName(
    val common: String,
    val official: String
)

data class Currency(
    val name: String,
    val symbol: String?
)

data class Flags(
    val png: String,
    val svg: String
)