package game;

import datastructures.CircularDoublyLinkedList;
import datastructures.CircularDoublyLinkedList.Node;
import datastructures.Stack;
import datastructures.Queue;
import datastructures.DynamicArray;
import datastructures.BST;

import java.util.Random;
import java.util.Scanner;

public class GameEngine {
    private CircularDoublyLinkedList<Casa> board;
    private Stack<Card> deck;
    private Stack<Card> discardPile;
    private DynamicArray<Player> players;
    private Queue<RoundEntry> history;
    private Queue<Player> jailQueue;

    // Configurações
    private double saldoInicial = 1500;
    private double salarioVolta = 200;
    private double valorFianca = 20; // Padrão: 10% do salário
    private int maxRounds = 30;
    private int capacidadeHistorico = 10;

    // Estado do jogo
    private int rodadaAtual = 1;
    private int jogadorTurnoIndex = 0;
    private boolean jogoTerminado = false;
    private Random random = new Random();
    private Scanner scanner;

    // Estatísticas para o relatório
    private Casa imovelMaiorAluguelCobrado = null;
    private double maiorAluguelCobrado = 0.0;

    // Cores ANSI para o Terminal
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";

    public GameEngine(Scanner scanner) {
        this.scanner = scanner;
        this.players = new DynamicArray<>();
        this.jailQueue = new Queue<>();
    }

    public void inicializarPartida() {
        inicializarTabuleiro();
        inicializarBaralho();
        this.history = new Queue<>(capacidadeHistorico);

        // Posicionar jogadores no Início
        Node<Casa> inicioNode = board.getHead();
        for (int i = 0; i < players.size(); i++) {
            Player p = players.get(i);
            p.setPosicaoAtual(inicioNode);
            p.setSaldo(saldoInicial);
        }

        this.rodadaAtual = 1;
        this.jogadorTurnoIndex = 0;
        this.jogoTerminado = false;
        this.imovelMaiorAluguelCobrado = null;
        this.maiorAluguelCobrado = 0.0;
        
        System.out.println(GREEN + "=== PARTIDA INICIALIZADA COM SUCESSO! ===" + RESET);
    }

    private void inicializarTabuleiro() {
        board = new CircularDoublyLinkedList<>();
        board.add(new Casa(0, "Início", CasaTipo.INICIO));
        board.add(new Casa(1, "Avenida Paulista", CasaTipo.IMOVEL, 200, 50));
        board.add(new Casa(2, "Sorte / Revés", CasaTipo.SORTE_REVES));
        board.add(new Casa(3, "Rua Augusta", CasaTipo.IMOVEL, 120, 30));
        board.add(new Casa(4, "Imposto", CasaTipo.IMPOSTO));
        board.add(new Casa(5, "Copacabana", CasaTipo.IMOVEL, 300, 80));
        board.add(new Casa(6, "Prisão (Visita)", CasaTipo.PRISAO));
        board.add(new Casa(7, "Ipanema", CasaTipo.IMOVEL, 280, 75));
        board.add(new Casa(8, "Sorte / Revés", CasaTipo.SORTE_REVES));
        board.add(new Casa(9, "Rua das Flores", CasaTipo.IMOVEL, 100, 20));
        board.add(new Casa(10, "Leilão", CasaTipo.LEILAO));
        board.add(new Casa(11, "Barra da Tijuca", CasaTipo.IMOVEL, 320, 90));
        board.add(new Casa(12, "Restituição", CasaTipo.RESTITUICAO));
        board.add(new Casa(13, "Avenida Central", CasaTipo.IMOVEL, 220, 55));
        board.add(new Casa(14, "Sorte / Revés", CasaTipo.SORTE_REVES));
        board.add(new Casa(15, "Rua da Bahia", CasaTipo.IMOVEL, 150, 35));
        board.add(new Casa(16, "Vá para a Prisão", CasaTipo.VA_PARA_PRISAO));
        board.add(new Casa(17, "Avenida Rebouças", CasaTipo.IMOVEL, 180, 45));
        board.add(new Casa(18, "Sorte / Revés", CasaTipo.SORTE_REVES));
        board.add(new Casa(19, "Rua do Ouvidor", CasaTipo.IMOVEL, 110, 25));
    }

