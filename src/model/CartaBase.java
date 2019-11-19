package model;

public abstract class CartaBase {
    CartaBase proxima;
    EfeitoCarta efeito;
    String ilustra;
    public CartaBase(EfeitoCarta efeito, String ilustra) {
        this.efeito = efeito;
        this.ilustra = ilustra;
    }
    public EfeitoCarta acao() {
        return efeito;
    }
    public String getIlustra() {
        return this.ilustra;
    }
}
