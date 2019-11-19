---------------------------------------
Descrição do Problema:
---------------------------------------

Vocês foram contratados para implementar uma versão adaptada do jogo conhecido como UNO. O "UNO Zoado" funcionará da seguinte maneira:

1) O jogo começa com x jogadores dispostos em uma roda (x>3 e fornecido em tempo de execução);

2) Os jogadores são inseridos na roda pelo nome e da forma como o programador desejar. Um destes é selecionado aleatoriamente para iniciar o jogo, que segue no sentido horário;

3) Um baralho contendo somente as cartas 2, 3, 5, 7 e 9 é fornecido para iniciar o jogo. Assuma um número ilimitado de cartas contendo estes 5 números. O significado de cada uma destas cartas é descrito a seguir:

- Carta Pular (Representada pelo número 2): O próximo jogador é pulado e passa a vez para o seguinte.

- Carta Suspender (Representada pelo número 3): O próximo jogador fica 3 rodadas sem jogar.

- Carta Reverter (Representada pelo número 5): A ordem do jogo é invertida, do sentido horário para o sentido anti-horário e vice-versa. Se o jogo for jogado por duas pessoas, a carta é tratada com o mesmo sentido de pular.

- Carta Eliminar Atual (Representada pelo número 7): O jogador atual é eliminado do jogo.

- Carta Eliminar Anterior (Representada pelo número 9): O jogador que jogou antes do jogador atual é eliminado do jogo.


4) O jogo segue no sentido horário e o primeiro jogador (não necessariamente o primeiro a ser inserido na roda, pois este deve ser sorteado) começa o jogo "tirando" uma carta do baralho: 2, 3, 5, 7 ou 9. Você deve implementar o método tirarCarta() de modo que o usuário possa escolher a carta via entrada de dados (para facilitar os testes). Certifique-se que o usuário não forneça ao programa um valor inválido;

5) Depois que é executado o que a carta tirada pede, passa-se a vez para o próximo jogador da roda, respeitando o sentido atual (sentido horário ou anti-horário);

6) O jogo termina quando só restar apenas 1 jogador, que é considerado o vencedor.


OBSERVAÇÕES:

- Os jogadores devem ser inseridos na roda pelo primeiro nome ou apelido. Não são permitidos nomes repetidos. Certifique-se para que isso ocorra;

- Não é necessário implementar uma interface. O jogo pode funcionar textualmente, mas um feedback deve ser dado em cada ação!

Coloquem a cuca para pensar! De qualquer forma, acredito que todos já devem ter percebido qual estrutura de dados deve ser utilizada!

Beijos em todos e bom trabalho,
Luciene
