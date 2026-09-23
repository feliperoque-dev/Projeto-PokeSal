# Declaração de Uso de IA

## 1. Início da implementação (Fase 01)

**Prompt:**
> como posso realizar (dar inicio) a implementação desse projeto? Fase 1 apenas

**Resposta obtida:**

Sim. Focando somente na Fase 01, a implementação do código pode ser iniciada depois da análise e da modelagem. A Fase 01 exige código Java funcional, boas práticas e Checkstyle, além dos requisitos autorais e UML.

Como o próprio trabalho proíbe a geração de código-fonte por IA, não vou te entregar o código pronto para copiar. Mas posso te mostrar exatamente como estruturar e implementar você mesmo, inclusive a ordem das classes e o que cada uma deve fazer.

#### 1. O que você precisa implementar na Fase 01

Pelo enunciado, o núcleo do sistema é:

```
Treinador
   ↓
1 Pokésal
   ↓
HP / ATK / DEF / SPD / Tipo

Batalha
   ↓
Turnos
   ↓
Velocidade
   ↓
Dano
   ↓
Vantagem elemental
   ↓
Terreno
   ↓
Status
   ↓
Itens
```

O projeto define seis Pokésal iniciais e os atributos HP, ATK, DEF, SPD e TipoElemental.

#### 2. Comece pelo projeto Java

No IntelliJ, por exemplo:

```
New Project
→ Java
→ Maven
→ JDK 17 ou superior
```

Eu sugiro Maven porque depois vocês precisarão trabalhar com JUnit.

Estrutura:

```
pokesal/
│
├── pom.xml
│
└── src/
    ├── main/
    │   └── java/
    │       └── br/
    │           └── edu/
    │               └── pokesal/
    │
    └── test/
        └── java/
            └── br/
                └── edu/
                    └── pokesal/
```

#### 3. Primeira classe: TipoElemental

Como existem somente três tipos (`FOGO`, `AGUA`, `PLANTA`), esse conceito é um bom candidato para ser um enum.

No seu código você deve pensar em algo conceitualmente semelhante a:

```java
enum TipoElemental {
    FOGO,
    AGUA,
    PLANTA
}
```

Isso evita ficar trabalhando com `"fogo"`, `"agua"`, `"planta"` espalhados pelo programa.

#### 4. Depois crie o Pokésal

Essa será uma das principais classes. Ela precisa representar:

```
Pokésal
├── nome
├── hp
├── hpMaximo
├── atk
├── def
├── spd
└── tipoElemental
```

Por exemplo, conceitualmente:

```
BulbaSal
HP   = ?
ATK  = ?
DEF  = ?
SPD  = ?
Tipo = PLANTA
```

⚠️ O documento não fornece os valores de HP, ATK, DEF e SPD dos seis Pokésal. Portanto, não invente esses valores como se fossem especificados pelo professor. Essa é uma informação que vocês precisam definir/documentar como decisão do projeto.

#### 5. Como representar os seis Pokésal

Vocês têm:

```
BulbaSal
CharSal
SquirtSal

ChikoSal
CyndaSal
TotoSal
```

O enunciado determina que o treinador deve escolher apenas um deles.

Uma possibilidade que vocês podem discutir no UML é utilizar uma classe/estrutura responsável por criar os Pokésal disponíveis. Por exemplo:

```
PokésalFactory
       │
       ├── cria BulbaSal
       ├── cria CharSal
       ├── cria SquirtSal
       ├── cria ChikoSal
       ├── cria CyndaSal
       └── cria TotoSal
```

Isso evita colocar todos os valores dentro do Main.

#### 6. Treinador

Depois crie o conceito de treinador. Ele precisa possuir um único Pokésal. Conceitualmente:

```
Treinador
├── nome
├── pokesal
└── itensUsados
```

A regra é:

```
Treinador
    ↓
1 Pokésal
```

e não:

```
Treinador
    ↓
6 Pokésal
```

#### 7. Terreno

O projeto possui efeitos específicos do estacionamento da UCSal:

- Asfalto Quente
- Poça de Chuva / Piso Escorregadio
- Canteiro Central

Uma representação possível para vocês avaliarem no UML é:

```java
enum Terreno {
    ASFALTO_QUENTE,
    POCA_CHUVA,
    CANTEIRO_CENTRAL
}
```

