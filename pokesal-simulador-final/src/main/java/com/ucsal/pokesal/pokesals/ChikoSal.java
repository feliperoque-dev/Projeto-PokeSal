package com.ucsal.pokesal.pokesals;

import com.ucsal.pokesal.enums.TipoElemental;

/** PokeSal inicial do tipo PLANTA (trio 2). */
public class ChikoSal extends PokeSal {

  private static final int HP_BASE = 98;
  private static final int ATK_BASE = 16;
  private static final int DEF_BASE = 21;
  private static final int SPD_BASE = 17;

  /**
   * Constroi o Chikosal com os atribuidos definidos.
   */
  public ChikoSal() {
    super("Chikorita", TipoElemental.PLANTA, HP_BASE, ATK_BASE, DEF_BASE, SPD_BASE);
  }
}
