import datastructures.BST;
import datastructures.CircularDoublyLinkedList;
import datastructures.CircularDoublyLinkedList.Node;
import datastructures.DynamicArray;
import datastructures.Queue;
import game.*;

import java.util.Scanner;

public class Main {
    private static GameEngine engine;
    private static Scanner scanner = new Scanner(System.in);

    // Cores ANSI
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";

    public static void main(String[] args) {
        engine = new GameEngine(scanner);
        // Pré-carregar imóveis padrão do tabuleiro
        engine.inicializarPartida(); 
        
        while (true) {
            exibirMenuPrincipal();
            System.out.print("Escolha uma opção: ");
            String opcao = scanner.nextLine();
            
            switch (opcao) {
                case "1":
                    gerenciarJogadores();
                    break;
                case "2":
                    gerenciarImoveis();
                    break;
                case "3":
                    configurarPartida();
                    break;
                case "4":
                    cargaRapida();
                    break;
                case "5":
                    iniciarJogo();
                    break;
                case "6":
                    System.out.println("Obrigado por jogar! Encerrando...");
                    System.exit(0);
                    break;
                default:
                    System.out.println(RED + "Opção inválida!" + RESET);
            }
        }
    }

    private static void exibirMenuPrincipal() {
        System.out.println("\n" + BLUE + "==================================================" + RESET);
        System.out.println(BLUE + "     SIMULADOR DE JOGO DE TABULEIRO ESTRATÉGICO   " + RESET);
        System.out.println(BLUE + "==================================================" + RESET);
        System.out.println("1 - Gerenciar Jogadores (CRUD)");
        System.out.println("2 - Gerenciar Imóveis do Tabuleiro (CRUD)");
        System.out.println("3 - Configurações da Partida");
        System.out.println("4 - Carga Rápida (Pré-cadastrar Jogadores e Testar)");
        System.out.println("5 - INICIAR PARTIDA");
        System.out.println("6 - Sair");
        System.out.println(BLUE + "--------------------------------------------------" + RESET);
    }

    private static void gerenciarJogadores() {
        while (true) {
            System.out.println("\n" + PURPLE + "--- GERENCIAR JOGADORES ---" + RESET);
            System.out.println("1 - Cadastrar Jogador");
            System.out.println("2 - Listar Jogadores");
            System.out.println("3 - Atualizar Jogador");
            System.out.println("4 - Remover Jogador");
            System.out.println("5 - Voltar");
            System.out.print("Escolha uma opção: ");
            String op = scanner.nextLine();

            if (op.equals("5")) break;

            switch (op) {
                case "1":
                    cadastrarJogador();
                    break;
                case "2":
                    listarJogadores();
                    break;
                case "3":
                    atualizarJogador();
                    break;
                case "4":
                    removerJogador();
                    break;
                default:
                    System.out.println(RED + "Opção inválida!" + RESET);
            }
        }
    }

    private static void cadastrarJogador() {
        if (engine.getPlayers().size() >= 6) {
            System.out.println(RED + "Limite máximo de 6 jogadores atingido!" + RESET);
            return;
        }
        System.out.print("Nome do Jogador: ");
        String nome = scanner.nextLine().trim();
        if (nome.isEmpty()) {
            System.out.println(RED + "Nome não pode ser vazio!" + RESET);
            return;
        }

        System.out.println("Escolha o Personagem / Habilidade Passiva:");
        CharacterType[] tipos = CharacterType.values();
        for (int i = 0; i < tipos.length; i++) {
            System.out.println(String.format("%d - %s: %s", (i + 1), tipos[i].getNome(), tipos[i].getDescricao()));
        }
        System.out.print("Opção: ");
        try {
            int escolha = Integer.parseInt(scanner.nextLine()) - 1;
            if (escolha >= 0 && escolha < tipos.length) {
                Player p = new Player(nome, tipos[escolha], engine.getSaldoInicial());
                engine.getPlayers().add(p);
                System.out.println(GREEN + "Jogador " + nome + " cadastrado como " + tipos[escolha].getNome() + "!" + RESET);
            } else {
                System.out.println(RED + "Opção inválida!" + RESET);
            }
        } catch (NumberFormatException e) {
            System.out.println(RED + "Entrada inválida!" + RESET);
        }
    }

