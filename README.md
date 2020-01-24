# Teste de conhecimento: Desenvolvedor Android (estágio)

Este teste tem como objetivo avaliar seus conhecimentos e perceber na prática sua familiaridade com programação para plataforma Android.

Você terá uma semana a partir da entrega deste teste para criar o aplicativo descrito abaixo. Caso aconteça de você não conseguir terminar todo o teste, envie-o mesmo assim, pois o não cumprimento de todos os requisitos não significa que você está automaticamente desclassificado.

Você terá a liberdade de utilizar a arquitetura e organização de código que desejar, mas lembre-se: Quanto mais alinhado for seu padrão de desenvolvimento com as boas práticas que a documentação oficial do Android aconselha, maiores serão as chances de você ser bem classificado!

Desde já, lhe desejamos boa sorte!

*Happy Coding!*

# Aplicativo a ser desenvolvido

Você irá criar um aplicativo que consulta as informações da API do GitHub (documentação: https://developer.github.com/v3/).

O aplicativo deverá conter as seguintes telas:

### Tela de informações do usuário

Nesta tela, você deverá consultar via API REST os dados de algum usuário do GitHub (o usuário fica à sua escolha) e mostrar os seguintes dados:
* Foto do usuário;
* Nome do usuário;
* Descrição do usuário;

Esta tela também deverá conter um botão chamado *Ver repositórios*, onde irá redirecionar o usuário para a próxima tela necessária.

### Tela de repositórios do usuário

Nesta tela, você deverá listar os repositórios que o usuário têm atrelado em sua conta. Pra cada item dessa lista, deverão conter as seguintes informações:
* Nome do repositório;
* Descrição do repositório;
* Linguagem na qual o repositório é baseado;

# Pré-requisitos

O aplicativo deverá funcionar em dispositivos com a versão do sistema operacional Android superior ou igual 5.0 (Lollipop - API 21).

A linguagem na qual o teste pode ser realizado é Java ou Kotlin.

Sinta-se livre para desenvolver sua própria UI/UX, mas se quiser uma sugestão, aqui está:

<img src="https://i.imgur.com/8T6TNQP.png" width="300">  <img src="https://i.imgur.com/3Z9Aasc.png" width="300">

# Como realizar o teste

1. Realize o Fork deste repositório e desenvolva sua aplicação dentro do mesmo (como pode perceber, conhecimentos em Git e GitHub são obrigatórios para realizar o teste).
2. Quando terminar todas suas implementações, abra um Pull Request do seu código e envie um e-mail para luiz.matias@wolk.com.br informando o envio do código. 
3. Agora basta aguardar o feedback do teste! Em alguns dias você obterá um retorno via e-mail do resultado :tada:

# Dicas

* Para fazer sua aplicação, escolha um usuário que tenha foto de perfil e alguns repositórios em sua conta, para garantir que todas as informações apareçam na tela corretamente.
* O aplicativo será testado a fim de verificar quebras e exceções (por exemplo: não tratar um possível dado nulo que venha da API). Portanto, garanta que o aplicativo esteja preparado para tratar qualquer ponto que possa gerar uma exceção que resulte em um *crash* do aplicativo.
* Se você deseja ir além do que o teste pede, toda adição é bem-vinda e será considerada! Só tenha em mente que se você implementou algo a mais e o mesmo não funcionou corretamente, tal ato irá pesar negativamente para sua avaliação.
* Sinta-se livre para utilizar bibliotecas de terceiros para desenvolver o aplicativo. No carregamento de imagens, por exemplo, sugiro que seja utilizado bibliotecas como o Picasso ou Glide.
