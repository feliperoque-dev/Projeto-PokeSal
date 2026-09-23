package com.ucsal.pokesal.itens;

import com.ucsal.pokesal.pokesals.PokeSal;

/** Restaura uma quantidade fixa de HP do PokeSal alvo. */
public class Potion implements Item {

  private static final int CURA_POTION = 20;

  @Override
    public void usar(PokeSal alvo) {
    alvo.curar(CURA_POTION);
  }

  @Override
    public String getNome() {
    return "Potion";
  }
}