Mas aqui existe uma questão: o efeito "Asfalto Quente" só acontece durante o dia. O enunciado não especifica como o sistema determina dia/noite. Isso deve ser registrado na análise de requisitos antes da implementação.

#### 8. Cálculo da vantagem elemental

Essa é uma das partes principais. A tabela do professor é:

| Ataque | Defesa | Multiplicador |
|---|---|---|
| Fogo | Planta | 2.0 |
| Fogo | Água | 0.5 |
| Água | Fogo | 2.0 |
| Água | Planta | 0.5 |
| Planta | Água | 2.0 |
| Planta | Fogo | 0.5 |

Você precisa transformar isso em uma função/método responsável por descobrir o multiplicador. Conceitualmente:

```
calcularMultiplicador(
    tipoAtacante,
    tipoDefensor
)
        ↓
    retorna 2.0
    ou 0.5
    ou valor definido pela equipe
```

O ponto importante é não espalhar `if` de vantagem elemental por várias classes. Centralize essa regra.

#### 9. Dano

Aqui você precisa ter cuidado. O enunciado informa HP, ATK, DEF e os multiplicadores elementais, mas não apresenta uma fórmula matemática específica para cálculo do dano. Portanto, a equipe precisa decidir e documentar uma fórmula.

Por exemplo, vocês podem discutir algo conceitualmente como:

```
danoBase
    ↓
considera ATK
    ↓
considera DEF
    ↓
multiplicador elemental
    ↓
efeito do terreno
    ↓
dano final
```

Não copie uma fórmula pronta de Pokémon. Definam a fórmula do simulador de vocês e coloquem essa decisão na análise.

#### 10. Efeito do terreno no dano

Depois de calcular o dano elemental, vocês precisam considerar o terreno. Por exemplo:

```
Ataque Fogo
       ↓
vantagem elemental
       ↓
terreno = Asfalto Quente
       ↓
+15%
```

O documento determina que ataques de Fogo recebem aumento de 15% nesse terreno. Da mesma forma:

```
Planta + Canteiro Central
        ↓
final do turno
        ↓
recupera 5% HP máximo
```

#### 11. Batalha

Depois de possuir Pokésal, Treinador, TipoElemental e Terreno, você pode criar a classe que representa a batalha. Conceitualmente:

```
Batalha
├── treinador1
├── treinador2
├── terreno
└── turno
```

Ela deve controlar o fluxo:

```
iniciar batalha
      ↓
determinar ordem
      ↓
jogador 1 ataca
      ↓
verificar derrota
      ↓
jogador 2 ataca
      ↓
verificar derrota
      ↓
aplicar efeitos do final do turno
      ↓
próximo turno
```

#### 12. Velocidade

O professor foi explícito: a ordem de ataque é determinada estritamente pelo SPD. Então você pode criar uma responsabilidade específica, `determinarPrimeiroAtacante()`, que compara o SPD de A e de B. Por exemplo:

```
A = 80
B = 60

A → primeiro
B → segundo
```

Mas novamente existe uma questão que o enunciado não define:

```
A = 80
B = 80
```

A equipe precisa decidir o desempate.

#### 13. Status

Depois você implementa os efeitos de status. O documento menciona Queimado, Envenenado e Paralisado, com os respectivos efeitos. Uma estrutura conceitual poderia ser:

```
Status
├── aplicar()
└── efeitoFinalTurno()
```

O importante é que o status não fique implementado diretamente dentro de Main.

#### 14. Itens

A regra é: máximo = 2 itens/batalha, e usar item consome o turno. Você precisa controlar `itensUsados` e impedir:

```
1º item → permitido
2º item → permitido
3º item → proibido
```

O próprio enunciado prevê que isso deverá resultar em exceção no teste da Fase 02.

#### 15. E o Main?

Eu deixaria o Main somente para iniciar o programa. Algo conceitualmente assim:

```
Main
 │
 └── inicia o simulador
       │
       └── cria batalha
             │
             └── executa batalha
```

Evite colocar:

```
Main
├── cálculo de dano
├── vantagem elemental
├── status
├── terreno
├── itens
├── HP
├── turnos
└── tudo mais
```

Isso vai contra a ideia de código organizado e dificultará a manutenção/testes.

#### 16. Ordem prática de implementação

Se você abrir o IntelliJ agora, eu faria nesta sequência:

1. TipoElemental
2. Pokésal
3. Criador dos Pokésal
4. Treinador
5. Terreno
6. Regra de vantagem elemental
7. Cálculo de dano
8. Efeito do terreno
9. Status
10. Itens
11. Batalha
12. Main

