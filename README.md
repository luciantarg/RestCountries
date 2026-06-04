Aplicativo RestCountries

Este é um projeto de aplicativo Android desenvolvido em Kotlin para a disciplina de Desenvolvimento Mobile do professor Bruno. O app consome dados de uma API pública para listar e mostrar detalhes sobre os países do mundo.

O que foi usado no projeto:
- Interface: Jetpack Compose para criar as telas.
- Internet e API: Retrofit e Gson para buscar os dados dos países na API RestCountries.
- Imagens: Biblioteca Coil para carregar as bandeiras dos países automaticamente.

Arquitetura: Padrão MVVM (Model - View - ViewModel).

O que o aplicativo faz:

- Tela Inicial: Carrega a lista completa de países e tem botões na parte superior para filtrar os países por continente (Europa, Américas, etc.).

- Tela de Detalhes: Quando você clica em um país da lista, o app abre uma nova tela mostrando a bandeira grande e dados como capital, população e área.

- Estados da Tela: O app avisa quando está carregando os dados e está preparado para mostrar se houver algum erro de conexão.

  Desenvolvido por: Luciano de Oliveira