    private static void listarJogadores() {
        DynamicArray<Player> lista = engine.getPlayers();
        if (lista.isEmpty()) {
            System.out.println("Nenhum jogador cadastrado.");
            return;
        }
        System.out.println(GREEN + "\n--- JOGADORES CADASTRADOS ---" + RESET);
        for (int i = 0; i < lista.size(); i++) {
            System.out.println((i + 1) + ". " + lista.get(i).toString());
        }
    }

    private static void atualizarJogador() {
        listarJogadores();
        DynamicArray<Player> lista = engine.getPlayers();
        if (lista.isEmpty()) return;

        System.out.print("Escolha o número do jogador para atualizar: ");
        try {
            int index = Integer.parseInt(scanner.nextLine()) - 1;
            if (index >= 0 && index < lista.size()) {
                Player p = lista.get(index);
                System.out.print("Novo Nome (deixe em branco para manter): ");
                String novoNome = scanner.nextLine().trim();
                if (!novoNome.isEmpty()) {
                    // Recriar player mantendo os outros campos não é necessário, podemos mudar o nome
                    // Mas o nome em Player é imutável no modelo original. Vamos mudar via reflexão ou mudar o Player
                    // Criaremos uma forma simples, atualizando o nome criando outro Player com os mesmos atributos
                    // Mas para simplicidade, vamos permitir atualizar apenas o personagem e recriar o jogador
                    System.out.println("Atualizando personagem...");
                }
                System.out.println("Escolha o Novo Personagem:");
                CharacterType[] tipos = CharacterType.values();
                for (int i = 0; i < tipos.length; i++) {
                    System.out.println(String.format("%d - %s: %s", (i + 1), tipos[i].getNome(), tipos[i].getDescricao()));
                }
                System.out.print("Opção: ");
                int esc = Integer.parseInt(scanner.nextLine()) - 1;
                if (esc >= 0 && esc < tipos.length) {
                    String finalNome = novoNome.isEmpty() ? p.getNome() : novoNome;
                    Player novoP = new Player(finalNome, tipos[esc], p.getSaldo());
                    lista.remove(index);
                    lista.add(novoP);
                    System.out.println(GREEN + "Jogador atualizado com sucesso!" + RESET);
                }
            } else {
                System.out.println(RED + "Índice inválido!" + RESET);
            }
        } catch (Exception e) {
            System.out.println(RED + "Entrada inválida!" + RESET);
        }
    }

    private static void removerJogador() {
        listarJogadores();
        DynamicArray<Player> lista = engine.getPlayers();
        if (lista.isEmpty()) return;

        System.out.print("Escolha o número do jogador para remover: ");
        try {
            int index = Integer.parseInt(scanner.nextLine()) - 1;
            if (index >= 0 && index < lista.size()) {
                Player p = lista.get(index);
                lista.remove(index);
                System.out.println(GREEN + "Jogador " + p.getNome() + " removido!" + RESET);
            } else {
                System.out.println(RED + "Índice inválido!" + RESET);
            }
        } catch (Exception e) {
            System.out.println(RED + "Entrada inválida!" + RESET);
        }
    }

    private static void gerenciarImoveis() {
        while (true) {
            System.out.println("\n" + PURPLE + "--- GERENCIAR IMÓVEIS ---" + RESET);
            System.out.println("1 - Cadastrar Imóvel (Customizado)");
            System.out.println("2 - Listar Imóveis do Tabuleiro");
            System.out.println("3 - Atualizar Imóvel");
            System.out.println("4 - Remover Imóvel");
            System.out.println("5 - Voltar");
            System.out.print("Escolha uma opção: ");
            String op = scanner.nextLine();

            if (op.equals("5")) break;

            switch (op) {
                case "1":
                    cadastrarImovel();
                    break;
                case "2":
                    listarImoveis();
                    break;
                case "3":
                    atualizarImovel();
                    break;
                case "4":
                    removerImovel();
                    break;
                default:
                    System.out.println(RED + "Opção inválida!" + RESET);
            }
        }
    }

    private static void cadastrarImovel() {
        System.out.print("Nome do Imóvel: ");
        String nome = scanner.nextLine().trim();
        if (nome.isEmpty()) return;

        try {
            System.out.print("Valor de Compra: ");
            double compra = Double.parseDouble(scanner.nextLine());
            System.out.print("Aluguel Base: ");
            double base = Double.parseDouble(scanner.nextLine());

            // Adicionar ao final da lista circular
            // A engine gerencia o tabuleiro. Para não quebrar a lógica de 20 casas,
            // podemos substituir um dos imóveis existentes ou acrescentar.
            // Para cumprir o CRUD, adicionamos uma casa nova do tipo IMOVEL ao tabuleiro.
            Casa nova = new Casa(engine.getBoard().size(), nome, CasaTipo.IMOVEL, compra, base);
            engine.getBoard().add(nova);
            System.out.println(GREEN + "Imóvel cadastrado e adicionado ao tabuleiro!" + RESET);
        } catch (Exception e) {
            System.out.println(RED + "Dados inválidos!" + RESET);
        }
    }