E somente depois vocês começam a refinar Checkstyle/Javadoc.

#### 17. O que eu faria agora no seu lugar

Para não tentar implementar tudo de uma vez, faça primeiro somente estas quatro coisas:

```
src/main/java/
│
├── TipoElemental.java
│
├── Pokesal.java
│
├── Treinador.java
│
└── Main.java
```

Faça essas classes funcionarem primeiro. Depois partimos para: Terreno → vantagem elemental → dano → batalha. Isso deixa a implementação muito mais controlada.

**Importante:** como a Fase 01 também exige os 3 requisitos autorais e os diagramas UML, não implemente essas partes com base em um código/diagrama que eu gere. O documento deixa explícito que geração de código e criação de UML por IA são proibidas.

Se você quiser seguir de forma prática, o próximo passo é você criar TipoElemental, Pokesal, Treinador e Main no IntelliJ. Aí você pode me mandar o seu código e eu posso te ajudar a identificar erros sintáticos apontados pelo compilador, que é um dos usos de IA expressamente permitido pelo trabalho.

---

## 2. Modelagem UML

**Prompt:**
> exemplo de como seria a modelagem UML desse projeto

**Resposta obtida:**

Sim. Posso te mostrar como pensar a modelagem UML, mas há uma restrição importante neste projeto: o próprio enunciado diz que a criação de diagramas UML com IA é proibida.

Então, para não fazer o diagrama que vocês devem entregar, vou te passar um modelo conceitual de estudo, mostrando quais elementos analisar e como vocês podem montar o UML por conta própria.

#### 1. Comece pelo Diagrama de Casos de Uso

Primeiro identifique os atores e as ações. No projeto, o ator principal é o **Treinador**. E algumas ações que aparecem diretamente no enunciado são:

```
Treinador
   │
   ├── Selecionar Pokésal
   ├── Iniciar batalha
   ├── Realizar ação no turno
   ├── Usar item
   └── Participar da batalha
```

A partir daí, vocês precisam analisar quais dessas ações realmente devem ser casos de uso independentes. Por exemplo, pense: *"O treinador seleciona o Pokésal antes da batalha."* Isso pode virar um caso de uso: **[Selecionar Pokésal]**.

O requisito determina que ele deve escolher exatamente um entre os seis Pokésal disponíveis.

#### 2. Depois pense no fluxo da batalha

O caso de uso principal provavelmente será algo relacionado à **[Batalhar]**. Dentro desse processo existem várias regras:

```
Batalhar
   │
   ├── determinar ordem por SPD
   ├── realizar ataque
   ├── calcular dano
   ├── aplicar vantagem elemental
   ├── aplicar efeito do terreno
   ├── aplicar status
   └── finalizar turno
```

Atenção: isso não significa automaticamente que cada item precisa ser um caso de uso no UML. Vocês precisam decidir isso na modelagem.

#### 3. Diagrama de Classes

Aqui está a parte mais importante para vocês estudarem. Pegue os substantivos do enunciado: Treinador, Pokésal, TipoElemental, Batalha, Terreno, Item, Status. Esses são candidatos naturais a classes/enums.

O raciocínio inicial pode ser:

```
                 Treinador
                     │
                     │ possui
                     ▼
                  Pokésal
                     │
              ┌──────┴──────┐
              │             │
             HP           Tipo
                           │
                           ▼
                   TipoElemental
```

Isso não é o diagrama para entregar, mas representa o raciocínio que vocês devem transformar no UML.

#### 4. A classe Pokésal

O enunciado diz que cada Pokésal possui HP, ATK, DEF, SPD e TipoElemental. Portanto, no UML vocês devem discutir algo como:

```
┌─────────────────────────┐
│        Pokésal          │
├─────────────────────────┤
│ - nome                  │
│ - hp                    │
│ - atk                   │
│ - def                   │
│ - spd                   │
│ - tipoElemental         │
├─────────────────────────┤
│ + ...                   │
└─────────────────────────┘
```

Os `+` e `-` representam visibilidade UML:

- `+` público
- `-` privado
- `#` protegido

Os métodos concretos devem ser definidos por vocês conforme a implementação.

#### 5. TipoElemental

Como o projeto trabalha somente com Fogo, Água e Planta, vocês podem avaliar a utilização de um enum:

