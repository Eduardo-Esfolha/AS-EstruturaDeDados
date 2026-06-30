package game;

public class Card {
    public enum CardEffect {
        RECEIVE_CASH,
        PAY_CASH,
        MOVE_FORWARD,
        MOVE_BACKWARD,
        GO_TO_JAIL,
        GO_TO_START,
        RECEIVE_FROM_PLAYERS,
        PAY_TO_PLAYERS
    }

    private String titulo;
    private String descricao;
    private CardEffect efeito;
    private double valor; // valor financeiro ou número de casas a andar

    public Card(String titulo, String descricao, CardEffect efeito, double valor) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.efeito = efeito;
        this.valor = valor;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public CardEffect getEfeito() {
        return efeito;
    }

    public double getValor() {
        return valor;
    }

    @Override
    public String toString() {
        return String.format("%s - %s", titulo, descricao);
    }
}