    private void inicializarBaralho() {
        deck = new Stack<>();
        discardPile = new Stack<>();

        // Cartas de ganho/avanço (6)
        deck.push(new Card("Bônus de Parceria", "Recebe R$ 200 do banco por uma consultoria bem-sucedida.", Card.CardEffect.RECEIVE_CASH, 200));
        deck.push(new Card("Rendimento de Ações", "Suas ações valorizaram! Receba R$ 150 do banco.", Card.CardEffect.RECEIVE_CASH, 150));
        deck.push(new Card("Avanço Estratégico", "Avance 3 casas no tabuleiro.", Card.CardEffect.MOVE_FORWARD, 3));
        deck.push(new Card("Super Salto", "Avance 5 casas no tabuleiro.", Card.CardEffect.MOVE_FORWARD, 5));
        deck.push(new Card("Voo Executivo", "Vá diretamente para a casa Início e receba seu salário.", Card.CardEffect.GO_TO_START, 0));
        deck.push(new Card("Cobrança Coletiva", "Hora de cobrar favores. Todos os outros jogadores ativos pagam R$ 50 a você.", Card.CardEffect.RECEIVE_FROM_PLAYERS, 50));

        // Cartas de perda/retrocesso (6)
        deck.push(new Card("Multa de Trânsito", "Você foi pego acima do limite de velocidade. Pague R$ 100 ao banco.", Card.CardEffect.PAY_CASH, 100));
        deck.push(new Card("Taxa de Licenciamento", "Pague impostos atrasados no valor de R$ 150 ao banco.", Card.CardEffect.PAY_CASH, 150));
        deck.push(new Card("Dividir Despesas", "Organizou um jantar de negócios. Pague R$ 50 a cada um dos outros jogadores ativos.", Card.CardEffect.PAY_TO_PLAYERS, 50));
        deck.push(new Card("Prisão Preventiva", "Sua conduta empresarial está sob suspeita. Vá diretamente para a Prisão.", Card.CardEffect.GO_TO_JAIL, 0));
        deck.push(new Card("Recuo Forçado", "Volte 3 casas no tabuleiro.", Card.CardEffect.MOVE_BACKWARD, 3));
        deck.push(new Card("Erro de Rota", "Volte 5 casas no tabuleiro.", Card.CardEffect.MOVE_BACKWARD, 5));

        embaralharBaralho();
    }

    private void embaralharBaralho() {
        int tamanho = deck.size();
        if (tamanho == 0) return;

        Card[] tempArr = new Card[tamanho];
        for (int i = 0; i < tamanho; i++) {
            tempArr[i] = deck.pop();
        }

        // Fisher-Yates
        for (int i = tamanho - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            Card temp = tempArr[i];
            tempArr[i] = tempArr[j];
            tempArr[j] = temp;
        }

        for (int i = 0; i < tamanho; i++) {
            deck.push(tempArr[i]);
        }
    }

    public void recolocarDescartes() {
        System.out.println(YELLOW + "[Baralho] O baralho acabou! Remontando com a pilha de descartes..." + RESET);
        while (!discardPile.isEmpty()) {
            deck.push(discardPile.pop());
        }
        embaralharBaralho();
    }

    public void processarTurno() {
        if (jogoTerminado) {
            System.out.println(RED + "O jogo já terminou!" + RESET);
            return;
        }

        Player p = players.get(jogadorTurnoIndex);
        if (p.isFalido()) {
            passarTurno();
            return;
        }

        System.out.println("\n" + CYAN + "==================================================" + RESET);
        System.out.println("VEZ DE: " + PURPLE + p.getNome() + RESET + " [" + p.getPersonagem().getNome() + "]");
        System.out.println(String.format("Saldo: R$ %.2f | Posição: %s", p.getSaldo(), p.getPosicaoAtual().data.toString()));
        System.out.println(CYAN + "==================================================" + RESET);

        boolean livreParaMover = true;
        int d1 = 0, d2 = 0;

        if (p.isPreso()) {
            livreParaMover = processarPrisao(p);
        }

        if (livreParaMover && !p.isFalido()) {
            System.out.println("Pressione ENTER para rolar os dados...");
            scanner.nextLine();
            d1 = random.nextInt(6) + 1;
            d2 = random.nextInt(6) + 1;
            int totalDados = d1 + d2;
            System.out.println(String.format("Dados rolados: [%d] + [%d] = %s%d%s", d1, d2, GREEN, totalDados, RESET));

            moverJogador(p, totalDados, true, String.format("%d+%d=%d", d1, d2, totalDados));
        }

        if (p.isFalido()) {
            System.out.println(RED + "O jogador " + p.getNome() + " faliu!" + RESET);
        }

        verificarFimDeJogo();
        if (!jogoTerminado) {
            passarTurno();
        }
    }

