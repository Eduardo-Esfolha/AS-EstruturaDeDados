# Simulação de Jogo de Tabuleiro Estratégico com Estruturas de Dados (CLI)

Este repositório contém o projeto de simulação de jogo de tabuleiro estratégico inspirado em gestão imobiliária, desenvolvido em Java para a avaliação semestral da disciplina de **Estruturas de Dados**.

O diferencial deste projeto é a implementação **100% manual** de todas as estruturas de dados fundamentais (Lista Encadeada Circular, Pilha, Fila, Vetor Dinâmico e Árvore Binária de Busca), sem a utilização de qualquer biblioteca utilitária do Java (`java.util`).

---

## 🚀 Como Compilar e Executar

### Pré-requisitos
- Ter o Java Development Kit (JDK) 24 ou superior instalado e no PATH do sistema.

### Executando no Terminal (Modo Interativo)
Foi disponibilizado um script em PowerShell para facilitar a compilação e execução. Abra o PowerShell na pasta raiz do projeto e execute:
```powershell
.\compile_and_run.ps1
```

### Executando a Simulação Automatizada (Geração de Prints)
Caso queira gerar novamente as capturas de tela das evidências:
```powershell
# Compilar todos os fontes
javac -d bin src/datastructures/*.java src/game/*.java src/Main.java src/Simulation.java

# Executar a simulação de logs e prints
java -cp bin Simulation
```

---

## 📁 Estrutura de Pastas do Projeto

```
.
├── bin/                       # Classes Java compiladas (.class)
├── src/                       # Código-fonte Java (.java)
│   ├── datastructures/        # Implementações manuais das Estruturas de Dados
│   │   ├── BST.java           # Árvore Binária de Busca (Classificação do Ranking)
│   │   ├── CircularDoublyLinkedList.java # Lista Encadeada Circular (Tabuleiro)
│   │   ├── DynamicArray.java  # Vetor Dinâmico Genérico (Coleções e CRUD)
│   │   ├── Queue.java         # Fila FIFO Genérica (Fila de Prisão e Histórico)
│   │   └── Stack.java         # Pilha LIFO Genérica (Baralho Sorte/Revés)
│   ├── game/                  # Lógica de Negócio e Componentes do Jogo
│   │   ├── BoardNode.java     # Nó do Tabuleiro
│   │   ├── Card.java          # Entidade de Cartas Sorte/Revés
│   │   ├── Casa.java          # Entidade de Espaços do Tabuleiro
│   │   ├── CasaTipo.java      # Enum dos tipos de Casas
│   │   ├── CharacterType.java # Enum dos perfis dos Personagens
│   │   ├── GameEngine.java    # Motor de controle do fluxo e regras
│   │   ├── Player.java        # Entidade do Jogador
│   │   └── RoundEntry.java    # Entidade de Log de Histórico de Rodada
│   ├── Main.java              # Loop principal e menus do jogo (CLI Interativa)
│   └── Simulation.java        # Automatizador de simulação e capturas de tela
├── screenshots/               # Prints das evidências do sistema (S1 a S20)
├── compile_and_run.ps1        # Script de build rápido
├── take_screenshot.ps1        # Script de captura de tela automatizado
└── README.md                  # Relatório de Documentação do Projeto
```

---

# 📜 DOCUMENTAÇÃO TÉCNICA E RESPOSTAS TEÓRICAS

Este relatório detalha as especificações do sistema, as decisões arquiteturais tomadas e serve como documentação de conformidade com os critérios de avaliação (Critério 5.3).

---

## 1. Resumo do Jogo e Regras Gerais

