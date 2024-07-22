
# Ganho de Capital

## Decisões Técnicas e Arquiteturais

O projeto foi desenvolvido em Java, uma linguagem orientada a objetos, robusta e amplamente utilizada no mercado. A escolha do Java se deu pela experiencia que tenho nessa liguagem, além da vasta documentação e uma comunidade bem ativa.
A arquitetura do projeto foi estruturada de forma a separar as responsabilidades de cada componente. Utilizamos o padrão de projeto Service para encapsular a lógica de negócios, mantendo a classe Main enxuta e focada na leitura de entradas e impressão de saídas.

## Uso de Frameworks e Bibliotecas

O projeto utiliza o Maven como ferramenta de gerenciamento de dependências e construção do projeto. O Maven é uma ferramenta padrão na indústria Java e facilita a gestão de dependências e o processo de build.
Para os testes, foi utilizado o JUnit, uma das bibliotecas mais populares para testes unitários em Java. O JUnit permite escrever testes de maneira simples e eficiente, garantindo a qualidade do código.

## Como Compilar e Executar o Projeto

Para compilar o projeto, você precisa ter o Java e o Maven instalados em seu sistema. Com essas ferramentas instaladas, navegue até a pasta raiz do projeto e execute o seguinte comando:

```bash
mvn clean compile assembly:single
```
Isso irá compilar o projeto e gerar um arquivo JAR executável na pasta target.  


Para executar o projeto e executar e ir digitando o json no console, use o seguinte comando:
```
java -jar target/ganho-capital-1.0-SNAPSHOT-jar-with-dependencies.jar
```

Para executar o projeto e executar e passar o json via arquivo para o console, use o seguinte comando:
```
java -jar target/ganho-capital-1.0-SNAPSHOT-jar-with-dependencies.jar < arquivo.json
```

## Como Executar os Testes
Para executar os testes, use o seguinte comando na pasta raiz do projeto:

```
mvn test
```

## Regras de Negócio

* Devo pagar 20% de imposto sobre o lucro obtido na operação de venda, cujo preço é superior ao preço médio ponderado de compra.

* Quando eu comprar ações devo recalcular o preço médio ponderado
Por exemplo, se você comprou 10 ações por R$ 20,00, vendeu 5, depois comprou outras 5 por R$ 10,00, a média ponderada é:
nova-media-ponderada = ((quantidade-de-acoes-atual * media-ponderadatual) + (quantidade-de-acoes * valor-de-compra)) / (quantidade-de-acoes-atual + quantidade-acoes-compradas)
nova-media-ponderada = ((5 x 20.00) + (5 x 10.00)) / (5 + 5) = 15.00

* Você deve usar o prejuízo passado para deduzir múltiplos lucros futuros, até que todo prejuízo seja deduzido.

* Prejuízos acontecem quando você vende ações a um valor menor do que o preço médio ponderado de compra.
Neste caso, nenhum imposto deve ser pago e você deve subtrair o prejuízo dos lucros seguintes, antes de calcular o imposto. -INTEGRADO

* Você não paga nenhum imposto se o valor total da operação (custo unitário da ação x quantidade) for menor ou igual a R$ 20000,00. Use o valor total da operação e não o lucro obtido para
determinar se o imposto deve ou não ser pago. E não se esqueça de deduzir o prejuízo dos lucros seguintes.

* Nenhum imposto é pago em operações de compra.

* Você pode assumir que nenhuma operação vai vender mais ações do que você tem naquele momento.


### Exemplo de Entrada

* **operation** = Se a operação é uma operação de compra ( buy ) ou venda ( sell )
* **unit-cost** = Preço unitário da ação em uma moeda com duas casas decimais
* **quantity** = Quantidade de ações negociada


```
[{"operation":"buy", "unit-cost":10.00, "quantity": 10000},
{"operation":"sell", "unit-cost":20.00, "quantity": 5000}]
```

### Exemplo de Saída

* **tax** = O valor do imposto pago em uma operação

```
[{"tax":0}, {"tax":10000}]
```

## Notas Adicionais
O projeto foi desenvolvido com foco na simplicidade e eficiência. A estrutura do projeto é fácil de entender e modificar, tornando-o flexível para futuras melhorias ou alterações. A cobertura de testes garante que o código funcione como esperado e facilita a detecção de quaisquer regressões que possam ocorrer durante o desenvolvimento.