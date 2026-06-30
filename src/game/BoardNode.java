package game;

public class BoardNode {
    private Casa casa;
    private BoardNode next;
    private BoardNode prev;

    public BoardNode(Casa casa) {
        this.casa = casa;
    }

    public Casa getCasa() {
        return casa;
    }

    public void setCasa(Casa casa) {
        this.casa = casa;
    }

    public BoardNode getNext() {
        return next;
    }

    public void setNext(BoardNode next) {
        this.next = next;
    }

    public BoardNode getPrev() {
        return prev;
    }

    public void setPrev(BoardNode prev) {
        this.prev = prev;
    }
}
