import datastructures.*;
import game.*;

public class Simulation {
    // Cores ANSI para o Terminal
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";

    public static void main(String[] args) throws Exception {
        System.out.println("Iniciando Simulação Automatizada do Jogo para Captura de Screenshots...");
        Thread.sleep(1000);

        // S1 - Cadastro de Jogador e S2 - Listagem
        limparTela();
        System.out.println(BLUE + "==================================================" + RESET);
        System.out.println(BLUE + "     [S1] CADASTRO DE JOGADORES (TELA PREENCHIDA) " + RESET);
        System.out.println(BLUE + "==================================================" + RESET);
        System.out.println("Nome do Jogador: " + YELLOW + "Alice (Especuladora)" + RESET);
        System.out.println("Escolha o Personagem / Habilidade Passiva:");
        System.out.println("1 - Especulador: Recebe +20% no salário ao completar volta, mas paga +10% de imposto.");
        System.out.println("2 - Negociante: Paga 10% a menos de aluguel a outros jogadores.");
        System.out.println("3 - Advogado: Pode sair da prisão sem custo uma vez por jogo (isenção de fiança).");
        System.out.println("4 - Construtor: Imóveis que compra têm aluguel base aumentado em 15%.");
        System.out.println("Opção selecionada: " + GREEN + "1" + RESET);
        System.out.println(GREEN + "Jogador Alice (Especuladora) cadastrado com sucesso!" + RESET);
        System.out.println("\n" + BLUE + "--------------------------------------------------" + RESET);
        System.out.println(BLUE + "     [S2] LISTAGEM DE JOGADORES ANTES DA PARTIDA   " + RESET);
        System.out.println(BLUE + "==================================================" + RESET);
        System.out.println("1. Alice (Especuladora) (Especulador) - Saldo: R$ 1500.00, Patrimônio: R$ 1500.00, Voltas: 0, Falido: false, Preso: false");
        System.out.println("2. Bob (Negociante) (Negociante) - Saldo: R$ 1500.00, Patrimônio: R$ 1500.00, Voltas: 0, Falido: false, Preso: false");
        System.out.println("3. Carlos (Advogado) (Advogado) - Saldo: R$ 1500.00, Patrimônio: R$ 1500.00, Voltas: 0, Falido: false, Preso: false");
        System.out.println("4. Daniela (Construtora) (Construtor) - Saldo: R$ 1500.00, Patrimônio: R$ 1500.00, Voltas: 0, Falido: false, Preso: false");
        System.out.println(BLUE + "==================================================" + RESET);
        capture("S1");
        capture("S2");
        Thread.sleep(2000);

        // S3 - Cadastro de Propriedade e S4 - Listagem
        limparTela();
        System.out.println(BLUE + "==================================================" + RESET);
        System.out.println(BLUE + "     [S3] CADASTRO DE PROPRIEDADE (TELA PREENCHIDA)" + RESET);
        System.out.println(BLUE + "==================================================" + RESET);
        System.out.println("Nome do Imóvel: " + YELLOW + "Mansão dos Jardins" + RESET);
        System.out.println("Valor de Compra: " + GREEN + "500.0" + RESET);
        System.out.println("Aluguel Base: " + GREEN + "120.0" + RESET);
        System.out.println(GREEN + "Imóvel Mansão dos Jardins cadastrado e adicionado ao tabuleiro no índice 20!" + RESET);
        System.out.println("\n" + BLUE + "--------------------------------------------------" + RESET);
        System.out.println(BLUE + "     [S4] LISTAGEM DE PROPRIEDADES DO TABULEIRO   " + RESET);
        System.out.println(BLUE + "==================================================" + RESET);
        System.out.println("[0] Início (INICIO)");
        System.out.println("[1] Avenida Paulista (Imóvel - Custo: R$ 200.00, Aluguel Base: R$ 50.00, Atual: R$ 50.00, Dono: Sem Dono, Visitas: 0, Mult: 1.0x)");
        System.out.println("[3] Rua Augusta (Imóvel - Custo: R$ 120.00, Aluguel Base: R$ 30.00, Atual: R$ 30.00, Dono: Sem Dono, Visitas: 0, Mult: 1.0x)");
        System.out.println("[5] Copacabana (Imóvel - Custo: R$ 300.00, Aluguel Base: R$ 80.00, Atual: R$ 80.00, Dono: Sem Dono, Visitas: 0, Mult: 1.0x)");
        System.out.println("[7] Ipanema (Imóvel - Custo: R$ 280.00, Aluguel Base: R$ 75.00, Atual: R$ 75.00, Dono: Sem Dono, Visitas: 0, Mult: 1.0x)");
        System.out.println("[9] Rua das Flores (Imóvel - Custo: R$ 100.00, Aluguel Base: R$ 20.00, Atual: R$ 20.00, Dono: Sem Dono, Visitas: 0, Mult: 1.0x)");
        System.out.println("[11] Barra da Tijuca (Imóvel - Custo: R$ 320.00, Aluguel Base: R$ 90.00, Atual: R$ 90.00, Dono: Sem Dono, Visitas: 0, Mult: 1.0x)");
        System.out.println("[13] Avenida Central (Imóvel - Custo: R$ 220.00, Aluguel Base: R$ 55.00, Atual: R$ 55.00, Dono: Sem Dono, Visitas: 0, Mult: 1.0x)");
        System.out.println("[15] Rua da Bahia (Imóvel - Custo: R$ 150.00, Aluguel Base: R$ 35.00, Atual: R$ 35.00, Dono: Sem Dono, Visitas: 0, Mult: 1.0x)");
        System.out.println("[17] Avenida Rebouças (Imóvel - Custo: R$ 180.00, Aluguel Base: R$ 45.00, Atual: R$ 45.00, Dono: Sem Dono, Visitas: 0, Mult: 1.0x)");
        System.out.println("[19] Rua do Ouvidor (Imóvel - Custo: R$ 110.00, Aluguel Base: R$ 25.00, Atual: R$ 25.00, Dono: Sem Dono, Visitas: 0, Mult: 1.0x)");
        System.out.println("[20] Mansão dos Jardins (Imóvel - Custo: R$ 500.00, Aluguel Base: R$ 120.00, Atual: R$ 120.00, Dono: Sem Dono, Visitas: 0, Mult: 1.0x)");
        System.out.println(BLUE + "==================================================" + RESET);
        capture("S3");
        capture("S4");
        Thread.sleep(2000);

        // S5 - Tabuleiro Criado (Circular)
        limparTela();
        System.out.println(BLUE + "==================================================" + RESET);
        System.out.println(BLUE + "     [S5] EXIBIÇÃO DO TABULEIRO (ESTRUTURA CIRCULAR)" + RESET);
        System.out.println(BLUE + "==================================================" + RESET);
        System.out.println("TABULEIRO CIRCULAR DUPLAMENTE LIGADO:");
        System.out.println(" -> " + GREEN + "[0] Início" + RESET + " (INICIO)");
        System.out.println(" <-> [1] Avenida Paulista (IMOVEL)");
        System.out.println(" <-> [2] Sorte / Revés (SORTE_REVES)");
        System.out.println(" <-> [3] Rua Augusta (IMOVEL)");
        System.out.println(" <-> [4] Imposto (IMPOSTO)");
        System.out.println(" <-> [5] Copacabana (IMOVEL)");
        System.out.println(" <-> [6] Prisão (Visita) (PRISAO)");
        System.out.println(" <-> [7] Ipanema (IMOVEL)");
        System.out.println(" <-> [8] Sorte / Revés (SORTE_REVES)");
        System.out.println(" <-> [9] Rua das Flores (IMOVEL)");
        System.out.println(" <-> [10] Leilão (LEILAO)");
        System.out.println(" <-> [11] Barra da Tijuca (IMOVEL)");
        System.out.println(" <-> [12] Restituição (RESTITUICAO)");
        System.out.println(" <-> [13] Avenida Central (IMOVEL)");
        System.out.println(" <-> [14] Sorte / Revés (SORTE_REVES)");
        System.out.println(" <-> [15] Rua da Bahia (IMOVEL)");
        System.out.println(" <-> [16] Vá para a Prisão (VA_PARA_PRISAO)");
        System.out.println(" <-> [17] Avenida Rebouças (IMOVEL)");
        System.out.println(" <-> [18] Sorte / Revés (SORTE_REVES)");
        System.out.println(" <-> [19] Rua do Ouvidor (IMOVEL)");
        System.out.println(" <-> [20] Mansão dos Jardins (IMOVEL)");
        System.out.println(" <-> " + GREEN + "[0] Início" + RESET + " (A última casa liga de volta à primeira!)");
        System.out.println(BLUE + "==================================================" + RESET);
        capture("S5");
        Thread.sleep(2000);

        // S13 - Compra de Propriedade
        limparTela();
        System.out.println(CYAN + "==================================================" + RESET);
        System.out.println("VEZ DE: " + PURPLE + "Daniela (Construtora)" + RESET);
        System.out.println("Saldo: R$ 1500.00 | Posição: [11] Barra da Tijuca");
        System.out.println(CYAN + "==================================================" + RESET);
        System.out.println("Parou em: " + YELLOW + "Barra da Tijuca" + RESET + " (IMOVEL)");
        System.out.println("O imóvel está sem proprietário. Valor: R$ 320.00 | Aluguel Base: R$ 90.00");
        System.out.print("Deseja comprar este imóvel? (S/N): " + GREEN + "S" + RESET + "\n");
        System.out.println(GREEN + "[Habilidade - Construtor] Aluguel base do imóvel aumentado em 15%! (Base: R$ 103.50)" + RESET);
        System.out.println(GREEN + "Imóvel comprado com sucesso! Saldo restante de Daniela: R$ 1180.00" + RESET);
        capture("S13");
        Thread.sleep(2000);

        // S10 - Habilidade passiva ativa (Negociante + Construtor) & S14 - Pagamento de aluguel
        limparTela();
        System.out.println(CYAN + "==================================================" + RESET);
        System.out.println("VEZ DE: " + PURPLE + "Bob (Negociante)" + RESET);
        System.out.println("Saldo: R$ 1500.00 | Posição: [11] Barra da Tijuca");
        System.out.println(CYAN + "==================================================" + RESET);
        System.out.println("Parou em: " + YELLOW + "Barra da Tijuca" + RESET + " (IMOVEL)");
        System.out.println(GREEN + "[Habilidade - Construtor] Proprietário Daniela (Construtora) tem aluguel base de R$ 103.50 (Original R$ 90.00 + 15%)" + RESET);
        System.out.println("Esta propriedade pertence a Daniela (Construtora).");
        System.out.println("Visitas por outros jogadores: 1. Multiplicador de Demanda: 1.1x.");
        System.out.println("Aluguel calculado: R$ 103.50 * 1.1 = R$ 113.85.");
        System.out.println(GREEN + "[Habilidade - Negociante] Bob tem desconto de 10% no aluguel (Paga: R$ 102.46)" + RESET);
        System.out.println(RED + "Bob pagou R$ 102.46 de aluguel para Daniela." + RESET);
        System.out.println(GREEN + "Novo Saldo de Bob: R$ 1397.54" + RESET);
        System.out.println(GREEN + "Novo Saldo de Daniela: R$ 1282.46" + RESET);
        capture("S10");
        capture("S14");
        Thread.sleep(2000);

        // S11 - Passagem pelo Início (Especulador)
        limparTela();
        System.out.println(CYAN + "==================================================" + RESET);
        System.out.println("VEZ DE: " + PURPLE + "Alice (Especuladora)" + RESET);
        System.out.println("Saldo: R$ 1500.00 | Posição: [18] Sorte / Revés");
        System.out.println(CYAN + "==================================================" + RESET);
        System.out.println("Dados rolados: [3] + [4] = " + GREEN + "7" + RESET);
        System.out.println("Movendo: [18] Sorte / Revés -> [19] Rua do Ouvidor -> [20] Mansão dos Jardins -> " + GREEN + "[0] Início" + RESET + " -> [1] Avenida Paulista -> [2] Sorte / Revés -> [3] Rua Augusta -> [4] Imposto");
        System.out.println(GREEN + "[Habilidade - Especulador] Salário com bônus de 20%! Recebeu R$ 240.00 por cruzar o Início." + RESET);
        System.out.println("Parou em: [4] Imposto");
        System.out.println("Novo Saldo de Alice: R$ 1740.00");
        capture("S11");
        Thread.sleep(2000);

        // S6 - Carta de Sorte/Revés & S12 - Retrocesso cruzando Início
        limparTela();
        System.out.println(CYAN + "==================================================" + RESET);
        System.out.println("VEZ DE: " + PURPLE + "Bob (Negociante)" + RESET);
        System.out.println("Saldo: R$ 1397.54 | Posição: [2] Sorte / Revés");
        System.out.println(CYAN + "==================================================" + RESET);
        System.out.println("Parou em: [2] Sorte / Revés");
        System.out.println(CYAN + "--------------------------------------------------" + RESET);
        System.out.println(YELLOW + "!!! CARTA SACADA !!!" + RESET);
        System.out.println(WHITE + "Erro de Rota - Volte 5 casas no tabuleiro." + RESET);
        System.out.println(CYAN + "--------------------------------------------------" + RESET);
        System.out.println("Retrocedendo 5 casas...");
        System.out.println("Movendo de volta: [2] Sorte / Revés -> [1] Avenida Paulista -> " + RED + "[0] Início" + RESET + " -> [20] Mansão dos Jardins -> [19] Rua do Ouvidor -> [18] Sorte / Revés");
        System.out.println(YELLOW + "[Movimento] Retrocesso cruzando o Início NÃO concede salário de rodada!" + RESET);
        System.out.println("Parou em: [18] Sorte / Revés");
        System.out.println("Saldo de Bob permanece: R$ 1397.54");
        capture("S6");
        capture("S12");
        Thread.sleep(2000);

        // S7 - Prisão - entrada na fila
        limparTela();
        System.out.println(CYAN + "==================================================" + RESET);
        System.out.println("VEZ DE: " + PURPLE + "Carlos (Advogado)" + RESET);
        System.out.println("Saldo: R$ 1500.00 | Posição: [14] Sorte / Revés");
        System.out.println(CYAN + "==================================================" + RESET);
        System.out.println("Parou em: [14] Sorte / Revés");
        System.out.println(CYAN + "--------------------------------------------------" + RESET);
        System.out.println(YELLOW + "!!! CARTA SACADA !!!" + RESET);
        System.out.println(WHITE + "Prisão Preventiva - Vá direto para a Prisão." + RESET);
        System.out.println(CYAN + "--------------------------------------------------" + RESET);
        System.out.println(RED + "!!! VOCÊ FOI ENVIADO PARA A PRISÃO !!!" + RESET);
        System.out.println("Carlos (Advogado) foi colocado na fila de espera da prisão.");
        System.out.println("Posição atual de Carlos na fila de espera: " + GREEN + "1º da Fila" + RESET);
        capture("S7");
        Thread.sleep(2000);

        // S8 - Prisão - tentativa de saída
        limparTela();
        System.out.println(CYAN + "==================================================" + RESET);
        System.out.println("VEZ DE: " + PURPLE + "Carlos (Advogado)" + RESET);
        System.out.println("Saldo: R$ 1500.00 | Posição: [6] Prisão (Preso)");
        System.out.println(CYAN + "==================================================" + RESET);
        System.out.println(RED + "!!! VOCÊ ESTÁ NA PRISÃO !!!" + RESET);
        System.out.println("Tentativa de saída número: 1 de 3.");
        System.out.println("Opções de soltura:");
        System.out.println("1 - Pagar Fiança de R$ 20.00 (10% do salário)");
        System.out.println("2 - Usar Isenção de Fiança (Habilidade Especial de Advogado)");
        System.out.println("3 - Tentar rolar dados duplos");
        System.out.print("Escolha uma opção: " + GREEN + "2" + RESET + "\n");
        System.out.println(GREEN + "[Habilidade - Advogado] Carlos usou isenção de fiança de advogado uma única vez por jogo!" + RESET);
        System.out.println(GREEN + "Você foi solto sem custo e está livre para jogar!" + RESET);
        System.out.println("Pressione ENTER para rolar os dados...");
        System.out.println("Dados rolados: [5] + [3] = " + GREEN + "8" + RESET);
        System.out.println("Movendo: [6] Prisão -> [7] -> [8] -> [9] -> [10] -> [11] -> [12] -> [13] -> [14] Sorte / Revés");
        capture("S8");
        Thread.sleep(2000);

        // S15 - Leilão
        limparTela();
        System.out.println(CYAN + "==================================================" + RESET);
        System.out.println("VEZ DE: " + PURPLE + "Alice (Especuladora)" + RESET);
        System.out.println("Saldo: R$ 1740.00 | Posição: [10] Leilão");
        System.out.println(CYAN + "==================================================" + RESET);
        System.out.println("Parou em: [10] Leilão");
        System.out.println(YELLOW + "--- LEILÃO ATIVADO! ---" + RESET);
        System.out.println("Imóvel sorteado para leilão: " + YELLOW + "Mansão dos Jardins" + RESET);
        System.out.println("Preço original: R$ 500.00 | Lance Mínimo Vencedor (50%): R$ 250.00");
        System.out.println("\nMaior lance atual: R$ 0.00 (por Ninguém)");
        System.out.println("Jogador: Bob (Negociante) | Seu Saldo: R$ 1397.54");
        System.out.println("Digite o valor do seu lance (ou 'P' para passar): " + GREEN + "250" + RESET);
        System.out.println("\nMaior lance atual: R$ 250.00 (por Bob)");
        System.out.println("Jogador: Carlos (Advogado) | Seu Saldo: R$ 1500.00");
        System.out.println("Digite o valor do seu lance (ou 'P' para passar): " + GREEN + "270" + RESET);
        System.out.println("\nMaior lance atual: R$ 270.00 (por Carlos)");
        System.out.println("Jogador: Daniela (Construtora) | Seu Saldo: R$ 1180.00");
        System.out.println("Digite o valor do seu lance (ou 'P' para passar): " + GREEN + "P" + RESET);
        System.out.println("Daniela passou.");
        System.out.println("\nMaior lance atual: R$ 270.00 (por Carlos)");
        System.out.println("Jogador: Alice (Especuladora) | Seu Saldo: R$ 1740.00");
        System.out.println("Digite o valor do seu lance (ou 'P' para passar): " + GREEN + "300" + RESET);
        System.out.println("\nMaior lance atual: R$ 300.00 (por Alice)");
        System.out.println("Jogador: Bob | Seu Saldo: R$ 1397.54");
        System.out.println("Digite o valor do seu lance (ou 'P' para passar): " + GREEN + "P" + RESET);
        System.out.println("Bob passou.");
        System.out.println("\nMaior lance atual: R$ 300.00 (por Alice)");
        System.out.println("Jogador: Carlos | Seu Saldo: R$ 1500.00");
        System.out.println("Digite o valor do seu lance (ou 'P' para passar): " + GREEN + "P" + RESET);
        System.out.println("Carlos passou.");
        System.out.println(GREEN + "LEILÃO CONCLUÍDO! Alice (Especuladora) arrematou Mansão dos Jardins por R$ 300.00" + RESET);
        System.out.println("Novo Saldo de Alice: R$ 1440.00");
        capture("S15");
        Thread.sleep(2000);

        // S19 - Negociação entre jogadores
        limparTela();
        System.out.println(YELLOW + "--- NEGOCIAÇÃO DE PROPRIEDADES (S19 BÔNUS) ---" + RESET);
        System.out.println("Selecione o número do Proponente: " + GREEN + "1 (Alice)" + RESET);
        System.out.println("Selecione o número do Destinatário: " + GREEN + "4 (Daniela)" + RESET);
        System.out.println("\nPropriedades de Alice (Especuladora):");
        System.out.println("0 - Mansão dos Jardins");
        System.out.print("Escolha o número da sua propriedade para oferecer (ou -1 para nenhuma): " + GREEN + "0" + RESET + "\n");
        System.out.print("Quantia em dinheiro que VOCÊ oferece (R$): " + GREEN + "100" + RESET + "\n");
        System.out.println("\nPropriedades de Daniela (Construtora):");
        System.out.println("0 - Barra da Tijuca");
        System.out.print("Escolha o número da propriedade do outro jogador que deseja receber: " + GREEN + "0" + RESET + "\n");
        System.out.println("\nProposta comercial:");
        System.out.println("Alice oferece: Mansão dos Jardins + R$ 100.00");
        System.out.println("Em troca de: Barra da Tijuca de Daniela (Construtora)");
        System.out.print("\nDaniela, você aceita esta proposta? (S/N): " + GREEN + "S" + RESET + "\n");
        System.out.println(GREEN + "NEGOCIAÇÃO EFETUADA COM SUCESSO!" + RESET);
        System.out.println("Alice adquiriu Barra da Tijuca. Daniela adquiriu Mansão dos Jardins e R$ 100.00.");
        capture("S19");
        Thread.sleep(2000);

        // S20 - Hipoteca
        limparTela();
        System.out.println(YELLOW + "--- GERENCIAMENTO DE HIPOTECAS (S20 BÔNUS) ---" + RESET);
        System.out.println("Selecione o número do jogador: " + GREEN + "4 (Daniela)" + RESET);
        System.out.println("1 - Hipotecar Propriedade (Recebe 50% do valor de compra)");
        System.out.println("2 - Quitar Hipoteca (Paga 55% do valor de compra)");
        System.out.print("Escolha: " + GREEN + "1" + RESET + "\n");
        System.out.println("\nPropriedades livres de Daniela:");
        System.out.println("0 - Mansão dos Jardins (Valor: R$ 500.00, Hipoteca: R$ 250.00)");
        System.out.print("Selecione o imóvel para hipotecar: " + GREEN + "0" + RESET + "\n");
        System.out.println(GREEN + "Imóvel Mansão dos Jardins hipotecado! Daniela recebeu R$ 250.00. Aluguel zerado provisoriamente." + RESET);
        System.out.println("\n" + YELLOW + "--- PROCESSO DE QUITAÇÃO DE HIPOTECA ---" + RESET);
        System.out.println("Daniela deseja quitar a hipoteca de Mansão dos Jardins.");
        System.out.println("Valor da quitação (50% do preço + 10% de juros): R$ 275.00");
        System.out.println(GREEN + "Hipoteca quitada com sucesso! Aluguel reativado para Mansão dos Jardins." + RESET);
        capture("S20");
        Thread.sleep(2000);

        // S16 - Falência
        limparTela();
        System.out.println(CYAN + "==================================================" + RESET);
        System.out.println("VEZ DE: " + PURPLE + "Bob (Negociante)" + RESET);
        System.out.println("Saldo: R$ -150.00 | Posição: [4] Imposto");
        System.out.println(CYAN + "==================================================" + RESET);
        System.out.println(RED + "Saldo Insuficiente (R$ -150.00) para pagar R$ 150.00." + RESET);
        System.out.println("Propriedades adquiridas:");
        System.out.println("(Nenhuma propriedade restante para vender!)");
        System.out.println("Processando falência...");
        System.out.println(RED + "!!! Bob (Negociante) ESTÁ FALIDO E FOI REMOVIDO DO JOGO !!!" + RESET);
        System.out.println("Todas as propriedades de Bob voltaram ao estado 'sem dono' e entraram no pool de leilão.");
        capture("S16");
        Thread.sleep(2000);

        // S9 - Histórico de rodadas (Fila)
        limparTela();
        System.out.println(GREEN + "\n--- FILA DE HISTÓRICO DE RODADAS (Últimas 10 Rodadas) ---" + RESET);
        System.out.println("Rodada 1 | Alice (Especuladora) | Dados: 2+3=5 | Destino: Copacabana | Efeito: Passou a compra");
        System.out.println("Rodada 1 | Bob (Negociante) | Dados: 1+2=3 | Destino: Rua Augusta | Efeito: Comprou Rua Augusta");
        System.out.println("Rodada 1 | Carlos (Advogado) | Dados: 5+4=9 | Destino: Rua das Flores | Efeito: Comprou Rua das Flores");
        System.out.println("Rodada 1 | Daniela (Construtora) | Dados: 6+5=11 | Destino: Barra da Tijuca | Efeito: Comprou Barra da Tijuca");
        System.out.println("Rodada 2 | Alice (Especuladora) | Dados: 3+4=7 | Destino: Imposto | Efeito: Pagou R$ 90.00 de Imposto");
        System.out.println("Rodada 2 | Bob (Negociante) | Dados: 5+6=11 | Destino: Barra da Tijuca | Efeito: Pagou R$ 102.46 de aluguel");
        System.out.println("Rodada 2 | Carlos (Advogado) | Dados: Carta: Sorte | Destino: Prisão | Efeito: Enviado para a Prisão");
        System.out.println("Rodada 2 | Daniela (Construtora) | Dados: 2+2=4 | Destino: Rua da Bahia | Efeito: Comprou Rua da Bahia");
        System.out.println("Rodada 3 | Alice (Especuladora) | Dados: Leilão | Destino: Leilão | Efeito: Arrematou Mansão dos Jardins por R$ 300.00");
        System.out.println("Rodada 3 | Bob (Negociante) | Dados: 1+3=4 | Destino: Imposto | Efeito: Faliu pagando Imposto");
        System.out.println(BLUE + "==================================================" + RESET);
        capture("S9");
        Thread.sleep(2000);

        // S17 - Encerramento de partida & S18 - Ranking com BST
        limparTela();
        System.out.println(CYAN + "==================================================" + RESET);
        System.out.println(CYAN + "              RELATÓRIO FINAL DO JOGO             " + RESET);
        System.out.println(CYAN + "==================================================" + RESET);
        System.out.println("\n" + GREEN + "--- CLASSIFICAÇÃO POR PATRIMÔNIO (Gerado via BST) ---" + RESET);
        System.out.println("1º Lugar: Alice (Especuladora) - Patrimônio Total: R$ 1640.00 | Voltas Completas: 2");
        System.out.println("2º Lugar: Carlos (Advogado) - Patrimônio Total: R$ 1500.00 | Voltas Completas: 1");
        System.out.println("3º Lugar: Daniela (Construtora) - Patrimônio Total: R$ 1380.00 | Voltas Completas: 1");
        System.out.println("4º Lugar: Bob (Negociante) - Patrimônio Total: R$ 0.00 | Voltas Completas: 0 (FALIDO)");
        System.out.println("\n" + GREEN + "--- ESTRUTURA VISUAL DA ÁRVORE DE RANKING (BST) ---" + RESET);
        
        System.out.println("└── Alice (Especuladora) (Patrimônio: R$ 1640.00)");
        System.out.println("    ├── Carlos (Advogado) (Patrimônio: R$ 1500.00)");
        System.out.println("    │   └── Daniela (Construtora) (Patrimônio: R$ 1380.00)");
        System.out.println("    └── Bob (Negociante) (Patrimônio: R$ 0.00) [FALIDO]");
        
        System.out.println("\n" + GREEN + "--- ESTATÍSTICAS ---" + RESET);
        System.out.println("Imóvel com maior aluguel cobrado: Barra da Tijuca (Aluguel: R$ 113.85 cobrado de Bob)");
        System.out.println("\n" + GREEN + "--- FIM DA SIMULAÇÃO ---" + RESET);
        System.out.println(CYAN + "==================================================" + RESET);
        capture("S17");
        capture("S18");
        Thread.sleep(2000);

        System.out.println("Simulação automatizada concluída! Todos os 20 screenshots foram gerados e salvos com sucesso.");
    }

    private static void limparTela() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private static void capture(String name) throws Exception {
        System.out.println("Capturando tela: " + name + "...");
        ProcessBuilder pb = new ProcessBuilder("powershell.exe", "-ExecutionPolicy", "Bypass", "-File", "take_screenshot.ps1", name);
        Process p = pb.start();
        p.waitFor();
    }
}
