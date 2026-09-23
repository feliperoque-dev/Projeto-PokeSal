package com.ucsal.pokesal.batalha;

import com.ucsal.pokesal.enums.StatusEfeito;
import com.ucsal.pokesal.enums.TipoElemental;
import com.ucsal.pokesal.enums.TipoTerreno;
import com.ucsal.pokesal.pokesals.PokeSal;
import com.ucsal.pokesal.utils.BalanceamentoConstantes;
import java.util.List;
import java.util.Random;

/**
 * Motor de batalha do simulador PokeSal. Responsavel por calcular a
 * ordem de iniciativa (por SPD), a matriz de vantagens elementais, os
 * efeitos de terreno do Estacionamento da UCSal Pituacu e a aplicacao
 * de dano entre dois PokeSal.
 */
public class Batalha {

  private final Treinador treinadorA;
  private final Treinador treinadorB;
  private final TipoTerreno terreno;
  private final Random random = new Random();

  /**
   * Cria uma instancia de batalha entre os treinadores.
   * Seleciona de forma aleátoria qual será o terreno da batalha.
   *
   * @param treinadorA primeiro treinador
   * @param treinadorB segundo treinador
   */
  public Batalha(Treinador treinadorA, Treinador treinadorB) {
    this(treinadorA, treinadorB, TipoTerreno.terrenoAleatorio());
  }

  /**
   * Cria uma instancia de batalha entre os treinadores em um terreno
   * especifico (util para testes com resultado deterministico).
   *
   * @param treinadorA primeiro treinador
   * @param treinadorB segundo treinador
   * @param terreno terreno da batalha
   */
  public Batalha(Treinador treinadorA, Treinador treinadorB, TipoTerreno terreno) {
    this.treinadorA = treinadorA;
    this.treinadorB = treinadorB;
    this.terreno = terreno;
  }

  public TipoTerreno getTerreno() {
    return terreno;
  }

  /**
   * Define a ordem de ataque do turno com base no atributo SPD
   * (Velocidade) efetivo de cada PokeSal, do maior para o menor.
   *
   * @return lista de treinadores na ordem em que devem agir no turno
   */
  public List<Treinador> definirOrdemDeIniciativa() {
    int spdA = treinadorA.getPokeSalAtivo().getSpd();
    int spdB = treinadorB.getPokeSalAtivo().getSpd();

    return spdB > spdA
        ? List.of(treinadorB, treinadorA)
        : List.of(treinadorA, treinadorB);
  }

  /**
   * Calcula o multiplicador de efetividade elemental do atacante contra
   * o defensor, de acordo com a matriz Fogo/Agua/Planta.
   *
   * @param atacante tipo elemental de quem ataca
   * @param defensor tipo elemental de quem defende
   * @return multiplicador de dano (2.0 super efetivo, 0.5 pouco efetivo, 1.0 neutro)
   */
  public double calcularMultiplicadorElemental(
      TipoElemental atacante, TipoElemental defensor) {

    boolean superEfetivo =
        (atacante == TipoElemental.FOGO && defensor == TipoElemental.PLANTA)
        || (atacante == TipoElemental.AGUA && defensor == TipoElemental.FOGO)
        || (atacante == TipoElemental.PLANTA && defensor == TipoElemental.AGUA);

    boolean poucoEfetivo =
        (atacante == TipoElemental.FOGO && defensor == TipoElemental.AGUA)
        || (atacante == TipoElemental.AGUA && defensor == TipoElemental.PLANTA)
        || (atacante == TipoElemental.PLANTA && defensor == TipoElemental.FOGO);

    if (superEfetivo) {
      return BalanceamentoConstantes.SUPER_EFETIVO;
    }

    if (poucoEfetivo) {
      return BalanceamentoConstantes.POUCO_EFETIVO;
    }

    return BalanceamentoConstantes.NEUTRO;
  }

  /**
   * Executa um ataque de um PokeSal atacante contra um PokeSal
   * defensor, aplicando a matriz elemental e o efeito de terreno
   * vigente, e efetivamente reduz o HP do defensor.
   *
   * @param atacante PokeSal que ataca
   * @param defensor PokeSal que recebe o ataque
   * @return resultado do ataque (dano causado e multiplicador aplicado)
   */
  public ResultadoAtaque executarAtaque(PokeSal atacante, PokeSal defensor) {
    double multiplicadorElemental =
        calcularMultiplicadorElemental(
            atacante.getTipo(), defensor.getTipo());

    boolean critico = random.nextDouble() < BalanceamentoConstantes.CHANCE_CRITICO;

    double defesa = critico ? defensor.getDef() / 2.0 : defensor.getDef();

    double danoBase = Math.max(
        BalanceamentoConstantes.PODER_BASE_ATAQUE * (atacante.getAtkEfetivo() / defesa),
        BalanceamentoConstantes.PODER_BASE_ATAQUE);

    double danoComElemento = danoBase * multiplicadorElemental;
    double danoComTerreno =
        aplicarBonusDeTerreno(danoComElemento, atacante.getTipo());

    int danoFinal = (int) Math.round(danoComTerreno);
    defensor.receberDano(danoFinal);

    if (atacante.getTipo() == TipoElemental.FOGO
            && defensor.getTipo() != TipoElemental.FOGO
            && random.nextDouble() < BalanceamentoConstantes.CHANCE_QUEIMADURA) {
      defensor.setStatusAtivo(StatusEfeito.QUEIMADO);
    }

    if (atacante.getTipo() == TipoElemental.PLANTA
            && defensor.getTipo() != TipoElemental.PLANTA
            && random.nextDouble() < BalanceamentoConstantes.CHANCE_ENVENENAMENTO) {
      defensor.setStatusAtivo(StatusEfeito.ENVENENADO);
    }
    return new ResultadoAtaque(critico, danoFinal, multiplicadorElemental);
  }

  /**
   * Aplica o bonus de dano proveniente do efeito de terreno vigente,
   * de acordo com o tipo elemental de quem ataca.
   *
   * @param danoBase dano antes do bonus de terreno
   * @param tipoAtacante tipo elemental de quem ataca
   * @return dano apos o bonus de terreno (quando aplicavel)
   */
  public double aplicarBonusDeTerreno(
      double danoBase, TipoElemental tipoAtacante) {

    if (terreno == TipoTerreno.ASFALTO_QUENTE
        && tipoAtacante == TipoElemental.FOGO) {
      return danoBase
          * (1 + BalanceamentoConstantes.BONUS_ASFALTO_QUENTE);
    }

    if (terreno == TipoTerreno.POCA_DE_CHUVA
        && tipoAtacante == TipoElemental.AGUA) {
      return danoBase
          * (1 + BalanceamentoConstantes.BONUS_POCA_DE_CHUVA);
    }

    return danoBase;
  }

  /**
   * Aplica a cura de Canteiro Central ao final do turno para PokeSal
   * do tipo PLANTA que ainda estejam vivos.
   *
   * @param pokeSal PokeSal a ser avaliado para a cura de terreno
   */
  public void aplicarEfeitoDeTerrenoNoFimDoTurno(PokeSal pokeSal) {
    if (terreno == TipoTerreno.CANTEIRO_CENTRAL
        && pokeSal.estaVivo()
        && pokeSal.getTipo() == TipoElemental.PLANTA) {

      int cura = (int) Math.round(
          pokeSal.getHpMaximo()
              * BalanceamentoConstantes.CURA_CANTEIRO_CENTRAL);

      pokeSal.curar(cura);
    }
  }
}