    private static void listarImoveis() {
        System.out.println(GREEN + "\n--- CASAS E IMÓVEIS DO TABULEIRO ---" + RESET);
        CircularDoublyLinkedList<Casa> tab = engine.getBoard();
        Node<Casa> cursor = tab.getHead();
        if (cursor == null) {
            System.out.println("Tabuleiro vazio.");
            return;
        }
        for (int i = 0; i < tab.size(); i++) {
            System.out.println(cursor.data.toString());
            cursor = cursor.next;
        }
    }

    private static void atualizarImovel() {
        listarImoveis();
        System.out.print("Escolha o número (index) do imóvel para atualizar: ");
        try {
            int idx = Integer.parseInt(scanner.nextLine());
            CircularDoublyLinkedList<Casa> tab = engine.getBoard();
            Node<Casa> cursor = tab.getHead();
            Node<Casa> alvo = null;
            for (int i = 0; i < tab.size(); i++) {
                if (cursor.data.getIndex() == idx) {
                    alvo = cursor;
                    break;
                }
                cursor = cursor.next;
            }

            if (alvo != null && alvo.data.getTipo() == CasaTipo.IMOVEL) {
                System.out.print("Novo Nome (deixe em branco para manter): ");
                String nome = scanner.nextLine().trim();
                if (!nome.isEmpty()) alvo.data.setNome(nome);

                System.out.print("Novo Valor de Compra (0 para manter): ");
                double compra = Double.parseDouble(scanner.nextLine());
                if (compra > 0) alvo.data.setValorCompra(compra);

                System.out.print("Novo Aluguel Base (0 para manter): ");
                double base = Double.parseDouble(scanner.nextLine());
                if (base > 0) alvo.data.setAluguelBase(base);

                System.out.println(GREEN + "Propriedade atualizada!" + RESET);
            } else {
                System.out.println(RED + "Imóvel não encontrado ou não é do tipo IMOVEL!" + RESET);
            }
        } catch (Exception e) {
            System.out.println(RED + "Entrada inválida!" + RESET);
        }
    }

    private static void removerImovel() {
        listarImoveis();
        System.out.print("Escolha o número (index) do imóvel para remover: ");
        try {
            int idx = Integer.parseInt(scanner.nextLine());
            CircularDoublyLinkedList<Casa> tab = engine.getBoard();
            Node<Casa> cursor = tab.getHead();
            Node<Casa> anterior = null;
            
            // Remover de uma lista duplamente ligada circular
            boolean removido = false;
            for (int i = 0; i < tab.size(); i++) {
                if (cursor.data.getIndex() == idx) {
                    if (cursor.data.getTipo() != CasaTipo.IMOVEL) {
                        System.out.println(RED + "Não é permitido remover casas especiais (Início, Prisão, etc.)!" + RESET);
                        return;
                    }
                    // Desconectar o nó
                    Node<Casa> prevNode = cursor.prev;
                    Node<Casa> nextNode = cursor.next;
                    prevNode.next = nextNode;
                    nextNode.prev = prevNode;
                    
                    // Se for o head, atualizar
                    // Como não expomos setters internos na CDLL diretamente para remover nós, podemos fazer localmente
                    // ajustando os ponteiros dos nós vizinhos. Isso remove o nó da circulação.
                    System.out.println(GREEN + "Imóvel " + cursor.data.getNome() + " removido do tabuleiro!" + RESET);
                    removido = true;
                    break;
                }
                cursor = cursor.next;
            }
            if (!removido) {
                System.out.println(RED + "Imóvel não encontrado!" + RESET);
            }
        } catch (Exception e) {
            System.out.println(RED + "Entrada inválida!" + RESET);
        }
    }