O simulador consiste em um tabuleiro de 20 casas dispostas em anel circular.
- **Salário**: Ao cruzar ou parar na casa de **Início**, o jogador recebe um salário configurável (padrão R$ 200,00). O Especulador ganha R$ 240,00 (+20%).
- **Valorização Imobiliária**: O aluguel base dos imóveis valoriza em 10% (+0.1 no multiplicador) a cada vez que o imóvel é visitado por outros jogadores (mesmo antes de ser comprado), simulando valorização por demanda, até o teto de 2.0x.
- **Imposto**: Jogadores que caem na casa de Imposto pagam 5% do seu patrimônio total (saldo + valor de compra de todas as propriedades). O Especulador paga 5.5%.
- **Restituição**: Jogadores que caem na casa de Restituição recebem 10% do salário do banco.
- **Prisão**: Jogadores presos entram em uma fila FIFO. A cada rodada tentam sair: pagando fiança (10% do salário), tirando dados duplos, usando a isenção de fiança do Advogado (1 única vez) ou após 3 turnos detidos (soltura automática).
- **Leilão**: Um imóvel sem dono aleatório é leiloado. Lances mínimos devem atingir 50% do valor de compra da propriedade.
- **Sorte ou Revés**: Baralho com 12 cartas positivas e negativas gerenciado em pilha.

---

## 2. Justificativa da Estrutura Escolhida para Jogadores e Imóveis

Para gerenciar a lista de jogadores ativos e a coleção de propriedades adquiridas por cada jogador, implementamos uma estrutura de vetor dinâmico manual chamada `DynamicArray.java`.

### Por que `DynamicArray`?
- **Iteração Sequencial Eficiente**: O turn-based do jogo exige iterar repetidamente sobre a lista de jogadores em ordem. O acesso aos elementos em um array dinâmico ocorre em tempo constante \(O(1)\), o que otimiza essa iteração direta.
- **Busca por Índice**: Durante a seleção de alvos, negociação de propriedades (Bônus S19) ou auditorias, precisamos acessar jogadores ou propriedades pelo seu índice de forma instantânea.
- **Inserção e Remoção Dinâmicas**: Quando um jogador vai à falência, ele é removido da lista de jogadores ativos. O `DynamicArray` gerencia essa remoção rearranjando os elementos internos em tempo linear \(O(N)\). Dado o limite máximo pequeno (6 jogadores e 40 imóveis), a complexidade de remoção é irrelevante, enquanto a simplicidade do array dinâmico garante um uso mínimo de memória e simplicidade de depuração.

### Evidências Visuais (CRUD de Configuração Pré-Jogo):

#### [S1] Cadastro de Jogador
A tela abaixo demonstra o cadastro de um jogador informando seu nome e selecionando o seu personagem e habilidade passiva correspondente.
![S1 - Cadastro de Jogador](screenshots/S1.png)

#### [S2] Listagem de Jogadores
Abaixo são listados todos os jogadores cadastrados no sistema e seus atributos iniciais antes do início da partida.
![S2 - Listagem de Jogadores](screenshots/S2.png)

#### [S3] Cadastro de Propriedade
Cadastro de um imóvel personalizado na base do jogo, especificando nome, valor de compra e aluguel base.
![S3 - Cadastro de Propriedade](screenshots/S3.png)

#### [S4] Listagem de Propriedades
Abaixo são listadas todas as propriedades cadastradas no jogo antes do início, detalhando seus atributos de custo e aluguel inicial.
![S4 - Listagem de Propriedades](screenshots/S4.png)

---

## 3. Adequação da Lista Duplamente Ligada Circular para o Tabuleiro

O tabuleiro do jogo é representado por uma `CircularDoublyLinkedList` composta por nós `BoardNode`.

### Por que a Lista Duplamente Ligada Circular é a Estrutura Perfeita?
1. **Circularidade Natural (Sem Módulo)**: O tabuleiro de um jogo de tabuleiro não tem fim; ao passar da última casa, o peão deve continuar na primeira casa. Na lista circular, o ponteiro `next` da última casa aponta diretamente de volta para a primeira casa (`tail.next = head`). A navegação avança indefinitivamente sem a necessidade de cálculos de índice ou verificações de limite (ex.: `if (index >= 20)`).
2. **Movimentação Bidirecional**: Cartas de Sorte/Revés podem forçar o jogador a retroceder \(N\) casas (ex.: "Marcha Ré" ou "Erro de Rota"). Em uma lista duplamente ligada, retroceder é tão simples quanto caminhar utilizando os ponteiros `prev` (`posicaoAtual = posicaoAtual.prev`). Em uma lista encadeada simples, seria necessário recalcular a posição dando a volta completa no tabuleiro para frente, o que seria ineficiente e complexo.
3. **Casas Dinâmicas**: Permite a inserção ou remoção de imóveis durante a execução sem a necessidade de redimensionar blocos de memória contíguos (como arrays).

