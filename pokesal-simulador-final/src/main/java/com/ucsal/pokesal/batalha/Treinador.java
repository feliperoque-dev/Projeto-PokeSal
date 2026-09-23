package com.ucsal.pokesal.batalha;

import com.ucsal.pokesal.itens.Mochila;
import com.ucsal.pokesal.pokesals.PokeSal;

/**
 * Representa um treinador PokeSal participante do torneio, com seu
 * PokeSal ativo e sua mochila de itens.
 */
public class Treinador {

  private final String nome;
  private final PokeSal pokeSalAtivo;
  private final Mochila mochila;

  /**
   * Contrói um treinador com atributos definidos.
   *
   * @param nome nome do treinador
   * @param pokeSalAtivo Verifica se o Pokesal está ativo
   * @param mochila Armazena itens na mochila
   */
  public Treinador(String nome, PokeSal pokeSalAtivo, Mochila mochila) {
    this.nome = nome;
    this.pokeSalAtivo = pokeSalAtivo;
    this.mochila = mochila;
  }

  public String getNome() {
    return nome;
  }

  public PokeSal getPokeSalAtivo() {
    return pokeSalAtivo;
  }

  public Mochila getMochila() {
    return mochila;
  }
}
