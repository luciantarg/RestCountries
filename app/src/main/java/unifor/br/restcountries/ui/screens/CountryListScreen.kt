package unifor.br.restcountries.ui.screens

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import unifor.br.restcountries.ui.components.CountryListItem
import unifor.br.restcountries.viewmodel.CountriesViewModel
import unifor.br.restcountries.viewmodel.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryListScreen(viewModel: CountriesViewModel, onCountryClick: (String) -> Unit) {
    val uiState by viewModel.uiState.collectAsState()
    val selectedContinent by viewModel.selectedContinent.collectAsState()
    val continents = listOf("All", "Africa", "Americas", "Asia", "Europe", "Oceania", "Antarctic")

    Scaffold(
        topBar = { TopAppBar(title = { Text("Países do Mundo") }) }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                continents.forEach { continent ->
                    FilterChip(
                        selected = selectedContinent == continent,
                        onClick = { viewModel.selectContinent(continent) },
                        label = { Text(continent) }
                    )
                }
            }

            when (val state = uiState) {
                is UiState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                is UiState.Error -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Erro: ${state.message}", color = MaterialTheme.colorScheme.error)
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(onClick = { viewModel.fetchCountries() }) {
                                Text("Tentar Novamente")
                            }
                        }
                    }
                }
                is UiState.Success -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)
                    ) {
                        items(state.countries) { country ->
                            CountryListItem(country = country, onClick = { onCountryClick(country.name.official) })
                        }
                    }
                }
            }
        }
    }
}