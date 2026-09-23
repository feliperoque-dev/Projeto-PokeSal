package com.ucsal.pokesal.pokesals;

import com.ucsal.pokesal.enums.TipoElemental;

/** PokeSal inicial do tipo AGUA (trio 2). */
public class TotoSal extends PokeSal {

  private static final int HP_BASE = 97;
  private static final int ATK_BASE = 19;
  private static final int DEF_BASE = 19;
  private static final int SPD_BASE = 15;

  /**
   * Constroi o Totosal com os atribuidos definidos.
   */
  public TotoSal() {
    super("Totodile", TipoElemental.AGUA, HP_BASE, ATK_BASE, DEF_BASE, SPD_BASE);
  }
}
