package com.ucsal.pokesal.utils;

/**
 * Centraliza as constantes numericas de balanceamento do jogo, evitando o
 * uso de "Magic Numbers" espalhados pelo codigo, conforme exigido nas boas
 * praticas de implementacao (Fase 01).
 */
public final class BalanceamentoConstantes {

  /** Multiplicador de dano quando o ataque e super efetivo. */
  public static final double SUPER_EFETIVO = 2.0;

  /** Multiplicador de dano quando o ataque e pouco efetivo. */
  public static final double POUCO_EFETIVO = 0.5;

  /** Multiplicador de dano neutro (sem vantagem/desvantagem). */
  public static final double NEUTRO = 1.0;

  /** Bonus de dano de golpes de FOGO sob efeito de Asfalto Quente. */
  public static final double BONUS_ASFALTO_QUENTE = 0.15;

  /** Bonus de dano de golpes de AGUA sob efeito de Poca de Chuva. */
  public static final double BONUS_POCA_DE_CHUVA = 0.10;

  /** Percentual de HP maximo recuperado por PokeSal PLANTA no Canteiro Central. */
  public static final double CURA_CANTEIRO_CENTRAL = 0.05;

  /** Numero maximo de itens que um treinador pode carregar por batalha. */
  public static final int MAXIMO_ITENS_MOCHILA = 2;

  /** HP minimo permitido (limite inferior de boundary values). */
  public static final int HP_MINIMO = 0;

  /** Chance de aplicar dano crítico. */
  public static final double CHANCE_CRITICO = 0.12;

  /** Poder base do ataque do Pokesal. */
  public static final int PODER_BASE_ATAQUE = 10;

  /** Chance de aplicar um efeito de queimadura de golpes de FOGO. */
  public static final double CHANCE_QUEIMADURA = 0.10;

  /** Chance de aplicar um efeito de envenenamento de golpes de PLANTA. */
  public static final double CHANCE_ENVENENAMENTO = 0.30;

  private BalanceamentoConstantes() {
        // Classe utilitaria: nao deve ser instanciada.
  }
}
