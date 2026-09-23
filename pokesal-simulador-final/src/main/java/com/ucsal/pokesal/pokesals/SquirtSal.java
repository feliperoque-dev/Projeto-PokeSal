package com.ucsal.pokesal.pokesals;

import com.ucsal.pokesal.enums.TipoElemental;

/** PokeSal inicial do tipo AGUA (trio 1). */
public class SquirtSal extends PokeSal {

  private static final int HP_BASE = 95;
  private static final int ATK_BASE = 17;
  private static final int DEF_BASE = 22;
  private static final int SPD_BASE = 16;

  /**
   * Constroi o Squirtsal com os atribuidos definidos.
   */
  public SquirtSal() {
    super("Squirtle", TipoElemental.AGUA, HP_BASE, ATK_BASE, DEF_BASE, SPD_BASE);
  }
}
