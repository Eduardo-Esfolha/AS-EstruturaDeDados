package game;

public enum CharacterType {
    ESPECULADOR("Especulador", "Recebe +20% no salário ao completar volta, mas paga +10% de imposto."),
    NEGOCIANTE("Negociante", "Paga 10% a menos de aluguel a outros jogadores."),
    ADVOGADO("Advogado", "Pode sair da prisão sem custo uma vez por jogo (isenção de fiança)."),
    CONSTRUTOR("Construtor", "Imóveis que compra têm aluguel base aumentado em 15%.");

    private final String nome;
    private final String descricao;

    CharacterType(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }
}
