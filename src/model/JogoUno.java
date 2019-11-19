package model;

// Lista duplamente encadeada

import java.util.Random;
import java.util.Scanner;

public class JogoUno {
    int quantidadeJogadores, numeroCartas;
    boolean reverso = false;
    Jogador inicial;
    Jogador atual;
    boolean jogando = false;
    int seed = 120;
    Random random;

    public JogoUno(int quantidadeJogadores) {
        inicial = null;
        random = new Random();
        random.setSeed(10);
        inserirJogadores(quantidadeJogadores);

    }
    public JogoUno(int quantidadeJogadores, int seed) {
        inicial = null;
        random = new Random();
        random.setSeed(seed);
        inserirJogadores(quantidadeJogadores);

    }

    public CartaBase darCarta() {
        int sorte = random.nextInt((5 - 1) + 1 ) + 1;
        System.out.println("Número sorteado: "+ sorte);
        switch (sorte) {
            case 0: return new Pular();
            case 1: return new Suspender();
            case 2: return new Reverter();
            case 3: return new EliminarAtual();
            case 4: return new EliminarAnterior();
            default: return new Pular();
        }
    }

    public void inserirJogadores(int quantidade) {
        Scanner leia = new Scanner(System.in);

        for (int i = 0; i < quantidade; i++) {
            System.out.println("Insira o nome do jogador " + (i+1));
            String nome = leia.nextLine();
            Jogador jogador = new Jogador(nome, String.valueOf(random.nextInt()));
            for (int j = 0; j < 5; j++) {
                System.out.println("Inserindo carta " + j);
                jogador.cartas.add(darCarta());
            }
            System.out.println("Exibindo mão");
            System.out.println(jogador.exibirMao());
            inserirJogador(jogador);
        }
    }
    public void jogar() {
	while (jogando) {

	    jogarProximoJogador();
	}
}
    public void inserirJogador(Jogador jogador) {
        System.out.println(jogador.toString());
        if (inicial == null) {
            System.out.println("Primeiro item na lista");
            inicial = jogador;
            inicial.setEsquerda(inicial);
            inicial.setDireita(inicial);
            return;
        }
        // sendo o segundo à entrar
        if (inicial.getEsquerda() == inicial && inicial.getDireita() == inicial) {
            System.out.println("Segundo item na lista");
            inicial.setEsquerda(jogador);
            inicial.setDireita(jogador);
            jogador.setEsquerda(inicial);
            jogador.setDireita(inicial);

            return;
        }
        if (inicial.getEsquerda() != inicial) {
            Jogador temporario = inicial.getEsquerda();
            do {
                System.out.println("Checando se Jogador " + temporario.getNome() + "Está do lado do primeiro(ou seja é o último)" );
                if (temporario.getEsquerda() == inicial) {
                    temporario.setEsquerda(jogador);
                    jogador.setDireita(temporario);
                    jogador.setEsquerda(inicial);
                    System.out.println("Olha que legal, o jogador" + temporario.getNome() + "é o último da rodada");
                    System.out.println("Adicionando " + jogador.getNome());
                    temporario = temporario.getEsquerda();
                }
                temporario = temporario.getEsquerda();
            } while (temporario != inicial);



        }

    }

    public void shuffle() {

    }

    public void jogarProximoJogador() {
        Jogador temporario = atual;
        if (reverso) {
            temporario = temporario.getDireita();
            do {
                if (temporario.getDireita().getStatus() == JogadorStatus.suspenso) {
                    temporario.getDireita().subtractRodadasSemJogar();

                }
                temporario = temporario.getDireita();
            } while (temporario != null);
        } else {
            temporario = temporario.getEsquerda();
            do {
                if (temporario.getEsquerda().getStatus() == JogadorStatus.suspenso) {
                    temporario.getEsquerda().subtractRodadasSemJogar();

                }
                temporario = temporario.getEsquerda();
            } while (temporario != null);
        }
    }
}
