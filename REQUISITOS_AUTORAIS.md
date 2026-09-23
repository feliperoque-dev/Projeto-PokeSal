# Requisitos Autorais

## 1. Dano Crítico
Todo ataque tem **12%** de chance de ser crítico.
No crítico, a defesa do alvo é **reduzida pela metade** no cálculo do dano.

*Código:* `Batalha.executarAtaque()` · constante `CHANCE_CRITICO`

## 2. Terreno Aleatório
No início de cada batalha, um terreno é sorteado:

| Terreno | Efeito |
|---|---|
| Asfalto Quente | +15% de dano para ataques de Fogo |
| Poça de Chuva | +10% de dano para ataques de Água |
| Canteiro Central | PokeSal de Planta recupera 5% do HP máximo no fim da rodada |
| Neutro | Sem efeito |

*Código:* `TipoTerreno.terrenoAleatorio()`

## 3. Habilidade Passiva
- PokeSal de **Fogo** não pode ser **queimado**.
- PokeSal de **Planta** não pode ser **envenenado**.

*Código:* `Batalha.executarAtaque()`
