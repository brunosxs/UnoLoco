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
        inserirJogadores(quantidadeJogadores);
        shuffle();

    }
    public JogoUno(int quantidadeJogadores, int seed) {
        inicial = null;
        random = new Random();
        random.setSeed(seed);
        inserirJogadores(quantidadeJogadores);
        shuffle();
    }

    public void verJogadores() {
        System.out.println("Sentido horário " + !reverso );
        Jogador temporario = getNextPlayerRelativeTo(inicial);
        System.out.println(inicial.getNome() + " à esquerda " + inicial.getEsquerda().getNome() + " à direita " + inicial.getDireita().getNome() );
        System.out.println("INICIO DO LOOP");
        do {
            System.out.print(temporario.getNome() + " à esquerda " + temporario.getEsquerda().getNome() + " à direita " + temporario.getDireita().getNome() );
            temporario = getNextPlayerRelativeTo(temporario);
            System.out.println("");
        } while (temporario != inicial);
    }

    private CartaBase darCarta() {
        int sorte = random.nextInt((5 - 1) + 1 ) + 1;
        switch (sorte) {
            case 0: return new Pular();
            case 1: return new Suspender();
            case 2: return new Reverter();
            case 3: return new EliminarAtual();
            case 4: return new EliminarAnterior();
            default: return new Pular();
        }
    }

    private void inserirJogadores(int quantidade) {
        System.out.println(quantidade + " serão inseridos");
        Scanner leia = new Scanner(System.in);
        for (int i = 0; i < quantidade; i++) {
            System.out.println("Insira o nome do jogador");
            String nome = leia.nextLine();
            Jogador jogador = new Jogador(nome, String.valueOf(random.nextInt()));
            inserirJogador(jogador);
            if(atual == null) {
                atual = jogador;
            }
        }
    }

    private void darCartas(int quantity, Jogador jogador) {
        for (int j = 0; j < quantity; j++) {
            jogador.cartas.add(darCarta());
        }
    }
    public void jogar() {
        jogando = true;
        int vez = 0;
	    while (jogando) {
	        verJogadores();
	        vez++;
	        System.out.println("Rodando por "+ vez);
	        CartaBase c = executarVez(atual);
	        EfeitoCarta efeito = c.efeito;
	        System.out.println(efeito);
	        if (efeito == EfeitoCarta.PULAR) {
                System.out.println("Jogador "+ getNextPlayerRelativeTo(atual).getNome() + " foi pulado!");
                jogarProximoJogador();
            } else if(efeito == EfeitoCarta.SUSPENDER) {
                Jogador j = getNextPlayerRelativeTo(atual);
                j.setStatus(JogadorStatus.suspenso);
                j.setRodadasSemJogar(j.getRodadasSemJogar() + 3);
                System.out.println("Jogador " + j.getNome() + " foi suspenso por " +j.getRodadasSemJogar() + " rodadas" );
            } else if (efeito == EfeitoCarta.REVERTER) {
                reverso = !reverso;
                String sentido = reverso? "antihorário": "horário";
                System.out.println();
                System.out.println("Revertendo a ordem para o sentido "+ sentido+ ", próximo jogador é "+ getNextPlayerRelativeTo(atual));
            } else if (efeito == EfeitoCarta.ELIMINAR_ANTERIOR) {
                System.out.println(" Rodando eliminar anterior");
                System.out.println("Eliminando o anterior de " + atual.getNome());
                System.out.println(getPreviousPlayerRelativeTo(atual).getNome());
                removerJogador(getPreviousPlayerRelativeTo(atual));
            } else if (efeito == EfeitoCarta.ELIMINAR_ATUAL) {
                System.out.println(" Rodando eliminar ATUAL");
                removerJogador(atual);
            }

	        jogarProximoJogador();
	}
}
    private void inserirJogador(Jogador jogador) {
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
                System.out.println("Ao fim foi adicionado " + jogador.getNome());
                jogador.setEsquerda(inicial);
                jogador.setDireita(inicial.getDireita());
                inicial.setDireita(jogador);
                jogador.getDireita().setEsquerda(jogador);
        }

    }

    private void removerJogador(Jogador jogador) {
        Jogador novoInicial, anterior;
        if (jogador == inicial) {
             novoInicial = getNextPlayerRelativeTo(inicial);
             anterior = getPreviousPlayerRelativeTo(inicial);
             inicial = novoInicial;
             if (reverso) {
                 novoInicial.setEsquerda(anterior);
                 anterior.setDireita(novoInicial);
             } else {
                 novoInicial.setDireita(anterior);
                 anterior.setEsquerda(novoInicial);
             }
             return;
        } else {
            novoInicial = getNextPlayerRelativeTo(jogador);
            anterior = getPreviousPlayerRelativeTo(jogador);
            if (reverso) {
                novoInicial.setEsquerda(anterior);
                anterior.setDireita(novoInicial);
            } else {
                novoInicial.setDireita(anterior);
                anterior.setEsquerda(novoInicial);
            }
        }
        String message = "Jogador " + jogador.getNome() + " foi eliminado!";
        message = jogador.getStatus() == JogadorStatus.suspenso? message + " E ainda estava suspenso o otário!": message;
        System.out.println(message);
    }

    private int countPlayers() {
        int count = 0;
        Jogador proximo = getNextPlayerRelativeTo(atual);
        do {
            count++;
            proximo = getNextPlayerRelativeTo(proximo);
        } while (proximo != atual);
        return count;
    }
    public void shuffle() {
        int sorte = random.nextInt((countPlayers() - 1) + 1 ) + 1;
        Jogador temporario = atual;
        for(int i = 0; i < sorte+1; i++) {
            temporario = getNextPlayerRelativeTo(temporario);
        }
        atual = temporario;
    }

    private Jogador getNextPlayerRelativeTo(Jogador jogadorRelativo) {
        if (reverso) {
            return jogadorRelativo.getDireita();
        } else {
            return jogadorRelativo.getEsquerda();
        }
    }
    private Jogador getPreviousPlayerRelativeTo(Jogador jogadorRelativo) {
        if (reverso) {
            return jogadorRelativo.getEsquerda();
        } else {
            return jogadorRelativo.getDireita();
        }
    }

    private boolean isLastPlayer() {
        return inicial.getEsquerda() == inicial.getDireita() && inicial == inicial.getEsquerda();
    }

    private void jogarProximoJogador() {
        if (isLastPlayer()) {
            jogando = false;
            System.out.println("Jogador "+ inicial.getNome() + "venceu!");
            return;
        }

        Jogador temporario = atual;
            temporario = atual;
            atual = getNextPlayerRelativeTo(atual);
             while (temporario != atual && atual.getStatus() == JogadorStatus.suspenso) {
                System.out.println(temporario.getNome() + " é o próximo depois do " + atual.getNome());
                if (atual.getStatus() == JogadorStatus.suspenso) {
                    atual.subtractRodadasSemJogar();
                    System.out.println(atual.getNome() + " está suspenso por mais "+ atual.getRodadasSemJogar());
                }
                atual = getNextPlayerRelativeTo(atual);
            }
    }
    private CartaBase executarVez(Jogador jogador) {
        CartaBase carta = null;
        Scanner leia = new Scanner(System.in);
        if (jogador.getCartas().size() == 0) {
            System.out.println("Jogador "+ jogador.getNome() + " não possui cartas, a mesa te dará mais 5");
            darCartas(5, jogador);
        }
        do {
            System.out.println(jogador.exibirMao());
            System.out.println(jogador.getNome() + ", é a sua vez! ");
            System.out.println("Selecione uma carta válida");
            System.out.println("["+ 1 + " - "+ jogador.getCartas().size());
            carta = jogador.jogarCarta(getInt() -1);
        } while (carta == null);
        return carta;
    }

    private int getInt() {
        Scanner leia = new Scanner(System.in);
        String result = leia.nextLine();
        int r = -1;
        try {
            r = Integer.parseInt(result);
        } catch (Exception e) {
            r = -1;
        }
        return  r;
    }
}
