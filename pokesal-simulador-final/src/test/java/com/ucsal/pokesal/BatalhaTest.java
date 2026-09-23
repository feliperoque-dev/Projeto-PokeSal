package com.ucsal.pokesal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ucsal.pokesal.batalha.Batalha;
import com.ucsal.pokesal.batalha.Treinador;
import com.ucsal.pokesal.enums.TipoElemental;
import com.ucsal.pokesal.enums.TipoTerreno;
import com.ucsal.pokesal.itens.Antidote;
import com.ucsal.pokesal.itens.Mochila;
import com.ucsal.pokesal.itens.Potion;
import com.ucsal.pokesal.itens.SuperPotion;
import com.ucsal.pokesal.pokesals.CharSal;
import com.ucsal.pokesal.pokesals.PokeSal;
import com.ucsal.pokesal.pokesals.SquirtSal;
import java.util.List;
import org.junit.jupiter.api.Test;

class BatalhaTest {

  /** 1. Validacao dos multiplicadores de dano da matriz elemental. */
  @Test
  void testVantagemElemental() {
    Batalha batalha = new Batalha(null, null);

    assertEquals(2.0, batalha.calcularMultiplicadorElemental(
        TipoElemental.FOGO, TipoElemental.PLANTA));
    assertEquals(0.5, batalha.calcularMultiplicadorElemental(
        TipoElemental.FOGO, TipoElemental.AGUA));
    assertEquals(1.0, batalha.calcularMultiplicadorElemental(
        TipoElemental.FOGO, TipoElemental.FOGO));
  }

  /** 2. Validacao do impacto do terreno (Asfalto Quente / Canteiro Central). */
  @Test
  void testEfeitoTerrenoEstacionamentoUcsal() {
    Batalha batalhaComTerreno = new Batalha(null, null, TipoTerreno.ASFALTO_QUENTE);
    Batalha batalhaSemTerreno = new Batalha(null, null, TipoTerreno.NEUTRO);

    double danoComBonus =
        batalhaComTerreno.aplicarBonusDeTerreno(100.0, TipoElemental.FOGO);
    double danoSemBonus =
        batalhaSemTerreno.aplicarBonusDeTerreno(100.0, TipoElemental.FOGO);

    assertTrue(danoComBonus > danoSemBonus);
  }

  /** 3. Validacao da ordem de ataque pelo atributo SPD (iniciativa). */
  @Test
  void testOrdemDeAtaquePorVelocidade() {
    PokeSal rapido = new CharSal(); // SPD 20
    PokeSal lento = new SquirtSal(); // SPD 16

    Treinador treinadorRapido =
        new Treinador("T1", rapido, new Mochila());
    Treinador treinadorLento =
        new Treinador("T2", lento, new Mochila());

    Batalha batalha =
        new Batalha(treinadorRapido, treinadorLento);
    List<Treinador> ordem = batalha.definirOrdemDeIniciativa();

    assertEquals("T1", ordem.get(0).getNome());
  }

  /** 4. Deve lancar excecao ao tentar exceder o limite de itens da mochila. */
  @Test
  void testUsoLimiteDeItensExcedido() {
    Mochila mochila = new Mochila();
    mochila.adicionarItem(new Potion());
    mochila.adicionarItem(new Antidote());

    assertThrows(IllegalStateException.class,
        () -> mochila.adicionarItem(new SuperPotion()));
  }

  /** 5. Validacao de valores limite (boundary values) de HP, ATK e DEF. */
  @Test
  void testCalculoDanoBoundaryValues() {
    PokeSal pokeSal = new CharSal();

    // HP nunca deve ficar negativo mesmo com dano excessivo.
    pokeSal.receberDano(999999);
    assertEquals(0, pokeSal.getHpAtual());
    assertFalse(pokeSal.estaVivo());

    // Cura nunca deve ultrapassar o HP maximo.
    pokeSal.curar(999999);
    assertEquals(pokeSal.getHpMaximo(), pokeSal.getHpAtual());
  }
}