    private boolean processarPrisao(Player p) {
        System.out.println(RED + "!!! VOCÊ ESTÁ NA PRISÃO !!!" + RESET);
        p.incrementTentativasPrisao();
        System.out.println("Tentativa de saída número: " + p.getTentativasPrisao() + " de 3.");

        // Apresentar opções
        System.out.println("Opções de soltura:");
        System.out.println(String.format("1 - Pagar Fiança de R$ %.2f (10%% do salário)", valorFianca));
        if (p.getPersonagem() == CharacterType.ADVOGADO && !p.isIsencaoFiancaUsada()) {
            System.out.println("2 - Usar Isenção de Fiança (Habilidade Especial de Advogado)");
        }
        System.out.println("3 - Tentar rolar dados duplos");

        System.out.print("Escolha uma opção: ");
        String opcao = scanner.nextLine();

        if (opcao.equals("1")) {
            if (p.getSaldo() >= valorFianca) {
                p.debitarSaldo(valorFianca);
                p.setPreso(false);
                removerDaFilaPrisao(p);
                System.out.println(GREEN + "Fiança paga! Você está livre para jogar nesta rodada." + RESET);
                return true;
            } else {
                System.out.println(RED + "Saldo insuficiente para pagar fiança!" + RESET);
            }
        } else if (opcao.equals("2") && p.getPersonagem() == CharacterType.ADVOGADO && !p.isIsencaoFiancaUsada()) {
            p.usarIsencaoFianca();
            p.setPreso(false);
            removerDaFilaPrisao(p);
            System.out.println(GREEN + "[Habilidade] Advogado usou isenção! Você está livre sem custo." + RESET);
            return true;
        }

        // Tenta dados duplos
        System.out.println("Rolando dados...");
        int d1 = random.nextInt(6) + 1;
        int d2 = random.nextInt(6) + 1;
        int total = d1 + d2;
        System.out.println(String.format("Dados rolados: [%d] + [%d]", d1, d2));

        if (d1 == d2) {
            p.setPreso(false);
            removerDaFilaPrisao(p);
            System.out.println(GREEN + "DADOS DUPLOS! Você foi solto e avança " + total + " casas." + RESET);
            moverJogador(p, total, true, String.format("Duplos: %d+%d=%d", d1, d2, total));
            return false; // Já moveu
        } else {
            System.out.println(RED + "Não foram dados duplos." + RESET);
            if (p.getTentativasPrisao() >= 3) {
                p.setPreso(false);
                removerDaFilaPrisao(p);
                System.out.println(YELLOW + "Após 3 rodadas presas, você foi solto automaticamente, mas perde a jogada atual." + RESET);
            } else {
                System.out.println("Você permanece na prisão.");
                registrarRodada(p.getNome(), "Preso", p.getPosicaoAtual().data.getNome(), "Permaneceu na prisão");
            }
            return false;
        }
    }

    private void removerDaFilaPrisao(Player p) {
        // Recriar a fila de prisão pulando o jogador solto
        Queue<Player> temp = new Queue<>();
        while (!jailQueue.isEmpty()) {
            Player tempP = jailQueue.dequeue();
            if (!tempP.equals(p)) {
                temp.enqueue(tempP);
            }
        }
        jailQueue = temp;
    }

    public void moverJogador(Player p, int casas, boolean avanco, String dadosTexto) {
        Node<Casa> atual = p.getPosicaoAtual();
        boolean completouVolta = false;

        System.out.print("Movendo: " + atual.data.getNome());

        for (int i = 0; i < casas; i++) {
            if (avanco) {
                atual = atual.next;
                if (atual.data.getTipo() == CasaTipo.INICIO) {
                    completouVolta = true;
                }
            } else {
                atual = atual.prev;
            }
            System.out.print(" -> " + atual.data.getNome());
        }
        System.out.println();

        p.setPosicaoAtual(atual);

        if (completouVolta && avanco) {
            double sal = salarioVolta;
            if (p.getPersonagem() == CharacterType.ESPECULADOR) {
                sal = salarioVolta * 1.20;
                System.out.println(GREEN + "[Habilidade - Especulador] Salário com bônus de 20%! Recebeu R$ " + sal + RESET);
            } else {
                System.out.println(GREEN + "Completou volta! Recebeu salário de R$ " + sal + RESET);
            }
            p.adicionarSaldo(sal);
            p.incrementVoltas();
        } else if (!avanco) {
            System.out.println(YELLOW + "[Movimento] Retrocesso cruzando casas não concede salário!" + RESET);
        }

        // Processar a casa de destino
        processarCasaDestino(p, atual.data, dadosTexto);
    }