```
┌──────────────────────┐
│   TipoElemental      │
│      <<enum>>        │
├──────────────────────┤
│ FOGO                 │
│ AGUA                 │
│ PLANTA               │
└──────────────────────┘
```

A regra de vantagens também precisa ser representada na implementação. O documento estabelece:

```
Fogo → Planta = 2.0
Fogo → Água   = 0.5

Água → Fogo   = 2.0
Água → Planta = 0.5

Planta → Água = 2.0
Planta → Fogo = 0.5
```

#### 6. Relação entre Treinador e Pokésal

Essa é uma relação importante para vocês decidirem. O requisito diz: o treinador deve escolher apenas 1 Pokésal inicial. Então conceitualmente:

```
┌──────────────┐
│  Treinador   │
└──────┬───────┘
       │
       │ 1
       │
       ▼
┌──────────────┐
│   Pokésal    │
└──────────────┘
```

No UML, essa relação pode ser representada com multiplicidade 1.

#### 7. Batalha

A batalha precisa envolver os dois treinadores. Pense:

```
Treinador 1
     │
     │
     ▼
  Batalha
     ▲
     │
     │
Treinador 2
```

Então vocês precisam decidir no diagrama:

- uma batalha possui dois treinadores?
- a batalha possui um terreno?
- a batalha controla o turno?
- quem é responsável pelo cálculo de dano?

Essas decisões são justamente parte da modelagem.

#### 8. Terreno

O enunciado apresenta três situações: Asfalto Quente, Poça de Chuva / Piso Escorregadio e Canteiro Central. Vocês podem avaliar um enum:

```
┌───────────────────────┐
│       Terreno         │
│        <<enum>>       │
├───────────────────────┤
│ ASFALTO_QUENTE        │
│ POCA_CHUVA            │
│ CANTEIRO_CENTRAL      │
└───────────────────────┘
```

Depois relacioná-lo à batalha.

#### 9. Item

O sistema possui itens de batalha e o treinador pode usar no máximo dois por batalha. Então vocês precisam decidir como representar isso. Por exemplo, conceitualmente:

```
Treinador
    │
    │ possui
    ▼
  Item
```

E a própria classe Treinador ou Batalha pode controlar a quantidade de itens utilizados. Essa decisão deve ser coerente com o código que vocês realmente implementarem.

#### 10. Status

O projeto menciona Queimado, Envenenado e Paralisado e define efeitos para eles. Vocês precisam decidir se Status será um enum, uma classe abstrata ou outra estrutura.

Não escolham isso apenas porque "fica bonito no UML". A escolha deve fazer sentido com a implementação.

#### 11. Um possível mapa para vocês montarem

Para facilitar o trabalho no Draw.io, Lucidchart, StarUML etc., pensem inicialmente nesses blocos:

```
                    ┌──────────────┐
                    │  Treinador   │
                    └──────┬───────┘
                           │
                           │ possui 1
                           ▼
                    ┌──────────────┐
                    │   Pokésal    │
                    └──────┬───────┘
                           │
              ┌────────────┼────────────┐
              ▼            ▼            ▼
             HP           ATK          DEF
                           │
                           ▼
                         SPD

                    TipoElemental
                           │
                     FOGO/ÁGUA/PLANTA


       ┌──────────────┐
       │   Batalha    │
       └──────┬───────┘
              │
       ┌──────┴───────┐
       ▼              ▼
  Treinador 1    Treinador 2
              │
              ▼
           Terreno

              │
              ▼
            Itens

              │
              ▼
            Status
```

Esse mapa é apenas um roteiro de modelagem, não um UML pronto para entrega.

#### 12. O ponto mais importante

Não tentem colocar todas as regras dentro das classes. Por exemplo, a regra *"Fogo causa dano ×2 contra Planta"* não significa necessariamente que a classe Pokésal deve possuir um método enorme com todas as combinações.

Vocês precisam decidir uma responsabilidade adequada para essa regra. O mesmo vale para:

- cálculo de dano;
- efeito do terreno;
- ordem por SPD;
- efeitos de status;
- limite de itens.

Isso será especialmente importante porque a Fase 01 exige código organizado e boas práticas, incluindo ausência de magic numbers, Javadoc e organização das classes.

#### Para vocês fazerem agora

Eu recomendo montar primeiro esta sequência no papel/quadro, sem código:

