package com.ucsal.pokesal.itens;

import com.ucsal.pokesal.pokesals.PokeSal;

/** Restaura uma quantidade maior de HP do PokeSal alvo. */
public class SuperPotion implements Item {

  private static final int CURA_SUPER_POTION = 50;

  @Override
    public void usar(PokeSal alvo) {
    alvo.curar(CURA_SUPER_POTION);
  }

  @Override
    public String getNome() {
    return "Super Potion";
  }
}
