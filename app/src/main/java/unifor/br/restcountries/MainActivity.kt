package unifor.br.restcountries

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import unifor.br.restcountries.navigation.AppNavigation
import unifor.br.restcountries.viewmodel.CountriesViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Forma nativa e segura de instanciar o ViewModel sem erros de importação
            val viewModel = ViewModelProvider(this)[CountriesViewModel::class.java]

            // Inicializa a navegação com o fluxo das telas
            AppNavigation(viewModel = viewModel)
        }
    }
}