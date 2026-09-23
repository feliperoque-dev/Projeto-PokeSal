package com.ucsal.pokesal.pokesals;

import com.ucsal.pokesal.enums.TipoElemental;

/** PokeSal inicial do tipo FOGO (trio 2). */
public class CyndaSal extends PokeSal {

  private static final int HP_BASE = 88;
  private static final int ATK_BASE = 23;
  private static final int DEF_BASE = 15;
  private static final int SPD_BASE = 21;

  /**
   * Constroi o Cyndalsal com os atribuidos definidos.
   */
  public CyndaSal() {
    super("Cyndaquil", TipoElemental.FOGO, HP_BASE, ATK_BASE, DEF_BASE, SPD_BASE);
  }
}
