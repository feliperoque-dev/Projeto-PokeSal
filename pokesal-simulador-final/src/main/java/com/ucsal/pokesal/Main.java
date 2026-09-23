package com.ucsal.pokesal;

import com.ucsal.pokesal.batalha.Batalha;
import com.ucsal.pokesal.batalha.ResultadoAtaque;
import com.ucsal.pokesal.batalha.Treinador;
import com.ucsal.pokesal.enums.StatusEfeito;
import com.ucsal.pokesal.itens.Antidote;
import com.ucsal.pokesal.itens.Item;
import com.ucsal.pokesal.itens.Mochila;
import com.ucsal.pokesal.itens.Potion;
import com.ucsal.pokesal.itens.SuperPotion;
import com.ucsal.pokesal.pokesals.BulbaSal;
import com.ucsal.pokesal.pokesals.CharSal;
import com.ucsal.pokesal.pokesals.ChikoSal;
import com.ucsal.pokesal.pokesals.CyndaSal;
import com.ucsal.pokesal.pokesals.PokeSal;
import com.ucsal.pokesal.pokesals.SquirtSal;
import com.ucsal.pokesal.pokesals.TotoSal;
import java.util.List;
import java.util.Scanner;

/**
 * Classe de demonstracao: executa uma batalha completa ate a derrota
 * de um dos PokeSals para validar as regras de negocio implementadas.
 */
public final class Main {

  private Main() {
  }

  /**
   * Contrói um Menu para iniciar o combate.
   *
   * @param args parâmetro args
   */
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    String resposta = "Sim";

    do {
      Treinador treinadorA = criarTreinador(sc, "A");
      Treinador treinadorB = criarTreinador(sc, "B");

      Batalha batalha = new Batalha(treinadorA, treinadorB);

      System.out.println("======INÍCIO DA BATALHA POKESAL======");
      System.out.println("Terreno sorteado: " + batalha.getTerreno());

      List<Treinador> ordem = batalha.definirOrdemDeIniciativa();
      System.out.println("Ordem de iniciativa: " + ordem.get(0).getNome()
          + " -> " + ordem.get(1).getNome());

      Treinador primeiro = ordem.get(0);
      Treinador segundo = ordem.get(1);

      int rodada = 1;

      // Laço que mantém a batalha viva até que um dos PokeSals chegue a 0 HP
      while (primeiro.getPokeSalAtivo().estaVivo()
          && segundo.getPokeSalAtivo().estaVivo()) {
        System.out.println("\n=== Rodada " + rodada + " ===");

        // Turno do Primeiro
        boolean defensorDerrotado = executarTurno(primeiro, segundo, batalha, sc);
        if (defensorDerrotado) {
          anunciarVencedor(primeiro, segundo);
          break;
        }

        // Turno do Segundo
        defensorDerrotado = executarTurno(segundo, primeiro, batalha, sc);
        if (defensorDerrotado) {
          anunciarVencedor(segundo, primeiro);
          break;
        }

        // Fim da rodada: dano de status (queimadura/veneno) e efeito de terreno
        System.out.println();
        aplicarEfeitosDeFimDeTurno(primeiro, batalha);
        aplicarEfeitosDeFimDeTurno(segundo, batalha);

        boolean primeiroVivo = primeiro.getPokeSalAtivo().estaVivo();
        boolean segundoVivo = segundo.getPokeSalAtivo().estaVivo();
        if (!primeiroVivo && !segundoVivo) {
          System.out.println("\nOs dois PokeSals foram derrotados! A batalha terminou empatada.");
        } else if (!segundoVivo) {
          anunciarVencedor(primeiro, segundo);
        } else if (!primeiroVivo) {
          anunciarVencedor(segundo, primeiro);
        }

        rodada++;
      }

      // Pergunta se deseja jogar novamente
      System.out.println("\nDeseja realizar outra batalha? (Sim/Não)");
      resposta = sc.nextLine();

    } while (resposta.equalsIgnoreCase("Sim") || resposta.equalsIgnoreCase("S"));