1. Quais são os atores?
2. Quais são os casos de uso?
3. Quais são as entidades do sistema?
4. Quais atributos cada entidade possui?
5. Quais relações existem?
6. Quem é responsável por cada regra?
7. Só então implementar em Java

E lembrem que a Fase 01 exige tanto o Diagrama de Casos de Uso quanto o Diagrama de Classes.

Se vocês montarem uma primeira versão do UML por conta própria e me enviarem uma imagem, eu posso te ajudar a revisar se as relações, multiplicidades e responsabilidades estão coerentes com o enunciado, sem criar o diagrama por vocês.

---

## 3. Sistema de dano dos jogos Pokémon

**Prompt:**
> como funciona o sistema de dano dos jogos de pokemon? qual o calculo de ataque e defesa

**Resposta obtida:**

O dano nos jogos principais de Pokémon é calculado usando uma fórmula matemática que envolve o nível do atacante, o poder do golpe, os atributos de ataque e defesa, e vários multiplicadores de tipo e sorte.

No cálculo de dano oficial dos jogos, a base da equação usa a seguinte estrutura fundamental: (Link)

$$Dano=\left(\left(\frac{\frac{2\times Nível}{5}+2\times Poder\times \frac{Ataque}{Defesa}}{50}\right)+2\right)\times Modificadores$$

**Como funcionam as variáveis principais**

- **Nível:** o nível atual do Pokémon que está atacando (varia de 1 a 100). Níveis mais altos aumentam bastante o dano causado.
- **Poder do Golpe (Power):** é o valor de força base que vem escrito na descrição do movimento (por exemplo, Scratch tem poder 40, enquanto Hyper Beam tem 150).
- **Ataque e Defesa:**
  - Se o golpe for físico, usa-se o Ataque (Attack) do seu Pokémon e a Defesa (Defense) do oponente.
  - Se o golpe for especial (como rajadas de energia ou água), usa-se o Ataque Especial (Sp. Atk) e a Defesa Especial (Sp. Def) do oponente. (YouTube · O Duelista Pokémon)

**Os Modificadores (Multiplicadores)**

O resultado da conta base é multiplicado por vários fatores que podem aumentar ou diminuir o dano final:

1. **STAB (Same Type Attack Bonus):** se o Pokémon usa um golpe do mesmo tipo que o dele (ex: um tipo Fogo usando Flamethrower), o dano recebe um bônus de 1.5x.
2. **Efetividade de Tipo:** se o golpe for super efetivo contra o tipo do defensor, multiplica por 2x (ou até 4x se tiver dupla fraqueza). Se for pouco efetivo, multiplica por 0.5x (ou 0.25x), e se for imune, o dano cai para 0. (Link)
3. **Critical Hit (Crítico):** se o golpe acertar um acerto crítico, o dano é multiplicado por 1.5x (gerações recentes) e ignora reduções de ataque do usuário ou aumentos de defesa do alvo.
4. **Random (Fator Aleatório):** o jogo multiplica o valor final por um número aleatório entre 0.85 e 1.00 para dar uma pequena variação a cada turno.
5. **Itens e Habilidades:** itens como Life Orb ou habilidades específicas do Pokémon também entram como multiplicações finais.

---

## 4. Erro do Checkstyle no Eclipse

**Prompt:**
> Explique esse erro no checkstyle do eclipe Multiple markers at this line - Line breakpoint:Main [line: 263] - executarTurno(Treinador, Treinador, Batalha, Scanner) - A distância entre a declaração da variável 'statusAntes' e o seu primeiro uso é 4. O máximo permitido é 3. Considere tornar essa variável final se você ainda precisa armazenar o seu valor com antecedência (antes que chamadas de métodos que possam causar efeitos colaterais sobre o seu valor original).

**Resposta obtida:**

Esse erro é do Checkstyle, e na verdade a mensagem está apontando duas coisas diferentes na mesma linha.

#### 1. O que significa a primeira mensagem

```
Line breakpoint: Main [line: 263] - executarTurno(...)
```

Isso não é erro do Checkstyle. É apenas um breakpoint do Eclipse que você colocou na linha 263 para depuração. Se aparecer uma bolinha/ponto azul na margem do código, significa que o Eclipse vai pausar a execução nessa linha quando o método for executado.

#### 2. O erro real do Checkstyle

A parte importante é:

> A distância entre a declaração da variável 'statusAntes' e o seu primeiro uso é 4. O máximo permitido é 3.

Isso significa que você provavelmente tem algo parecido com:

