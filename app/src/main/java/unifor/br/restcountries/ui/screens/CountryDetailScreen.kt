package unifor.br.restcountries.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import unifor.br.restcountries.viewmodel.CountriesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryDetailScreen(
    officialName: String,
    viewModel: CountriesViewModel,
    onBackClick: () -> Unit
) {
    // Busca o país pelo nome usando a função do seu ViewModel
    val country = viewModel.getCountryByOfficialName(officialName)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(officialName) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Voltar"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        if (country != null) {
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Carrega a imagem de forma segura buscando o link da bandeira
                AsyncImage(
                    model = country.flags.png ?: country.flags.toString(),
                    contentDescription = "Bandeira",
                    modifier = Modifier.fillMaxWidth().height(200.dp),
                    contentScale = ContentScale.Fit
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Exibe as informações do país linha por linha
                DetailRow("Nome", country.name.common)
                DetailRow("Capital", country.capital?.firstOrNull() ?: "N/A")
                DetailRow("Continente", country.continents?.firstOrNull() ?: "N/A")
                DetailRow("População", country.population.toString())
                DetailRow("Área", "${country.area} km²")
            }
        } else {
            Box(modifier = Modifier.fillMaxSize()) {
                Text("País não encontrado.", modifier = Modifier.padding(16.dp))
            }
        }
    }
}

@Composable
fun DetailRow(label: String, value: String) {
    Column(modifier = Modifier.padding(vertical = 6.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Text(text = value, style = MaterialTheme.typography.bodyLarge)
        HorizontalDivider(modifier = Modifier.padding(top = 4.dp))
    }
}