    System.out.println("Obrigado por jogar PokeSal!");
    sc.close();
  }

  /**
   * Pergunta o nome do treinador, o PokeSal e os itens da mochila.
   *
   * @param sc leitor da entrada do usuario
   * @param letra identificacao do treinador (A ou B)
   * @return treinador pronto para a batalha
   */
  private static Treinador criarTreinador(Scanner sc, String letra) {
    System.out.println("\nQual sera o nome do treinador " + letra + "?");
    String nome = sc.nextLine();
    return new Treinador(nome, escolherPokeSal(sc, nome), escolherMochila(sc, nome));
  }

  /**
   * Mostra o menu de PokeSals ate o treinador escolher uma opcao valida.
   *
   * @param sc leitor da entrada do usuario
   * @param nomeTreinador nome exibido na pergunta
   * @return PokeSal escolhido
   */
  private static PokeSal escolherPokeSal(Scanner sc, String nomeTreinador) {
    while (true) {
      System.out.println("Qual PokeSal o " + nomeTreinador + " escolhe?");
      System.out.println("1 - CharSal");
      System.out.println("2 - SquirtSal");
      System.out.println("3 - BulbaSal");
      System.out.println("4 - CyndaSal");
      System.out.println("5 - ChikoSal");
      System.out.println("6 - TotoSal");

      switch (lerOpcao(sc)) {
        case 1:
          return new CharSal();
        case 2:
          return new SquirtSal();
        case 3:
          return new BulbaSal();
        case 4:
          return new CyndaSal();
        case 5:
          return new ChikoSal();
        case 6:
          return new TotoSal();
        default:
          System.out.println("Opção inválida! Escolha um número de 1 a 6.\n");
      }
    }
  }

  /**
   * Mostra o menu de itens ate o treinador escolher uma opcao valida.
   *
   * @param sc leitor da entrada do usuario
   * @param nomeTreinador nome exibido na pergunta
   * @return mochila com os itens escolhidos
   */
  private static Mochila escolherMochila(Scanner sc, String nomeTreinador) {
    while (true) {
      System.out.println("Quais itens " + nomeTreinador + " deseja levar para a batalha?");
      System.out.println("1 - Potion, Super Potion");
      System.out.println("2 - Potion, Antidote");
      System.out.println("3 - Super Potion, Antidote");

      Mochila mochila = new Mochila();
      switch (lerOpcao(sc)) {
        case 1:
          mochila.adicionarItem(new Potion());
          mochila.adicionarItem(new SuperPotion());
          return mochila;
        case 2:
          mochila.adicionarItem(new Potion());
          mochila.adicionarItem(new Antidote());
          return mochila;
        case 3:
          mochila.adicionarItem(new SuperPotion());
          mochila.adicionarItem(new Antidote());
          return mochila;
        default:
          System.out.println("Opção inválida! Escolha de 1 a 3.\n");
      }
    }
  }

  /**
   * Le uma opcao numerica digitada pelo usuario.
   *
   * @param sc leitor da entrada do usuario
   * @return numero digitado, ou -1 se o texto nao for um numero
   */
  private static int lerOpcao(Scanner sc) {
    try {
      return Integer.parseInt(sc.nextLine().trim());
    } catch (NumberFormatException e) {
      return -1;
    }
  }

  /**
   * Exibe a mensagem de fim de batalha.
   *
   * @param vencedor treinador que venceu
   * @param perdedor treinador cujo PokeSal foi derrotado
   */
  private static void anunciarVencedor(Treinador vencedor, Treinador perdedor) {
    System.out.println("\n" + perdedor.getPokeSalAtivo().getNome() + " foi derrotado!");
    System.out.println(vencedor.getNome() + " venceu a batalha!");
  }

  /**
   * Aplica os efeitos de fim de turno (status e terreno) ao PokeSal do
   * treinador, informando o que aconteceu.
   *
   * @param treinador treinador cujo PokeSal sera afetado
   * @param batalha batalha em andamento (define o terreno)
   */
  private static void aplicarEfeitosDeFimDeTurno(Treinador treinador, Batalha batalha) {
    PokeSal pokeSal = treinador.getPokeSalAtivo();

    int hpAntes = pokeSal.getHpAtual();
    pokeSal.aplicarEfeitoDeStatusNoFimDoTurno();
    if (pokeSal.getHpAtual() < hpAntes) {
      System.out.println(pokeSal.getNome() + " sofreu " + (hpAntes - pokeSal.getHpAtual())
          + " de dano por estar " + pokeSal.getStatusAtivo() + "!");
    }

    hpAntes = pokeSal.getHpAtual();
    batalha.aplicarEfeitoDeTerrenoNoFimDoTurno(pokeSal);
    if (pokeSal.getHpAtual() > hpAntes) {
      System.out.println(pokeSal.getNome() + " recuperou " + (pokeSal.getHpAtual() - hpAntes)
          + " de HP no Canteiro Central!");
    }
  }

  /**
   * Executa a escolha de ação de um treinador durante o seu turno.
   *
   * @return true se o Pokémon alvo/defensor foi derrotado, false caso contrário.
   */
  private static boolean executarTurno(Treinador atacante, Treinador defensor,
      Batalha batalha, Scanner sc) {
    String status = atacante.getPokeSalAtivo().getStatusAtivo() == StatusEfeito.NENHUM
        ? "" : " " + atacante.getPokeSalAtivo().getStatusAtivo();
    System.out.println("\nTurno de " + atacante.getNome() + " ["
        + atacante.getPokeSalAtivo().getNome() + " HP: "
        + atacante.getPokeSalAtivo().getHpAtual() + "/"
        + atacante.getPokeSalAtivo().getHpMaximo() + status + "]");

    boolean acaoConcluida = false;

    while (!acaoConcluida) {
      System.out.println("Escolha sua ação:");
      System.out.println("1 - Atacar");
      System.out.println("2 - Usar Item");

      int escolhaAcao = lerOpcao(sc);

      switch (escolhaAcao) {
        case 1:
          final StatusEfeito statusAntes = defensor.getPokeSalAtivo().getStatusAtivo();
          ResultadoAtaque resultado = batalha.executarAtaque(
              atacante.getPokeSalAtivo(), defensor.getPokeSalAtivo());

          System.out.println("\n" + atacante.getPokeSalAtivo().getNome()
              + " (" + atacante.getNome() + ") ataca "
              + defensor.getPokeSalAtivo().getNome()
              + " (" + defensor.getNome() + ")");

          String tipoDano = resultado.isCritico() ? "Dano crítico: " : "Dano causado: ";
          System.out.println(tipoDano + resultado.getDanoCausado()
              + " (Efetividade: " + resultado.getMultiplicadorEfetividade() + "x)");

          System.out.println(defensor.getPokeSalAtivo().getNome()
              + " HP restante: " + defensor.getPokeSalAtivo().getHpAtual() + "/"
              + defensor.getPokeSalAtivo().getHpMaximo());

          StatusEfeito statusDepois = defensor.getPokeSalAtivo().getStatusAtivo();
          if (statusDepois != statusAntes) {
            System.out.println(defensor.getPokeSalAtivo().getNome()
                + " agora está " + statusDepois + "!");
          }

          acaoConcluida = true;
          break;

        case 2:
          Mochila mochila = atacante.getMochila();
          List<Item> itens = mochila.getItens();

          if (itens.isEmpty()) {
            System.out.println("A mochila de " + atacante.getNome()
                + " está vazia! Escolha outra ação.\n");
            break;
          }

          System.out.println("\nItens disponíveis:");
          for (int i = 0; i < itens.size(); i++) {
            System.out.println((i + 1) + " - "
                + itens.get(i).getNome());
          }

          System.out.println("0 - Voltar");

          int escolhaItem = lerOpcao(sc);

          if (escolhaItem == 0) {
            break;
          }

          if (escolhaItem > 0 && escolhaItem <= itens.size()) {
            Item itemSelecionado = itens.get(escolhaItem - 1);

            itemSelecionado.usar(atacante.getPokeSalAtivo());
            mochila.remove(itemSelecionado);

            System.out.println("\n" + atacante.getNome() + " usou "
                + itemSelecionado.getNome() + " em "
                + atacante.getPokeSalAtivo().getNome() + "!");

            System.out.println("HP do " + atacante.getPokeSalAtivo().getNome()
                + ": " + atacante.getPokeSalAtivo().getHpAtual() + "/"
                + atacante.getPokeSalAtivo().getHpMaximo());

            acaoConcluida = true;
          } else {
            System.out.println("Opção de item inválida!\n");
          }
          break;

        default:
          System.out.println(
              "Opção inválida! Escolha 1 para Atacar ou 2 para Usar Item.\n");
          break;
      }
    }

    return !defensor.getPokeSalAtivo().estaVivo();
  }
}
