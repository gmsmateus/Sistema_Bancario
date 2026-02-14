💰 Sistema Bancário Pro
Este é um projeto que desenvolvi para consolidar meus conhecimentos em Programação Orientada a Objetos (POO) e interfaces gráficas. O objetivo foi simular o núcleo de um sistema bancário real, focando em segurança de dados e organização lógica das operações.

🎯 Sobre o Projeto
O sistema permite o gerenciamento completo de contas bancárias, desde a abertura da conta até a realização de transações complexas como transferências e geração de extratos detalhados.

Foquei na construção de um código escalável e robusto: o sistema utiliza exceções personalizadas para garantir que operações inválidas (como sacar mais do que o saldo disponível) sejam barradas e reportadas corretamente ao usuário.

🛠️ Tecnologias e conceitos utilizados
Java Swing – Interface gráfica intuitiva com diálogos de entrada e alertas de feedback.

Persistência em Arquivo (.txt) – Sistema de salvamento automático que mantém os dados das contas e saldos mesmo após fechar o programa.

Arquitetura em Camadas (MVC) – Separação clara entre a visualização (View), a lógica de negócio (Service) e os dados (Model).

Tratamento de Exceções Personalizadas – Criação de erros específicos para o domínio bancário, melhorando a rastreabilidade de falhas.

POO Avançada – Uso intensivo de Classes Abstratas, Herança e Polimorfismo para diferenciar tipos de conta (Corrente vs. Poupança).

🚀 Principais Funcionalidades
Gestão de Contas – Cadastro de clientes vinculados a números de conta exclusivos.

Operações Financeiras – Depósitos, saques e transferências entre contas com validação de saldo.

Histórico e Extrato – Registro de cada movimentação (tipo, valor e data/hora) disponível para consulta instantânea.

Rendimento Automático – Lógica preparada para aplicação de taxas em contas do tipo poupança.

Persistência Automática – Os dados são carregados ao iniciar e salvos ao encerrar a aplicação.

📂 Como rodar o projeto
Ter o Java (JDK 17 ou superior) instalado na máquina.

Clonar o repositório ou baixar os arquivos fonte.

Executar a classe App.java (localizada no pacote app).

Projeto desenvolvido por Mateus Gomes