#### [S5] Tabuleiro Criado
Exibição das casas do tabuleiro em ordem, provando que a última casa (Mansão dos Jardins) está circularmente conectada de volta à primeira casa (Início).
![S5 - Tabuleiro Criado](screenshots/S5.png)

---

## 4. O Baralho de Sorte/Revés como Pilha (LIFO)

A pilha é a estrutura ideal para modelar um baralho de cartas de jogo, pois as cartas são sacadas sempre do topo (Princípio LIFO - Last-In, First-Out).

### Detalhes de Implementação da Pilha (`Stack.java`):
- **Operação de Saque**: É realizada através do método `pop()`, removendo e retornando o nó do topo do baralho.
- **Descarte e Reabastecimento**: As cartas sacadas são colocadas em uma pilha secundária de descarte. Quando o baralho principal se esgota (`isEmpty()`), as cartas da pilha de descarte são retiradas, re-embaralhadas usando o algoritmo de Fisher-Yates (que permuta elementos aleatoriamente em complexidade \(O(N)\)) e reinseridas no baralho principal.

#### [S6] Carta de Sorte/Revés
Abaixo vemos a sequência de saque de uma carta de Sorte/Revés e a aplicação de seu efeito de retrocesso no jogador Bob.
![S6 - Carta de Sorte/Revés](screenshots/S6.png)

---

## 5. Modelagem de Filas (FIFO) no Jogo

A estrutura de dados Fila (`Queue.java`) segue a regra de que o primeiro a entrar é o primeiro a sair (FIFO - First-In, First-Out). No projeto, ela é utilizada para duas finalidades distintas:

1. **Fila de Espera da Prisão**: Quando um jogador é enviado à prisão, ele entra no final da fila. A cada rodada, a engine processa a tentativa de saída dos prisioneiros na ordem exata de entrada (quem entrou primeiro tenta se libertar primeiro).
2. **Histórico de Rodadas (Fila de Capacidade Limitada)**: Mantém o histórico das últimas \(N\) ações (padrão 10). Ao adicionar um registro quando a fila está cheia, o elemento mais antigo (início da fila) é descartado automaticamente (`dequeue()`), mantendo uma janela deslizante estável.

#### [S7] Prisão — Entrada na Fila
Jogador sendo enviado à prisão pela carta de Sorte/Revés e sendo inserido na fila de espera.
![S7 - Entrada na Prisão](screenshots/S7.png)

#### [S8] Prisão — Tentativa de Saída
Abaixo vemos o jogador Carlos utilizando a sua isenção de fiança como Advogado para sair da prisão de graça e rolar os dados na mesma rodada.
![S8 - Saída da Prisão](screenshots/S8.png)

#### [S9] Histórico de Rodadas
Exibição do histórico de rodadas armazenado na fila circular, demonstrando as ações recentes dos turnos.
![S9 - Histórico de Rodadas](screenshots/S9.png)

---

## 6. Integração das Habilidades Passivas dos Personagens

As habilidades passivas foram implementadas como flags e modificadores específicos na lógica central do `GameEngine.java`, sem poluir o fluxo de controle principal.

- **Especulador**: Ao cruzar o Início, a engine verifica o tipo do jogador. Se for Especulador, multiplica o salário por 1.2. Se cair no imposto, aplica a alíquota de 5.5% sobre o patrimônio total (em vez de 5.0%).
- **Negociante**: Quando cai em um imóvel de outrem, a engine aplica o desconto: `aluguel = aluguel * 0.90`.
- **Advogado**: No menu de prisão, a opção de usar a isenção de fiança só aparece se o jogador for Advogado e a flag `isencaoFiancaUsada` for falsa.
- **Construtor**: No momento da compra de um imóvel, a base do aluguel é permanentemente redefinida para `baseRent * 1.15`.

