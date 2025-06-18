# Design e Arquitetura de Software

## Aula 26/02/2025
**Livro: Engenharia de Software Moderna - Capítulo 7**  
[Link para o capítulo](https://engsoftmoderna.info/cap7.html)

Quando falamos em arquitetura de software, estamos lidando com o planejamento de alto nível de um sistema. O foco vai além da organização de classes individuais e passa a considerar unidades maiores, como pacotes, componentes, módulos, subsistemas, camadas ou serviços. A arquitetura não se limita a decisões de alto nível, mas também abrange a organização de elementos mais próximos do código, como pacotes, classes, banco de dados, dispositivos, casos de uso e outros.

- **Pacote**: Agrupamento de elementos que trabalham juntos para um propósito comum. É a forma como os arquivos são organizados e interagem entre si.
- **Componentes de Software**: Bibliotecas ou ferramentas reutilizáveis que evitam a necessidade de programar tudo do zero.
- **Módulos**: Aplicações separadas que se comunicam por meio de interfaces (APIs).
- **Serviços**: Camadas que contêm lógica importante para realizar tarefas específicas.
- **Interface**: Meio de comunicação entre componentes de software (ex.: APIs).
- **Camadas**: Divisões como "front-end", "back-end" e "banco de dados".

---

## Aula 27/02/2025
Os padrões arquiteturais ajudam a organizar sistemas de software em um nível mais alto, definindo módulos principais e suas relações. Essas relações podem, por exemplo, restringir ou permitir que um módulo utilize os serviços de outro. É comum combinar diferentes padrões arquiteturais para atender às necessidades do projeto.

- **Arquitetura em Camadas**: Uma das mais tradicionais, separa responsabilidades entre front-end, back-end e banco de dados.
- **Model-View-Controller (MVC)**:
  - **Model**: Representa os dados persistidos.
  - **View**: Responsável por desenhar a interface.
  - **Controller**: Gerencia a lógica e a interação entre a View e o Model.
  - O objetivo do MVC é separar responsabilidades, evitando que a lógica fique concentrada na View ou no Controller.
- **Microsserviços**: Partes do software que realizam tarefas específicas, geralmente expostas por APIs REST. Mesmo microsserviços podem adotar camadas, mostrando que diferentes arquiteturas podem coexistir.
- **Monolito**: O oposto dos microsserviços. Todo o código está em um único repositório, utilizando uma única tecnologia, com deploy e execução como um único sistema.

---

## Aula 05/03/2025
**Padrão vs Estilo Arquitetural**  
- **Padrão**: Solução para problemas específicos (ex.: MVC).  
- **Estilo**: Organização geral do código ou projeto.

- **Big Ball of Mud**: Falta de padrões arquiteturais, resultando em um sistema desorganizado.
- **Arquitetura em Camadas**:
  - **Camada de Apresentação**: Interface gráfica.
  - **Camada de Lógica de Negócio**: Centraliza regras e escalabilidade.
  - **Camada de Persistência**: Gerencia o banco de dados.

---

## Aula 06/03/2025
**Artigo: Who Needs an Architect?**  
[Link para o artigo](https://martinfowler.com/ieeeSoftware/whoNeedsArchitect.pdf)

- **O que é arquitetura?**  
  É um entendimento compartilhado entre os desenvolvedores sobre como o sistema é dividido em componentes e como eles se comunicam.

- **Tipos de Arquitetos**:
  - **Arquiteto "Matrix"**: Centraliza decisões e tenta impor regras rígidas.
  - **Arquiteto Ideal**: Colabora com a equipe, monitora o sistema e resolve problemas antes que eles aconteçam. Atua como um guia, explicando o sistema para todos os envolvidos.

---

## Aula 03/04/2025
**Filas (Queues)**  
- Funcionam no modelo FIFO (First In, First Out).  
- Permitem desacoplar componentes temporais da aplicação.  
- Acumulam mensagens em caso de falhas.  
  - **Producer**: Gera mensagens.  
  - **Consumer**: Consome mensagens.

---

## Aula 09/04/2025
**Fundamentos da Arquitetura de Software - Capítulo 4**  

- **Backup**: Mais barato, mas lento para restaurar.  
- **Réplica**: Recuperação rápida, porém mais cara e sujeita a desincronização.

**Características da Arquitetura**:
- **Operacionais**: Desempenho, escalabilidade, elasticidade, disponibilidade e confiabilidade.  
- **Estruturais**: Modularidade, acoplamento controlado, código legível.  
- **Transversais**: Restrições externas, como requisitos legais.

**Trade-offs**: Decisões arquiteturais sempre envolvem compromissos. Ex.: Aumentar a segurança pode impactar o desempenho.

---

# Aula – 23/04

## Bola de Lama

Refere-se a sistemas desenvolvidos sem uma arquitetura definida. Esse modelo, embora comum, tende a ter um alto custo de manutenção e apresenta sérias dificuldades para evoluir ou escalar com o tempo.

## Débito Técnico

São melhorias ou ajustes que deveriam ter sido feitos, mas foram deixados de lado por motivos como prazos apertados, mudanças de escopo ou decisões conscientes de priorização.

## Arquitetura Cliente/Servidor

- Um modelo tradicional onde o cliente consome dados e serviços fornecidos por um servidor.
- Exemplo: aplicações desktop conectadas diretamente a um banco de dados — facilita o controle de concorrência e a troca de informações.
- Também se aplica ao modelo navegador/servidor web.
- Estrutura em três camadas (apresentação, negócio, dados) é comum nesse tipo de arquitetura.

## Arquiteturas Monolíticas

- Toda a lógica do sistema está em um único projeto ou repositório.
- Pode ser organizada em camadas, pipelines ou adotar o estilo microkernel.

## Arquiteturas Distribuídas

- O sistema é composto por partes menores que interagem entre si.
- Tipos comuns:
  - Arquitetura baseada em serviços
  - Arquitetura orientada a eventos
  - Arquitetura baseada em espaços (tuplespaces)
  - Arquitetura orientada a serviços (SOA)
  - Microsserviços

## Falácias de Sistemas Distribuídos

- A rede é confiável
- A latência é zero
- A largura de banda é infinita
- A topologia da rede nunca muda

## Desafios em Sistemas Distribuídos

- Logs distribuídos: registrar eventos de diferentes serviços.
- Transações distribuídas: manter a consistência quando múltiplos serviços estão envolvidos.
- Manutenção e versionamento: garantir que mudanças em um serviço não afetem negativamente os demais.

# Resumo de Arquiteturas

## Arquitetura em Camadas

- A estrutura da organização pode influenciar na forma como o software é desenhado.
- Promove a separação de responsabilidades.
- As camadas devem interagir apenas com suas vizinhas imediatas.
- Com o tempo, pode se tornar difícil de manter se não for bem gerenciada.

## Arquitetura Pipeline

- Baseia-se em uma cadeia de programas, onde a saída de um é usada como entrada do próximo.
- Exemplo: MapReduce
  - `Map`: aplica transformações a todos os elementos.
  - `Reduce`: agrega os dados transformados.
- Utiliza padrões como:
  - STDIN / STDOUT
  - Programas produtores (geram dados)
  - Filtros (por exemplo, `grep`)
  - Transformadores (modificam o formato dos dados)
  - Consumidores (armazenam os dados finais)
- Aplicações práticas incluem sistemas de streaming e ferramentas como Apache Kafka.
- Ferramentas visuais como Node-RED seguem esse modelo.

## Arquitetura Microkernel

- Um sistema com núcleo central e componentes que podem ser conectados conforme necessário.
- Exemplos incluem IDEs com plugins, navegadores com extensões e sistemas Java com bibliotecas externas.
- Segue o princípio de depender de abstrações em vez de implementações.
- Baseia-se em contratos bem definidos para permitir a extensão da funcionalidade sem modificar o núcleo.

---

## Aula 24/04/2025
**Estilo de Arquitetura em Camadas**  
- **Lei de Conway**: A estrutura do software reflete a organização da empresa.  
- Idealmente, cada camada deve se comunicar apenas com a adjacente.  
  - Exemplo: Apresentação → Comercial → Persistência → Banco de Dados.  
- Com o crescimento do software, é comum que essa separação se deteriore, prejudicando o sistema.

---

# Aula – 28/05

## Arquitetura em Serviços

- Organização do sistema em aplicações que refletem o domínio do problema.
- Essas aplicações podem ser reutilizadas em diferentes contextos e são agrupadas com base em suas responsabilidades de negócio, não por critérios técnicos.

# Aula – 29/05

## Arquitetura de Microsserviços

- O sistema é composto por pequenos serviços independentes, cada um com um domínio bem definido.
- Cada microsserviço deve lidar apenas com suas regras de negócio específicas.
- Essa abordagem favorece o desacoplamento e facilita a composição e reutilização de lógica.

### Considerações

- Os microsserviços devem manter o isolamento de dados.
- A coordenação entre as equipes é essencial para garantir a integridade do sistema.
- A comunicação entre os microsserviços é crucial para garantir que o sistema como um todo funcione corretamente.

### Vantagens

- Alta escalabilidade
- Elasticidade: o sistema pode se adaptar facilmente a variações de carga
