package com.ucsal.pokesal.pokesals;

import com.ucsal.pokesal.enums.StatusEfeito;
import com.ucsal.pokesal.enums.TipoElemental;
import com.ucsal.pokesal.utils.BalanceamentoConstantes;

/**
 * Classe abstrata que representa um PokeSal, com seus atributos base
 * (HP, ATK, DEF, SPD) e seu TipoElemental.
 */
public abstract class PokeSal {

  private final TipoElemental tipo;
  private final int hpMaximo;
  private final String nome;
  private int hpAtual;
  private final int atk;
  private final int def;
  private final int spd;
  private StatusEfeito statusAtivo;

  /**
   * Constroi um PokeSal com os atributos base informados.
   *
   * @param nome nome do PokeSal
   * @param tipo tipo elemental (FOGO, AGUA ou PLANTA)
   * @param hpMaximo pontos de vida maximos
   * @param atk atributo de ataque
   * @param def atributo de defesa
   * @param spd atributo de velocidade (define a iniciativa)
   */
  protected PokeSal(String nome, TipoElemental tipo, int hpMaximo, int atk, int def, int spd) {
    this.nome = nome;
    this.tipo = tipo;
    this.hpMaximo = hpMaximo;
    this.hpAtual = hpMaximo;
    this.atk = atk;
    this.def = def;
    this.spd = spd;
    this.statusAtivo = StatusEfeito.NENHUM;
  }

  public String getNome() {
    return nome;
  }

  public TipoElemental getTipo() {
    return tipo;
  }

  public int getHpMaximo() {
    return hpMaximo;
  }

  public int getHpAtual() {
    return hpAtual;
  }

  public int getDef() {
    return def;
  }

  public int getSpd() {
    return spd;
  }

  public StatusEfeito getStatusAtivo() {
    return statusAtivo;
  }

  public void setStatusAtivo(StatusEfeito statusAtivo) {
    this.statusAtivo = statusAtivo;
  }

  /**
   * Aplica dano ao PokeSal, respeitando o limite de HP.
   *
   * @param dano quantidade de dano a ser aplicada.
   */
  public void receberDano(int dano) {
    this.hpAtual = Math.max(this.hpAtual - dano, BalanceamentoConstantes.HP_MINIMO);
  }

  /**
   * Cura o PokeSal, respeitando o limite de HP maximo.
   *
   * @param cura quantidade de HP a ser restaurada
   */
  public void curar(int cura) {
    this.hpAtual = Math.min(this.hpAtual + cura, this.hpMaximo);
  }

  /**
   * Indica se o PokeSal ainda esta apto a batalhar.
   *
   * @return true se o HP atual for maior que zero
   */
  public boolean estaVivo() {
    return this.hpAtual > BalanceamentoConstantes.HP_MINIMO;
  }

  /**
   * Aplica os efeitos de status ativos ao final do turno
   * nao alterando os valores base permanentemente.
   */
  public void aplicarEfeitoDeStatusNoFimDoTurno() {
    switch (this.statusAtivo) {
      case QUEIMADO:
        receberDano((int) Math.round(this.hpMaximo * 0.06));
        break;
      case ENVENENADO:
        receberDano((int) Math.round(this.hpMaximo * 0.08));
        break;
      case NENHUM:
      default:
        break;
    }
  }

  /**
   * Retorna o ATK efetivo, considerando a penalidades de status.
   *
   * @return valor de ATK apos penalidade de status.
   */
  public int getAtkEfetivo() {
    if (this.statusAtivo == StatusEfeito.QUEIMADO) {
      return (int) Math.round(this.atk * 0.5);
    }
    return this.atk;
  }

}