    private void processarCasaDestino(Player p, Casa casa, String dadosTexto) {
        System.out.println("Parou em: " + YELLOW + casa.getNome() + RESET + " (" + casa.getTipo() + ")");
        String efeitoLog = "";

        switch (casa.getTipo()) {
            case INICIO:
                efeitoLog = "Parou no Início";
                break;

            case IMOVEL:
                efeitoLog = processarImovel(p, casa);
                break;

            case IMPOSTO:
                double patrimonio = p.getPatrimonioTotal();
                double taxa = 0.05;
                if (p.getPersonagem() == CharacterType.ESPECULADOR) {
                    taxa = 0.055; // +10% sobre 5% = 5.5%
                    System.out.println(RED + "[Habilidade - Especulador] Paga +10% de taxa no imposto (Total: 5.5%%)" + RESET);
                }
                double valorImposto = patrimonio * taxa;
                System.out.println(String.format("Imposto cobrado: 5%% (ou 5.5%%) de R$ %.2f (Patrimônio) = R$ %.2f", patrimonio, valorImposto));
                garantirSaldo(p, valorImposto);
                if (!p.isFalido()) {
                    p.debitarSaldo(valorImposto);
                    System.out.println(RED + String.format("Você pagou R$ %.2f de imposto. Saldo atual: R$ %.2f", valorImposto, p.getSaldo()) + RESET);
                    efeitoLog = String.format("Pagou R$ %.2f de Imposto", valorImposto);
                } else {
                    efeitoLog = "Faliu pagando Imposto";
                }
                break;

            case RESTITUICAO:
                double valorRestituicao = salarioVolta * 0.10;
                p.adicionarSaldo(valorRestituicao);
                System.out.println(GREEN + String.format("Recebeu restituição de 10%% do salário: R$ %.2f. Saldo atual: R$ %.2f", valorRestituicao, p.getSaldo()) + RESET);
                efeitoLog = String.format("Recebeu R$ %.2f de Restituição", valorRestituicao);
                break;

            case PRISAO:
                efeitoLog = "Apenas visitando a Prisão";
                break;

            case VA_PARA_PRISAO:
                enviarParaPrisao(p);
                efeitoLog = "Enviado para a Prisão";
                break;

            case LEILAO:
                efeitoLog = processarLeilao();
                break;

            case SORTE_REVES:
                efeitoLog = processarSorteReves(p, dadosTexto);
                break;
        }

        registrarRodada(p.getNome(), dadosTexto, casa.getNome(), efeitoLog);
    }

    private String processarImovel(Player p, Casa casa) {
        if (casa.getProprietario() == null) {
            // Imóvel sem dono
            System.out.println(String.format("O imóvel está sem proprietário. Valor: R$ %.2f | Aluguel Base: R$ %.2f", casa.getValorCompra(), casa.getAluguelBase()));
            System.out.print("Deseja comprar este imóvel? (S/N): ");
            String escolha = scanner.nextLine().trim().toUpperCase();
            if (escolha.equals("S")) {
                if (p.getSaldo() >= casa.getValorCompra()) {
                    p.debitarSaldo(casa.getValorCompra());
                    double baseRentOriginal = casa.getAluguelBase();
                    if (p.getPersonagem() == CharacterType.CONSTRUTOR) {
                        double novoAluguel = baseRentOriginal * 1.15;
                        casa.setAluguelBase(novoAluguel);
                        System.out.println(GREEN + "[Habilidade - Construtor] Aluguel base do imóvel aumentado em 15%! (Base: R$ " + novoAluguel + ")" + RESET);
                    }
                    p.adicionarPropriedade(casa);
                    System.out.println(GREEN + "Imóvel comprado com sucesso! Saldo restante: R$ " + p.getSaldo() + RESET);
                    return "Comprou " + casa.getNome();
                } else {
                    System.out.println(RED + "Saldo insuficiente para comprar este imóvel!" + RESET);
                    return "Passou a compra de " + casa.getNome() + " (Saldo insuficiente)";
                }
            } else {
                System.out.println("Você optou por não comprar.");
                return "Não quis comprar " + casa.getNome();
            }
        } else if (casa.getProprietario().equals(p)) {
            // Imóvel é do próprio jogador
            System.out.println("Você está na sua própria propriedade. Nada acontece.");
            return "Visitou propriedade própria";
        } else {
            // Imóvel de outro jogador
            Player dono = casa.getProprietario();
            
            // Incrementar visita por outros jogadores
            casa.incrementVisitCount();
            
            double aluguel = casa.calcularAluguelAtual();
            
            // Habilidade passiva do Negociante
            if (p.getPersonagem() == CharacterType.NEGOCIANTE) {
                aluguel = aluguel * 0.90;
                System.out.println(GREEN + "[Habilidade - Negociante] Desconto de 10% no aluguel aplicado!" + RESET);
            }

            System.out.println(String.format("Esta propriedade pertence a %s. Aluguel atualizado: R$ %.2f (Visitas: %d, Multiplicador: %.1fx)", 
                dono.getNome(), aluguel, casa.getVisitCount(), casa.getMultiplicadorVisita()));

            // Registrar estatísticas
            if (aluguel > maiorAluguelCobrado) {
                maiorAluguelCobrado = aluguel;
                imovelMaiorAluguelCobrado = casa;
            }

            garantirSaldo(p, aluguel);
            if (!p.isFalido()) {
                p.debitarSaldo(aluguel);
                dono.adicionarSaldo(aluguel);
                System.out.println(RED + String.format("Você pagou R$ %.2f de aluguel para %s.", aluguel, dono.getNome()) + RESET);
                return String.format("Pagou R$ %.2f de aluguel para %s", aluguel, dono.getNome());
            } else {
                // Transferir saldo restante antes de falir
                double restante = Math.max(0, p.getSaldo());
                p.debitarSaldo(restante);
                dono.adicionarSaldo(restante);
                return "Faliu pagando aluguel para " + dono.getNome();
            }
        }
    }