```java
String statusAntes = ...;

// alguma coisa
// outra coisa
// outra coisa
// outra coisa

if (...) {
    ...
}
```

O Checkstyle está contando a quantidade de linhas/comandos entre a declaração `String statusAntes = ...;` e o primeiro momento em que você realmente usa `statusAntes`. No seu projeto, a regra permite uma distância máxima de 3.

#### 3. Por que ele reclama?

Essa regra existe para evitar variáveis declaradas muito antes de serem utilizadas. Imagine:

```java
String statusAntes = treinador.getStatus();

verificarVelocidade();

aplicarTerreno();

calcularDano();

if (statusAntes.equals("QUEIMADO")) {
    ...
}
```

Você declarou `statusAntes` no começo, mas só utilizou várias linhas depois. O Checkstyle entende que isso pode prejudicar a leitura do código.

#### 4. O que a mensagem está sugerindo

Ela diz: *"Considere tornar essa variável final se você ainda precisa armazenar o seu valor com antecedência..."*

Ou seja, existe uma segunda possibilidade. Se você precisa realmente capturar o valor antes de executar outros métodos, pode declarar a variável como `final`. Por exemplo:

```java
final String statusAntes = treinador.getStatus();
```

Isso informa que `statusAntes` não será reatribuída.

**Mas atenção:** não coloque `final` simplesmente para "calar" o Checkstyle. Primeiro veja por que você precisa declarar `statusAntes` tão cedo.

#### 5. A solução mais simples

Se não existe motivo para guardar o valor antecipadamente, normalmente você pode simplesmente aproximar a declaração do primeiro uso. Em vez de:

```java
String statusAntes = treinador.getStatus();

verificarAlgumaCoisa();

fazerOutraCoisa();

if (statusAntes.equals(...)) {
    ...
}
```

organize:

```java
verificarAlgumaCoisa();

fazerOutraCoisa();

String statusAntes = treinador.getStatus();

if (statusAntes.equals(...)) {
    ...
}
```

Assim a declaração fica próxima do uso.

#### 6. No seu caso específico

O erro informa:

```
Main [line: 263]
executarTurno(Treinador, Treinador, Batalha, Scanner)

variável: statusAntes
distância: 4
permitido: 3
```

Então procure dentro do método `executarTurno(...)` pela declaração de `statusAntes`. Provavelmente você encontrará algo como `String statusAntes = ...;`. Depois conte até onde `statusAntes` aparece pela primeira vez. Se estiver algo como:

```
String statusAntes = ...;    // declaração

linha
linha
linha
linha

if (statusAntes...)           // primeiro uso
```

o Checkstyle está reclamando exatamente disso.

#### Resumindo

| Mensagem | O que significa |
|---|---|
| Line breakpoint | É um breakpoint do Eclipse, não um erro |
| statusAntes | Variável declarada muito antes do uso |
| Máximo permitido | 3 linhas/comandos |
| Encontrado | Distância de 4 |
| Solução comum | Aproximar declaração do primeiro uso |
| Alternativa | `final`, se realmente precisar capturar o valor antecipadamente |

Se você colar aqui o método `executarTurno()`, especialmente a região em torno da linha 263, eu consigo apontar exatamente qual trecho está causando o Checkstyle e por que, sem precisar alterar sua lógica.

---

## 5. Documentação dos requisitos autorais

**Prompt:**
> estou documentando alguns requisitos que adicionei no codigo, o primeiro é o sistema de dano critico, o segundo é a geração do terreno aleatória e o terceiro é a habilidade passiva dos pokesals (pokesal tipo fogo nao sofre efeito queimadura, assim como planta nao sofre envenenamento), criei um md para isso, quero que documente da maneira mais simples e sucinta possivel

**Resposta obtida:**

A IA escreveu o arquivo `REQUISITOS_AUTORAIS.md`, descrevendo os 3 requisitos a partir do código já implementado pela equipe. A implementação dos requisitos não foi feita pela IA.

---

## 6. Formatação deste arquivo

**Prompt:**
> formate o arquivo IA_DECLARATION para que tenha um padrao no formato da documentação, isso é meramente para melhorar a visualização, inclua inclusive esse comando agora, o output obviamente sera o resultado do arquivo

**Resposta obtida:**

A IA reorganizou o `IA_DECLARATION.md` no formato atual (títulos, prompts em citação e respostas formatadas), sem alterar o conteúdo das respostas anteriores.