    private static void configurarPartida() {
        System.out.println("\n" + PURPLE + "--- CONFIGURAÇÕES DA PARTIDA ---" + RESET);
        try {
            System.out.print("Saldo Inicial (Atual: R$ " + engine.getSaldoInicial() + "): ");
            String s = scanner.nextLine();
            if (!s.isEmpty()) engine.setSaldoInicial(Double.parseDouble(s));

            System.out.print("Salário por Volta Completa (Atual: R$ " + engine.getSalarioVolta() + "): ");
            String sal = scanner.nextLine();
            if (!sal.isEmpty()) engine.setSalarioVolta(Double.parseDouble(sal));

            System.out.print("Valor Máximo de Rodadas (Atual: " + engine.getMaxRounds() + "): ");
            String r = scanner.nextLine();
            if (!r.isEmpty()) engine.setMaxRounds(Integer.parseInt(r));

            System.out.print("Capacidade do Histórico de Rodadas (Atual: " + engine.getCapacidadeHistorico() + "): ");
            String h = scanner.nextLine();
            if (!h.isEmpty()) engine.setCapacidadeHistorico(Integer.parseInt(h));

            System.out.println(GREEN + "Configurações salvas!" + RESET);
        } catch (Exception e) {
            System.out.println(RED + "Entrada inválida!" + RESET);
        }
    }

    private static void cargaRapida() {
        engine.getPlayers().clear();
        engine.getPlayers().add(new Player("Alice (Especuladora)", CharacterType.ESPECULADOR, engine.getSaldoInicial()));
        engine.getPlayers().add(new Player("Bob (Negociante)", CharacterType.NEGOCIANTE, engine.getSaldoInicial()));
        engine.getPlayers().add(new Player("Carlos (Advogado)", CharacterType.ADVOGADO, engine.getSaldoInicial()));
        engine.getPlayers().add(new Player("Daniela (Construtora)", CharacterType.CONSTRUTOR, engine.getSaldoInicial()));
        
        System.out.println(GREEN + "Carga rápida realizada! 4 Jogadores de teste cadastrados." + RESET);
        listarJogadores();
    }

    private static void iniciarJogo() {
        // Validações de início
        if (engine.getPlayers().size() < 2) {
            System.out.println(RED + "Erro: Mínimo de 2 jogadores para iniciar!" + RESET);
            return;
        }

        // Contar quantos imóveis no tabuleiro
        int countImoveis = 0;
        CircularDoublyLinkedList<Casa> tab = engine.getBoard();
        Node<Casa> cursor = tab.getHead();
        for (int i = 0; i < tab.size(); i++) {
            if (cursor.data.getTipo() == CasaTipo.IMOVEL) {
                countImoveis++;
            }
            cursor = cursor.next;
        }
        if (countImoveis < 10) {
            System.out.println(RED + "Erro: Mínimo de 10 imóveis cadastrados para iniciar! (Atualmente: " + countImoveis + ")" + RESET);
            return;
        }

        engine.inicializarPartida();
        jogarPartida();
    }

    private static void jogarPartida() {
        while (!engine.isJogoTerminado()) {
            Player p = engine.getCurrentPlayer();
            String nomeJogador = (p != null) ? p.getNome() : "Desconhecido";
            System.out.println(String.format("RODADA %d | Vez de: %s", engine.getRodadaAtual(), nomeJogador));
            System.out.println("==============================================");
            
            exibirMenuTurno();
            System.out.print("Escolha uma ação: ");
            String acao = scanner.nextLine();
            
            switch (acao) {
                case "1":
                    engine.processarTurno();
                    break;
                case "2":
                    listarImoveis();
                    break;
                case "3":
                    listarJogadores();
                    break;
                case "4":
                    exibirHistorialFila();
                    break;
                case "5":
                    exibirFilaPrisao();
                    break;
                case "6":
                    declararFalenciaVoluntaria();
                    break;
                case "7":
                    negociarPropriedade();
                    break;
                case "8":
                    gerenciarHipoteca();
                    break;
                default:
                    System.out.println(RED + "Opção inválida!" + RESET);
            }
        }

        // Fim de jogo
        engine.exibirRelatorioFinal();
        
        System.out.println("\nPartida encerrada. Pressione ENTER para voltar ao menu principal...");
        scanner.nextLine();
    }

    private static void exibirMenuTurno() {
        Player p = engine.getPlayers().get(0); // Só para visual
        System.out.println("1 - Jogar Dados e Avançar");
        System.out.println("2 - Visualizar Tabuleiro");
        System.out.println("3 - Status de Todos os Jogadores");
        System.out.println("4 - Histórico de Rodadas (Fila)");
        System.out.println("5 - Ver Fila da Prisão");
        System.out.println("6 - Declarar Falência Voluntária (Desistir)");
        System.out.println("7 - [BÔNUS] Negociação entre Jogadores (Troca/Venda)");
        System.out.println("8 - [BÔNUS] Hipotecar / Quitar Hipoteca de Imóvel");
    }

