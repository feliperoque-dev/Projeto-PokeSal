package com.ucsal.pokesal.pokesals;

import com.ucsal.pokesal.enums.TipoElemental;

/** PokeSal inicial do tipo PLANTA (trio 1). */
public class BulbaSal extends PokeSal {

  private static final int HP_BASE = 100;
  private static final int ATK_BASE = 18;
  private static final int DEF_BASE = 20;
  private static final int SPD_BASE = 15;

  /**
   * Constroi o Bulbasal com os atribuidos definidos.
   */
  public BulbaSal() {
    super("Bulbasaur", TipoElemental.PLANTA, HP_BASE, ATK_BASE, DEF_BASE, SPD_BASE);
  }
}
