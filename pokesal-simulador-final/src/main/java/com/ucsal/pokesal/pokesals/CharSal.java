package com.ucsal.pokesal.pokesals;

import com.ucsal.pokesal.enums.TipoElemental;

/** PokeSal inicial do tipo FOGO (trio 1). */
public class CharSal extends PokeSal {

  private static final int HP_BASE = 90;
  private static final int ATK_BASE = 22;
  private static final int DEF_BASE = 16;
  private static final int SPD_BASE = 20;

  /**
   * Constroi o Charsal com os atribuidos definidos.
   */
  public CharSal() {
    super("Charmander", TipoElemental.FOGO, HP_BASE, ATK_BASE, DEF_BASE, SPD_BASE);
  }
}
