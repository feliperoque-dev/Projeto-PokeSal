package com.ucsal.pokesal.itens;

import com.ucsal.pokesal.utils.BalanceamentoConstantes;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa a mochila de um treinador, limitada a
 * {@link BalanceamentoConstantes#MAXIMO_ITENS_MOCHILA} itens por batalha.
 */
public class Mochila {

  private final List<Item> itens = new ArrayList<>();

  /**
   * Adiciona um item a mochila.
   *
   * @param item item a ser adicionado
   * @throws IllegalStateException se a mochila ja estiver no limite de itens
   */
  public void adicionarItem(Item item) {
    if (this.itens.size() >= BalanceamentoConstantes.MAXIMO_ITENS_MOCHILA) {
      throw new IllegalStateException("A mochila comporta no maximo "
          + BalanceamentoConstantes.MAXIMO_ITENS_MOCHILA + " itens.");
    }
    this.itens.add(item);
  }

  public List<Item> getItens() {
    return itens;
  }

  /**
   * Remove um item da mochila.
   *
   * @param itemSelecionado item é removido
   */
  public void remove(Item itemSelecionado) {
    itens.remove(itemSelecionado);
  }
}
