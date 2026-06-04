package unifor.br.restcountries.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import unifor.br.restcountries.data.model.Country
import unifor.br.restcountries.data.repository.CountriesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch

sealed interface UiState {
    object Loading : UiState
    data class Success(val countries: List<Country>) : UiState
    data class Error(val message: String) : UiState
}

class CountriesViewModel : ViewModel() {
    private val repository = CountriesRepository()

    private val _allCountries = MutableStateFlow<List<Country>>(emptyList())
    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _selectedContinent = MutableStateFlow("All")
    val selectedContinent: StateFlow<String> = _selectedContinent.asStateFlow()

    init {
        fetchCountries()
        observeFilter()
    }

    fun fetchCountries() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val list = repository.getCountries()
                _allCountries.value = list
                updateFilteredList(_selectedContinent.value)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.localizedMessage ?: "Erro desconhecido")
            }
        }
    }

    private fun observeFilter() {
        _selectedContinent.onEach { continent ->
            updateFilteredList(continent)
        }.launchIn(viewModelScope)
    }

    private fun updateFilteredList(continent: String) {
        val all = _allCountries.value
        if (all.isEmpty()) return

        if (continent == "All") {
            _uiState.value = UiState.Success(all)
        } else {
            val filtered = all.filter { country ->
                country.region.equals(continent, ignoreCase = true) ||
                        country.continents.any { it.equals(continent, ignoreCase = true) }
            }
            _uiState.value = UiState.Success(filtered)
        }
    }

    fun selectContinent(continent: String) {
        _selectedContinent.value = continent
    }

    fun getCountryByOfficialName(name: String): Country? {
        return _allCountries.value.find { it.name.official == name }
    }
}
