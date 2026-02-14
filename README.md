💰 Sistema Bancário

Este projeto foi desenvolvido para consolidar meus conhecimentos em Programação Orientada a Objetos (POO), arquitetura em camadas e desenvolvimento de interfaces gráficas com Java.

O objetivo foi simular o núcleo de um sistema bancário real, priorizando organização, separação de responsabilidades e tratamento adequado de erros de domínio.

🎯 Sobre o Projeto

O sistema permite o gerenciamento completo de contas bancárias, incluindo:

Abertura de contas (Corrente e Poupança)

Depósitos e saques com validação de saldo

Transferências entre contas

Geração de extrato detalhado com histórico de transações

Aplicação de rendimento para contas poupança

As operações são protegidas por exceções personalizadas, garantindo que ações inválidas (como saldo insuficiente ou conta inexistente) sejam tratadas de forma controlada.

🛠️ Tecnologias e Conceitos Utilizados

Java (JDK 17+)

Java Swing – Interface gráfica com layout organizado e feedback visual ao usuário.

Gson (JSON) – Persistência de dados em arquivo contas.json.

Arquitetura em Camadas (MVC) – Separação entre:

model (entidades)

service (regras de negócio)

view (interface gráfica)

Exceções Personalizadas – Controle de erros específicos do domínio bancário.

POO Avançada – Uso de:

Classes abstratas

Herança

Polimorfismo

Encapsulamento

🚀 Principais Funcionalidades

✔ Gestão de contas com validação de número e CPF únicos

✔ Depósitos, saques e transferências com validação de regras

✔ Histórico completo de transações com data e hora

✔ Aplicação de rendimento em contas poupança

✔ Persistência automática ao fechar a aplicação

✔ Carregamento automático dos dados ao iniciar

📂 Como Executar

1. Ter o Java JDK 17 ou superior instalado.

2. Clonar o repositório:

  git clone <url-do-repositorio>

3. Executar a classe App.java localizada no pacote app.

Projeto desenvolvido por Mateus Gomes