#### [S10] Habilidade Passiva Ativa
O print exibe a passiva do Construtor aumentando o aluguel base de Barra da Tijuca em 15% e o Negociante Bob recebendo um desconto de 10% sobre o aluguel a pagar.
![S10 - Habilidades Passivas Ativas](screenshots/S10.png)

---

## 7. Passagem pelo Início vs. Retrocesso

- **Avanço**: O sistema simula o movimento do peão de casa em casa pelo tabuleiro utilizando `posicaoAtual = posicaoAtual.next`. Se, ao longo desse laço de movimentação, o nó de destino ou qualquer nó intermediário corresponder à casa do **Início**, o jogador completa uma volta, incrementa seu contador de voltas e recebe o salário base de rodada.
- **Retrocesso**: Quando o jogador volta casas devido a uma carta de Sorte/Revés, o peão move-se no sentido anti-horário utilizando `posicaoAtual = posicaoAtual.prev`. A engine desativa explicitamente qualquer verificação de bônus de salário nesse trajeto de ré, impedindo fraudes ou vantagens indevidas.

#### [S11] Passagem pelo Início
Jogador avançando no tabuleiro e recebendo o bônus salarial de Especulador ao cruzar a casa Início.
![S11 - Passagem pelo Início](screenshots/S11.png)

#### [S12] Retrocesso passando pelo Início
Jogador retrocedendo casas pela carta "Erro de Rota" e cruzando a casa Início de marcha ré sem receber salário.
![S12 - Retrocesso sem Salário](screenshots/S12.png)

---

## 8. Funcionalidades Obrigatórias Adicionais

#### [S13] Compra de Propriedade
Exibe a decisão de compra de propriedade ao parar em uma casa sem dono, debitando o valor e registrando o imóvel.
![S13 - Compra de Propriedade](screenshots/S13.png)

#### [S14] Pagamento de Aluguel
Transação financeira entre jogadores com multiplicador de demanda acumulado visível.
![S14 - Pagamento de Aluguel](screenshots/S14.png)

#### [S15] Leilão
O processo interativo de lances entre os jogadores por um imóvel sem proprietário e a definição do comprador vencedor.
![S15 - Leilão de Propriedade](screenshots/S15.png)

#### [S16] Falência
O jogador Bob sendo declarado falido após ficar com saldo negativo e não possuir propriedades para vender. Seus imóveis são liberados para o pool comum.
![S16 - Falência de Jogador](screenshots/S16.png)

#### [S17] Encerramento da Partida
Tela de fim de jogo exibindo o relatório final contendo classificação dos jogadores por patrimônio líquido, estatísticas e histórico de rodadas.
![S17 - Fim de Jogo](screenshots/S17.png)

---

## 9. Funcionalidades de Bônus Implementadas

#### [S18] Ranking com Árvore Binária de Busca (BST)
No encerramento da partida, todos os jogadores são inseridos em uma Árvore Binária de Busca (`BST.java`) cuja chave de comparação é o patrimônio total de cada um. O percurso in-order decrescente (Direita -> Raiz -> Esquerda) gera o ranking final. A estrutura hierárquica da árvore é exibida em formato ASCII provando o uso real da BST.
![S18 - Ranking via BST](screenshots/S18.png)

#### [S19] Negociação entre Jogadores (Troca Comercial)
Permite a proposta de trocas comerciais voluntárias de dinheiro e propriedades entre os jogadores.
![S19 - Negociação Comercial](screenshots/S19.png)

#### [S20] Hipoteca de Imóveis
Possibilidade de hipotecar um imóvel para obter 50% de seu valor de compra. Imóveis hipotecados têm aluguel zerado temporariamente. O proprietário pode quitar a hipoteca posteriormente pagando o valor mais 10% de juros.
![S20 - Hipoteca de Imóvel](screenshots/S20.png)
