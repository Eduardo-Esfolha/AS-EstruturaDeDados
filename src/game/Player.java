package game;

import datastructures.CircularDoublyLinkedList.Node;
import datastructures.DynamicArray;

public class Player implements Comparable<Player> {
    private String nome;
    private double saldo;
    private Node<Casa> posicaoAtual;
    private DynamicArray<Casa> propriedades;
    private CharacterType personagem;
    private int voltas;
    private boolean preso;
    private int tentativasPrisao;
    private boolean isencaoFiancaUsada;
    private boolean falido;

    public Player(String nome, CharacterType personagem, double saldoInicial) {
        this.nome = nome;
        this.personagem = personagem;
        this.saldo = saldoInicial;
        this.propriedades = new DynamicArray<>();
        this.voltas = 0;
        this.preso = false;
        this.tentativasPrisao = 0;
        this.isencaoFiancaUsada = false;
        this.falido = false;
    }

    public String getNome() {
        return nome;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public void adicionarSaldo(double valor) {
        this.saldo += valor;
    }

    public void debitarSaldo(double valor) {
        this.saldo -= valor;
    }

    public Node<Casa> getPosicaoAtual() {
        return posicaoAtual;
    }

    public void setPosicaoAtual(Node<Casa> posicaoAtual) {
        this.posicaoAtual = posicaoAtual;
    }

    public DynamicArray<Casa> getPropriedades() {
        return propriedades;
    }

    public void adicionarPropriedade(Casa casa) {
        this.propriedades.add(casa);
        casa.setProprietario(this);
    }

    public void removerPropriedade(Casa casa) {
        this.propriedades.remove(casa);
        casa.reset();
    }

    public CharacterType getPersonagem() {
        return personagem;
    }

    public int getVoltas() {
        return voltas;
    }

    public void incrementVoltas() {
        this.voltas++;
    }

    public boolean isPreso() {
        return preso;
    }

    public void setPreso(boolean preso) {
        this.preso = preso;
        if (!preso) {
            this.tentativasPrisao = 0;
        }
    }

    public int getTentativasPrisao() {
        return tentativasPrisao;
    }

    public void setTentativasPrisao(int tentativasPrisao) {
        this.tentativasPrisao = tentativasPrisao;
    }

    public void incrementTentativasPrisao() {
        this.tentativasPrisao++;
    }

    public boolean isIsencaoFiancaUsada() {
        return isencaoFiancaUsada;
    }

    public void usarIsencaoFianca() {
        this.isencaoFiancaUsada = true;
    }

    public boolean isFalido() {
        return falido;
    }

    public void setFalido(boolean falido) {
        this.falido = falido;
    }

    public double getPatrimonioTotal() {
        double valorPropriedades = 0;
        for (int i = 0; i < propriedades.size(); i++) {
            valorPropriedades += propriedades.get(i).getValorCompra();
        }
        return saldo + valorPropriedades;
    }

    @Override
    public int compareTo(Player outro) {
        double pat1 = this.getPatrimonioTotal();
        double pat2 = outro.getPatrimonioTotal();
        if (pat1 < pat2) return -1;
        if (pat1 > pat2) return 1;
        return this.nome.compareTo(outro.nome);
    }

    @Override
    public String toString() {
        return String.format("%s (%s) - Saldo: R$ %.2f, Patrimônio: R$ %.2f, Voltas: %d, Falido: %b, Preso: %b",
            nome, personagem.getNome(), saldo, getPatrimonioTotal(), voltas, falido, preso);
    }
}
