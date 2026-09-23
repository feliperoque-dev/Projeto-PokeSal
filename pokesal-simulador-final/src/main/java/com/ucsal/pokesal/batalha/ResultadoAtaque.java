package com.ucsal.pokesal.batalha;

/**
 * DTO (Data Transfer Object) com o resultado de um ataque: dano final
 * causado e o multiplicador de efetividade elemental aplicado.
 */
public class ResultadoAtaque {

  private final boolean critico;
  private final int danoCausado;
  private final double multiplicadorEfetividade;

  /**
   * Controi o resultado do ataque com o dano causado e o multiplicador de efetividade.
   *
   * @param critico se o ataque foi critico
   * @param danoCausado valor do dano final
   * @param multiplicadorEfetividade multiplicador de dano (ex:x0.5, x2.0)
   */
  public ResultadoAtaque(boolean critico, int danoCausado, double multiplicadorEfetividade) {
    this.critico = critico;
    this.danoCausado = danoCausado;
    this.multiplicadorEfetividade = multiplicadorEfetividade;
  }

  /**
   * Retorna o valor de critico (true/false).
   *
   * @return true se o ataque foi critico
   */
  public boolean isCritico() {
    return critico;
  }

  public int getDanoCausado() {
    return danoCausado;
  }

  public double getMultiplicadorEfetividade() {
    return multiplicadorEfetividade;
  }
}
