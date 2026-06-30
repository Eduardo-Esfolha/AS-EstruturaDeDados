package game;

public class Casa {
    private int index;
    private String nome;
    private CasaTipo tipo;
    private double valorCompra;
    private double aluguelBase;
    private Player proprietario;
    private int visitCount; // visits by other players

    public Casa(int index, String nome, CasaTipo tipo) {
        this.index = index;
        this.nome = nome;
        this.tipo = tipo;
        this.valorCompra = 0;
        this.aluguelBase = 0;
        this.proprietario = null;
        this.visitCount = 0;
    }

    public Casa(int index, String nome, CasaTipo tipo, double valorCompra, double aluguelBase) {
        this.index = index;
        this.nome = nome;
        this.tipo = tipo;
        this.valorCompra = valorCompra;
        this.aluguelBase = aluguelBase;
        this.proprietario = null;
        this.visitCount = 0;
    }

    public int getIndex() {
        return index;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public CasaTipo getTipo() {
        return tipo;
    }

    public double getValorCompra() {
        return valorCompra;
    }

    public void setValorCompra(double valorCompra) {
        this.valorCompra = valorCompra;
    }

    public double getAluguelBase() {
        return aluguelBase;
    }

    public void setAluguelBase(double aluguelBase) {
        this.aluguelBase = aluguelBase;
    }

    public Player getProprietario() {
        return proprietario;
    }

    public void setProprietario(Player proprietario) {
        this.proprietario = proprietario;
    }

    public int getVisitCount() {
        return visitCount;
    }

    public void setVisitCount(int visitCount) {
        this.visitCount = visitCount;
    }

    public void incrementVisitCount() {
        this.visitCount++;
    }

    public double getMultiplicadorVisita() {
        double mult = 1.0 + (visitCount * 0.1);
        if (mult > 2.0) {
            return 2.0;
        }
        return mult;
    }

    public double calcularAluguelAtual() {
        return aluguelBase * getMultiplicadorVisita();
    }

    public void reset() {
        this.proprietario = null;
        this.visitCount = 0;
    }

    @Override
    public String toString() {
        if (tipo == CasaTipo.IMOVEL) {
            String dono = (proprietario != null) ? proprietario.getNome() : "Sem Dono";
            return String.format("[%d] %s (Imóvel - Custo: R$ %.2f, Aluguel Base: R$ %.2f, Atual: R$ %.2f, Dono: %s, Visitas: %d, Mult: %.1fx)", 
                index, nome, valorCompra, aluguelBase, calcularAluguelAtual(), dono, visitCount, getMultiplicadorVisita());
        }
        return String.format("[%d] %s (%s)", index, nome, tipo);
    }
}
