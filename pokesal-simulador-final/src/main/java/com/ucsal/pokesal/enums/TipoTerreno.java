package com.ucsal.pokesal.enums;

import java.util.Random;

/**
 * Representa os efeitos de terreno do Estacionamento da UCSal Pituacu.
 */
public enum TipoTerreno {

    /** Aumenta o dano de golpes do tipo FOGO em 15%. */
    ASFALTO_QUENTE,

    /** Aumenta o dano de golpes do tipo AGUA em 10%. */
    POCA_DE_CHUVA,

    /** PokeSal do tipo PLANTA recuperam 5% do HP maximo ao final do turno. */
    CANTEIRO_CENTRAL,

    /** Nenhum efeito especial de terreno. */
    NEUTRO;

  private static final Random RANDOM = new Random();

  /** Sortear um terreno para batalha. */
  public static TipoTerreno terrenoAleatorio() {
    TipoTerreno[] terreno = values();
    return terreno[RANDOM.nextInt(terreno.length)];
  }
}