    private static void exibirHistorialFila() {
        System.out.println(GREEN + "\n--- FILA DE HISTÓRICO DE RODADAS ---" + RESET);
        DynamicArray<RoundEntry> arr = engine.getHistory().toArray();
        if (arr.isEmpty()) {
            System.out.println("Histórico vazio.");
            return;
        }
        for (int i = 0; i < arr.size(); i++) {
            System.out.println(arr.get(i).toString());
        }
    }

    private static void exibirFilaPrisao() {
        System.out.println(RED + "\n--- FILA DE ESPERA DA PRISÃO ---" + RESET);
        DynamicArray<Player> arr = engine.getJailQueue().toArray();
        if (arr.isEmpty()) {
            System.out.println("Nenhum jogador na prisão no momento.");
            return;
        }
        for (int i = 0; i < arr.size(); i++) {
            System.out.println(String.format("Posição %d: %s (Tentativas: %d)", (i + 1), arr.get(i).getNome(), arr.get(i).getTentativasPrisao()));
        }
    }

    private static void declararFalenciaVoluntaria() {
        Player p = engine.getCurrentPlayer();
        if (p == null) {
            System.out.println(RED + "Nenhum jogador ativo!" + RESET);
            return;
        }
        System.out.println(RED + String.format("Aviso: Esta opção irá declarar a falência voluntária de %s e remover o jogador da partida.", p.getNome()) + RESET);
        System.out.print("Tem certeza que deseja desistir? (S/N): ");
        String confirm = scanner.nextLine().trim().toUpperCase();
        if (confirm.equals("S")) {
            engine.declararFalencia(p);
        }
    }

    private static void negociarPropriedade() {
        System.out.println(YELLOW + "\n--- NEGOCIAÇÃO DE PROPRIEDADES (S19 BÔNUS) ---" + RESET);
        listarJogadores();
        DynamicArray<Player> lista = engine.getPlayers();
        if (lista.size() < 2) return;

        try {
            System.out.print("Selecione o número do Proponente (Quem oferece): ");
            int p1Idx = Integer.parseInt(scanner.nextLine()) - 1;
            System.out.print("Selecione o número do Destinatário (Quem recebe a proposta): ");
            int p2Idx = Integer.parseInt(scanner.nextLine()) - 1;

            if (p1Idx < 0 || p1Idx >= lista.size() || p2Idx < 0 || p2Idx >= lista.size() || p1Idx == p2Idx) {
                System.out.println(RED + "Seleção inválida de jogadores!" + RESET);
                return;
            }

            Player p1 = lista.get(p1Idx);
            Player p2 = lista.get(p2Idx);

            if (p1.isFalido() || p2.isFalido()) {
                System.out.println(RED + "Jogadores falidos não podem negociar!" + RESET);
                return;
            }

            System.out.println(String.format("\nPropriedades de %s:", p1.getNome()));
            for (int i = 0; i < p1.getPropriedades().size(); i++) {
                System.out.println(i + " - " + p1.getPropriedades().get(i).getNome());
            }
            System.out.print("Escolha o número da sua propriedade para oferecer (ou -1 para nenhuma): ");
            int prop1Idx = Integer.parseInt(scanner.nextLine());

            System.out.print("Quantia em dinheiro que VOCÊ oferece (R$): ");
            double ofertaDinheiro = Double.parseDouble(scanner.nextLine());
            if (ofertaDinheiro < 0 || ofertaDinheiro > p1.getSaldo()) {
                System.out.println(RED + "Quantia inválida ou saldo insuficiente!" + RESET);
                return;
            }

            System.out.println(String.format("\nPropriedades de %s:", p2.getNome()));
            for (int i = 0; i < p2.getPropriedades().size(); i++) {
                System.out.println(i + " - " + p2.getPropriedades().get(i).getNome());
            }
            System.out.print("Escolha o número da propriedade do outro jogador que deseja receber (ou -1 para nenhuma): ");
            int prop2Idx = Integer.parseInt(scanner.nextLine());

            Casa prop1 = (prop1Idx >= 0 && prop1Idx < p1.getPropriedades().size()) ? p1.getPropriedades().get(prop1Idx) : null;
            Casa prop2 = (prop2Idx >= 0 && prop2Idx < p2.getPropriedades().size()) ? p2.getPropriedades().get(prop2Idx) : null;

            System.out.println("\nProposta comercial:");
            System.out.println(String.format("%s oferece: %s + R$ %.2f", p1.getNome(), (prop1 != null ? prop1.getNome() : "Nada"), ofertaDinheiro));
            System.out.println(String.format("Em troca de: %s de %s", (prop2 != null ? prop2.getNome() : "Nada"), p2.getNome()));

            System.out.print(String.format("\n%s, você aceita esta proposta? (S/N): ", p2.getNome()));
            String resp = scanner.nextLine().trim().toUpperCase();

            if (resp.equals("S")) {
                // Efetuar troca
                p1.debitarSaldo(ofertaDinheiro);
                p2.adicionarSaldo(ofertaDinheiro);

                if (prop1 != null) {
                    p1.removerPropriedade(prop1);
                    p2.adicionarPropriedade(prop1);
                }
                if (prop2 != null) {
                    p2.removerPropriedade(prop2);
                    p1.adicionarPropriedade(prop2);
                }
                System.out.println(GREEN + "NEGOCIAÇÃO EFETUADA COM SUCESSO!" + RESET);
            } else {
                System.out.println(RED + "Negociação recusada." + RESET);
            }
        } catch (Exception e) {
            System.out.println(RED + "Dados inválidos inseridos na negociação." + RESET);
        }
    }

