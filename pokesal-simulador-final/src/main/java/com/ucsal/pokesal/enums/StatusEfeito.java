package com.ucsal.pokesal.enums;

/**
 * Representa os efeitos de status que podem ser aplicados a um PokeSal
 * ao final do turno.
 */
public enum StatusEfeito {

    /** Reduz HP e ATK. */
    QUEIMADO,

    /** Aplica dano progressivo ao final do turno. */
    ENVENENADO,

    /** Nenhum status ativo. */
    NENHUM
}
