package game;

public class RoundEntry {
    private int rodada;
    private String jogadorNome;
    private String dadosLancados;
    private String casaDestino;
    private String efeitoAplicado;

    public RoundEntry(int rodada, String jogadorNome, String dadosLancados, String casaDestino, String efeitoAplicado) {
        this.rodada = rodada;
        this.jogadorNome = jogadorNome;
        this.dadosLancados = dadosLancados;
        this.casaDestino = casaDestino;
        this.efeitoAplicado = efeitoAplicado;
    }

    public int getRodada() {
        return rodada;
    }

    public String getJogadorNome() {
        return jogadorNome;
    }

    public String getDadosLancados() {
        return dadosLancados;
    }

    public String getCasaDestino() {
        return casaDestino;
    }

    public String getEfeitoAplicado() {
        return efeitoAplicado;
    }

    @Override
    public String toString() {
        return String.format("Rodada %d | %s | Dados: %s | Destino: %s | Efeito: %s",
            rodada, jogadorNome, dadosLancados, casaDestino, efeitoAplicado);
    }
}
