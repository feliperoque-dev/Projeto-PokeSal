package com.ucsal.pokesal.itens;

import com.ucsal.pokesal.enums.StatusEfeito;
import com.ucsal.pokesal.pokesals.PokeSal;

/** Remove qualquer status de efeito ativo no PokeSal alvo. */
public class Antidote implements Item {

  @Override
    public void usar(PokeSal alvo) {
    alvo.setStatusAtivo(StatusEfeito.NENHUM);
  }

  @Override
    public String getNome() {
    return "Antidote";
  }
}
