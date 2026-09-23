package com.ucsal.pokesal.itens;

import com.ucsal.pokesal.pokesals.PokeSal;

/**
 * Contrato para itens de batalha.
 *  Usar um item consome o turno do treinador.
 */
public interface Item {

  /**
   * Aplica o efeito do item ao PokeSal alvo.
   *
   * @param alvo PokeSal que recebera o efeito do item
   */
  void usar(PokeSal alvo);

  /**
   * Retorna o nome  do item.
   *
   * @return nome de exibicao do item
   */
  String getNome();
}
