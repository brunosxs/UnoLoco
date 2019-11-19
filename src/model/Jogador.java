package model;

import java.util.ArrayList;

public class Jogador {
    String nome, cor;
    JogadorStatus status;
    int rodadasSemJogar = 0;
    Jogador esquerda;
    Jogador direita;
    ArrayList<CartaBase> cartas;

    public Jogador(String nome, String cor) {
        this.nome = nome;
        this.cor = cor;
        this.status = JogadorStatus.emJogo;
        this.cartas = new ArrayList<CartaBase>();
    }
    public Jogador(String nome, String cor, JogadorStatus status) {
        this.nome = nome;
        this.cor = cor;
        this.status = status;
    }

    public void setCartas(ArrayList<CartaBase> cartas) {
        this.cartas = cartas;
    }

    public ArrayList<CartaBase> getCartas() {
        return this.cartas;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public JogadorStatus getStatus() {
        return status;
    }

    public void setStatus(JogadorStatus status) {
        this.status = status;
    }

    public int getRodadasSemJogar() {
        return rodadasSemJogar;
    }

    public void setRodadasSemJogar(int rodadasSemJogar) {
        this.rodadasSemJogar = rodadasSemJogar;
    }

    public void subtractRodadasSemJogar() {
        if (rodadasSemJogar > 0) {
            this.rodadasSemJogar-=1;
        }
        if (rodadasSemJogar == 0) {
            this.status = JogadorStatus.emJogo;
        }
    }

    public Jogador getEsquerda() {
        return esquerda;
    }

    public void setEsquerda(Jogador esquerda) {
        this.esquerda = esquerda;
    }

    public Jogador getDireita() {
        return direita;
    }

    public void setDireita(Jogador direita) {
        this.direita = direita;
    }

    @Override
    public String toString() {
        return "Nome: "+ nome + " cor: "+ cor;
    }

    public CartaBase jogarCarta(int id) {
        System.out.println(" valor provido pra escolha de carta foi "+ id);
        CartaBase returnedCard;
        try {
            returnedCard = cartas.get(id);
            cartas.remove(id);
        } catch (Exception e) {
            returnedCard = null;
        }
        return returnedCard;
    }

    public String exibirMao() {
        String retornar ="\n";
        int lineSize = 40;
        int lines = 28;
        int offset = 0;
        int currentLine = 0;
        while(currentLine < lines) {
            String linhaFinal = "";
            for(CartaBase carta : cartas) {
                String linha = carta.getIlustra().substring(offset, offset + lineSize);
                linha = linha.replaceFirst("\n", "");
                linhaFinal = linhaFinal + linha;
            }
            currentLine ++;
            offset = lineSize*currentLine;
            retornar = retornar + linhaFinal + "\n";
        }
        return retornar;
    }
}