    private String processarLeilao() {
        System.out.println(YELLOW + "--- LEILÃO ATIVADO! ---" + RESET);
        // Achar imóvel sem dono aleatório
        DynamicArray<Casa> disponiveis = new DynamicArray<>();
        Node<Casa> cursor = board.getHead();
        for (int i = 0; i < board.size(); i++) {
            if (cursor.data.getTipo() == CasaTipo.IMOVEL && cursor.data.getProprietario() == null) {
                disponiveis.add(cursor.data);
            }
            cursor = cursor.next;
        }

        if (disponiveis.isEmpty()) {
            System.out.println("Não há imóveis disponíveis para leilão!");
            return "Leilão cancelado (sem imóveis)";
        }

        // Escolher aleatório
        Casa alvo = disponiveis.get(random.nextInt(disponiveis.size()));
        double precoBase = alvo.getValorCompra();
        double bidMinimo = precoBase * 0.50;

        System.out.println(String.format("Imóvel a ser leiloado: %s", alvo.getNome()));
        System.out.println(String.format("Preço original: R$ %.2f | Lance Mínimo Vencedor (50%%): R$ %.2f", precoBase, bidMinimo));

        Player vencedor = null;
        double maiorBid = 0.0;

        // Fazer lances
        // Inicia do próximo jogador na ordem
        int indexInicial = (jogadorTurnoIndex + 1) % players.size();
        DynamicArray<Player> licitantes = new DynamicArray<>();
        for (int i = 0; i < players.size(); i++) {
            Player temp = players.get((indexInicial + i) % players.size());
            if (!temp.isFalido()) {
                licitantes.add(temp);
            }
        }

        boolean[] passou = new boolean[licitantes.size()];
        int ativos = licitantes.size();

        while (ativos > 0) {
            boolean algumLanceNestaRodada = false;
            for (int i = 0; i < licitantes.size(); i++) {
                if (passou[i]) continue;
                Player tempP = licitantes.get(i);
                
                System.out.println(String.format("\nMaior lance atual: R$ %.2f (por %s)", maiorBid, (vencedor != null ? vencedor.getNome() : "Ninguém")));
                System.out.println(String.format("Jogador: %s | Seu Saldo: R$ %.2f", tempP.getNome(), tempP.getSaldo()));
                System.out.print("Digite o valor do seu lance (ou 'P' para passar): ");
                String input = scanner.nextLine().trim();

                if (input.equalsIgnoreCase("P")) {
                    passou[i] = true;
                    ativos--;
                    System.out.println(tempP.getNome() + " passou.");
                } else {
                    try {
                        double bid = Double.parseDouble(input);
                        if (bid <= maiorBid) {
                            System.out.println(RED + "O lance deve ser maior do que o lance atual!" + RESET);
                        } else if (bid > tempP.getSaldo()) {
                            System.out.println(RED + "Você não tem saldo suficiente para este lance!" + RESET);
                        } else {
                            maiorBid = bid;
                            vencedor = tempP;
                            algumLanceNestaRodada = true;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println(RED + "Entrada inválida! Digite um número ou 'P'." + RESET);
                    }
                }
            }
            if (!algumLanceNestaRodada && ativos > 0) {
                // Se ninguém deu lance numa rodada completa e restam ativos, acaba
                break;
            }
        }

        if (vencedor != null && maiorBid >= bidMinimo) {
            vencedor.debitarSaldo(maiorBid);
            if (vencedor.getPersonagem() == CharacterType.CONSTRUTOR) {
                alvo.setAluguelBase(alvo.getAluguelBase() * 1.15);
                System.out.println(GREEN + "[Habilidade - Construtor] Aluguel base do imóvel aumentado em 15%!" + RESET);
            }
            vencedor.adicionarPropriedade(alvo);
            System.out.println(GREEN + String.format("LEILÃO CONCLUÍDO! %s arrematou %s por R$ %.2f", vencedor.getNome(), alvo.getNome(), maiorBid) + RESET);
            return String.format("%s arrematou %s no leilão por R$ %.2f", vencedor.getNome(), alvo.getNome(), maiorBid);
        } else {
            System.out.println(RED + "Leilão sem vencedor ou lance final abaixo do mínimo exigido. O imóvel continua sem dono." + RESET);
            return "Imóvel não arrematado no leilão";
        }
    }

    private String processarSorteReves(Player p, String dadosTexto) {
        if (deck.isEmpty()) {
            recolocarDescartes();
        }

        Card card = deck.pop();
        System.out.println(CYAN + "--------------------------------------------------" + RESET);
        System.out.println(YELLOW + "!!! CARTA SACADA !!!" + RESET);
        System.out.println(WHITE + card.toString() + RESET);
        System.out.println(CYAN + "--------------------------------------------------" + RESET);

        String desc = card.getTitulo() + ": " + card.getDescricao();

        switch (card.getEfeito()) {
            case RECEIVE_CASH:
                p.adicionarSaldo(card.getValor());
                System.out.println(GREEN + String.format("Saldo adicionado! Saldo atual: R$ %.2f", p.getSaldo()) + RESET);
                break;

            case PAY_CASH:
                garantirSaldo(p, card.getValor());
                if (!p.isFalido()) {
                    p.debitarSaldo(card.getValor());
                    System.out.println(RED + String.format("Saldo debitado! Saldo atual: R$ %.2f", p.getSaldo()) + RESET);
                }
                break;

            case MOVE_FORWARD:
                int casasF = (int) card.getValor();
                System.out.println("Avançando " + casasF + " casas...");
                moverJogador(p, casasF, true, "Carta: " + dadosTexto);
                break;

            case MOVE_BACKWARD:
                int casasB = (int) card.getValor();
                System.out.println("Retrocedendo " + casasB + " casas...");
                moverJogador(p, casasB, false, "Carta: " + dadosTexto);
                break;

            case GO_TO_START:
                System.out.println("Voando direto para o Início...");
                // Mover diretamente alterando a posição
                Node<Casa> cursor = p.getPosicaoAtual();
                boolean passou = false;
                while (cursor.data.getTipo() != CasaTipo.INICIO) {
                    cursor = cursor.next;
                    if (cursor.data.getTipo() == CasaTipo.INICIO) {
                        passou = true;
                    }
                }
                p.setPosicaoAtual(cursor);
                
                double sal = salarioVolta;
                if (p.getPersonagem() == CharacterType.ESPECULADOR) {
                    sal = salarioVolta * 1.20;
                    System.out.println(GREEN + "[Habilidade - Especulador] Salário com bônus! Recebeu R$ " + sal + RESET);
                } else {
                    System.out.println(GREEN + "Chegou ao Início! Recebeu salário de R$ " + sal + RESET);
                }
                p.adicionarSaldo(sal);
                p.incrementVoltas();
                break;

            case GO_TO_JAIL:
                enviarParaPrisao(p);
                break;

            case RECEIVE_FROM_PLAYERS:
                double valorRec = card.getValor();
                for (int i = 0; i < players.size(); i++) {
                    Player outro = players.get(i);
                    if (!outro.isFalido() && !outro.equals(p)) {
                        System.out.println(String.format("Cobrando R$ %.2f de %s", valorRec, outro.getNome()));
                        garantirSaldo(outro, valorRec);
                        if (!outro.isFalido()) {
                            outro.debitarSaldo(valorRec);
                            p.adicionarSaldo(valorRec);
                        } else {
                            double res = Math.max(0, outro.getSaldo());
                            outro.debitarSaldo(res);
                            p.adicionarSaldo(res);
                            System.out.println(RED + outro.getNome() + " faliu tentando pagar você!" + RESET);
                        }
                    }
                }
                System.out.println(GREEN + String.format("Cobrança concluída! Saldo atual de %s: R$ %.2f", p.getNome(), p.getSaldo()) + RESET);
                break;

            case PAY_TO_PLAYERS:
                double valorPag = card.getValor();
                int ativosCount = 0;
                for (int i = 0; i < players.size(); i++) {
                    if (!players.get(i).isFalido() && !players.get(i).equals(p)) {
                        ativosCount++;
                    }
                }
                double custoTotal = valorPag * ativosCount;
                System.out.println(String.format("Custo total de despesas a pagar: R$ %.2f", custoTotal));
                garantirSaldo(p, custoTotal);

                if (!p.isFalido()) {
                    for (int i = 0; i < players.size(); i++) {
                        Player outro = players.get(i);
                        if (!outro.isFalido() && !outro.equals(p)) {
                            p.debitarSaldo(valorPag);
                            outro.adicionarSaldo(valorPag);
                        }
                    }
                    System.out.println(RED + String.format("Você pagou R$ %.2f a cada um dos outros jogadores ativos. Saldo atual: R$ %.2f", valorPag, p.getSaldo()) + RESET);
                } else {
                    // Pagar o que puder antes de falir
                    double parcial = Math.max(0, p.getSaldo()) / (ativosCount > 0 ? ativosCount : 1);
                    for (int i = 0; i < players.size(); i++) {
                        Player outro = players.get(i);
                        if (!outro.isFalido() && !outro.equals(p)) {
                            p.debitarSaldo(parcial);
                            outro.adicionarSaldo(parcial);
                        }
                    }
                }
                break;
        }

        discardPile.push(card);
        return desc;
    }

    private void enviarParaPrisao(Player p) {
        System.out.println(RED + "!!! VOCÊ FOI ENVIADO PARA A PRISÃO !!!" + RESET);
        p.setPreso(true);
        p.setTentativasPrisao(0);

        // Achar a casa da prisão
        Node<Casa> cursor = board.getHead();
        while (cursor.data.getTipo() != CasaTipo.PRISAO) {
            cursor = cursor.next;
        }
        p.setPosicaoAtual(cursor);
        jailQueue.enqueue(p);

        // Exibir posição na fila de prisão
        int posFila = jailQueue.size();
        System.out.println(String.format("%s foi colocado na fila de espera da prisão. Posição na fila: %d", p.getNome(), posFila));
    }

    private void garantirSaldo(Player p, double valorNecessario) {
        while (p.getSaldo() < valorNecessario && p.getPropriedades().size() > 0) {
            System.out.println(RED + String.format("Saldo Insuficiente (R$ %.2f) para pagar R$ %.2f.", p.getSaldo(), valorNecessario) + RESET);
            System.out.println("Propriedades adquiridas:");
            for (int i = 0; i < p.getPropriedades().size(); i++) {
                Casa c = p.getPropriedades().get(i);
                System.out.println(String.format("%d - %s (Valor Compra: R$ %.2f, Venda ao Banco [70%%]: R$ %.2f)", 
                    i, c.getNome(), c.getValorCompra(), c.getValorCompra() * 0.70));
            }
            System.out.print("Escolha o número da propriedade para vender de volta ao banco (ou qualquer outra tecla para falir): ");
            String input = scanner.nextLine();
            try {
                int escolhaIndex = Integer.parseInt(input);
                if (escolhaIndex >= 0 && escolhaIndex < p.getPropriedades().size()) {
                    Casa c = p.getPropriedades().get(escolhaIndex);
                    double valorVenda = c.getValorCompra() * 0.70;
                    p.adicionarSaldo(valorVenda);
                    p.removerPropriedade(c);
                    System.out.println(GREEN + String.format("Vendeu %s por R$ %.2f. Novo saldo: R$ %.2f", c.getNome(), valorVenda, p.getSaldo()) + RESET);
                } else {
                    System.out.println("Opção inválida!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Escolha cancelada ou inválida. Processando falência...");
                break;
            }
        }

        if (p.getSaldo() < valorNecessario) {
            p.setFalido(true);
            liberarPropriedades(p);
            removerDaFilaPrisao(p);
            System.out.println(RED + String.format("!!! %s ESTÁ FALIDO E FOI REMOVIDO DO JOGO !!!", p.getNome()) + RESET);
        }
    }

    private void liberarPropriedades(Player p) {
        DynamicArray<Casa> props = p.getPropriedades();
        while (props.size() > 0) {
            Casa c = props.get(0);
            p.removerPropriedade(c);
            c.reset();
        }
    }

    private void registrarRodada(String jogador, String dados, String destino, String efeito) {
        RoundEntry entry = new RoundEntry(rodadaAtual, jogador, dados, destino, efeito);
        history.enqueue(entry);
    }

    private void passarTurno() {
        int tentativas = 0;
        do {
            jogadorTurnoIndex = (jogadorTurnoIndex + 1) % players.size();
            tentativas++;
        } while (players.get(jogadorTurnoIndex).isFalido() && tentativas < players.size());

        if (jogadorTurnoIndex == 0) {
            rodadaAtual++;
            System.out.println("\n" + BLUE + ">>> INICIANDO RODADA " + rodadaAtual + " <<<" + RESET);
        }
    }

    private void verificarFimDeJogo() {
        int ativos = 0;
        Player vencedorRestante = null;

        for (int i = 0; i < players.size(); i++) {
            if (!players.get(i).isFalido()) {
                ativos++;
                vencedorRestante = players.get(i);
            }
        }

        if (ativos <= 1) {
            jogoTerminado = true;
            System.out.println("\n" + GREEN + "=== FIM DE JOGO: Apenas restou um jogador ativo! ===" + RESET);
        } else if (rodadaAtual > maxRounds) {
            jogoTerminado = true;
            System.out.println("\n" + GREEN + "=== FIM DE JOGO: Limite máximo de rodadas atingido! ===" + RESET);
        }
    }

    public BST<Player> gerarRankingBST() {
        BST<Player> rankingTree = new BST<>();
        for (int i = 0; i < players.size(); i++) {
            // Insere todos os jogadores, mesmo falidos (patrimônio total de falido será o saldo negativo ou zero)
            rankingTree.insert(players.get(i));
        }
        return rankingTree;
    }

    public void exibirRelatorioFinal() {
        System.out.println("\n" + CYAN + "==================================================" + RESET);
        System.out.println(CYAN + "              RELATÓRIO FINAL DO JOGO             " + RESET);
        System.out.println(CYAN + "==================================================" + RESET);

        BST<Player> rankingTree = gerarRankingBST();
        DynamicArray<Player> ordenados = rankingTree.getDescendingOrder();

        System.out.println("\n" + GREEN + "--- CLASSIFICAÇÃO POR PATRIMÔNIO (Gerado via BST) ---" + RESET);
        for (int i = 0; i < ordenados.size(); i++) {
            Player p = ordenados.get(i);
            String status = p.isFalido() ? " (FALIDO)" : "";
            System.out.println(String.format("%dº Lugar: %s - Patrimônio Total: R$ %.2f | Voltas Completas: %d%s", 
                (i + 1), p.getNome(), p.getPatrimonioTotal(), p.getVoltas(), status));
        }

        System.out.println("\n" + GREEN + "--- ESTRUTURA VISUAL DA ÁRVORE DE RANKING (BST) ---" + RESET);
        rankingTree.printTree();

        System.out.println("\n" + GREEN + "--- ESTATÍSTICAS ---" + RESET);
        if (imovelMaiorAluguelCobrado != null) {
            System.out.println(String.format("Imóvel com maior aluguel cobrado: %s (Aluguel: R$ %.2f cobrado de outrem)", 
                imovelMaiorAluguelCobrado.getNome(), maiorAluguelCobrado));
        } else {
            System.out.println("Nenhum aluguel foi cobrado na partida.");
        }

        System.out.println("\n" + GREEN + "--- HISTÓRICO DAS ÚLTIMAS RODADAS (Fila do Histórico) ---" + RESET);
        DynamicArray<RoundEntry> histArr = history.toArray();
        for (int i = 0; i < histArr.size(); i++) {
            System.out.println(histArr.get(i).toString());
        }
        System.out.println(CYAN + "==================================================" + RESET);
    }

    // Getters e Setters para configurações
    public double getSaldoInicial() { return saldoInicial; }
    public void setSaldoInicial(double saldoInicial) { this.saldoInicial = saldoInicial; }
    public double getSalarioVolta() { return salarioVolta; }
    public void setSalarioVolta(double salarioVolta) { 
        this.salarioVolta = salarioVolta;
        this.valorFianca = salarioVolta * 0.10; // fiança atualiza automaticamente
    }
    public double getValorFianca() { return valorFianca; }
    public void setValorFianca(double valorFianca) { this.valorFianca = valorFianca; }
    public int getMaxRounds() { return maxRounds; }
    public void setMaxRounds(int maxRounds) { this.maxRounds = maxRounds; }
    public int getCapacidadeHistorico() { return capacidadeHistorico; }
    public void setCapacidadeHistorico(int capacidadeHistorico) { this.capacidadeHistorico = capacidadeHistorico; }

    public DynamicArray<Player> getPlayers() { return players; }
    public CircularDoublyLinkedList<Casa> getBoard() { return board; }
    public Stack<Card> getDeck() { return deck; }
    public Queue<Player> getJailQueue() { return jailQueue; }
    public Queue<RoundEntry> getHistory() { return history; }
    
    public int getRodadaAtual() { return rodadaAtual; }
    public boolean isJogoTerminado() { return jogoTerminado; }

    public Player getCurrentPlayer() {
        if (players == null || players.size() == 0) return null;
        return players.get(jogadorTurnoIndex);
    }

    public void declararFalencia(Player p) {
        p.setFalido(true);
        liberarPropriedades(p);
        removerDaFilaPrisao(p);
        System.out.println(RED + String.format("!!! %s DECLAROU FALÊNCIA E DESISTIU DO JOGO !!!", p.getNome()) + RESET);
        verificarFimDeJogo();
        if (!jogoTerminado) {
            passarTurno();
        }
    }
}