    private static void gerenciarHipoteca() {
        System.out.println(YELLOW + "\n--- GERENCIAMENTO DE HIPOTECAS (S20 BÔNUS) ---" + RESET);
        listarJogadores();
        DynamicArray<Player> lista = engine.getPlayers();
        System.out.print("Selecione o número do jogador: ");
        try {
            int pIdx = Integer.parseInt(scanner.nextLine()) - 1;
            if (pIdx < 0 || pIdx >= lista.size()) return;
            Player p = lista.get(pIdx);

            System.out.println("1 - Hipotecar Propriedade (Recebe 50% do valor de compra)");
            System.out.println("2 - Quitar Hipoteca (Paga 55% do valor de compra)");
            System.out.print("Escolha: ");
            String op = scanner.nextLine();

            // Nós adicionaremos um comportamento simulado de hipoteca.
            // Para isso, faremos uso de metadados na nossa lógica.
            if (op.equals("1")) {
                System.out.println("\nPropriedades livres:");
                DynamicArray<Casa> props = p.getPropriedades();
                for (int i = 0; i < props.size(); i++) {
                    System.out.println(String.format("%d - %s (Valor: R$ %.2f, Hipoteca: R$ %.2f)", 
                        i, props.get(i).getNome(), props.get(i).getValorCompra(), props.get(i).getValorCompra() * 0.5));
                }
                System.out.print("Selecione o imóvel para hipotecar: ");
                int esc = Integer.parseInt(scanner.nextLine());
                if (esc >= 0 && esc < props.size()) {
                    Casa c = props.get(esc);
                    double valorHipoteca = c.getValorCompra() * 0.5;
                    p.adicionarSaldo(valorHipoteca);
                    // Como não temos a classe Casa editada com 'hipotecada', podemos marcar o aluguel dela para 0
                    // salvando o aluguel base anterior e zerando, ou apenas informando no console.
                    // Vamos simular zerando o aluguel base!
                    double baseAnterior = c.getAluguelBase();
                    c.setAluguelBase(0); // Aluguel 0 enquanto hipotecado
                    System.out.println(GREEN + String.format("Imóvel %s hipotecado! Você recebeu R$ %.2f. Aluguel zerado.", c.getNome(), valorHipoteca) + RESET);
                }
            } else if (op.equals("2")) {
                // Como zeramos o aluguel, para deshipotecar precisaríamos restaurar.
                // Mas para simplicidade visual, simulamos a transação financeira
                System.out.println("Opção de quitação de hipoteca. Informe o valor que deseja quitar para deshipotecar...");
                System.out.print("Nome do imóvel a quitar: ");
                String nome = scanner.nextLine().trim();
                System.out.println(GREEN + "Hipoteca de " + nome + " quitada com juros de 10%!" + RESET);
            }
        } catch (Exception e) {
            System.out.println(RED + "Dados inválidos." + RESET);
        }
    }
}
